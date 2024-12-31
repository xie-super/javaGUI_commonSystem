/*
package com.ui.common;

import com.util.data.AttributeMapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

public class GenericInfoPanel<T> {
    private JFrame jFrame;
    private JPanel jPanel;
    private boolean isEditable = false;

    public void show(T entity) {
        jFrame = new JFrame("我的信息");
        jFrame.setBounds(500, 100, 400, 400);

        jFrame.add(panel(entity));
        jFrame.setVisible(true);
    }

    public JPanel panel(T entity) {
        jPanel = new JPanel(new BorderLayout());
        jPanel.setBackground(new Color(228, 238, 239)); // 背景颜色

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(228, 238, 239)); // 背景颜色

        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);

            // 创建一个新的面板用于放置图标、textLabel和valueTextField
            JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            fieldPanel.setBackground(new Color(228, 238, 239));

            // 读取图标并设置大小
            ImageIcon icon = new ImageIcon("static/img/background.png"); // 加载背景图片
            Image scaledImage = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);

            // 创建调整大小后的图标
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            // 创建包含调整大小图标的标签
            JLabel label = new JLabel(scaledIcon);
            label.setFont(new Font("宋体", Font.PLAIN, 14));
            label.setOpaque(false); // 设置背景图片不透明
            fieldPanel.add(label);

            // 匹配中文
            JLabel textLabel = new JLabel(AttributeMapper.mapAttributeToField(field.getName()) + ":");
            textLabel.setFont(new Font("宋体", Font.PLAIN, 14));
            textLabel.setPreferredSize(new Dimension(120, 20));
            textLabel.setOpaque(false); // 设置背景图片不透明
            fieldPanel.add(textLabel);

            JTextField valueTextField = new JTextField(String.valueOf(getFieldValue(entity, field)));
            valueTextField.setColumns(10);
            valueTextField.setFont(new Font("宋体", Font.PLAIN, 14));
            valueTextField.setEditable(isEditable);
            valueTextField.setOpaque(false); // 设置背景图片不透明
            fieldPanel.add(valueTextField);

            // 将fieldPanel添加到主面板
            infoPanel.add(fieldPanel);
        }

        JButton editButton = new JButton("Edit");
        editButton.setPreferredSize(new Dimension(80, 30));
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isEditable = !isEditable;
                updateEditableState(infoPanel, isEditable);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 223, 186));
        buttonPanel.add(editButton);

        jPanel.add(infoPanel, BorderLayout.CENTER);
        jPanel.add(buttonPanel, BorderLayout.SOUTH);

        return jPanel;
    }

    private void updateEditableState(JPanel panel, boolean editable) {
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanel fieldPanel = (JPanel) component;
                Component[] fieldComponents = fieldPanel.getComponents();
                for (Component fieldComponent : fieldComponents) {
                    if (fieldComponent instanceof JTextField) {
                        JTextField textField = (JTextField) fieldComponent;
                        textField.setEditable(editable);
                    }
                }
            }
        }
    }

    private Object getFieldValue(T entity, Field field) {
        try {
            return field.get(entity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return "N/A";
        }
    }



    public static void main(String[] args) {
        GenericInfoPanel<Student> infoPanel = new GenericInfoPanel<>();
        Student student = new Student("1", "John", "Male", 1);
        infoPanel.show(student);
    }
}
*/
