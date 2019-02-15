package com.glee.autorecyclerviewadapter.core;


import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;

public interface AutoItem {
    @LayoutRes
    int getLayoutId();

    @IdRes
    int getBrId();


}
