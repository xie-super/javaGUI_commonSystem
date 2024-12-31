package com.ui.view;

import scaffolding.gui.start.JsonParser;
import scaffolding.gui.start.config.UserConfig;
import scaffolding.gui.start.config.UserConfig.User.Function;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Menue {
    private JFrame jFrame;
    private JPanel jPanel;
    private JSplitPane splitPane;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JButton exitButton;
    public void show(List<Function> functions) {
        jFrame = new JFrame();
        jFrame.setBounds(400, 100, 800, 500);

        jFrame.add(panel(functions));

        jFrame.setVisible(true);
    }

    public JPanel panel(List<Function> functions) {
        jPanel = new JPanel(new BorderLayout());

        leftPanel = new JPanel(new GridLayout(9, 1));
        leftPanel.setBackground(new Color(14, 198, 203));
        rightPanel = new JPanel();
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(200);

        jPanel.add(splitPane, BorderLayout.CENTER);

        for (Function function : functions) {
            JButton button = new JButton(function.getFunctionName());
            button.setBackground(new Color(255, 255, 255));
            button.setForeground(Color.RED);

            // 为按钮添加事件监听器
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 这里的逻辑留空，不需要添加具体内容
                    // 可以在实际使用时动态实现逻辑
                }
            });

            // 将按钮添加到左侧面板
            leftPanel.add(button);
        }

        return jPanel;
    }


    private void showContent(JPanel contentPanel) {

        rightPanel.removeAll();
        System.out.println(contentPanel.getName());
        rightPanel.add(contentPanel);

        rightPanel.revalidate();
        rightPanel.repaint();
    }

    public static void main(String[] args) throws Exception {

        UserConfig userConfig = JsonParser.parseUserConfig();
        UserConfig.User user =  userConfig.getUsers().get(0);
        List<Function> functionList = user.getFunction();
        new Menue().show(functionList);
    }
}
