package com.tools.shapes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import com.constant.constants;

import com.miniCAD;

public class stringShape extends basicShape {
    private JTextField mJTextField=new JTextField();
    private JLabel mJLabel=new JLabel();
    private String mContent=new String();




    class myTextFileldListener implements KeyListener
    {

        /**
         * Invoked when a key has been typed.
         * See the class description for {@link KeyEvent} for a definition of
         * a key typed event.
         *
         * @param e
         */
        @Override
        public void keyTyped(KeyEvent e) {
            //System.out.println(e.getKeyChar());
            if(e.getKeyChar()==KeyEvent.VK_ENTER)
            {
                mJTextField.setEnabled(false);
                mJTextField.setOpaque(false);
                mJTextField.setVisible(false);
                mJLabel.setText(mJTextField.getText());
                mJLabel.setVisible(true);

                miniCAD.getMyPanel().repaint();
                System.out.println("finish text");
            }
        }

        /**
         * Invoked when a key has been pressed.
         * See the class description for {@link KeyEvent} for a definition of
         * a key pressed event.
         *
         * @param e
         */
        @Override
        public void keyPressed(KeyEvent e) {

        }

        /**
         * Invoked when a key has been released.
         * See the class description for {@link KeyEvent} for a definition of
         * a key released event.
         *
         * @param e
         */
        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    @Override
    public void draw(Graphics2D g2)
    {
        super.draw(g2);
        //mJTextField.setText(mContent);
        mJTextField.setName("textfield"+mIndex);
        mJTextField.setSize(new Dimension(Math.abs(x1-x2),Math.abs(y1-y2)));
        mJTextField.setLocation(x1,y1);
//        if(mJTextField.getSelectedText()!=null&&mJTextField.getSelectedText().equals("")&&!mJTextField.getText().equals(""))
//        {
//            mJTextField.setCaretColor(mColor);
//        }
//        else{
//            mJTextField.setSelectedTextColor(mColor);
//        }
        mJTextField.setForeground(mColor);
        mJLabel.setForeground(mColor);
        mJLabel.setName("label"+mIndex);
        mJLabel.setSize(new Dimension(Math.abs(x1-x2),Math.abs(y1-y2)));
        mJLabel.setLocation(x1,y1);
        mJLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

//        ystem.out.println("numbers:"+mJTextField.getKeyListeners().length);
        if(mJTextField.getKeyListeners().length==0)
        {
            mJTextField.addKeyListener(new myTextFileldListener());
        }
        if(miniCAD.getMyPanel().getComponentByName(mJTextField.getName())==null)
        {
            miniCAD.getMyPanel().add(mJTextField,mJTextField.getName());
        }
        if(miniCAD.getMyPanel().getComponentByName(mJLabel.getName())==null)
        {
            miniCAD.getMyPanel().add(mJLabel,mJLabel.getName());
            mJLabel.setVisible(false);
        }
    }

    public stringShape()
    {
        mEditAble=true;
    }

    /**
     * @param Buffer buffer is a z-buffer, to make a index tag on the bitmap
     * @param index  index is the tag
     */
    @Override
    public void fill(Integer[][] Buffer, int index) {
        reArange();
        for(int i=y1;i<=y2;i++)
        {
            for(int j=x1;j<=x2;j++)
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        stringShape newShape=(stringShape) super.clone();
        newShape.mJTextField=new JTextField();
        return newShape;
    }

    @Override
    public void edit()
    {
        mJLabel.setVisible(false);
        mJTextField.setEnabled(true);
        mJTextField.setVisible(true);
    }

    @Override
    public void stopEdit()
    {
        mJTextField.setEnabled(false);
        mJTextField.setVisible(false);
        mJLabel.setText(mJTextField.getText());
        mJLabel.setVisible(true);
    }

    public void setTextSize(int size)
    {
        String font=mJTextField.getFont().getFontName();
        mJTextField.setFont(new Font(font,Font.PLAIN,size));
        mJLabel.setFont(new Font(font,Font.PLAIN,size));
    }


}
