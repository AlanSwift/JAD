import com.csvreader.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Spreadsheet {
    private static JFrame mJFrame=null;
    private static JPanel mJPanel=null;
    private static JButton mOpenBtn=null;
    private static JButton mSaveBtn=null;
    private static JTable mJTable=null;
    private String mFilePath="";

    public Spreadsheet()
    {
        mJFrame= new JFrame();
        mJPanel= new JPanel(new BorderLayout());
        mJFrame.setPreferredSize(new Dimension(400,400));

        // add hint
        JLabel title=new JLabel("CSV Editer",JLabel.CENTER);
        title.setFont(new Font("Courier",Font.BOLD,16));
        mJPanel.add(title,BorderLayout.NORTH);
        // add button
        JPanel bottonJPanel=new JPanel(new FlowLayout());
        mJPanel.add(bottonJPanel,BorderLayout.SOUTH);
        mOpenBtn=new JButton("open");
        mOpenBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser=new JFileChooser();
                chooser.setCurrentDirectory(new File("~"));
                chooser.setAcceptAllFileFilterUsed(false);
                chooser.addChoosableFileFilter(


                        new javax.swing.filechooser.FileFilter() {
                            @Override
                            public boolean accept(File f) {

                                String fileName=f.getName();
                                return fileName.endsWith(".csv");
                            }

                            @Override
                            public String getDescription() {
                                return ".csv";
                            }
                        }

                );
                chooser.showDialog(null,null);
                if(chooser.getSelectedFile()==null)
                {
                    return;
                }
                mFilePath=chooser.getSelectedFile().getAbsolutePath();
                String [][]content=readFromCSV(mFilePath);
                String[] colon=new String[content.length];
                for(int i=0;i<content.length;i++)
                {
                    colon[i]="colunm:"+i;
                    System.out.println(colon[i]);
                }
                if(mJTable!=null)
                {
                    //save the file first
                    try {
                        saveCSV(mFilePath);
                    } catch (IOException e1) {

                    }
                    mJPanel.remove(mJTable);
                }
                //create a new table
                mJTable=new JTable(content,colon);
                mJPanel.add(new JScrollPane(mJTable),BorderLayout.CENTER);

                //repaint
                mJPanel.repaint();
                mJPanel.revalidate();

            }
        });

        bottonJPanel.add(mOpenBtn);


        mSaveBtn=new JButton("save");
        //save action
        mSaveBtn.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        mJTable.getCellEditor().stopCellEditing();
                        try {
                            saveCSV(mFilePath);
                            JOptionPane.showMessageDialog(null,"Save success!","Success!",JOptionPane.INFORMATION_MESSAGE);
                        } catch (IOException e1) {
                            JOptionPane.showMessageDialog(null,"Fail to save, please check the path or permissions.","Fail to save",JOptionPane.ERROR_MESSAGE);
                            //e1.printStackTrace();
                        }
                    }
                }

        );
        bottonJPanel.add(mSaveBtn);



    }

    void work()
    {
        mJFrame.setContentPane(mJPanel);
        mJFrame.setVisible(true);
        mJFrame.setDefaultCloseOperation(mJFrame.EXIT_ON_CLOSE);
        mJFrame.pack();
    }
    private void saveCSV(String filePath) throws IOException {

        CsvWriter writer=new CsvWriter(filePath);
        String [][] content=new String[mJTable.getRowCount()][mJTable.getColumnCount()];
        for(int i=0;i<mJTable.getRowCount();i++)
        {
            for(int j=0;j<mJTable.getColumnCount();j++)
            {
                content[i][j]=(String)mJTable.getValueAt(i,j);
                System.out.println(content[i][j]);
            }
            writer.writeRecord(content[i]);
        }
        writer.close();

    }

    private String[][] readFromCSV(String fileName)
    {
        try {
            CsvReader reader=new CsvReader(fileName);
            ArrayList<String[]> content=new ArrayList<>();
            while(reader.readRecord())
            {
                content.add(reader.getValues());
            }
            return content.toArray(new String[content.size()][]);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args)
    {
        Spreadsheet client=new Spreadsheet();
        client.work();
    }
}
