package com.window;
import com.achieve.DB;
import com.achieve.DB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JFrame {

    private JTextField searchField;
    private JButton searchButton;
    private JList<String> menuList;
    private JLabel infoLabel;
    private JLabel reportLabel;
    private JLabel passwordLabel;
    private JLabel exitLabel;
    private JLabel coverLabel;
    private JTable infoTable;
    private JButton reportButton;
    private JButton passwordButton;
    private JButton exitButton;
    private JButton settingsButton;
    private JButton notificationsButton;
    private DB db = new DB();

    private static final int WIDTH = 900;
    private static final int HEIGHT = 600;
    private static final String TITLE = "我的菜单";
    private static final String[] MENU_ITEMS = {"我的信息", "报道", "修改我的密码", "退出"};
    private static final String INFO_NAME = "我的信息";
    private static final String REPORT_NAME = "报道";
    private static final String PASSWORD_NAME = "修改我的密码";
    private static final String EXIT_NAME = "退出";
    private static final String COVER_PATH = "cover.jpg";
    private static final String[] COLUMN_NAMES = {"姓名", "学号", "班级", "专业"};
    private static final Object[][] DATA = {
            {"张三", "2021001", "软件工程1班", "软件工程"},
            {"李四", "2021002", "软件工程2班", "软件工程"},
            {"王五", "2021003", "软件工程3班", "软件工程"}
    };
    private static final String REPORT_ICON = "report.png";
    private static final String PASSWORD_ICON = "password.png";
    private static final String EXIT_ICON = "exit.png";
    private static final String SETTINGS_ICON = "settings.png";
    private static final String NOTIFICATIONS_ICON = "notifications.png";

    public Menu() {
        setSize(WIDTH, HEIGHT);
        setTitle(TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        searchField = new JTextField(20);
        searchButton = new JButton("搜索");

        menuList = new JList<>(MENU_ITEMS);
        menuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        menuList.setSelectedIndex(0);

        infoLabel = new JLabel(INFO_NAME);
        infoLabel.setFont(new Font("宋体", Font.BOLD, 16));
        reportLabel = new JLabel(REPORT_NAME);
        reportLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        passwordLabel = new JLabel(PASSWORD_NAME);
        passwordLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        exitLabel = new JLabel(EXIT_NAME);
        exitLabel.setFont(new Font("宋体", Font.PLAIN, 14));

        coverLabel = new JLabel();
        coverLabel.setIcon(new ImageIcon(COVER_PATH));

        infoTable = new JTable(DATA, COLUMN_NAMES);
        infoTable.setRowHeight(40);
        infoTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        infoTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        infoTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        infoTable.getColumnModel().getColumn(3).setPreferredWidth(200);

        reportButton = new JButton();
        reportButton.setIcon(new ImageIcon(REPORT_ICON));
        passwordButton = new JButton();
        passwordButton.setIcon(new ImageIcon(PASSWORD_ICON));
        exitButton = new JButton();
        exitButton.setIcon(new ImageIcon(EXIT_ICON));

        settingsButton = new JButton();
        settingsButton.setIcon(new ImageIcon(SETTINGS_ICON));
        notificationsButton = new JButton();
        notificationsButton.setIcon(new ImageIcon(NOTIFICATIONS_ICON));

        add(createTopPanel(), BorderLayout.NORTH);
        add(createLeftPanel(), BorderLayout.WEST);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
        add(createRightPanel(), BorderLayout.EAST);

        setVisible(true);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(searchField);
        panel.add(searchButton);
        return panel;
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(180, HEIGHT));
        panel.setBackground(new Color(242, 242, 242)); // 设置颜色
        panel.add(menuList, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createInfoPanel(), BorderLayout.NORTH);
        panel.add(new JScrollPane(infoTable), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(coverLabel, BorderLayout.WEST);
        panel.add(createLabelPanel(), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createLabelPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.add(infoLabel);
        panel.add(reportLabel);
        panel.add(passwordLabel);
        panel.add(exitLabel);
        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(reportButton);
        panel.add(passwordButton);
        panel.add(exitButton);
        return panel;
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(new Color(254, 254, 254)); // 设置颜色
        panel.add(settingsButton);
        panel.add(notificationsButton);
        return panel;
    }

    public static void main(String[] args) {
        new Menu();
    }
}
