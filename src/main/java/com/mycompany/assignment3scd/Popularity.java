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
import java.util.List;
import java.awt.Color;
import java.util.ArrayList;

public class Popularity extends JFrame {
    private final List<Integer> data;
    private final List<String> labels;

    public Popularity(List<Integer> data, List<String> labels) {
        this.data = data;
        this.labels = labels;

        setTitle("Pie Chart");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new PieChartPanel(data, labels));
    }

    static class PieChartPanel extends JPanel {
        private final List<Integer> data;
        private final List<String> labels;

        PieChartPanel(List<Integer> data, List<String> labels) {
            this.data = data;
            this.labels = labels;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();
            int cx = width / 2;
            int cy = height / 2;
            int radius = Math.min(width, height) / 2 - 20;

            double total = data.stream().mapToDouble(Integer::doubleValue).sum();

            double startAngle = 0.0;
            for (int i = 0; i < data.size(); i++) {
                double extent = 360.0 * (data.get(i) / total);
                g2.setColor(getColorForIndex(i, data.size()));
                g2.fill(new Arc2D.Double(cx - radius, cy - radius, 2 * radius, 2 * radius, startAngle, extent, Arc2D.PIE));
                startAngle += extent;
            }

            // Draw labels
            int legendX = cx + radius + 20;
            int legendY = cy - radius;
            int lineHeight = 20;
            for (int i = 0; i < labels.size(); i++) {
                g2.setColor(getColorForIndex(i, data.size()));
                g2.fillRect(legendX, legendY + i * lineHeight, 10, 10);
                g2.setColor(Color.BLACK);
                g2.drawString(labels.get(i), legendX + 20, legendY + i * lineHeight + 10);
            }
        }

        private Color getColorForIndex(int index, int totalColors) {
            float hue = (float) index / totalColors;
            return Color.getHSBColor(hue, 1, 1);
        }
    }

    public static void main(String[] args) {
        List<Integer> data = List.of(10, 20, 10, 15, 5);
        List<String> labels = List.of("Item 1", "Item 2", "Item 3", "Item 4", "Item 5");
        SwingUtilities.invokeLater(() -> {
            Popularity popularity = new Popularity(data, labels);
            popularity.setVisible(true);
        });
    }
}
