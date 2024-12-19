package com.window.common;

import com.achieve.DB;
import com.achieve.entity.Student;
import com.util.data.AttributeMapper;
import lombok.SneakyThrows;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GenericModifyPanel<T> {
    private JFrame jFrame;
    private JPanel jPanel;
    private JTabbedPane tabbedPane;
    private String[] heard;
    private String[] chineseHeard;
    private Object[][] data;
    private JTable table;
    private JScrollPane jScrollPane;
    private DefaultTableModel model;  // 添加的成员变量
    private List<T> dataList;
    public GenericModifyPanel() {
        // 在构造方法中初始化 DefaultTableModel
        this.model = new DefaultTableModel();
        this.dataList = new ArrayList<>();
    }
    public void show(String[] heard, T entity, String fieldName) throws SQLException {
        jFrame = new JFrame();
        jFrame.setBounds(500, 100, 650, 600);
        jFrame.add(panel(heard, entity,fieldName));

        jFrame.setVisible(true);
    }

    public JPanel panel(String[] heard, T entity, String fieldName) throws SQLException {
        this.dataList = DB.select(entity, fieldName);
        this.heard = heard;
        this.chineseHeard = convertHeardToChinese(heard);
        this.data = convertDataArray(dataList, heard);

        jPanel = new JPanel(new BorderLayout());
        tabbedPane = new JTabbedPane();

        
        JPanel modifyPanel = createModifyPanel(entity);
        String tableName = entity.getClass().getSimpleName().toLowerCase();


        tabbedPane.addTab("修改1"+AttributeMapper.mapAttributeToField(tableName), modifyPanel);

        jPanel.add(tabbedPane, BorderLayout.CENTER);

        return jPanel;
    }



// Helper method to set property value using reflection
    private void setEntityProperty(T entity, String propertyName, Object value) {
        try {
            // Assuming T has a setter method for the property (e.g., setName())
            Method setterMethod = findSetterMethod(entity.getClass(), propertyName);
            Class<?>[] parameterTypes = setterMethod.getParameterTypes();
            System.out.printf(setterMethod.getName());
            if (setterMethod != null) {
                Object convertedValue = convertValue(value, parameterTypes[0]);

                // Invoke the setter method with the converted value
                setterMethod.invoke(entity, convertedValue);
            }
        } catch (IllegalAccessException | InvocationTargetException ex) {
            ex.printStackTrace(); // Handle the exception appropriately
        }
    }

    // Utility method for converting the value to the appropriate type
    private Object convertValue(Object value, Class<?> targetType) {
        if (value == null) {
            return null;
        }

        // If the value is already the correct type, return it as is
        if (targetType.isInstance(value)) {
            return value;
        }

        // Perform type conversion based on targetType
        if (targetType == int.class || targetType == Integer.class) {
            return Integer.valueOf(value.toString());
        } else if (targetType == long.class || targetType == Long.class) {
            return Long.valueOf(value.toString());
        } else if (targetType == double.class || targetType == Double.class) {
            return Double.valueOf(value.toString());
        } else if (targetType == float.class || targetType == Float.class) {
            return Float.valueOf(value.toString());
        } else if (targetType == boolean.class || targetType == Boolean.class) {
            return Boolean.valueOf(value.toString());
        } else if (targetType == String.class) {
            return value.toString();
        } else if (targetType == BigDecimal.class) {
            // Convert String to BigDecimal
            if (value instanceof String) {
                return new BigDecimal((String) value);
            } else {
                throw new IllegalArgumentException("Cannot convert value of type " + value.getClass() + " to BigDecimal");
            }
        }else if (targetType == Date.class) {
            if (value instanceof String) {
                // Parse the String into a Date using SimpleDateFormat
                try {
                    String dateString = (String) value;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Adjust format as needed
                    return sdf.parse(dateString);
                } catch (ParseException e) {
                    throw new IllegalArgumentException("Cannot convert value of type " + value.getClass() + " to Date", e);
                }
            } else {
                throw new IllegalArgumentException("Cannot convert value of type " + value.getClass() + " to Date");
            }
        }else {
            // You can add more type conversions as necessary
            throw new IllegalArgumentException("Cannot convert value of type " + value.getClass() + " to " + targetType);
        }
    }

    // Helper method to find the setter method for a property
    private Method findSetterMethod(Class<?> clazz, String propertyName) {
        String setterMethodName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(setterMethodName) && method.getParameterCount() == 1) {
                return method;
            }
        }
        return null;
    }
    private JPanel createModifyPanel(T entity) {

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

        table.setBackground(new Color(14, 198, 203)); // Light background color





        jScrollPane = new JScrollPane(table);
        jScrollPane.setBounds(0, 35, 600, 400);
        modifyPanel.add(jScrollPane);

        insertProjectButton.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt the user to input new values
                String[] newValues = new String[chineseHeard.length];
                for (int i = 0; i < chineseHeard.length; i++) {
                    String inputValue = JOptionPane.showInputDialog(jPanel, "输入" + chineseHeard[i] + ":");
                    newValues[i] = inputValue != null ? inputValue : ""; // If user cancels, set empty string
                }

                // Add the new row to the model
                int rowIndex = model.getRowCount() - 1;
                model.insertRow(rowIndex, newValues);

                // Insert into the database
                T entityToInsert;
                try {
                    entityToInsert = (T) entity.getClass().getDeclaredConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                    ex.printStackTrace(); // Handle the exception appropriately
                    return;
                }

                // Set values from newValues to the entity
                for (int i = 0; i < chineseHeard.length; i++) {
                    setEntityProperty(entityToInsert, heard[i], newValues[i]);
                }

                // Insert into the database
                DB.insert(entityToInsert);
            }
        });

        updateProjectButton.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Create a new instance of T
                    T entityToUpdate;

                    try {
                        // Assuming T has a default constructor
                        entityToUpdate = (T) entity.getClass().getDeclaredConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                        ex.printStackTrace(); // Handle the exception appropriately
                        return;
                    }

                    // Set data from the selected row using reflection
                    for (int i = 0; i < chineseHeard.length; i++) {
                        Object value = table.getValueAt(selectedRow, i);
                        // Use reflection to set the value to the corresponding property of the entity
                        setEntityProperty(entityToUpdate, heard[i], value);
                    }

                   /* // Check if the first column (heard[0]) is modified
                    Object originalValue = dataList.get(selectedRow).getClass().getDeclaredField(heard[0]).get(dataList.get(selectedRow));
                    Object updatedValue = table.getValueAt(selectedRow, 0);

                    if (!originalValue.equals(updatedValue)) {
                        // Value in the first column is modified, show a message
                        JOptionPane.showMessageDialog(jPanel, "不能修改" + chineseHeard[0] + "的值");
                        // Revert to the original value in the table
                        table.setValueAt(originalValue, selectedRow, 0);
                    } else {
                        // Call the update method with the updated entity and the property to update
                        DB.update(entityToUpdate, heard[0]); // Replace 'update' with your actual update method
                    }*/
                    DB.update(entityToUpdate, heard[0]);
                }
            }
        });


        deleteProjectButton.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the value of the identifier (heard[0]) from the selected row
                    Object identifierValue = table.getValueAt(selectedRow, 0);

                    // Create an instance of T (entityToDelete) and set the identifier field
                    T entityToDelete;
                    try {
                        entityToDelete = (T) entity.getClass().getDeclaredConstructor().newInstance();
                        setEntityProperty(entityToDelete, heard[0], identifierValue);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                        ex.printStackTrace(); // Handle the exception appropriately
                        return;
                    }

                    // Remove the row directly from the model
                    model.removeRow(selectedRow);
                    dataList.remove(selectedRow);

                    // Call the delete method to delete the entity from the database
                    DB.delete(entityToDelete, heard[0]); // Replace 'delete' with your actual delete method
                } else {
                    JOptionPane.showMessageDialog(modifyPanel, "请先选择要删除的行");
                }
            }
        });



        return modifyPanel;
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
    private String[] convertHeardToChinese(String[] heard) {
        String[] chineseHeard = new String[heard.length];
        for (int i = 0; i < heard.length; i++) {
            chineseHeard[i] = AttributeMapper.mapAttributeToField(heard[i]);
        }
        return chineseHeard;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                String[] heard = {"id", "name", "sex", "clazz", "password"};
                GenericModifyPanel<Student> modifyPanel = new GenericModifyPanel<>();
                Student student = new Student(); // Create an instance of Student (you might need to set some values)

                student.setSex("男");
                modifyPanel.show(heard, student, "sex");
            }
        });
    }
}
