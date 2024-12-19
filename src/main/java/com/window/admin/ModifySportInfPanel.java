package com.window.admin;
import com.achieve.DB;
import com.achieve.service.Projectsql;
import lombok.SneakyThrows;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModifySportInfPanel {
    private JFrame jFrame;
    private JPanel jPanel;
    private JTabbedPane tabbedPane;
    private Object[][] data;
    private JTable table;
    private JScrollPane jScrollPane;
    private DefaultTableModel model;  // 添加的成员变量
    private List<SportInformationVO> dataList;
    private JComboBox<String> userTypeComboBox;
    private JComboBox<String> sportNameComboBox;
    private Projectsql projectsql = new Projectsql();
    public ModifySportInfPanel() {
        // 在构造方法中初始化 DefaultTableModel
        this.model = new DefaultTableModel();
        this.dataList = new ArrayList<>();
    }

    public void show() throws SQLException {
        jFrame = new JFrame();
        jFrame.setBounds(500, 100, 650, 600);
        jFrame.add(panel());

        jFrame.setVisible(true);
    }

    public JPanel panel() throws SQLException {
        // Initialize the table model and table
        model = new DefaultTableModel();
        table = new JTable(model);

        // Create a JScrollPane for the JTable
        JScrollPane tableScrollPane = new JScrollPane(table);

        jPanel = new JPanel(new BorderLayout());
        tabbedPane = new JTabbedPane();

        JPanel modifyPanel = createModifyPanel();

        tabbedPane.addTab("参赛信息", modifyPanel);

        // Add the user type and sport name dropdowns
        JPanel dropdownPanel = createDropdownPanel();
        jPanel.add(dropdownPanel, BorderLayout.NORTH);

        jPanel.add(tabbedPane, BorderLayout.CENTER);
        updateTable();
        return jPanel;
    }

    private JPanel createDropdownPanel() throws SQLException {
        JPanel dropdownPanel = new JPanel(new FlowLayout());

        // Dropdown for selecting user type (学生 or 老师)
        userTypeComboBox = new JComboBox<>(new String[]{"学生", "老师"});
        dropdownPanel.add(new JLabel("用户类型:"));
        dropdownPanel.add(userTypeComboBox);

        // Dropdown for selecting sport name
        List<Sport> sportList = DB.select(new Sport(), "");
        sportNameComboBox = new JComboBox<>(sportList.stream().map(Sport::getSportName).toArray(String[]::new));
        dropdownPanel.add(new JLabel("项目名称:"));
        dropdownPanel.add(sportNameComboBox);

        // Add ActionListener to refresh the table when dropdowns are changed
        ActionListener dropdownListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        };
        userTypeComboBox.addActionListener(dropdownListener);
        sportNameComboBox.addActionListener(dropdownListener);

        return dropdownPanel;
    }

    private JPanel createModifyPanel() {
        JPanel modifyPanel = new JPanel(null);

        // Create a new DefaultTableModel and set the data and column names
        model = new DefaultTableModel(data, new String[]{"ID", "姓名", "项目ID", "项目名称", "分数", "开始时间", "管理员"});
        table.setModel(model);

        table.setBackground(new Color(14, 198, 203)); // Light background color

        jScrollPane = new JScrollPane(table);
        jScrollPane.setBounds(0, 35, 600, 400);
        modifyPanel.add(jScrollPane);

        JButton insertButton = new JButton("添加");
        insertButton.setBounds(150, 5, 80, 25);
        modifyPanel.add(insertButton);

        JButton updateButton = new JButton("修改");
        updateButton.setBounds(300, 5, 80, 25);
        modifyPanel.add(updateButton);

        JButton deleteButton = new JButton("删除");
        deleteButton.setBounds(450, 5, 80, 25);
        modifyPanel.add(deleteButton);

        // Add ActionListener for insert button
        insertButton.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] newValues = new String[7]; // Assuming there are 7 columns
                for (int i = 0; i < newValues.length; i++) {
                    String inputValue = JOptionPane.showInputDialog(jPanel, "输入" + model.getColumnName(i) + ":");
                    newValues[i] = inputValue != null ? inputValue : ""; // If user cancels, set empty string
                }
                int rowIndex = model.getRowCount() - 1;


                SportInformation sportInformation = new SportInformation(newValues[0],newValues[2],Integer.parseInt(newValues[4]),newValues[5],newValues[6]);
                DB.insert(sportInformation);
                updateTable();
            }
        });

        // Add ActionListener for update button
        updateButton.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Create a new instance of T
                    System.out.println(11111);
                    System.out.println(table.getValueAt(selectedRow, 0));
                    System.out.println(table.getValueAt(selectedRow, 2));
                    System.out.println(table.getValueAt(selectedRow, 4));
                    System.out.println(table.getValueAt(selectedRow, 5));
                    System.out.println(table.getValueAt(selectedRow, 6));

                    SportInformation sportInformation = new SportInformation((String) table.getValueAt(selectedRow, 0),(String) table.getValueAt(selectedRow, 2),Integer.parseInt((String) table.getValueAt(selectedRow, 4)),(String) table.getValueAt(selectedRow, 5),(String) table.getValueAt(selectedRow, 6));
                    DB.update(sportInformation, "id");
                    updateTable();
                }else{
                    JOptionPane.showMessageDialog(null, "Please select a row to update");
                }
            }
        });

        // Add ActionListener for delete button
        deleteButton.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String  identifierValue = (String) table.getValueAt(selectedRow, 0);
                    SportInformation sportInformation = new SportInformation();
                    sportInformation.setId(identifierValue);
                    DB.delete(sportInformation, "id");
                    updateTable();
                }

            }
        });

        return modifyPanel;
    }

    private void updateTable() {
        try {
            String selectedUserType = (String) userTypeComboBox.getSelectedItem();
            String selectedSportName = (String) sportNameComboBox.getSelectedItem();

            if (selectedUserType != null && selectedSportName != null) {
                if("学生".equals(selectedUserType)){
                    dataList = projectsql.findSportInfoStudentsBySport(selectedSportName);
                }else{
                    dataList = projectsql.findSportInfoTeachersBySport(selectedSportName);
                }
                data = convertDataArray(dataList);
                model.setDataVector(data, new String[]{"ID", "姓名", "项目ID", "项目名称", "分数", "开始时间", "管理员"});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Object[][] convertDataArray(List<SportInformationVO> dataList) {
        Object[][] data = new Object[dataList.size()][7];

        for (int i = 0; i < dataList.size(); i++) {
            SportInformationVO sportInformation = dataList.get(i);

            data[i][0] = sportInformation.getId();
            data[i][1] = sportInformation.getName();
            data[i][2] = sportInformation.getSportId();
            data[i][3] = sportInformation.getSportName();
            data[i][4] = sportInformation.getMark();
            data[i][5] = sportInformation.getStartTime();
            data[i][6] = sportInformation.getAdminUsername();
        }

        return data;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new ModifySportInfPanel().show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

