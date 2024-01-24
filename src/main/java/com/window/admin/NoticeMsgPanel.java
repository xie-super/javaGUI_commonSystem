package com.window.admin;

import com.achieve.DB;
import com.achieve.entity.Notice;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;

public class NoticeMsgPanel {

    private DefaultListModel<String> noticeListModel;
    private int i = 1;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create an instance of the NoticeMsgPanel class
            NoticeMsgPanel noticeMsgPanel = new NoticeMsgPanel();
            // Call the show method to display the notice message panel
            try {
                noticeMsgPanel.show();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    // Method to display the notice message panel
    public void show() throws SQLException {
        JFrame frame = new JFrame("公告栏");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        List<Notice> noticeData = new ArrayList<>();
        noticeData = DB.select(new Notice(), "");
        noticeListModel = new DefaultListModel<>();
        JList<String> noticeJList = new JList<>(noticeListModel);

        JButton addButton = new JButton("增加公告");
        addButton.addActionListener(e -> {
            try {
                addNotice();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(noticeJList), BorderLayout.CENTER);

        // Populate the noticeListModel with initial notice data

        for (Notice notice : noticeData) {
            noticeListModel.addElement(i+".  标题："+notice.getTitle()+"\n");
            noticeListModel.addElement("内容:"+notice.getContent());
            noticeListModel.addElement("");
            i++;
        }

        frame.getContentPane().add(panel);
        frame.setSize(600, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Method to add a new notice
    private void addNotice() throws SQLException {
        String title = JOptionPane.showInputDialog("请输入公告标题：");
        if (title != null && !title.trim().isEmpty()) {
            String content = JOptionPane.showInputDialog("请输入公告内容：");
            if (content != null) {

                noticeListModel.addElement(i+".  标题"+title+"\n");
                noticeListModel.addElement("内容:"+content);
                noticeListModel.addElement("");
                Notice notice = new Notice(title, content);
                DB.insert(notice);

                // Add the new notice to the data list (You may want to store it in a List<Notice>)
                // For simplicity, I'm using the title as data in this example
            }
        }
    }
}