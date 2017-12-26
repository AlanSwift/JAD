package com.tools;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import com.constant.constants;

public class myPanel extends JPanel {
    private static int[][] sBuffer=new int[constants.SCREEN_HEIGHT][constants.SCREEN_WIDTH];
    public  ArrayList<apply> mApplies=new ArrayList<>();

    @Override
    public void paintComponent(Graphics g)
    {
        mApplies.forEach(
            tApply -> {
                // set color
                tApply.draw(g);

            }
        );
    }
}
