package com.wen.love.util;

import java.util.ArrayList;
import java.util.List;

public class GeneralDto<T> extends PageDto{

    private List<T>  items = new ArrayList<T>();
    private Object item;
    private String retCode;
    private String retMsg;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }
}
