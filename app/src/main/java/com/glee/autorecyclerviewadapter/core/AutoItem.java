package com.glee.autorecyclerviewadapter.core;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

public interface AutoItem {
    @LayoutRes
    int getLayoutId();

    @IdRes
    int getBrId();
}
