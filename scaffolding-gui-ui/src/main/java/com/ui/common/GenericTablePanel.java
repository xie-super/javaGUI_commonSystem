package com.ui.common;

import scaffolding.gui.common.vo.FunctionDataVO;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

import static com.ui.common.GenericModifyPanel.convertTwoDimToObjectArray;

public class GenericTablePanel {
    private JFrame jFrame;
    private JPanel jPanel;
    private JTable table;
    private JScrollPane jScrollPane;
    private List<List<Object>> dataList;
    public void show(FunctionDataVO functionDataVO) {
        jFrame = new JFrame();
        jFrame.setBounds(500, 100, 700, 500);
        jFrame.add(panel(functionDataVO));
        jFrame.setVisible(true);
    }

    public JPanel panel(FunctionDataVO functionDataVO) {
        this.dataList =  functionDataVO.getDataList();
        Object[][] data = convertTwoDimToObjectArray(dataList);
        String[] chineseHeard = functionDataVO.getFunctionHeaderList().toArray(new String[0]);;
        jPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Gradient background
                Graphics2D g2d = (Graphics2D) g;
                Color startColor = new Color(255, 223, 186);
                Color endColor = new Color(8, 234, 234);
                g2d.setPaint(new GradientPaint(0, 0, startColor, 0, getHeight(), endColor));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        table = new JTable(data, chineseHeard);
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(255, 240, 210)); // Alternating row colors
                return c;
            }
        });

        table.setRowHeight(30);
        jScrollPane = new JScrollPane(table);
        jScrollPane.setBounds(0, 0, 690, 490);
        jPanel.add(jScrollPane);
        return jPanel;
    }

    public static void main(String[] args) throws SQLException {
    }
}
