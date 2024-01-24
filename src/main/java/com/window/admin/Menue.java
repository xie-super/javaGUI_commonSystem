package com.window.admin;


import com.achieve.DB;
import com.achieve.entity.*;
import com.window.Login;

import com.window.common.GenericModifyPanel;
import com.window.common.GenericTablePanel;
import javafx.scene.effect.Light;
import lombok.SneakyThrows;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class Menue {
    private JFrame jFrame;
    private JPanel jPanel;
    private JSplitPane splitPane;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JButton studentInfButton;
    private JButton teacherInfButton;
    private JButton infStatisticsButton;
    private JButton ifOpenButton;
    private JButton clazzStatisticsButton;
    private JButton getAll;
    private JButton fenshu;


    private JButton noticeButton;


    public void show() {
        jFrame = new JFrame();
        jFrame.setBounds(400, 100, 800, 600);
        jFrame.add(panel());
        jFrame.setVisible(true);
    }

    public JPanel panel() {
        jPanel = new JPanel(new BorderLayout());

        leftPanel = new JPanel(new GridLayout(8, 1));

        leftPanel.setBackground(new Color(14, 198, 203)); // 左侧面板背景色
        rightPanel = new JPanel();
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(200); // 设置初始分隔位置

        jPanel.add(splitPane, BorderLayout.CENTER);

        studentInfButton = new JButton("学生信息");
        studentInfButton.setBackground(new Color(255, 255, 255)); // 暖色按钮背景色
        studentInfButton.setForeground(Color.RED);
        studentInfButton.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] heard = {"id", "name", "sex", "clazz", "password"};
                GenericModifyPanel<Student> tablePanel = new GenericModifyPanel<>();
                tablePanel.show(heard, new Student(), "");
            }
        });
        infStatisticsButton = new JButton("查询和统计汇总");
        infStatisticsButton.setBackground(new Color(255, 255, 255)); // 暖色按钮背景色
        infStatisticsButton.setForeground(Color.RED);
        infStatisticsButton.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                new Top8InfPanel();
            }
        });
        clazzStatisticsButton = new JButton("学院统计");
        clazzStatisticsButton.setBackground(new Color(255, 255, 255)); // 暖色按钮背景色
        clazzStatisticsButton.setForeground(Color.RED);
        clazzStatisticsButton.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClazzInfPanel();
            }


        });
        teacherInfButton = new JButton("教师信息");
        teacherInfButton.setBackground(new Color(255, 255, 255)); // 暖色按钮背景色
        teacherInfButton.setForeground(Color.RED);
        teacherInfButton.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] heard = {"id", "name", "title", "sex","phone", "password"};
                GenericModifyPanel<Teacher> tablePanel = new GenericModifyPanel<>();
                tablePanel.show(heard, new Teacher(), "");
            }
        });
        getAll = new JButton("运动项目信息");
        getAll.setBackground(new Color(255, 255, 255)); // 暖色按钮背景色
        getAll.setForeground(Color.RED);
        getAll.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] heard = {"sportId", "sportName", "startTime", "endTime","type"};
                GenericModifyPanel<Sport> tablePanel = new GenericModifyPanel<>();
                tablePanel.show(heard, new Sport(), "");
            }
        });

        fenshu = new JButton("参赛信息");
        fenshu.setBackground(new Color(255, 255, 255)); // 暖色按钮背景色
        fenshu.setForeground(Color.RED);
        fenshu.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {

               new ModifySportInfPanel().show();
            }
        });

        noticeButton = new JButton("公告栏");
        noticeButton.setBackground(new Color(255, 255, 255)); // 暖色按钮背景色
        noticeButton.setForeground(Color.RED);
        noticeButton.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                new NoticeMsgPanel().show();
                }
        });
        ifOpenButton = new JButton("开启或关闭本次运动会");
        ifOpenButton.setBackground(new Color(255, 255, 255)); // 暖色按钮背景色
        ifOpenButton.setForeground(Color.RED);
        ifOpenButton.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Open> list = DB.select(new Open(), "");
                Open open = list.get(0);
                if(open.getIfopen() == 1){
                    open.setIfopen(0);
                    JOptionPane.showMessageDialog(jFrame, "本次运动会已经关闭，无法再报名！");

                    DB.update(open,"flag");
                }else{
                    JOptionPane.showMessageDialog(jFrame, "本次运动会已开启，学生老师可以报名了！！！");
                    open.setIfopen(1);
                    DB.update(open,"flag");
                }
            }
        });



        leftPanel.add(studentInfButton);
        leftPanel.add(teacherInfButton);
        leftPanel.add(getAll);
        leftPanel.add(fenshu);
        leftPanel.add(infStatisticsButton);
        leftPanel.add(clazzStatisticsButton);

        leftPanel.add(noticeButton);
        leftPanel.add(ifOpenButton);

        return jPanel;
    }




    private void showContent(JPanel contentPanel) {
        rightPanel.removeAll();
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

