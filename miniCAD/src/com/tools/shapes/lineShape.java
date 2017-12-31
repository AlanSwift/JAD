package com.tools.shapes;

import com.constant.constants;
import com.tools.*;

import javax.swing.*;
import java.awt.*;

public class lineShape extends basicShape{

    public lineShape()
    {
        mType= constants.LINE;
    }
    @Override
    public void draw(Graphics2D g2)
    {
        g2.setColor(mColor);
        g2.setStroke(new BasicStroke(lineSize));
        g2.drawLine(x1,y1,x2,y2);
    }

    /**
     * @param Buffer buffer is a z-buffer, to make a index tag on the bitmap
     * @param index index is the tag
     */
    @Override
    public void fill(Integer[][] Buffer,int index) {
        try{
            if(x1==x2&&y1==y2)
            {
                return;
            }
            int stepY=(y1>y2)?-1:1;
            int stepX=(x1>x2)?-1:1;

            System.out.println("in fill line: "+index);

            for(int i=y1;i!=y2;i+=stepY)
            {
                for(int j=x1;j!=x2;j+=stepX)
                {
                    if(isInner((double)j,(double)i,(double)mBias))
                    {
                        Buffer[i][j]=index;
                        //System.out.println(""+i+" , "+j);
                        //myPanel.sBuffer[i][j]=index;
                    }

                }
            }
        }catch (IndexOutOfBoundsException e)
        {
            JOptionPane.showMessageDialog(null,"Buffer out of range","Fatal Error",JOptionPane.ERROR_MESSAGE);

        }
    }

    /**
     * @param x x is the x-coordinate of a point
     * @param y y is the y-coordinate of a point
     * @param bias bias is the offset
     * @return whether the point is in the shape
     */
    @Override
    public boolean isInner(double x, double y,double bias) {
        // Ax+By+C=0
        double A=y2-y1;
        double B=x1-x2;
        double C=x2*y1-x1*y2;
        if(A==0&&B==0)  return false;

        double distance=(A*x+B*y+C)/Math.sqrt(A*A+B*B);
        //System.out.println("distance:" +distance);
        return Math.abs(distance)<Math.abs(bias);

    }

    /**
     * @return a cloned object
     */
    @Override
    public Object clone() throws CloneNotSupportedException {

        return super.clone();
    }

    /**
     * @param x x is x-axis's coordinate
     * @param y y is y-axis's coordinate
     * @return which location of the shape, used for resize
     */
    @Override
    public int judgeLoc(int x, int y) {
        if(Math.sqrt(Math.pow(x-x1,2)+Math.pow(y-y1,2))<mBias)
        {
            if(x1<x2&&y1<y2)
            {
                return constants.NORTH_WEST;
            }
            else if(x1<x2&&y1>y2)
            {
                return constants.SOUTH_WEST;
            }
            else if(x1>x2&&y1<y2)
            {
                return constants.NORTH_EAST;
            }
            else if(x1>x2&&y1>y2)
            {
                return constants.SOUTH_EAST;
            }
            else if(x1==x2&&y1<y2)
            {
                return constants.NORTH;
            }
            else if(x1==x2&&y1>y2)
            {
                return constants.SOUTH;
            }
            else if(x1<x2&&y1==y2)
            {
                return constants.WEST;
            }
            else if(x1>x2&&y1==y2)
            {
                return constants.EAST;
            }
        }
        else if(Math.sqrt(Math.pow(x-x2,2)+Math.pow(y-y2,2))<mBias)
        {
            if(x2<x1&&y2<y1)
            {
                return constants.NORTH_WEST;
            }
            else if(x2>x1&&y2<y1)
            {
                return constants.NORTH_EAST;
            }
            else if(x2<x1&&y2>y1)
            {
                return constants.SOUTH_WEST;
            }
            else if(x2>x1&&y2>y1)
            {
                return constants.SOUTH_EAST;
            }
            else if(x1==x2&&y2>y1)
            {
                return constants.SOUTH;
            }
            else if(x1==x2&&y2<y1)
            {
                return constants.NORTH;
            }
            else if(x1<x2&&y1==y2)
            {
                return constants.EAST;
            }
            else if(x1>x2&&y1==y2)
            {
                return constants.WEST;
            }
        }
        return constants.OTHER_LOC;
    }

    /**
     * @param prex old x
     * @param prey old y
     * @param x    new x
     * @param y    new y
     * @param loc  the location user click
     */
    @Override
    public void reSize(int prex, int prey, int x, int y, int loc) {
        if(Math.sqrt(Math.pow(prex-x1,2)+Math.pow(prey-y1,2))<mBias)
        {
            x1=x;y1=y;
        }
        else if(Math.sqrt(Math.pow(prex-x2,2)+Math.pow(prey-y2,2))<mBias)
        {
            x2=x;y2=y;
        }
    }


}
