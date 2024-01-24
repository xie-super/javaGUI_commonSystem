package com.window.admin;

/**
 * Author:xie-super
 * Time:2024/1/14
 * Name:IntelliJ IDEA
 */
import com.achieve.DB;
import com.achieve.entity.Sport;
import com.achieve.service.Projectsql;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ClazzInfPanel {
    private JFrame jFrame;
    private JComboBox<String> userTypeComboBox;
    private JComboBox<String> sportNameComboBox;
    private DefaultTableModel tableModel;
    private JTable top8Table;
    private Projectsql projectsql = new Projectsql();

    public ClazzInfPanel() throws SQLException {
        jFrame = new JFrame("学院统计");


        // Initialize components




        // Initialize the table model and table
        tableModel = new DefaultTableModel();
        top8Table = new JTable(tableModel);

        // Create a panel for components with FlowLayout
        JPanel panel = new JPanel(new FlowLayout());


        // Create a JScrollPane for the JTable
        JScrollPane tableScrollPane = new JScrollPane(top8Table);

        // Create a panel for the main layout with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(panel, BorderLayout.NORTH);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Set up the frame
        jFrame.getContentPane().add(mainPanel);
        jFrame.setSize(500, 400);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);

        // Initial update of the table
        updateTop8Table();
    }

    private void updateTop8Table() {
        try {



            // Clear existing table data
            tableModel.setColumnCount(0);
            tableModel.setRowCount(0);
            tableModel.addColumn("学院信息");
            tableModel.addRow(new Object[]{"", "", ""});
            // Check if both user type and sport name are selected
            List<String> top8InfoList;
            top8InfoList = projectsql.findTop8ByClazz();
            for (String info : top8InfoList) {
                tableModel.addRow(new Object[]{info});
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }






    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new ClazzInfPanel();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
