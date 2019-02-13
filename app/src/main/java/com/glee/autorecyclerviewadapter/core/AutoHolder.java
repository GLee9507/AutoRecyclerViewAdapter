package com.glee.autorecyclerviewadapter.core;

import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class AutoHolder extends RecyclerView.ViewHolder {
    private final ViewDataBinding binding;
    private final int brId;
    private final AutoAdapter adapter;
    private int position = -1;

    public AutoHolder(AutoAdapter adapter,
                      @NonNull ViewDataBinding binding,
                      int brId, boolean isItem) {
        super(binding.getRoot());
        this.binding = binding;
        this.brId = brId;
        this.adapter = adapter;
        if (isItem) {
            OnItemClickListener onItemClickListener = adapter.binder.getOnItemClickListener();
            OnItemLongClickListener onItemLongClickListener = adapter.binder.getOnItemLongClickListener();
            if (onItemClickListener != null) {
                itemView.setOnClickListener(v -> onItemClickListener.onItemClick(getLayoutPosition() - adapter.binder.getHeaderCount()));
            }

            if (onItemLongClickListener != null) {
                itemView.setOnLongClickListener(v -> onItemLongClickListener.onItemLongClick(getLayoutPosition() - adapter.binder.getHeaderCount()));
            }
        }
    }

    public void bind(Object o) {
        position = getLayoutPosition();
        binding.setVariable(brId, o);
        binding.executePendingBindings();
    }
}
