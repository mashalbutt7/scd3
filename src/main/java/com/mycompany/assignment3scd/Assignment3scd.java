/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.assignment3scd;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JCheckBox;

/**
 *
 * @author mashalbutt
 */
public class Assignment3scd
{

    private final JFrame frame;
    private JTable table = null;
    private DefaultTableModel tableModel;
    private final JButton addButton;
    private final JButton deleteButton;
    private final JButton editButton;
    private final JButton popularityCount;
    private ArrayList <String> li=new ArrayList<>();
    String s;

    public Assignment3scd() 
    {
        frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

        String[] columnNames = {"Title", "Author", "Publication Year", "Read Item"};
        tableModel = new DefaultTableModel(columnNames, 0) 
        {
            @Override
            public boolean isCellEditable(int row, int column) 
            {
                return column == 3;
            }
        };

        String filePath = "data.txt";
       
        try 
        {
            File f = new File(filePath);
            Scanner file = new Scanner(f);
            while (file.hasNextLine()) 
            {
                String line = file.nextLine();
                String[] p = line.split(",");
               // int idd = Integer.parseInt(p[0]);
                String title = p[0];
               // if (idd == 1) 
              //  {
                    String author = p[1];
                    s=title;
                 li.add(title);
                    int year = Integer.parseInt(p[2]);
//                    int pc = Integer.parseInt(p[4]);
//                    int cost = Integer.parseInt(p[5]);
                    Object[] rowData = {title, author, year};
                    tableModel.addRow(rowData);
                  
              //  }
            }
            file.close();
        } catch (FileNotFoundException e) 
        {
            System.err.println("File not found: " + filePath);
        } catch (NumberFormatException g)
        {
            System.err.println("");
        }

        table = new JTable(tableModel);
        table.setShowGrid(true);
        table.getColumnModel().getColumn(3).setCellRenderer(new RenderButtonForTable());
       
    
     for (int i = 0; i < li.size(); i++) 
    {
        
        String item = li.get(i);
        table.getColumnModel().getColumn(3).setCellEditor(new ButtonEditorForTable(new JTextField(),item));
    
    }   JScrollPane j = new JScrollPane(table);
        frame.add(j, BorderLayout.CENTER);

        JPanel bPanel = new JPanel();
        addButton = new JButton("Add Item");
        deleteButton = new JButton("Delete Item");
        editButton = new JButton("Edit Item");
        popularityCount=new JButton("Popularity Count");

        addButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
           {
                Object[] rowData = new Object[columnNames.length];
            
                JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2)); 
        JTextField field1 = new JTextField(10);
        JTextField field2 = new JTextField(10);
        JTextField field3 = new JTextField(10);
        panel.add(new JLabel("Name of the book:"));
        panel.add(field1);
        panel.add(new JLabel("Author of the book:"));
        panel.add(field2);
         panel.add(new JLabel("Publishing Year of the book:"));
        panel.add(field3);
        int result = JOptionPane.showConfirmDialog(null, panel, "Add a book",
                JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) 
        {
            String name = field1.getText();
            String author = field2.getText();
            String year=field3.getText();
            rowData[0]=name;
            rowData[1]=author;
            rowData[2]=year;
             tableModel.addRow(rowData);

    
        
        
               

               try (FileWriter writer = new FileWriter("data.txt", true)) 
               {
    String newItemData = String.format("%s,%s,%d%n", rowData[0], rowData[1],
            Integer.parseInt(rowData[2].toString()));
    
    writer.write(newItemData);
}

                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            
        } 
        else 
        {
            JOptionPane.showMessageDialog(null,  "You canceled the add book dialog box"
                );
        }}  });

        deleteButton.addActionListener(new ActionListener() 
        {
            
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String msg = JOptionPane.showInputDialog(null, "Enter the name of the book you want to delete:", "Delete a book", JOptionPane.QUESTION_MESSAGE);
                if(msg!=null)
                {
                  int column = 0;
                  boolean found = false; 
                   Object cellValue;
                   int count=0;
for (int row = 0; row < tableModel.getRowCount(); row++) 
{
    cellValue = tableModel.getValueAt(row, column);
    if (cellValue != null && cellValue.toString().equals(msg)) 
    {
       
        found = true;
        break;
    }
    count++;
}
                
if (found) 
{
   
    tableModel.removeRow(count);
    JOptionPane.showMessageDialog(null, "Match found in table and deleted");
     String filePath = "data.txt";

        
               
       try (FileWriter writer = new FileWriter(filePath, false)) {
    for (int row = 0; row < tableModel.getRowCount(); row++) 
    {
        for (int col = 0; col < tableModel.getColumnCount(); col++) 
        {
            Object val = tableModel.getValueAt(row, col);
            if (val != null)
            {
                writer.write(val.toString());
                if (col < tableModel.getColumnCount() - 1)
                {
                    writer.write(",");
                }
            }
        }
        writer.write("\n"); 
    }
} catch (IOException ex) {
    Logger.getLogger(Assignment3scd.class.getName()).log(Level.SEVERE, null, ex);
}
       
}
else
{
    JOptionPane.showMessageDialog(null, "Not found");
}
                }
                
                else
                {
                    JOptionPane.showMessageDialog(null, "You canceled the delete dialog");
                }
            }
             });
        
        editButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String msg = JOptionPane.showInputDialog(null, "Enter the name of the book you want to edit:", "Edit a book", JOptionPane.QUESTION_MESSAGE);
                if(msg!=null)
                {
                  int column = 0;
                  boolean found = false; 
                   Object cellValue;
                   int count=0;
for (int row = 0; row < tableModel.getRowCount(); row++) 
{
    cellValue = tableModel.getValueAt(row, column);
    if (cellValue != null && cellValue.toString().equals(msg)) 
    {
       
        found = true;
        break;
    }
    count++;
}
      if (found)
      {
           Object[] rowData = new Object[columnNames.length];
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(3, 2)); 
            JTextField field1 = new JTextField(10);
            JTextField field2 = new JTextField(10);
            JTextField field3 = new JTextField(10);
            panel.add(new JLabel("New name of the book:"));
            panel.add(field1);
            panel.add(new JLabel("New author of the book:"));
            panel.add(field2);
            panel.add(new JLabel("New publishing Year of the book:"));
            panel.add(field3);
        int result = JOptionPane.showConfirmDialog(null, panel, "Edit a book",
                JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) 
        {
            String name = field1.getText();
            String author = field2.getText();
            String year=field3.getText();
            rowData[0]=name;
            rowData[1]=author;
            rowData[2]=year;
            tableModel.removeRow(count);
            tableModel.insertRow(count, rowData);

    
        
        
               

               try (FileWriter writer = new FileWriter("data.txt", false)) 
               {
    String newItemData = String.format("1,%s,%s,%d%n", rowData[0], rowData[1],
            Integer.parseInt(rowData[2].toString()));
    
    writer.write(newItemData);
}

                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            
        } 
      }
      else 
      {
           JOptionPane.showMessageDialog(null,  "The item is not available"
                );
      }
          
          
            
            } 
             else 
        {
            JOptionPane.showMessageDialog(null,  "You canceled the add book dialog box"
                );
        
      }       } });

        popularityCount.addActionListener(new ActionListener ()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                display();
             //   popularityScreen.setVisible(true);
            }
            
        });
        MouseListener m=new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                
            }

            @Override
            public void mousePressed(MouseEvent e) 
            {
               
            }

            @Override
            public void mouseReleased(MouseEvent e) 
            {
               
            }

            @Override
            public void mouseEntered(MouseEvent e) 
            {
                int row = table.rowAtPoint(e.getPoint());
        if (row >= 0)
        {
            table.setRowSelectionInterval(row, row);
             
        }
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
               table.clearSelection();
            }
        };
        bPanel.add(addButton);
        bPanel.add(deleteButton);
        bPanel.add(editButton);
        bPanel.add(popularityCount);
        table.addMouseListener(m);
        frame.add(bPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public void setTableModel(DefaultTableModel tableModel) 
    {
        this.tableModel = tableModel;
    }

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() 
        {
            @Override
            public void run() {
                new Assignment3scd();
            }
        });
    }
    public int[] readPopularityDataFromFile() 
     {
        String file = "data.txt"; 
        ArrayList<Integer> popularity = new ArrayList<>();
         //System.out.println("hi");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) 
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
              //  System.out.println("hiiiii");
                String[] parts = line.split(",");
                if (parts.length >= 4)
                {
                     int popularityc = Integer.parseInt(parts[3]); 
                  //  System.out.println("hii");
                    popularity.add(popularityc);
                  //  System.out.println(popularityc);
                }
            }
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
        int[] popularityData = new int[popularity.size()];
        for (int i = 0; i < popularity.size(); i++) 
        {
            popularityData[i] = popularity.get(i);
        //    System.out.println("haha"+popularityData[i]);
        }
        return popularityData;
    }
    public void display() 
    {
        int[] popularityData = readPopularityDataFromFile(); 
        String[] bookData=readBookNamesFromFile();
        if (popularityData != null)
        {
            Popularity frame = new Popularity(popularityData,bookData);
            frame.setVisible(true);
        }
        else 
        {
           
            JOptionPane.showMessageDialog(null, "No popularity data available.");
        }
    }
   
    private String[] readBookNamesFromFile() 
    {
        String filePath = "data.txt"; 
        ArrayList<String> bookNames = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                String[] parts = line.split(",");
                if (parts.length >= 4)
                {
                    String bookName = parts[0]; 
                    bookNames.add(bookName);
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        // Convert the list to a string array
        String[] bookNamesArray = bookNames.toArray(new String[0]);
        return bookNamesArray;
    }
}

