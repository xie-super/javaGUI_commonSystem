package com.ui.view;


import scaffolding.gui.ui.entity.User;
import com.util.data.Cookie;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class ChangePassword {
    private JFrame jFrame;
    private JPanel jPanel;
    private JLabel label1;
    private JPasswordField changePassword;
    private JLabel label2;
    private JPasswordField repeatChangePassword;
    private JTextField tishi;
    private JButton update;

    public void show(){
        jFrame = new JFrame();
        jFrame.setBounds(700, 100, 300, 200);
        jFrame.add(panel());
        jFrame.setVisible(true);
    }

    public JPanel panel(){
        jPanel = new JPanel(null)
        {
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

        label1 = new JLabel("修改后密码");
        label1.setBounds(30, 30, 100, 30);
        jPanel.add(label1);

        changePassword = new JPasswordField();
        changePassword.setBounds(130, 30, 100, 30);
        jPanel.add(changePassword);

        label2 = new JLabel("重复修改后密码");
        label2.setBounds(30, 80, 100, 30);
        jPanel.add(label2);

        repeatChangePassword = new JPasswordField();
        repeatChangePassword.setBounds(130, 80, 100, 30);
        jPanel.add(repeatChangePassword);

        update = new JButton("修改");
        update.setBackground(new Color(255, 255, 255)); // Warm button color
        update.setForeground(Color.RED);
        update.setBounds(110, 130, 80, 30);
        jPanel.add(update);
        update.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            if (changePassword.getText().equals(repeatChangePassword.getText())) {
                Cookie cookie = Cookie.getInstance();
                User user = new User(cookie.getUsername());
                user.setUserId(cookie.getUserId());
                user.setPassword(changePassword.getText());
                try {
                    DB.update(user, "username");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(null, "修改成功", "成功", JOptionPane.INFORMATION_MESSAGE);

                jFrame.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "两次密码不一致", "失败", JOptionPane.INFORMATION_MESSAGE);

            }
            }
        });
        return jPanel;
    }
}
