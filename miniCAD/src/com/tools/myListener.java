package com.tools;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

import com.constant.constants;
import com.miniCAD;
import com.tools.extendLib.Handler;
import com.tools.shapes.stringShape;

public final class myListener implements MouseListener,MouseMotionListener,KeyListener {

    private static int type=constants.CHOOSE;
    private static int state=constants.LEAVING;
    private static int preX,preY;
    private static boolean isDrawing=false;
    private static apply myApply;
    private static apply operOnApply;
    private static int preIndex;
    private static Handler sHandler=new Handler() {
        @Override
        public void sendMessage(HashMap<Object, Object> msg) {
            synchronized (this)
            {
                if(msg.get("return")!=null)
                {
                    Object which=msg.get("return");
                    System.out.println(which);
                    if(which instanceof Integer)
                    {
                        System.out.println(which);
                        switch ((int)which)
                        {
                            case constants.CALL_BACK_COLORCHANGED:
                            {
                                Color newColor=(Color) msg.get("color");
                                if(type==constants.CHOOSE&&operOnApply!=null)
                                {
                                    operOnApply.getShape().setColor(newColor);
                                    miniCAD.getMyPanel().repaint();
                                }
                                else{
                                    myApply.getShape().setColor(newColor);
                                    //operOnApply.getShape().setColor(newColor);
                                    miniCAD.getMyPanel().repaint();
                                }
                                break;
                            }
                            case constants.CALL_BACK_TEXTSIZECHANGED:
                            {
                                int textSize=(Integer)msg.get("size");
                                if(type==constants.CHOOSE&&operOnApply!=null)
                                {
                                    if(operOnApply.getShape() instanceof stringShape)
                                    {
                                        ((stringShape) operOnApply.getShape()).setTextSize(textSize);
                                        miniCAD.getMyPanel().repaint();
                                    }

                                }
                                else if(type==constants.TEXT)
                                {
                                    ((stringShape)myApply.getShape()).setTextSize(textSize);
                                    //((stringShape)operOnApply.getShape()).setTextSize(textSize);
                                    miniCAD.getMyPanel().repaint();
                                }
                                break;
                            }
                            case constants.CALL_BACK_MAKECHOP:
                            {
                                if(type==constants.CHOOSE&&operOnApply!=null)
                                {
                                    operOnApply.getShape().makeChop();
                                    miniCAD.getMyPanel().repaint();
                                }

                                break;
                            }
                            case constants.CALL_BACK_MAKETHIN:
                            {
                                if(type==constants.CHOOSE&&operOnApply!=null)
                                {
                                    operOnApply.getShape().makeThin();
                                    miniCAD.getMyPanel().repaint();
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
    };
    public static void setOperOnApply(apply tApply)
    {
        operOnApply=tApply;
    }
    public static Handler getHandler(){
        return sHandler;
    }

    public static void setMyApply(apply tApply)
    {
        myApply=tApply;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("click: "+e.getX()+", "+e.getY());
        if(e.getClickCount()==2&&type==constants.CHOOSE)
        {
            //double click
            if (operOnApply!=null)
            {
                if(operOnApply.ifEditAble())
                {
                    operOnApply.getShape().edit();
                    state=constants.EDITING;
                }
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("press");

        if(type==constants.CHOOSE) {
            int index = myPanel.getBuffer()[e.getY()][e.getX()];
            if(state==constants.EDITING&&preIndex!=index)
            {
                operOnApply.getShape().stopEdit();
                state=constants.LEAVING;
            }
            if (index != constants.NONE) {
                operOnApply = myPanel.moveToTop(index);

                int Loc = operOnApply.getShape().judgeLoc(e.getX(), e.getY());
                if (Loc == constants.OTHER_LOC) {
                    state = constants.MOVING;
                    preX = e.getX();
                    preY = e.getY();
                } else {
                    state = constants.RESIZING;
                    preX = e.getX();
                    preY = e.getY();
                }


            }
            preIndex=index;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("release");

        int xScreen=e.getX();
        int yScreen=e.getY();
        System.out.println("clickfuck: "+xScreen+", "+yScreen);
        if(xScreen<0)   return;
        if(type==constants.CHOOSE)
        {
            System.out.println(myPanel.getBuffer()[yScreen][xScreen]);
            //myPanel.debug();
        }
        else
        {
            if(isDrawing)
            {
                System.out.println("2*****^^^");
                myApply.setPoint2(xScreen,yScreen);
                try {
                    myPanel.addApply(myApply);
                } catch (CloneNotSupportedException e1) {
                    e1.printStackTrace();
                }
                miniCAD.getMyPanel().repaint();
                myApply.fill(myPanel.getBuffer());
                operOnApply=myPanel.getApplyByIndex(myApply.getIndex());

                isDrawing=false;
            }
            else{
                System.out.println("1*****");
                myApply.setPonit1(xScreen,yScreen);
                isDrawing=true;
            }
        }
        System.out.println("exit click");


        state=constants.LEAVING;
        miniCAD.getMyPanel().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //System.out.println("enter");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //System.out.println("exit");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //System.out.println("Dragged");
        if(type==constants.CHOOSE&&state==constants.MOVING)
        {
            miniCAD.getMyPanel().setCursor(new Cursor(Cursor.MOVE_CURSOR));
            operOnApply.getShape().move(e.getX()-preX,e.getY()-preY);
            miniCAD.getMyPanel().repaint();
            myPanel.reFill();
            preX=e.getX();
            preY=e.getY();
        }
        else if(type==constants.CHOOSE&&state==constants.RESIZING)
        {
            int loc=operOnApply.getShape().judgeLoc(e.getX(),e.getY());
            miniCAD.getMyPanel().setCursor(new Cursor(loc));
            operOnApply.getShape().reSize(preX,preY,e.getX(),e.getY(),loc);

            miniCAD.getMyPanel().repaint();
            myPanel.reFill();
            preX=e.getX();
            preY=e.getY();

        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(type==constants.CHOOSE && myPanel.getBuffer()[e.getY()][e.getX()]!=constants.NONE)
        {
            if(operOnApply!=null)
            {
                int loc=operOnApply.getShape().judgeLoc(e.getX(),e.getY());
                if(loc!=constants.OTHER_LOC)
                {
                    miniCAD.getMyPanel().setCursor(new Cursor(loc));
                }
                else{
                    miniCAD.getMyPanel().setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

            }
            else{
                miniCAD.getMyPanel().setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

        }
        else{
            miniCAD.getMyPanel().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println(e.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("key press");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("key release");
    }

    public static void setType(int type) {
        myListener.type = type;
    }
}
