package com.glee.autorecyclerviewadapter.core;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class AutoHolder extends RecyclerView.ViewHolder {
    private final ViewDataBinding binding;
    private final int brId;

    public AutoHolder(@NonNull ViewDataBinding binding, int brId) {
        super(binding.getRoot());
        this.binding = binding;
        this.brId = brId;
    }

    public void bind(Object o) {
        binding.setVariable(brId, o);
        binding.executePendingBindings();
    }
}
