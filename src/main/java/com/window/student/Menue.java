package com.window.student;

import com.achieve.DB;
import com.achieve.entity.Open;
import com.achieve.entity.Sport;
import com.achieve.entity.SportInformation;
import com.achieve.entity.Student;
import com.util.data.Cookie;
import com.window.ChangePassword;
import com.window.Login;
import com.window.admin.ClazzInfPanel;
import com.window.admin.NoticeMsgPanel;
import com.window.admin.Top8InfPanel;
import com.window.common.GenericInfoPanel;
import com.window.common.GenericTablePanel;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Menue {
    private JFrame jFrame;
    private JPanel jPanel;
    private JSplitPane splitPane;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JButton exitButton;
    private JButton project;
    private JButton infStatisticsButton;
    private JButton clazzStatisticsButton;
    private JButton choose;
    private JButton signUp;
    private JButton mySport;
    private JButton changePassword;
    private JButton top8Button;
    private JButton noticeButton;
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
                    List<Student> studentList = DB.select(new Student(username), "name");
                    GenericInfoPanel<Student> infoPanel = new GenericInfoPanel<>();
                    infoPanel.show(studentList.get(0));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });



        choose = new JButton("查看所有运动项目");
        choose.setBackground(new Color(255, 255, 255)); // 暖色按钮背景色
        choose.setForeground(Color.RED);
        choose.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] heard = {"sportId", "sportName", "startTime", "endTime", "type"};
                GenericTablePanel<Sport> tablePanel = new GenericTablePanel<>();
                Sport sport = new Sport(); // Create an instance of Student (you might need to set some values)
                tablePanel.show(heard, sport, "");


            }
        });

        signUp = new JButton("报名运动项目");
        signUp.setBackground(new Color(255, 255, 255)); // 暖色按钮背景色
        signUp.setForeground(Color.RED);
        signUp.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel signUpPanel = new JPanel();
                signUpPanel.setLayout(new BoxLayout(signUpPanel, BoxLayout.Y_AXIS));

                JLabel sportIdLabel = new JLabel("请输入运动项目ID:");
                JTextField sportIdTextField = new JTextField();

                JButton signUpButton = new JButton("报名");
                signUpButton.addActionListener(new ActionListener() {
                    @SneakyThrows
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        List<Open> list = DB.select(new Open(), "");
                        Open open = list.get(0);
                        System.out.println("是否已开启" + open.getIfopen());
                        if(open.getIfopen() == 0){
                            JOptionPane.showMessageDialog(jFrame, "运动会未开启", "报名失败", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        String sportId = sportIdTextField.getText();
                        Cookie cookie = Cookie.getInstance();
                        String username = cookie.getUsername();

                        // 查询是否存在该运动项目
                        List<Sport> sportList = DB.select(Sport.builder().sportId(sportId).build(), "sportId");
                        if (sportList.isEmpty()) {
                            JOptionPane.showMessageDialog(jFrame, "该运动项目不存在", "报名失败", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // 查询学生信息
                        List<Student> studentList = DB.select(new Student(username), "name");
                        if (studentList.isEmpty()) {
                            JOptionPane.showMessageDialog(jFrame, "用户信息不存在", "报名失败", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        // 获取当前时间
                        String formattedDateString = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                        // 查询运动项目报名信息
                        List<SportInformation> sportInformationList = DB.select(
                                SportInformation.builder()
                                        .sportId(sportId)
                                        .id(studentList.get(0).getId())
                                        .build(),
                                "sportId");

                        if (!sportInformationList.isEmpty()) {
                            // 存在报名记录，检查报名时间是否截止
                            String endTimeString = sportList.get(0).getEndTime();
                            if (formattedDateString.compareTo(endTimeString) > 0) {
                                JOptionPane.showMessageDialog(jFrame, "报名时间已截止", "报名失败", JOptionPane.ERROR_MESSAGE);
                                return;
                            } else {
                                JOptionPane.showMessageDialog(jFrame, "你已经报过名了", "报名失败", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }

                        // 进行报名
                        SportInformation sportInformation = SportInformation.builder()
                                .sportId(sportId)
                                .id(studentList.get(0).getId())
                                .startTime(formattedDateString)
                                .build();

                        DB.insert(sportInformation);
                        JOptionPane.showMessageDialog(jFrame, "报名成功", "成功", JOptionPane.INFORMATION_MESSAGE);
                    }
                });

                signUpPanel.add(sportIdLabel);
                signUpPanel.add(sportIdTextField);
                signUpPanel.add(signUpButton);

                showContent(signUpPanel);
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
        mySport = new JButton("查看我的参赛信息");
        mySport.setBackground(new Color(255, 255, 255)); // 暖色按钮背景色
        mySport.setForeground(Color.RED);
        mySport.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {

                String[] heard = {"id", "sportId", "mark", "startTime", "adminUsername"};
                GenericTablePanel<SportInformation> tablePanel = new GenericTablePanel<>();
                Cookie cookie = Cookie.getInstance();
                String username = cookie.getUsername();
                List<Student> studentList = DB.select(new Student(username), "name");
                 // Create an instance of Student (you might need to set some values)
                tablePanel.show(heard, SportInformation.builder()
                        .id(studentList.get(0).getId())
                        .build(), "id");
            }
        });
        top8Button = new JButton("查看各个比赛前 8 名");
        top8Button.setBackground(new Color(255, 255, 255)); // 暖色按钮背景色
        top8Button.setForeground(Color.RED);
        top8Button.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                new Top8InfPanel();
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
        leftPanel.add(infStatisticsButton);
        leftPanel.add(clazzStatisticsButton);
        leftPanel.add(noticeButton);
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
