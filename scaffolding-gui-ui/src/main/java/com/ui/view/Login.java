package com.ui.view;

import com.util.data.CommonData;
import com.util.data.Cookie;
import com.ui.admin.Menue;
import lombok.SneakyThrows;
import scaffolding.gui.ui.entity.Admin;
import scaffolding.gui.ui.entity.User;


import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.List;

public class Login {

    public static int id;
    private JFrame jFrame;
    private JPanel jPanel;
    private JLabel userId;
    private JLabel password;
    private JTextField idtxt;
    private JPasswordField passwordtxt;
    private JButton login;

    private JLabel captchaLabel;
    private JTextField captchaField;
    private String captchaCode;
    private JButton registerButton;

    // 添加角色选择下拉框和变量
    private JComboBox<String> roleComboBox;
    private String selectedRole = CommonData.roles[0];

    private DB db = new DB();
    public void show() {
        jFrame = new JFrame(CommonData.ProjectName);
        jFrame.setBounds(700, 200, 380, 350);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.add(panel());
        jFrame.setVisible(true);
    }

    public JPanel panel() {
        jPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Gradient background
                Graphics2D g2d = (Graphics2D) g;
                Color startColor = new Color(255, 255, 255, 255);
                Color endColor = new Color(255, 234, 234);
                g2d.setPaint(new GradientPaint(0, 0, startColor, 0, getHeight(), endColor));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        String currentUrl = System.getProperty("user.dir");
        currentUrl += "/src/com/window/img.png";
        System.out.println(currentUrl);


        ImageIcon imageIcon = new ImageIcon(currentUrl);
        Image image = imageIcon.getImage();


        int newWidth = 400; // 新宽度
        int newHeight = 400; // 新高度
        Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);


        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

// 创建带有图像的JLabel
        JLabel background = new JLabel(scaledImageIcon);
        background.setBounds(0, 0, 400, 300);
        jPanel.add(background);

        userId = new JLabel("用户名");
        userId.setBounds(50, 50, 50, 30);
        jPanel.add(userId);

        idtxt = new JTextField();
        idtxt.setBounds(120, 50, 100, 30);
        jPanel.add(idtxt);

        password = new JLabel("密码");
        password.setBounds(50, 100, 50, 30);
        jPanel.add(password);

        passwordtxt = new JPasswordField();
        passwordtxt.setBounds(120, 100, 100, 30);
        jPanel.add(passwordtxt);

        captchaLabel = new JLabel("验证码");
        captchaLabel.setBounds(50, 150, 60, 30);
        jPanel.add(captchaLabel);

        captchaField = new JTextField();
        captchaField.setBounds(120, 150, 100, 30);
        jPanel.add(captchaField);

        captchaCode = generateCaptcha(); // 生成验证码
        JLabel captchaImageLabel = new JLabel(new ImageIcon(generateCaptchaImage(captchaCode)));
        captchaImageLabel.setBounds(230, 150, 100, 30);
        jPanel.add(captchaImageLabel);

        // 添加角色选择下拉框
        roleComboBox = new JComboBox<>(CommonData.roles);
        roleComboBox.setBounds(120, 200, 100, 25);
        jPanel.add(roleComboBox);

        // 重新生成验证码按钮
        JButton refreshCaptchaButton = new JButton("刷新验证码");
        refreshCaptchaButton.setBounds(235, 200, 100, 25);
        jPanel.add(refreshCaptchaButton);

        login = new JButton("登录");
        login.setBounds(120, 240, 100, 25);
        jPanel.add(login);

        // 新添加的注册按钮
        registerButton = new JButton("注册");
        registerButton.setBounds(235, 240, 100, 25);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Register reg = new Register();
                reg.show();
            }
        });
        jPanel.add(registerButton);



        // 设置按钮和其他组件的样式...

        // 重新生成验证码按钮的点击事件
        refreshCaptchaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                captchaCode = generateCaptcha();
                captchaImageLabel.setIcon(new ImageIcon(generateCaptchaImage(captchaCode)));
            }
        });

        // 角色选择下拉框的事件监听
        roleComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox comboBox = (JComboBox) e.getSource();
                selectedRole = (String) comboBox.getSelectedItem();
            }
        });

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = idtxt.getText();
                String password = passwordtxt.getText();
                String enteredCaptcha = captchaField.getText();
                if (false && !enteredCaptcha.equalsIgnoreCase(captchaCode)) {
                    JOptionPane.showMessageDialog(null, "验证码错误", "错误", JOptionPane.ERROR_MESSAGE);
                } else {
                    System.out.println(selectedRole);

                    // 根据选择的角色执行不同的登录逻辑
                    if ("用户".equals(selectedRole)) {
                        User user = new User();
                        user.setUsername(username);
                        try {
                            List<User> list = DB.select(user, "username");
                            if (list.size() != 0 && list.get(0).getPassword().equals(password)) {
                                Cookie cookie = Cookie.getInstance();
                                cookie.setAccountType(Cookie.AccountType.STUDENT);
                                cookie.setUsername(list.get(0).getUsername());
                                cookie.setUserId(list.get(0).getUserId());
                                new com.ui.view.Menue().show();
                                jFrame.setVisible(false);
                            }else{
                                JOptionPane.showMessageDialog(jFrame, "用户名或密码错误！", "错误", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });



        return jPanel;
    }

    private String generateCaptcha() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 6;
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            captcha.append(characters.charAt(index));
        }
        return captcha.toString();
    }

    // 生成验证码图片
    private Image generateCaptchaImage(String captcha) {
        // 实现生成验证码图片的逻辑，返回包含验证码的 Image 对象
        // 可以使用 Java 的绘图 API 来创建图像
        // 这里简化示例，直接创建一个红色背景、黑色文字的图片
        int width = 100;
        int height = 30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.RED);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("宋体", Font.BOLD, 20));
        g2d.drawString(captcha, 10, 20);
        g2d.dispose();
        return image;
    }

    public static void main(String[] args) {
        Login login = new Login();
        login.show();
    }
}
