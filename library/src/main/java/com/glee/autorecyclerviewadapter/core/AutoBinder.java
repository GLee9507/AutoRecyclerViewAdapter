package com.glee.autorecyclerviewadapter.core;


import java.util.Objects;

import androidx.arch.core.util.Function;

public class AutoBinder<T> implements AutoItem {
    private final ObservableArrayList<T> data = new ObservableArrayList<>();
    private final int layoutId;
    private final int brId;
    private final ObservableArrayList<Header> headers = new ObservableArrayList<>();
    private final ObservableArrayList<Footer> footers = new ObservableArrayList<>();
    private OnItemLongClickListener onItemLongClickListener;
    private OnItemClickListener onItemClickListener;

    public int getCount() {
        return getItemCount() + getHeaderCount() + getFooterCount();
    }

    public int getItemCount() {
        return data.size();
    }

    public int getHeaderCount() {
        return headers.size();
    }

    public int getFooterCount() {
        return footers.size();
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

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public AutoBinder(int layoutId, int brId, OnItemClickListener onItemClickListener, OnItemLongClickListener onItemLongClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.onItemLongClickListener = onItemLongClickListener;
        this.layoutId = layoutId;
        this.brId = brId;
    }

    ObservableArrayList<Header> getHeaders() {
        return headers;
    }

    ObservableArrayList<Footer> getFooters() {
        return footers;
    }

    public void addHeader(Header header) {
        for (int i = 0; i < headers.size(); i++) {
            if (Objects.equals(header.getKey(), headers.get(i).getKey())) {
                headers.set(i, header);
                return;
            }
        }

        headers.add(header);
    }

    public void addFooter(Footer footer) {
        for (int i = 0; i < footers.size(); i++) {
            if (Objects.equals(footer.getKey(), footers.get(i).getKey())) {
                footers.set(i, footer);
                return;
            }
        }

        footers.add(footer);
    }

    public <H> void updateHeader(int index, Function<H, H> func) {
        Header header = headers.get(index);
        H data = header.getData();
        header.setData(func.apply(data));
        headers.set(index, header);
    }

    public <F> void updateFooter(int index, Function<F, F> func) {
        Footer footer = footers.get(index);
        F data = footer.getData();
        footer.setData(func.apply(data));
        footers.set(index, footer);
    }

    public <H> void updateHeader(String key, Function<H, H> func) {
        for (int i = 0; i < headers.size(); i++) {
            if (Objects.equals(key, headers.get(i).getKey())) {
                updateHeader(i, func);
                return;
            }
        }
    }

    public <F> void updateFooter(String key, Function<F, F> func) {
        for (int i = 0; i < footers.size(); i++) {
            if (Objects.equals(key, footers.get(i).getKey())) {
                updateFooter(i, func);
                return;
            }
        }
    }

    public Header removeHeader(int index) {
        return headers.remove(index);
    }

    public Header removeHeader(String key) {
        for (int i = 0; i < headers.size(); i++) {
            if (Objects.equals(key, headers.get(i).getKey())) {
                return removeHeader(i);
            }
        }
        return null;
    }

    public Footer removeFooter(int index) {
        return footers.remove(index);
    }

    public Footer removeFooter(String key) {
        for (int i = 0; i < footers.size(); i++) {
            if (Objects.equals(key, footers.get(i).getKey())) {
                return removeFooter(i);
            }
        }
        return null;
    }


}