class RenderButtonForTable extends JButton implements TableCellRenderer
{
    public RenderButtonForTable()
    {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText("read");
        return this;
    }
}

class ButtonEditorForTable extends DefaultCellEditor 
{
    private final JButton button;
    private String label;
    private boolean isPushed;
   private String bookName;
  // private ArrayList<String> l=new ArrayList<>();
    public ButtonEditorForTable(JTextField checkBox,String s) 
    {
    
        super(checkBox);
        this.bookName=s;
         System.out.println("Book name in constructor: " + bookName);
//     for (int i = 0; i < s.size(); i++) 
//    {
//        
//        String item = s.get(i);
//        l.add(item);
//    
//    }
        button = new JButton("Read");
        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                // Read the book associated with this row
                if (ButtonEditorForTable.this.bookName!= null)
                {
                   try {
                  File file = new File(bookName+ ".txt");
                   System.out.println("File path: " + file.getAbsolutePath()); // Debug line
    StringBuilder content;
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }
    }
    JOptionPane.showMessageDialog(null, content.toString(), "Book Content", JOptionPane.INFORMATION_MESSAGE);
} catch (IOException ex) {
    ex.printStackTrace(); // Print the exception to the console for debugging
    JOptionPane.showMessageDialog(null, "Failed to read the book.", "Error", JOptionPane.ERROR_MESSAGE);
}

                }
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (isSelected) 
        {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else
        {
            button.setForeground(table.getForeground());
            button.setBackground(table.getBackground());
        }
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    @Override
    public Object getCellEditorValue()
    {
        if (isPushed) 
        {
            
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing()
    {
        isPushed = false;
        return super.stopCellEditing();
    }
     
}


