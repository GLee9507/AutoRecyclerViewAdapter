package com.glee.autorecyclerviewadapter.core;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author liji
 * @date 2019/2/13 11:54
 * description
 */


public class BindingAdapter {
    @androidx.databinding.BindingAdapter("app:bind")
    public static void bind(RecyclerView recyclerView, AutoBinder binder) {
        if (recyclerView.getAdapter()==null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext().getApplicationContext()));
            recyclerView.setAdapter(new AutoAdapter(binder));
        }
    }
}
