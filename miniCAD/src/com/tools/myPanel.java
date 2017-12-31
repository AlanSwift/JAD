package com.tools;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import com.constant.constants;
import com.miniCAD;
import com.tools.extendLib.Handler;
import com.tools.shapes.basicShape;
import javafx.beans.binding.ObjectExpression;

public class myPanel extends JPanel {
    private static Integer[][] sBuffer=new Integer[constants.PANEL_HEIGHT][constants.PANEL_WIDTH];
    private static ArrayList<apply> sApplies=new ArrayList<>();
    private static HashMap<Integer,apply> sApplyMap=new HashMap<>();
    private static int cnt=0;
    private HashMap<String,Component>nameMap=new HashMap<>();



    public myPanel()
    {
        setBackground(Color.WHITE);
        for(int i=0;i<sBuffer.length;i++)
        {
            for(int j=0;j<sBuffer[i].length;j++)
            {
                sBuffer[i][j]= 0;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        sApplies.forEach(
            tApply -> {
                System.out.println("in paint");
                tApply.draw((Graphics2D) g);

            }
        );

    }
    static void addApply(apply tApply) throws CloneNotSupportedException {
        apply newApply=(apply) tApply.clone();
        newApply.setIndex(++cnt);
        tApply.setIndex(cnt);
        newApply.getShape().setIndex(cnt);
        sApplyMap.put(newApply.getIndex(),newApply);
        sApplies.add(sApplyMap.get(newApply.getIndex()));
    }
    static void removeApply(int index)
    {
        if(sApplyMap.get(index)!=null)
        {
            apply tApply=sApplyMap.get(index);
            sApplyMap.remove(index);
            sApplies.remove(tApply);
        }
    }
    static apply moveToTop(int index)
    {
        apply tApply=sApplyMap.get(index);
        sApplies.remove(tApply);
        sApplies.add(tApply);
        tApply.fill(sBuffer);
        return tApply;
    }
    static apply getApplyByIndex(int index)
    {
        return sApplyMap.get(index);
    }

    static void reFill()
    {
        clearBuffer();
        sApplies.forEach(
                tApply->
                {
                    tApply.fill(sBuffer);
                }
        );
    }

    static void clearBuffer()
    {
        for(int i=0;i<sBuffer.length;i++)
        {
            for(int j=0;j<sBuffer[0].length;j++)
            {
                sBuffer[i][j]=0;
            }
        }
    }

    public static void debug()
    {
        for(int i=0;i<sBuffer.length;i++)
        {
            for(int j=0;j<sBuffer[i].length;j++)
            {
                System.out.print(sBuffer[i][j]);

            }
            System.out.println("");
        }
    }

    public Component add(Component v,String name)
    {
        super.add(v);
        if(!nameMap.containsKey(name))
        {
            nameMap.put(name,v);
        }
        return v;
    }
    public Component getComponentByName(String name)
    {
        return nameMap.get(name);
    }


    public static Integer[][] getBuffer() {
        return sBuffer;
    }

    public static void saveApplies(String path) throws IOException {
        FileOutputStream fout=new FileOutputStream(path);
        ObjectOutputStream objOut=new ObjectOutputStream(fout);
        objOut.writeObject(sApplies);
        objOut.writeObject(sApplyMap);
    }
    public static void load(String path) throws IOException, ClassNotFoundException {
        sApplyMap.clear();
        sApplies.clear();
        FileInputStream fin=new FileInputStream(path);
        ObjectInputStream objIn=new ObjectInputStream(fin);

             sApplies=(ArrayList<apply>) objIn.readObject();
            sApplyMap=(HashMap<Integer,apply>)objIn.readObject();

        miniCAD.getMyPanel().repaint();
    }
}
