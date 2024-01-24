package com.window.admin;

/**
 * Author:xie-super
 * Time:2024/1/14
 * Name:IntelliJ IDEA
 */
import com.achieve.DB;
import com.achieve.entity.Sport;
import com.achieve.entity.SportInformation;
import com.achieve.entity.Student;
import com.achieve.entity.Teacher;
import com.achieve.service.Projectsql;
import lombok.SneakyThrows;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Top8InfPanel {
    private JFrame jFrame;
    private JComboBox<String> userTypeComboBox;
    private JComboBox<String> sportNameComboBox;
    private DefaultTableModel tableModel;
    private JTable top8Table;
    private Projectsql projectsql = new Projectsql();

    public Top8InfPanel() throws SQLException {
        jFrame = new JFrame("Top 8 Information");


        // Initialize components
        userTypeComboBox = new JComboBox<>(new String[]{"学生", "老师"});
        List<Sport> sportList = DB.select(new Sport(), "");
        sportNameComboBox = new JComboBox<>(sportList.stream().map(Sport::getSportName).toArray(String[]::new));

        userTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle user type selection
                updateTop8Table();
            }
        });

        sportNameComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle sport name selection
                updateTop8Table();
            }
        });

        // Initialize the table model and table
        tableModel = new DefaultTableModel();
        top8Table = new JTable(tableModel);

        // Create a panel for components with FlowLayout
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(userTypeComboBox);
        panel.add(sportNameComboBox);

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
            // Retrieve the selected user type and sport name
            String userType = (String) userTypeComboBox.getSelectedItem();
            String sportName = (String) sportNameComboBox.getSelectedItem();

            // Clear existing table data
            tableModel.setColumnCount(0);
            tableModel.setRowCount(0);
            tableModel.addColumn(sportName+"-"+userType+"-"+"前八名信息");
            tableModel.addRow(new Object[]{"", "", ""});
            // Check if both user type and sport name are selected
            if (userType != null && sportName != null) {
                List<String> top8InfoList;

                // Call the appropriate JDBC method based on the user type
                if ("学生".equals(userType)) {
                    top8InfoList = projectsql.findTop8StudentsBySport(sportName);
                } else if ("老师".equals(userType)) {
                    top8InfoList = projectsql.findTop8TeachersBySport(sportName);
                } else {
                    return; // Handle this case based on your requirements
                }

                // Add the retrieved top 8 information to the table model
                for (String info : top8InfoList) {
                    tableModel.addRow(new Object[]{info});
                }
            }else{
                JOptionPane.showMessageDialog(jFrame, "请选择用户类型和运动项目！", "提示", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }






    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new Top8InfPanel();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
