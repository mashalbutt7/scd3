/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment3scd;

/**
 *
 * @author mashalbutt
 */

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;

class Popularity extends JFrame 
{
    private final int[] data;
    private final String[] labels;

    public Popularity(int[] data, String[] labels)
    {
        this.data = data;
        this.labels = labels;

        setTitle("Pie Chart");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new PieChartPanel());
    }

    class PieChartPanel extends JPanel
    {
        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();
            int cx = width / 2;
            int cy = height / 2;
            int radius = Math.min(width, height) / 2 - 20;

            double total = 0.0;
            for (int value : data) 
            {
                total += value;
            }

            double startAngle = 0.0;
            for (int i = 0; i < data.length; i++)
            {
                double extent = 360.0 * (data[i] / total);
                g2.setColor(getColorForIndex(i));
                g2.fill(new Arc2D.Double(cx - radius, cy - radius, 2 * radius, 2 * radius, startAngle, extent, Arc2D.PIE));
                startAngle += extent;
            }

          //labels
            int legendX = cx + radius + 20;
            int legendY = cy - radius;
            int lineHeight = 20;
            for (int i = 0; i < labels.length; i++)
            {
                g2.setColor(getColorForIndex(i));
                g2.fillRect(legendX, legendY + i * lineHeight, 10, 10);
                g2.setColor(Color.BLACK);
                g2.drawString(labels[i], legendX + 20, legendY + i * lineHeight + 10);
            }
        }

        private Color getColorForIndex(int index)
        {
            Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.ORANGE, Color.MAGENTA,Color.GRAY};
            return colors[index % colors.length];
        }
    }
}
