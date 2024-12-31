package com.ui;

import com.ui.view.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Menue {
    private JFrame jFrame;
    private JPanel jPanel;
    private JButton student;
    private JButton start;
    private JButton signUp;
    private JButton creatNotice;
    private JButton tixing;
    public void show() {
        jFrame = new JFrame();
        jFrame.setBounds(400, 100, 300, 300);
        jFrame.add(panel());
        jFrame.setVisible(true);
    }

    public JPanel panel() {
        jPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 10, 20);

        Font buttonFont = new Font("simsun", Font.BOLD, 16);



        start = new JButton("开启运动会报名");
        start.setBackground(new Color(255, 255, 255));
        start.setForeground(Color.BLACK);
        start.setFont(buttonFont);
        jPanel.add(start, gbc);
        start.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(jFrame,
                        "确定要开启运动会吗？",
                        "确认",
                        JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    // 用户点击确认，显示运动会已开启的消息
                    JOptionPane.showMessageDialog(jFrame,
                            "本次运动会已开启，所有项目都可以报名了",
                            "运动会已开启",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        gbc.gridy = 1;

        signUp = new JButton("报名运动会");
        signUp.setBackground(new Color(155, 201, 179));
        signUp.setForeground(Color.BLACK);
        signUp.setFont(buttonFont);
        jPanel.add(signUp, gbc);
        signUp.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取学号输入
                String studentId = JOptionPane.showInputDialog(jFrame, "请输入你的学号:");

                if (studentId != null && !studentId.isEmpty()) {
                    String sportId = JOptionPane.showInputDialog(jFrame, "请输入你报名的项目IDn:");
                    // 模拟从数据库或其他数据源获取运动会项目信息
                    String eventInfo = "项目1: xxx\n项目2: xxx\n项目3: xxx";

                    // 展示运动会项目信息
                    JOptionPane.showMessageDialog(jFrame, eventInfo, "运动会项目信息", JOptionPane.INFORMATION_MESSAGE);

                    // 模拟报名成功，将信息加入 enroll 表
                    // 这里应该调用你的数据库操作方法
                    boolean signUpSuccess = true;

                    if (signUpSuccess) {
                        // 报名成功消息
                        JOptionPane.showMessageDialog(jFrame, "报名成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        // 报名失败消息
                        JOptionPane.showMessageDialog(jFrame, "报名失败，请重试！", "失败", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        gbc.gridy = 2;
        creatNotice = new JButton("生成秩序册");
        creatNotice.setBackground(new Color(155, 201, 179));
        creatNotice.setForeground(Color.BLACK);
        creatNotice.setFont(buttonFont);
        jPanel.add(creatNotice, gbc);
        creatNotice.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                jFrame.setVisible(false);
            }
        });

        gbc.gridy = 3;
        tixing = new JButton("项目提醒");
        tixing.setBackground(new Color(155, 201, 179));
        tixing.setForeground(Color.BLACK);
        tixing.setFont(buttonFont);
        jPanel.add(tixing, gbc);
        tixing.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login().show();
                jFrame.setVisible(false);
            }
        });

        jPanel.setBackground(new Color(255, 255, 255));

        return jPanel;
    }

    public static void main(String[] args) {
        new Menue().show();
    }
}
