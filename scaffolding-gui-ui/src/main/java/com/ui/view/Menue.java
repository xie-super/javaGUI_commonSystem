package com.ui.view;

import scaffolding.gui.common.util.JsonParser;
import scaffolding.gui.service.impl.FunctionImpl;
import scaffolding.gui.start.init.UserConfig;
import scaffolding.gui.start.init.UserConfig.User.Function;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
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
            
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String functionName = button.getText();
                    FunctionImpl functionImpl = new FunctionImpl();
                    FunctionDataVO functionDataVO = functionImpl.getFunctionData(functionName);
                    String className = String.format("com.ui.common.%s", functionDataVO.getPanelName());

                    try {
                        Class<?> panelClazz = Class.forName(className);
                        Method showMethod = panelClazz.getMethod("show", FunctionDataVO.class);
                        Object panelInstance = panelClazz.getDeclaredConstructor().newInstance();
                        showMethod.invoke(panelInstance, functionDataVO);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            
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
