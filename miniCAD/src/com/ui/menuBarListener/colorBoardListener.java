package com.ui.menuBarListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import com.miniCAD;
import com.tools.extendLib.Handler;
import com.constant.constants;

public class colorBoardListener implements MouseListener {

    private static JColorChooser sJColorChooser=new JColorChooser();
    private static Color sColor=Color.BLACK;

    private Handler mHandler;

    public colorBoardListener(Handler tHandler)
    {
        mHandler=tHandler;
    }


    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        Color newColor= JColorChooser.showDialog(null,"Please choose a color",sColor);
        if(newColor!=null)
        {
            sColor=newColor;
            HashMap<Object,Object> msg=new HashMap<Object, Object>() ;
            msg.put("return",constants.CALL_BACK_COLOR);
            msg.put("color",newColor);
            System.out.println("in color chooser");
            mHandler.sendMessage(msg);
        }
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
