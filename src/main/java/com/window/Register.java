package com.window;
import com.achieve.DB;
import com.achieve.entity.Student;

import com.util.data.CommonData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Register {

    private JFrame jFrame;
    private JPanel jPanel;
    private JTextField idtxt;
    private JPasswordField passwordtxt;
    private JPasswordField confirmPasswordTxt;
    private JTextField realNamelTxt;
    private JCheckBox agreeCheckbox;
    private JComboBox<String> roleComboBox;
    private DB db = new DB();
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Register register = new Register();
            register.show();
        });
    }

    public void show() {
        jFrame = new JFrame(CommonData.ProjectName+"注册");
        jFrame.setBounds(700, 200, 300, 350);

        jFrame.add(registrationPanel());
        jFrame.setVisible(true);
    }

    public JPanel registrationPanel() {
        jPanel = new JPanel(null) {
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

        String currentUrl = System.getProperty("user.dir");
        currentUrl += "/src/com/window/img.png";
        System.out.println(currentUrl);

        ImageIcon imageIcon = new ImageIcon(currentUrl);
        Image image = imageIcon.getImage();

        int newWidth = 400; // 新宽度
        int newHeight = 400; // 新高度
        Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);

        JLabel background = new JLabel(scaledImageIcon);
        background.setBounds(0, 0, 400, 300);
        jPanel.add(background);

        JLabel user = new JLabel("学号");
        user.setBounds(50, 50, 50, 30);
        jPanel.add(user);

        idtxt = new JTextField();
        idtxt.setBounds(110, 50, 100, 30);
        jPanel.add(idtxt);

        JLabel password = new JLabel("密码");
        password.setBounds(50, 90, 50, 30);
        jPanel.add(password);

        passwordtxt = new JPasswordField();
        passwordtxt.setBounds(110, 90, 100, 30);
        jPanel.add(passwordtxt);

        JLabel confirmPasswordLabel = new JLabel("确认密码");
        confirmPasswordLabel.setBounds(50, 130, 80, 30);
        jPanel.add(confirmPasswordLabel);

        confirmPasswordTxt = new JPasswordField();
        confirmPasswordTxt.setBounds(110, 130, 100, 30);
        jPanel.add(confirmPasswordTxt);

        JLabel realNamelLabel = new JLabel("姓名");
        realNamelLabel.setBounds(50, 170, 80, 30);
        jPanel.add(realNamelLabel);

        realNamelTxt = new JTextField();
        realNamelTxt.setBounds(110, 170, 100, 30);
        jPanel.add(realNamelTxt);

        // Checkbox for registration
        agreeCheckbox = new JCheckBox("同意注册条款");
        agreeCheckbox.setBounds(40, 210, 150, 30);
        jPanel.add(agreeCheckbox);

        // Dropdown for selecting roles
        roleComboBox = new JComboBox<>(CommonData.roles);
        roleComboBox.setBounds(40, 245, 90, 30);
        jPanel.add(roleComboBox);

        JButton registerButton = new JButton("注册");
        registerButton.setBounds(130, 245 , 100, 30);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedRole = (String) roleComboBox.getSelectedItem();


                try {
                    registerUser();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        jPanel.add(registerButton);

        return jPanel;
    }

    private void registerUser() throws SQLException {
        String username = idtxt.getText();
        String password = new String(passwordtxt.getPassword());
        String confirmPassword = new String(confirmPasswordTxt.getPassword());
        String realNamel = realNamelTxt.getText();
        String selectedRole = (String) roleComboBox.getSelectedItem();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || realNamel.isEmpty() || selectedRole == null) {
            JOptionPane.showMessageDialog(jFrame, "请填写所有字段", "注册失败", JOptionPane.ERROR_MESSAGE);
        } else if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(jFrame, "两次输入的密码不一致", "注册失败", JOptionPane.ERROR_MESSAGE);
        } else if (!agreeCheckbox.isSelected()) {
            JOptionPane.showMessageDialog(jFrame, "请同意注册条款", "注册失败", JOptionPane.ERROR_MESSAGE);
        } else {
            if(selectedRole.equals(CommonData.roles[0])){
                Student student = new Student(username, password,realNamel,0);
                DB.insert(student);
            }
            else if(selectedRole.equals(CommonData.roles[2])){
                JOptionPane.showMessageDialog(jFrame, CommonData.roles[2]+"不可注册", "注册失败", JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(jFrame, "注册成功", "成功", JOptionPane.INFORMATION_MESSAGE);
            
        }
    }
}
