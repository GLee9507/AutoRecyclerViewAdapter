package com.glee.autorecyclerviewadapter.core;


import java.util.LinkedHashMap;
import java.util.List;

public class AutoBinder<T> implements AutoItem {
    private final ObservableArrayList<T> data = new ObservableArrayList<>();
    private int layoutId;
    private int brId;
    private List<Header> headers;
    private List<Footer> footers;


    public int getCount() {
        return getItemCount() + getHeaderCount() + getFooterCount();
    }

    public int getItemCount() {
        return data.size();
    }

    public int getHeaderCount() {
        return headers == null ? 0 : headers.size();
    }

    public int getFooterCount() {
        return footers == null ? 0 : footers.size();
    }

    @Override
    public int getLayoutId() {
        return layoutId;
    }

    @Override
    public int getBrId() {
        return brId;
    }

    public ObservableArrayList<T> getData() {
        return data;
    }

    public AutoBinder(int layoutId, int brId) {
        this.layoutId = layoutId;
        this.brId = brId;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public List<Footer> getFooters() {
        return footers;
    }
}
