/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.assignment3scd;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
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
public class Assignment3scd {


/**
 *
 * @author mashalbutt
 */






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
            while (file.hasNextLine()) {
                String line = file.nextLine();
                String[] p = line.split(",");
                int idd = Integer.parseInt(p[0]);
                String title = p[1];
                if (idd == 1) 
                {
                    String author = p[2];
                    int year = Integer.parseInt(p[3]);
                    int pc = Integer.parseInt(p[4]);
                    int cost = Integer.parseInt(p[5]);
                    Object[] rowData = {title, author, year, pc, cost};
                    tableModel.addRow(rowData);
                }
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
                for (int i = 0; i < columnNames.length - 1; i++) 
                {
                    String userInput = JOptionPane.showInputDialog(null, "Enter " + columnNames[i] + ":");
                    if (userInput != null) 
                    {
                        rowData[i] = userInput;
                    } else
                    {
                        JOptionPane.showMessageDialog(null, "You canceled the input dialog.");
                        return;
                    }
                }

                tableModel.addRow(rowData);

                try (FileWriter writer = new FileWriter("data.txt", true))
                {
                    String newItemData = String.format("1,%s,%s,%d,%d,%d%n", rowData[0], rowData[1],
                            Integer.parseInt(rowData[2].toString()), Integer.parseInt(rowData[3].toString()),
                            Integer.parseInt(rowData[4].toString()));
                    writer.write(newItemData);
                } 
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
               
            }
        });

        editButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
               
            }
        });

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


