package com.tools.shapes;

import java.awt.*;
import com.constant.constants;
import org.omg.CORBA.MARSHAL;

public class rectangleShape extends basicShape{

    @Override
    public void draw(Graphics2D g2) {
        super.draw(g2);
        g2.setColor(mColor);
        g2.setStroke(new BasicStroke(lineSize));
        g2.drawRect(Math.min(x1,x2),Math.min(y1,y2),Math.abs(x1-x2),Math.abs(y1-y2));
    }

    /**
     * @param Buffer buffer is a z-buffer, to make a index tag on the bitmap
     * @param index  index is the tag
     */
    @Override
    public void fill(Integer[][] Buffer, int index) {
        //four line to fill
        //1 x1, y1->y2
        int ySmall=Math.min(y1,y2);
        int yBig=Math.max(y1,y2);
        int xSmall=Math.min(x1,x2);
        int xBig=Math.max(x1,x2);
        for(int i=Math.max(0,xSmall-mBias);i<=Math.min(constants.PANEL_HEIGHT-1,xSmall+mBias);i++)
        {
            for(int j=Math.max(ySmall-mBias,0);j<=Math.min(constants.PANEL_WIDTH-1,yBig+mBias);j++)
            {
                Buffer[j][i]=index;
            }
        }
        for(int i=Math.max(0,xBig-mBias);i<=Math.min(constants.PANEL_HEIGHT-1,xBig+mBias);i++)
        {
            for(int j=Math.max(ySmall-mBias,0);j<=Math.min(constants.PANEL_WIDTH-1,yBig+mBias);j++)
            {
                Buffer[j][i]=index;
            }
        }
        for(int i=Math.max(0,ySmall-mBias);i<=Math.min(constants.PANEL_WIDTH-1,ySmall+mBias);i++)
        {
            for(int j=Math.max(xSmall-mBias,0);j<=Math.min(constants.PANEL_HEIGHT-1,xBig+mBias);j++)
            {
                Buffer[i][j]=index;
            }
        }
        for(int i=Math.max(yBig-mBias,0);i<=Math.min(constants.PANEL_WIDTH-1,yBig+mBias);i++)
        {
            for(int j=Math.max(xSmall-mBias,0);j<=Math.min(constants.PANEL_HEIGHT-1,xBig+mBias);j++)
            {
                Buffer[i][j]=index;
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

        return false;
    }

    /**
     * @param x x is x-axis's coordinate
     * @param y y is y-axis's coordinate
     * @return which location of the shape, used for resize
     */
    @Override
    public int judgeLoc(int x, int y) {
        reArange();
        //  x1 y1
        //    x2 y2
        if(Math.sqrt(Math.pow(x-x1,2)+Math.pow(y-y1,2))<mBias)
        {
            return constants.NORTH_WEST;
        }
        else if(Math.sqrt(Math.pow(x-x2,2)+Math.pow(y-y1,2))<mBias)
        {
            return constants.NORTH_EAST;
        }
        else if(Math.sqrt(Math.pow(x-x1,2)+Math.pow(y-y2,2))<mBias)
        {
            return constants.SOUTH_WEST;
        }
        else if(Math.sqrt(Math.pow(x-x2,2)+Math.pow(y-y2,2))<mBias)
        {
            return constants.SOUTH_EAST;
        }


        return constants.OTHER_LOC;
    }

    /**
     * @param prex old x
     * @param prey old y
     * @param x    new x
     * @param y    new y
     * @param oldloc  the location user click
     */
    @Override
    public void reSize(int prex, int prey, int x, int y, int oldloc) {

        int loc=judgeLoc(prex,prey);
        if(loc==constants.NORTH_WEST)
        {
            x1=x;y1=y;
        }
        else if(loc==constants.NORTH_EAST)
        {
            x2=x;
            y1=y;
        }
        else if(loc==constants.SOUTH_WEST)
        {
            x1=x;
            y2=y;
        }
        else if(loc==constants.SOUTH_EAST)
        {
            x2=x;y2=y;
        }

    }


}
