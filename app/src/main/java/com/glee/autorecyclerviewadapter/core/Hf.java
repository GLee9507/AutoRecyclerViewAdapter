package com.glee.autorecyclerviewadapter.core;

public class Hf implements AutoItem{
    private String key;
    private int layoutId;
    private int brId;
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

    public void setKey(String key) {
        this.key = key;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public int getBrId() {
        return brId;
    }

    public void setBrId(int brId) {
        this.brId = brId;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
