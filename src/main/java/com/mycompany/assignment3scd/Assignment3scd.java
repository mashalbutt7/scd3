/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.assignment3scd;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        table.getColumnModel().getColumn(3).setCellEditor(new ButtonEditorForTable(new JTextField()));

        JScrollPane j = new JScrollPane(table);
        frame.add(j, BorderLayout.CENTER);

        JPanel bPanel = new JPanel();
        addButton = new JButton("Add Item");
        deleteButton = new JButton("Delete Item");
        editButton = new JButton("Edit Item");

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

        bPanel.add(addButton);
        bPanel.add(deleteButton);
        bPanel.add(editButton);
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

    public ButtonEditorForTable(JTextField textField) 
    {
        super(textField);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener((ActionEvent e) -> 
        {
            fireEditingStopped();
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


