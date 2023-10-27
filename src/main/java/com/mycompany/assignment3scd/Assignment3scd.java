/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.assignment3scd;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import java.util.Map;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;


/**
 *
 * @author mashalbutt
 */
public class Assignment3scd
{

    private final JFrame frame;
    public JTable table = null;
    private DefaultTableModel tableModel;
    private final JButton addButton;
    private final JButton deleteButton;
    private final JButton editButton;
    private final JButton popularityCount;
   // private String content;
   // private ArrayList <String> li=new ArrayList<>();
   private static final Map<String,String> c=new HashMap<>();
   private ArrayList <Integer> pc=new ArrayList<>();
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
                String title = p[0];
                    String author = p[1];
                    int year = Integer.parseInt(p[2]);
                    int pct = Integer.parseInt(p[3]);
                    pc.add(pct);
                //  c.put(title, null);
                    Object[] rowData = {title, author, year};
                    tableModel.addRow(rowData);
                 
            }
            file.close();
            
        } 
        catch (FileNotFoundException e) 
        {
            System.err.println("File not found: " + filePath);
        } 
        catch (NumberFormatException g)
        {
            System.err.println("");
        }

        table = new JTable(tableModel);
        table.setShowGrid(true);
        table.getColumnModel().getColumn(3).setCellRenderer(new RenderButtonForTable());
        table.getColumnModel().getColumn(3).setCellEditor(new ButtonEditorForTable(new JCheckBox(),table,tableModel,c));
    
         JScrollPane j = new JScrollPane(table);
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
                panel.setLayout(new GridLayout(4, 2)); 
                JTextField field1 = new JTextField(10);
                JTextField field2 = new JTextField(10);
                JTextField field3 = new JTextField(10);
                JTextField field4 = new JTextField(30);
                panel.add(new JLabel("Name of the book:"));
                panel.add(field1);
                panel.add(new JLabel("Author of the book:"));
                panel.add(field2);
                panel.add(new JLabel("Publishing Year of the book:"));
                panel.add(field3);
                panel.add(new JLabel("Enter content for the book:"));
                panel.add(field4);
               int result = JOptionPane.showConfirmDialog(null, panel, "Add a book",
                JOptionPane.OK_CANCEL_OPTION);
       
        if (result == JOptionPane.OK_OPTION) 
        {
            String name = field1.getText();
            String author = field2.getText();
            String year=field3.getText();
            String cc=field4.getText();
            System.out.println(cc);
            rowData[0]=name;
            rowData[1]=author;
            rowData[2]=year;
             tableModel.addRow(rowData);
             c.put(name, cc);
               for (String key : c.keySet()) 
               {
            String value = c.get(key);
            System.out.println("Key: " + key + ", Value: " + value);
        }
           

               try (FileWriter writer = new FileWriter("data.txt", true)) 
               {
    String newItemData = String.format("%s,%s,%d,%d%n", rowData[0], rowData[1],
            Integer.parseInt(rowData[2].toString()),10);
    
    writer.write(newItemData);
     pc.add(10);
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

            if (found) {
               
                tableModel.removeRow(count);
                pc.remove(count);
                  for (int i=0;i<pc.size();i++) 
        {
            Integer it=pc.get(i);
            System.out.println(it);
        }
                JOptionPane.showMessageDialog(null, "Match found in table and deleted");
                String filePath = "data.txt";
  try (FileWriter writer = new FileWriter(filePath, false)) 
  {
    for (int row = 0; row < tableModel.getRowCount(); row++)
    {
        for (int col = 0; col < tableModel.getColumnCount(); col++) 
        {
            Object val = tableModel.getValueAt(row, col);
            if (val != null) {
                writer.write(val.toString());
            }
            if (col < tableModel.getColumnCount() - 2)
            {
                writer.write(",");
            }
        }
        int popularityValue = getPopularityValueForRow(row); 
        writer.write("," + popularityValue); 
        writer.write("\n");
    }
}
 catch (IOException ex)
                {
                    Logger.getLogger(Assignment3scd.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Not found");
          }
        } else {
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
            panel.setLayout(new GridLayout(4, 2)); 
            JTextField field1 = new JTextField(10);
            JTextField field2 = new JTextField(10);
            JTextField field3 = new JTextField(10);
            JTextField field4 = new JTextField(30);
            panel.add(new JLabel("New name of the book:"));
            panel.add(field1);
            panel.add(new JLabel("New author of the book:"));
            panel.add(field2);
            panel.add(new JLabel("New publishing Year of the book:"));
            panel.add(field3);
            panel.add(new JLabel("New content of the book:"));
            panel.add(field4);
        int result = JOptionPane.showConfirmDialog(null, panel, "Edit a book",
                JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) 
        {
            String name = field1.getText();
            String author = field2.getText();
            String year=field3.getText();
            String con=field4.getText();
            rowData[0]=name;
            rowData[1]=author;
            rowData[2]=year;
           
            tableModel.removeRow(count);
            tableModel.insertRow(count, rowData);
            c.put(name, con);

              try (FileWriter writer = new FileWriter(filePath, false)) {
    for (int row = 0; row < tableModel.getRowCount(); row++) {
        for (int col = 0; col < tableModel.getColumnCount(); col++) {
            Object val = tableModel.getValueAt(row, col);
            if (val != null) {
                writer.write(val.toString());
            }
            if (col < tableModel.getColumnCount() - 2) {
                writer.write(",");
            }
        }
        int popularityValue = getPopularityValueForRow(row); 
        writer.write("," + popularityValue); 
        writer.write("\n");
    }
}
 catch (IOException ex)
                {
                    Logger.getLogger(Assignment3scd.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
        
          else {
                JOptionPane.showMessageDialog(null, "Not found");
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
      table.addMouseMotionListener(new MouseAdapter()
      {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                int row = table.rowAtPoint(e.getPoint());
                if (row >= 0)
                {
                    table.getSelectionModel().setSelectionInterval(row, row);
                }
                else
                {
                    table.getSelectionModel().clearSelection();
                }
            }
    });
        bPanel.add(addButton);
        bPanel.add(deleteButton);
        bPanel.add(editButton);
        bPanel.add(popularityCount);
     //   table.addMouseListener(m);
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
    private int getPopularityValueForRow(int row)
    {
     
        if (row >= 0 && row < pc.size())
        {
            return pc.get(row);
        } 
        else 
        {
            return 0; 
        }
    }

    public ArrayList<Integer> readPopularityDataFromFile() 
     {
        String file = "data.txt"; 
        ArrayList<Integer> popularity = new ArrayList<>();
         System.out.println("hi");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) 
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                System.out.println("hiiiii");
                String[] parts = line.split(",");
                if (parts.length >= 4)
                {
                     int popularityc = Integer.parseInt(parts[3]); 
                   System.out.println("hii");
                    popularity.add(popularityc);
                   System.out.println(popularityc);
                }
            }
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
       
        return popularity;
    }
    public void display() 
    {
        ArrayList<Integer> popularityData = readPopularityDataFromFile(); 
        ArrayList<String> bookData=readBookNamesFromFile();
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
   
    private ArrayList<String> readBookNamesFromFile() 
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
      
       return bookNames;
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
    public ButtonEditorForTable(JCheckBox checkBox,JTable table, DefaultTableModel tableModel,Map<String,String> c) 
    {
    
        super(checkBox);
    
        button = new JButton("Read");
     
        button.addActionListener(new ActionListener()
        {        
            @Override
            public void actionPerformed(ActionEvent e) 
            {
               int s=table.getRowCount();
               System.out.println(s);
               int r = table.getSelectedRow();
                System.out.println(r);
               if (r >= 0 && r < tableModel.getRowCount())
              {
            String bookTitle = tableModel.getValueAt(r, 0).toString();
            Map<String, String> resultMap = c;
            openBookContentWindow(bookTitle, resultMap);
              } 
              
            }
  public static void openBookContentWindow(String bookTitle,Map<String, String> content)
 {
        JFrame bookContentFrame = new JFrame(bookTitle + " - Read Book");
        JTextArea textArea = new JTextArea(20, 60);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setCaretPosition(0);
        textArea.setEditable(false); 
        String bookContent = null;
        JScrollPane scrollPane = new JScrollPane(textArea);
               
               if ("To Kill a Mockingbird".equals(bookTitle)) {
    bookContent = "This is the content of the book titled '" + bookTitle + "'.\n\n" +
        "Chapter 1: The Early Years\n" +
        "\n" +
        "In a small Southern town, young Scout Finch lives with her brother Jem and their father Atticus.\n" +
        "\n" +
        "Chapter 2: The Radley Place\n" +
        "\n" +
        "The children are fascinated by their reclusive neighbor Boo Radley, whom they have never seen.\n" +
        "\n" +
        "Chapter 3: Miss Maudie's House\n" +
        "\n" +
        "They befriend a neighbor named Miss Maudie, who helps them understand the town and its people.\n" +
        "\n" +
        "Chapter 4: School Days\n" +
        "\n" +
        "Scout and Jem begin school and experience the challenges of dealing with their classmates.\n" +
        "\n" +
        "Chapter 5: The New Items\n" +
        "\n" +
        "Atticus defends Tom Robinson, an African American accused of raping Mayella Ewell.\n\n";
    content.put(bookTitle, bookContent);
}

           else if ("The Fault in Our Stars".equals(bookTitle)) {
    bookContent = "This is the content of the book titled '" + bookTitle + "'.\n\n" +
        "Chapter 1: Hazel's Story\n" +
        "\n" +
        "Hazel Grace Lancaster is a 16-year-old cancer patient who meets Augustus Waters at a support group.\n" +
        "\n" +
        "Chapter 2: Augustus's Story\n" +
        "\n" +
        "Augustus Waters, an amputee, joins Hazel at the support group. They bond over their favorite books.\n" +
        "\n" +
        "Chapter 3: Amsterdam Adventure\n" +
        "\n" +
        "Hazel and Augustus travel to Amsterdam to meet their favorite author Peter Van Houten.\n" +
        "\n" +
        "Chapter 4: Roller Coaster of Emotions\n" +
        "\n" +
        "The relationship between Hazel and Augustus deepens, but they also face the harsh realities of cancer.\n" +
        "\n" +
        "Chapter 5: The Fault in Our Stars\n" +
        "\n" +
        "Hazel and Augustus come to terms with the imperfections of their lives and the impact they have on others.\n\n";
    content.put(bookTitle, bookContent);
}

           else if ("A Walk to Remember".equals(bookTitle)) {
    bookContent = "This is the content of the book titled '" + bookTitle + "'.\n\n" +
        "Chapter 1: A Chance Encounter\n" +
        "\n" +
        "Landon Carter, a high school student, meets Jamie Sullivan, the minister's daughter.\n" +
        "\n" +
        "Chapter 2: A Night to Remember\n" +
        "\n" +
        "Landon and Jamie spend time together at a school play and begin to form a connection.\n" +
        "\n" +
        "Chapter 3: Revealing a Secret\n" +
        "\n" +
        "Jamie confides in Landon, revealing a deep secret, and their relationship deepens.\n" +
        "\n" +
        "Chapter 4: Facing Challenges\n" +
        "\n" +
        "Landon and Jamie face challenges, but their love for each other helps them overcome adversity.\n" +
        "\n" +
        "Chapter 5: A Walk to Remember\n" +
        "\n" +
        "The story unfolds, and Landon and Jamie create unforgettable memories during their time together.\n\n";
    content.put(bookTitle, bookContent);
}
           else if ("Pride and Prejudice".equals(bookTitle)) {
    bookContent = "This is the content of the book titled '" + bookTitle + "'.\n\n" +
        "Chapter 1: The Bennet Family\n" +
        "\n" +
        "Introduces the Bennet family, including Mr. and Mrs. Bennet and their five daughters.\n" +
        "\n" +
        "Chapter 2: The Arrival of Mr. Bingley\n" +
        "\n" +
        "A wealthy bachelor, Mr. Bingley, moves to the neighborhood, creating excitement among the Bennet daughters.\n" +
        "\n" +
        "Chapter 3: The Pride of Mr. Darcy\n" +
        "\n" +
        "Mr. Darcy, a wealthy and reserved man, is introduced, and his pride becomes evident.\n" +
        "\n" +
        "Chapter 4: The Dance at Netherfield\n" +
        "\n" +
        "A social event at Mr. Bingley's residence brings the Bennet family into contact with new acquaintances.\n" +
        "\n" +
        "Chapter 5: The Proposal\n" +
        "\n" +
        "Various proposals and misunderstandings occur, affecting the relationships of the characters.\n\n";
    content.put(bookTitle, bookContent);
}

else 
{
    bookContent = "This is the content of the book titled '" + bookTitle + "'.\n\n" +
            content.get(bookTitle);
}

                
        textArea.setText(bookContent);
        bookContentFrame.add(scrollPane);
        bookContentFrame.pack();
        bookContentFrame.setVisible(true);
        bookContentFrame.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                int confirm = JOptionPane.showOptionDialog(
                        bookContentFrame, "Are you sure you want to close this book?", "Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);

                if (confirm == JOptionPane.YES_OPTION) 
                {
                    bookContentFrame.dispose();
                } 
                else if (confirm == JOptionPane.NO_OPTION)
                {
                   Map<String, String> resultMap = content;
                    openBookContentWindow(bookTitle,resultMap);
                    // Do nothing, bookContentFrame will remain open
                }
            }
        });
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


