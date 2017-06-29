package com.tim.gaea2.web.models;

/**
 * Created by Tim on 2017/6/29.
 */
public class BasePagedReqModel {
    private int start;
    private int length;
    private int draw;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }
}
