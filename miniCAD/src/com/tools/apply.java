package com.tools;

import com.constant.constants;
import com.shapes.basicShape;

import java.awt.*;



public class apply {
    private int mIndex;
    private String mContent="";

    private basicShape mShape;

    public apply(basicShape i)
    {
        mShape=i;
    }
    public void draw(Graphics g)
    {
        mShape.draw(g);
    }


    public int getIndex() {
        return mIndex;
    }

    public void setIndex(int index) {
        mIndex = index;
    }



}
