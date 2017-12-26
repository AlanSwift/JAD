package com.shapes;

import java.awt.*;
import com.constant.constants;

public abstract class basicShape {
    public int x1,x2,y1,y2;
    public int mType=constants.NONE;
    protected Color mColor=Color.BLACK;
    private String mContent="";

    public void setColor(Color mColor)
    {
        this.mColor=mColor;
    }
    public abstract void draw(Graphics g);
}
