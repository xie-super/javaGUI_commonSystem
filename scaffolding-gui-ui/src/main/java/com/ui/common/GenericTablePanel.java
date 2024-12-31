package com.ui.common;
import com.util.data.AttributeMapper;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

//通用列表，使用方式

/*
String[] heard = {"id", "name", "sex", "clazz", "password"};

        GenericTablePanel<Student> tablePanel = new GenericTablePanel<>();
        Student student = new Student(); // Create an instance of Student (you might need to set some values)
        student.setSex("男");
        tablePanel.show(heard, student, "sex");
* */
public class GenericTablePanel<T> {
    private JFrame jFrame;
    private JPanel jPanel;
    private JTable table;
    private JScrollPane jScrollPane;
    private DB db = new DB();
    public void show(String[] heard, T entity, String fieldName) throws SQLException {


        jFrame = new JFrame();
        jFrame.setBounds(500, 100, 700, 500);
        jFrame.add(panel(heard, entity,fieldName));
        jFrame.setVisible(true);
    }

    public JPanel panel(String[] heard, T entity, String fieldName) throws SQLException {
        List<T> dataList = db.select(entity, fieldName);
        Object[][] data = convertDataArray(dataList, heard);
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

        // Convert header to Chinese names
        String[] chineseHeard = convertHeardToChinese(heard);

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

    private String[] convertHeardToChinese(String[] heard) {
        String[] chineseHeard = new String[heard.length];
        for (int i = 0; i < heard.length; i++) {
            chineseHeard[i] = AttributeMapper.mapAttributeToField(heard[i]);
        }
        return chineseHeard;
    }




    private Object[][] convertDataArray(List<T> dataList, String[] heard) {
        Object[][] data = new Object[dataList.size()][heard.length];

        for (int i = 0; i < dataList.size(); i++) {
            T entity = dataList.get(i);

            for (int j = 0; j < heard.length; j++) {
                try {
                    Field field = entity.getClass().getDeclaredField(heard[j]);
                    field.setAccessible(true);
                    data[i][j] = field.get(entity);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return data;
    }

    public static void main(String[] args) throws SQLException {
        String[] heard = {"id", "name", "sex", "clazz", "password"};
        GenericTablePanel<Student> tablePanel = new GenericTablePanel<>();
        Student student = new Student(); // Create an instance of Student (you might need to set some values)
        student.setSex("男");
        tablePanel.show(heard, student, "sex");
    }
}
