package com.tools.shapes;

import java.awt.*;
import java.io.Serializable;
import java.util.Map;

import com.constant.constants;
import com.tools.myPanel;

public abstract class basicShape implements Cloneable,Serializable {
    protected float lineSize=1.0f;
    public int x1,x2,y1,y2;
    public int middleX,middleY;
    int mType=constants.NONE;
    protected Color mColor=Color.BLACK;
    protected  int mBias=constants.BIAS;
    protected boolean mEditAble=false;

    public int getIndex() {
        return mIndex;
    }

    public void setIndex(int index) {
        mIndex = index;
    }

    protected int mIndex;

    public void setColor(Color mColor)
    {
        this.mColor=mColor;
    }
    public void draw(Graphics2D g2)
    {
        reArange();
    }
    /**
       @param Buffer buffer is a z-buffer, to make a index tag on the bitmap
       @param index index is the tag
     */
    public abstract void fill(Integer[][] Buffer,int index);

    /**
      @param x x is the x-coordinate of a point
      @param y y is the y-coordinate of a point
      @param bias bias is the offset
      @return whether the point is in the shape
     */
    public abstract boolean isInner(double x,double y,double bias);

    /**
     * @return a cloned object
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void calcMiddle()
    {
        middleX=(x1+x2)/2;
        middleY=(y1+y2)/2;
    }

    /**
     *
     * @param deltaX the movement of x-axis
     * @param deltaY the movement of y-axis
     */
    public void move(int deltaX,int deltaY)
    {
        int xx1=x1+deltaX;
        int xx2=x2+deltaX;
        int yy1=y1+deltaY;
        int yy2=y2+deltaY;
        if(xx1>=0&&xx1<constants.PANEL_WIDTH&&
                xx2>=0&&xx2<constants.PANEL_WIDTH&&
                yy1>=0&&yy1<constants.PANEL_HEIGHT&&
                yy2>=0&&yy2<constants.PANEL_HEIGHT)
        {
            x1=xx1;y1=yy1;x2=xx2;y2=yy2;
        }
    }
    protected  void reArange()
    {
        int xSmall=Math.min(x1,x2);
        int xBig=Math.max(x1,x2);
        int ySmall=Math.min(y1,y2);
        int yBig=Math.max(y1,y2);
        x1=xSmall;
        y1=ySmall;
        x2=xBig;
        y2=yBig;
    }

    /**
     *
     * @param x x is x-axis's coordinate
     * @param y y is y-axis's coordinate
     * @return which location of the shape, used for resize
     */
    public abstract int judgeLoc(int x,int y);

    /**
     *
      * @param prex old x
     * @param prey old y
     * @param x new x
     * @param y new y
     * @param loc the location user click
     */
    public abstract void reSize(int prex,int prey,int x,int y,int loc);

    public boolean ifEditAble()
    {
        return mEditAble;
    }

    /**
     * called to edit the shape, but it should be editable
     *
     */
    public void edit()
    {

    }

    public void stopEdit()
    {

    }
    public void makeChop()
    {
        lineSize= Math.min(5,lineSize+1);
    }
    public void makeThin()
    {
        lineSize=Math.max(1,lineSize-1);
    }
}
