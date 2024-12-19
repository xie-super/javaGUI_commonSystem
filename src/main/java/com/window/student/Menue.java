package com.window.student;

import com.achieve.DB;
import com.achieve.entity.*;
import com.util.data.Cookie;
import com.window.ChangePassword;
import com.window.Login;
import com.window.common.GenericInfoPanel;
import com.window.common.GenericTablePanel;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class Menue {
    private JFrame jFrame;
    private JPanel jPanel;
    private JSplitPane splitPane;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JButton exitButton;
    private JButton project;
    private JButton choose;
    private JButton signUp;
    private JButton mySport;
    private JButton changePassword;
    private DB db = new DB();
    private JLabel label1;
    public void show() {
        jFrame = new JFrame();
        jFrame.setBounds(400, 100, 800, 500);

        jFrame.add(panel());

        jFrame.setVisible(true);
    }

    public JPanel panel() {
        jPanel = new JPanel(new BorderLayout());

        leftPanel = new JPanel(new GridLayout(9, 1));
        leftPanel.setBackground(new Color(14, 198, 203)); // 左侧面板背景色
        rightPanel = new JPanel();
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(200); // 设置初始分隔位置

        jPanel.add(splitPane, BorderLayout.CENTER);
        project = new JButton("我的信息");
        project.setBackground(new Color(255, 255, 255)); // 暖色按钮背景色
        project.setForeground(Color.RED);
        project.addActionListener(new ActionListener() {
            GenericInfoPanel<Student> infoPanel = new GenericInfoPanel<>();
            Cookie cookie = Cookie.getInstance();
            String username = cookie.getUsername();

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println("sss"+username);
                    List<User> userList = DB.select(new User(username), "username");
                    GenericInfoPanel<User> infoPanel = new GenericInfoPanel<>();
                    infoPanel.show(userList.get(0));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });



        choose = new JButton("查看所有汽车信息");
        choose.setBackground(new Color(255, 255, 255)); // 暖色按钮背景色
        choose.setForeground(Color.RED);
        choose.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] heard = {"carId", "model", "status", "rentPrice"};
                GenericTablePanel<Car> tablePanel = new GenericTablePanel<>();
                Car car = new Car();
                tablePanel.show(heard, car, "");


            }
        });

        signUp = new JButton("租赁");
        signUp.setBackground(new Color(255, 255, 255)); // 暖色按钮背景色
        signUp.setForeground(Color.RED);
        signUp.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel signUpPanel = new JPanel();
                signUpPanel.setLayout(new BoxLayout(signUpPanel, BoxLayout.Y_AXIS));

                JLabel sportIdLabel = new JLabel("请输入租车ID:");
                JTextField carIdField = new JTextField();
                JLabel durationTime = new JLabel("请输入租车时间（天）:");
                JTextField durationTimeField = new JTextField();

                JButton signUpButton = new JButton("租赁");
                signUpButton.addActionListener(new ActionListener() {
                    @SneakyThrows
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        Integer carId = Integer.valueOf(carIdField.getText());
                        Cookie cookie = Cookie.getInstance();
                        String username = cookie.getUsername();

                        // 查询是否存在该运动项目
                        Car car = new Car();
                        car.setCarId(carId);
                        List<Car> carList = DB.select(car, "carId");
                        if (carList.isEmpty()) {
                            JOptionPane.showMessageDialog(jFrame, "该车辆项目不存在", "失败", JOptionPane.ERROR_MESSAGE);
                            return;
                        }else{
                            if(carList.get(0).equals("不可用")){
                                JOptionPane.showMessageDialog(jFrame, "该车辆不可用", "失败", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }
                        Integer day = Integer.valueOf(durationTimeField.getText());
                        RentalRecord rentalRecord = new RentalRecord();
                        rentalRecord.setCarId(carId);
                        rentalRecord.setUserId(cookie.getUserId());
                        rentalRecord.setStartDate(new Date());
                        long nDaysInMillis = day * 24L * 60 * 60 * 1000; // n 天对应的毫秒数
                        Date newDate = new Date(new Date().getTime() + nDaysInMillis);
                        rentalRecord.setEndDate(newDate);
                        rentalRecord.setTotalCost(carList.get(0).getRentPrice().multiply(BigDecimal.valueOf(day)));

                        DB.insert(rentalRecord);
                        JOptionPane.showMessageDialog(jFrame, "租赁成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                    }
                });

                signUpPanel.add(sportIdLabel);
                signUpPanel.add(carIdField);
                signUpPanel.add(signUpButton);
                signUpPanel.add(durationTime);
                signUpPanel.add(durationTimeField);

                showContent(signUpPanel);
            }
        });


        changePassword = new JButton("修改我的密码");
        changePassword.setBackground(new Color(255, 255, 255)); // 暖色按钮背景色
        changePassword.setForeground(Color.RED);
        changePassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                sword().panel());
                new ChangePassword().show();

            }
        });

        mySport = new JButton("查看我的租赁记录");
        mySport.setBackground(new Color(255, 255, 255)); // 暖色按钮背景色
        mySport.setForeground(Color.RED);
        mySport.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {

                String[] heard = {"userId", "carId", "startDate", "endDate", "totalCost"};
                GenericTablePanel<RentalRecord> tablePanel = new GenericTablePanel<>();
                Cookie cookie = Cookie.getInstance();
                Integer userId = cookie.getUserId();
                List<RentalRecord> studentList = DB.select(new RentalRecord(userId), "userId");
                 // Create an instance of Student (you might need to set some values)

                tablePanel.show(heard, studentList.get(0),"userId");
            }
        });


        exitButton = new JButton("退出");
        exitButton.setBackground(new Color(10, 188, 229)); // 暖色按钮背景色
        exitButton.setForeground(Color.RED);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login.id = -1;
                new Login().show();
                jFrame.setVisible(false);
            }
        });
        leftPanel.add(project);
        leftPanel.add(signUp);
        leftPanel.add(choose);
        leftPanel.add(mySport);
        leftPanel.add(changePassword);

        leftPanel.add(exitButton);



        return jPanel;
    }

    private void showContent(JPanel contentPanel) {

        rightPanel.removeAll();
        System.out.println(contentPanel.getName());
        rightPanel.add(contentPanel);

        rightPanel.revalidate();
        rightPanel.repaint();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Menue().show();
            }
        });
    }
}
