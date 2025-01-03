package com.ui.common;

import lombok.SneakyThrows;
import scaffolding.gui.common.vo.FunctionDataVO;
import scaffolding.gui.service.impl.FunctionImpl;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GenericModifyPanel {
    private JFrame jFrame;
    private JPanel jPanel;
    private JTabbedPane tabbedPane;
    private String[] chineseHeard;
    private Object[][] data;
    private JTable table;
    private JScrollPane jScrollPane;
    private DefaultTableModel model;
    private List<List<Object>> dataList;
    private String tableName;
    public GenericModifyPanel() {
        // 在构造方法中初始化 DefaultTableModel
        this.model = new DefaultTableModel();
        this.dataList = new ArrayList<>();
    }
    public void show(FunctionDataVO functionDataVO) {
        jFrame = new JFrame();
        jFrame.setBounds(500, 100, 650, 600);
        jFrame.add(panel(functionDataVO));
        jFrame.setVisible(true);
    }

    public JPanel panel(FunctionDataVO functionDataVO) {
        this.dataList =  functionDataVO.getDataList();
        this.chineseHeard =functionDataVO.getFunctionHeaderList().toArray(new String[0]);
        this.tableName = functionDataVO.getTableName();
        this.data = convertTwoDimToObjectArray(dataList);
        jPanel = new JPanel(new BorderLayout());
        tabbedPane = new JTabbedPane();
        JPanel modifyPanel = createModifyPanel();
        tabbedPane.addTab("修改1", modifyPanel);
        jPanel.add(tabbedPane, BorderLayout.CENTER);

        return jPanel;
    }

    private JPanel createModifyPanel() {

        JPanel modifyPanel = new JPanel(null);
        JButton insertProjectButton = new JButton("添加");
        insertProjectButton.setBounds(150, 5, 80, 25);
        modifyPanel.add(insertProjectButton);

        JButton updateProjectButton = new JButton("修改");
        updateProjectButton.setBounds(300, 5, 80, 25);
        modifyPanel.add(updateProjectButton);

        JButton deleteProjectButton = new JButton("删除");
        deleteProjectButton.setBounds(450, 5, 80, 25);
        modifyPanel.add(deleteProjectButton);

        // Create a new DefaultTableModel and set the data and column names
        model = new DefaultTableModel(data, chineseHeard);
        table = new JTable(model);

        table.setBackground(new Color(14, 198, 203));

        jScrollPane = new JScrollPane(table);
        jScrollPane.setBounds(0, 35, 600, 400);
        modifyPanel.add(jScrollPane);

        insertProjectButton.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] newValues = new String[chineseHeard.length];
                for (int i = 0; i < chineseHeard.length; i++) {
                    String inputValue = JOptionPane.showInputDialog(jPanel, "输入" + chineseHeard[i] + ":");
                    newValues[i] = inputValue != null ? inputValue : ""; // If user cancels, set empty string
                }
                int rowIndex = model.getRowCount() - 1;
                model.insertRow(rowIndex, newValues);

                FunctionImpl functionImpl = new FunctionImpl();
                functionImpl.insertEntity(tableName, newValues);
            }
        });

        updateProjectButton.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String[] newValues = new String[ chineseHeard.length];
                    for (int i = 0; i < chineseHeard.length; i++) {
                        String value = table.getValueAt(selectedRow, i).toString();
                        newValues[i] = value;
                    }
                   FunctionImpl functionImpl = new FunctionImpl();
                   functionImpl.updateEntity(tableName, newValues);
                }
            }
        });


        deleteProjectButton.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String identifierValue = table.getValueAt(selectedRow, 0).toString();

                    model.removeRow(selectedRow);
                    dataList.remove(selectedRow);
                    FunctionImpl functionImpl = new FunctionImpl();
                    functionImpl.deleteEntity(tableName, identifierValue);
                } else {
                    JOptionPane.showMessageDialog(modifyPanel, "请先选择要删除的行");
                }
            }
        });

        return modifyPanel;
    }
    public static Object[][] convertTwoDimToObjectArray(List<List<Object>> list) {
        int rows = list.size();
        int cols = list.isEmpty() ? 0 : list.get(0).size();
        Object[][] array = new Object[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                array[i][j] = list.get(i).get(j);
            }
        }
        return array;
    }



    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
////            @SneakyThrows
////            @Override
////            public void run() {
////                String[] heard = {"id", "name", "sex", "clazz", "password"};
////                Gen<Student> modifyPanel = new Gen<>();
////                Student student = new Student(); // Create an instance of Student (you might need to set some values)
////
////                student.setSex("男");
////                modifyPanel.show(heard, student, "sex");
////            }
//        });
    }
}
