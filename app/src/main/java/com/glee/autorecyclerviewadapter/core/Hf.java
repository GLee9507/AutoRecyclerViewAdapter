package com.glee.autorecyclerviewadapter.core;

public class Hf implements AutoItem{
    private final String key;
    private final int layoutId;
    private final int brId;
    private Object data;

    public Hf(String key, int layoutId, int brId, Object data) {
        this.key = key;
        this.layoutId = layoutId;
        this.brId = brId;
        this.data = data;
    }

    public Hf(String key, int layoutId, int brId) {
        this.key = key;
        this.layoutId = layoutId;
        this.brId = brId;
    }

    public String getKey() {
        return key;
    }


    @Override
    public int getLayoutId() {
        return layoutId;
    }


    @Override
    public int getBrId() {
        return brId;
    }


    public <T>T getData() {
        return (T) data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
