package com;

import javax.swing.*;
import java.awt.*;
import com.tools.myPanel;
import com.constant.constants;
import com.tools.apply;

import com.shapes.*;


public class miniCAD {
    private static JFrame sJFrame;
    private static myPanel sJPanel;
    private static JMenuBar sJMenuBar;

    public miniCAD(){
        sJFrame=new JFrame();
        sJPanel=new myPanel();
        sJMenuBar=new JMenuBar();

        // set UI
        // set Frame
        sJFrame.setTitle("Shina Mashiro's CAD");
        sJFrame.setSize(new Dimension(constants.SCREEN_WIDTH,constants.SCREEN_HEIGHT));
        sJFrame.setResizable(false);
        sJFrame.setBackground(Color.WHITE);
        sJFrame.setLocationRelativeTo(null);
        sJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        sJFrame.setContentPane(sJPanel);
        sJFrame.setJMenuBar(sJMenuBar);

        //set menuBar
        // file menu
        JMenu fileMenu=new JMenu("File");
        JMenuItem open=new JMenuItem("Open");
        // TODO: add listerner
        JMenuItem close=new JMenuItem("Close");
        // TODO: add listerner
        JMenuItem save=new JMenuItem("Save");
        // TODO: add listerner

        fileMenu.add(open);
        fileMenu.add(close);
        fileMenu.add(save);
        sJMenuBar.add(fileMenu);
        // Draw menu
        JMenu drawMenu=new JMenu("Draw");
        JMenuItem line=new JMenuItem("Line");


        drawMenu.add(line);
        sJMenuBar.add(drawMenu);


    }
    public void test()
    {
        lineShape l=new lineShape();
        l.setColor(Color.RED);
        l.x1=100;
        l.x2=300;
        l.y1=100;
        l.y2=300;
        apply myapply=new apply(l);

        sJPanel.mApplies.add(myapply);
    }

    public void show()
    {
        sJPanel.repaint();
        sJFrame.setVisible(true);
    }
    public static void main(String[] args)
    {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "");
        miniCAD myCAD=new miniCAD();
        myCAD.test();
        myCAD.show();

    }
}
