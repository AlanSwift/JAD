package com.tools.shapes;

import java.awt.*;
import com.constant.constants;
import com.tools.extendLib.pair;

public class ovalShape extends basicShape {


    private pair<Integer,Integer> north=new pair<>();
    private pair<Integer,Integer> south=new pair<>();
    private pair<Integer,Integer> west=new pair<>();
    private pair<Integer,Integer> east=new pair<>();

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        g2.setColor(mColor);
        g2.setStroke(new BasicStroke(lineSize));
        g2.drawOval(Math.min(x1,x2),Math.min(y1,y2),Math.abs(x1-x2),Math.abs(y1-y2));
    }

    /**
     * @param Buffer buffer is a z-buffer, to make a index tag on the bitmap
     * @param index  index is the tag
     */
    @Override
    public void fill(Integer[][] Buffer, int index) {
        int xSmall=Math.min(x1,x2);
        int xBig=Math.max(x1,x2);
        int ySmall=Math.min(y1,y2);
        int yBig=Math.max(y1,y2);

        for(int i=Math.max(0,xSmall-mBias);i<=Math.min(constants.PANEL_HEIGHT-1,xBig+mBias);i++)
        {
            for(int j=Math.max(0,ySmall-mBias);j<=Math.min(constants.PANEL_WIDTH-1,yBig+mBias);j++)
            {
                if (isInner(i,j,0.1))
                {
                    Buffer[j][i]=index;
                }
            }
        }
    }

    /**
     * @param x    x is the x-coordinate of a point
     * @param y    y is the y-coordinate of a point
     * @param bias bias is the offset
     * @return whether the point is in the shape
     */
    @Override
    public boolean isInner(double x, double y, double bias) {
        double A=Math.abs(x1-x2)/2.0;
        double B=Math.abs(y1-y2)/2.0;
        double centerX=(x1+x2)/2.0;
        double centerY=(y1+y2)/2.0;
        return Math.abs(1-(Math.pow(x - centerX, 2) / (A * A) + Math.pow(y - centerY, 2) / (B * B) ))<bias;
    }

    @Override
    public void reArange()
    {
        super.reArange();
        north.setFirst((x1+x2)/2);
        north.setSecond(y1);
        south.setFirst((x1+x2)/2);
        south.setSecond(y2);
        west.setFirst(x1);
        west.setSecond((y1+y2)/2);
        east.setFirst(x2);
        east.setSecond((y1+y2)/2);
    }

    /**
     * @param x x is x-axis's coordinate
     * @param y y is y-axis's coordinate
     * @return which location of the shape, used for resize
     */
    @Override
    public int judgeLoc(int x, int y) {
        reArange();
        mBias=4;
        if(Math.sqrt(Math.pow(x-north.getFirst(),2)+Math.pow(y-north.getSecond(),2))<mBias)
        {
            return constants.NORTH;
        }
        else if(Math.sqrt(Math.pow(x-south.getFirst(),2)+Math.pow(y-south.getSecond(),2))<mBias)
        {
            return constants.SOUTH;
        }
        else if(Math.sqrt(Math.pow(x-west.getFirst(),2)+Math.pow(y-west.getSecond(),2))<mBias)
        {
            return constants.WEST;
        }
        else if(Math.sqrt(Math.pow(x-east.getFirst(),2)+Math.pow(y-east.getSecond(),2))<mBias)
        {
            return constants.EAST;
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
        int oldloc=judgeLoc(prex,prey);
        if(oldloc==constants.NORTH)
        {
            x2=2*x-x1;
            y1=y;
        }
        else if(oldloc==constants.SOUTH)
        {
            x2=2*x-x1;
            y2=y;
        }
        else if(oldloc==constants.WEST)
        {
            x1=x;
            y2=2*y-y1;
        }
        else if(oldloc==constants.EAST)
        {
            x2=x;
            y2=2*y-y1;
        }
    }


}
