package com.glee.autorecyclerviewadapter.core;


import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

public class AutoHolder extends RecyclerView.ViewHolder {
    private final ViewDataBinding binding;
    private final int brId;

    public AutoHolder(AutoAdapter adapter,
                      @NonNull ViewDataBinding binding,
                      int brId, boolean isItem) {
        super(binding.getRoot());
        this.binding = binding;
        this.brId = brId;
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
        binding.setVariable(brId, o);
        binding.executePendingBindings();
    }
}
