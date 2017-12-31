package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.tools.myPanel;
import com.constant.constants;
import com.tools.myListener;
import com.tools.extendLib.Handler;
import com.ui.menuBarListener.*;
import sun.jvm.hotspot.ui.ObjectHistogramPanel;


public class miniCAD {
    private static JFrame sJFrame;
    private static JFrame sToolFrame;
    private static myPanel sJPanel;
    private static JMenuBar sJMenuBar;
    private static final JLabel sColorBoard=new JLabel();
    private static final JComboBox<String> sTextSize=new JComboBox<>();

    private static JPanel sToolJPanel;
    private Handler listenerHanlder;
    //private static tools mTools;
    private Handler myHandler=new Handler() {
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
                            case constants.CALL_BACK_COLOR:
                            {
                                Color newColor=(Color) msg.get("color");
                                System.out.println("fuck"+newColor);
                                sColorBoard.setBackground(newColor);
                                System.out.println("after fuck:"+sColorBoard.getBackground());
                                sColorBoard.repaint();
                                HashMap<Object,Object> msgToListener=new HashMap<>();
                                msgToListener.put("return",constants.CALL_BACK_COLORCHANGED);
                                msgToListener.put("color",newColor);
                                listenerHanlder.sendMessage(msgToListener);
                                break;
                            }
                        }
                    }
                }
            }
        }
    };

    public miniCAD(){
        sJFrame=new JFrame();
        sJPanel=new myPanel();
        sJMenuBar=new JMenuBar();
        sToolFrame=new JFrame();
        sToolJPanel=new JPanel();


        // set UI
        // set Frame
        sJFrame.setTitle("Shina Mashiro's CAD");
        sJFrame.setSize(new Dimension(constants.SCREEN_WIDTH,constants.SCREEN_HEIGHT));
        //sJFrame.setLayout(new BorderLayout());
        sJFrame.setResizable(false);
        sJFrame.setBackground(Color.WHITE);

        // sJFrame.setLocationRelativeTo(null);
        sJFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        sJFrame.add(sJPanel);
        //sJFrame.add(sToolJPanel,BorderLayout.WEST);
        sJFrame.setJMenuBar(sJMenuBar);

        //set mypanel
        sJPanel.setPreferredSize(new Dimension(constants.PANEL_WIDTH,constants.PANEL_HEIGHT));

        //set tool frame
        sToolFrame.setSize(new Dimension(constants.TOOLBAR_WIDTH,constants.TOOLBAR_HEIGHT));
        sToolFrame.setResizable(false);
        sToolFrame.add(sToolJPanel);
        sToolJPanel.setPreferredSize(new Dimension(constants.TOOLBAR_WIDTH,constants.TOOLBAR_HEIGHT));

        //set in middle
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        sJFrame.setLocation((int)(toolkit.getScreenSize().getWidth()-sJFrame.getWidth())/2,(int)(toolkit.getScreenSize().getHeight()-sJFrame.getHeight())/2);
        sToolFrame.setLocation((int)((toolkit.getScreenSize().getWidth()-sJFrame.getWidth())/2-sToolFrame.getWidth()),(int)(toolkit.getScreenSize().getHeight()-sJFrame.getHeight())/2);
        setToolFrame();


        // add mouseListener
        //myListener tMyListener=new myListener();
        sJPanel.addMouseListener(new myListener());
        sJPanel.addMouseMotionListener(new myListener());
        sJPanel.addKeyListener(new myListener());
        listenerHanlder=myListener.getHandler();

        //set menuBar
        // file menu
        JMenu fileMenu=new JMenu("File");
        JMenuItem open=new JMenuItem("Open");
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        // TODO: add listerner
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    myPanel.load("save.cad");
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });
        JMenuItem close=new JMenuItem("Close");
        // TODO: add listerner

        JMenuItem save=new JMenuItem("Save");
        // TODO: add listerner
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    myPanel.saveApplies("save.cad");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        fileMenu.add(open);
        fileMenu.add(close);
        fileMenu.add(save);
        sJMenuBar.add(fileMenu);
        // Draw menu
        JMenu drawMenu=new JMenu("Draw");
        JMenuItem choose=new JMenuItem("Choose");
        JMenuItem line=new JMenuItem("Line");
        JMenuItem rec=new JMenuItem("Rectangle");
        JMenuItem oval=new JMenuItem("Oval");
        JMenuItem text=new JMenuItem("Text");


        // add listener
        line.addActionListener(new lineListener());
        choose.addActionListener(new chooseListener());
        rec.addActionListener(new rectangleListener());
        oval.addActionListener(new ovalListener());
        text.addActionListener(new stringListener());


        drawMenu.add(choose);
        drawMenu.add(line);
        drawMenu.add(rec);
        drawMenu.add(oval);
        drawMenu.add(text);
        sJMenuBar.add(drawMenu);




    }
    public static Color getColor()
    {
        synchronized (sColorBoard)
        {
            return sColorBoard.getBackground();
        }
    }


    public Handler getMyHandler()
    {
        return myHandler;
    }


    private void setToolFrame()
    {
        //sJFrame.setBackground(Color.WHITE);
        sToolJPanel.setOpaque(false);
        sToolJPanel.setBackground(Color.WHITE);
        sToolJPanel.setLayout(new FlowLayout());
        JButton chooseBtn=new JButton();
        chooseBtn.setSize(new Dimension(40,40));
        chooseBtn.setIcon(new ImageIcon("pic//icons//choose.png"));
        JButton lineBtn=new JButton();
        lineBtn.setSize(new Dimension(40,40));
        lineBtn.setIcon(new ImageIcon("pic//icons//line.png"));
        JButton rectangleBtn=new JButton();
        rectangleBtn.setSize(new Dimension(40,40));
        rectangleBtn.setIcon(new ImageIcon("pic//icons//rectangle.png"));
        JButton circleBtn=new JButton();
        circleBtn.setSize(new Dimension(40,40));
        circleBtn.setIcon(new ImageIcon("pic//icons//circle.png"));
        JButton textBtn=new JButton();
        textBtn.setSize(new Dimension(40,40));
        textBtn.setIcon(new ImageIcon("pic//icons//text.png"));

        JPanel textPanel=new JPanel();
        textPanel.setBackground(Color.WHITE);
        textPanel.setLayout(new BorderLayout());
        textPanel.setOpaque(false);
        //sColorBoard.setForeground(Color.BLACK);
        sColorBoard.setBackground(Color.BLACK);
        sColorBoard.setPreferredSize(new Dimension(40,40));

        sColorBoard.setOpaque(true);
        sColorBoard.addMouseListener(new colorBoardListener(myHandler));

        textPanel.add(sColorBoard,BorderLayout.SOUTH);
        for(int i=10;i<20;i++)
        {
            sTextSize.addItem(""+i);
        }
        sTextSize.addActionListener(e -> {
            if ((String)sTextSize.getSelectedItem() != null) {
                int sizeNow=Integer.parseInt((String)sTextSize.getSelectedItem());
                HashMap<Object,Object> msg=new HashMap<>();
                msg.put("return",constants.CALL_BACK_TEXTSIZECHANGED);
                msg.put("size",sizeNow);
                listenerHanlder.sendMessage(msg);
            }
        });
        textPanel.add(sTextSize,BorderLayout.NORTH);
        JButton makeChap=new JButton("变粗");
        makeChap.addActionListener(e -> {
            HashMap<Object,Object>msg=new HashMap<>();
            msg.put("return",constants.CALL_BACK_MAKECHOP);
            listenerHanlder.sendMessage(msg);
        });
        textPanel.add(makeChap,BorderLayout.WEST);
        JButton makeThin=new JButton("变细");
        makeThin.addActionListener(e -> {
            HashMap<Object,Object>msg=new HashMap<>();
            msg.put("return",constants.CALL_BACK_MAKETHIN);
            listenerHanlder.sendMessage(msg);
        });
        textPanel.add(makeThin,BorderLayout.EAST);



        //add to panel
        sToolJPanel.add(chooseBtn);
        sToolJPanel.add(lineBtn);
        sToolJPanel.add(rectangleBtn);
        sToolJPanel.add(circleBtn);
        sToolJPanel.add(textBtn);
        sToolJPanel.add(textPanel);



        //add listener

        chooseBtn.addActionListener(new chooseListener());
        lineBtn.addActionListener(new lineListener());
        rectangleBtn.addActionListener(new rectangleListener());
        circleBtn.addActionListener(new ovalListener());
        textBtn.addActionListener(new stringListener());



    }

    public void show()
    {
        sJPanel.repaint();
        sJFrame.setVisible(true);
        sToolFrame.setVisible(true);
    }

    public static myPanel getMyPanel()
    {
        return sJPanel;
    }

    public static void main(String[] args)
    {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "");
        miniCAD myCAD=new miniCAD();
        //myCAD.test();
        myCAD.show();

    }
}
