package com.tools;

import com.tools.shapes.basicShape;

import java.awt.*;
import java.io.Serializable;


public class apply implements Cloneable,Serializable{
    private int mIndex;
    private String mContent="";
    private boolean isEditAble=false;

    private basicShape mShape;

    public apply(basicShape i)
    {
        mShape=i;
    }
    public void draw(Graphics2D g)
    {
        mShape.draw(g);
    }

    public void fill(Integer [][] buffer)
    {
        mShape.fill(buffer,mIndex);
    }


    public int getIndex() {
        return mIndex;
    }

    public void setIndex(int index) {
        mIndex = index;
    }

    public void setShape(basicShape t)
    {
        mShape=t;
    }
    public basicShape getShape()
    {
        return mShape;
    }
    public void setPonit1(int x,int y)
    {
        mShape.x1=x;
        mShape.y1=y;
    }
    public void setPoint2(int x,int y)
    {
        mShape.x2=x;
        mShape.y2=y;
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        apply newApply=(apply) super.clone();
        newApply.mShape=(basicShape) mShape.clone();
        return newApply;
    }

    public boolean ifEditAble()
    {
        if(mShape!=null)
        {
            return mShape.ifEditAble();
        }
        return false;
    }


}
