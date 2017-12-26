package com.shapes;

import com.constant.constants;

import java.awt.*;

public class lineShape extends basicShape{
    public lineShape()
    {
        mType= constants.LINE;
    }
    @Override
    public void draw(Graphics g)
    {
        g.setColor(mColor);
        g.drawLine(x1,y1,x2,y2);
    }

}
