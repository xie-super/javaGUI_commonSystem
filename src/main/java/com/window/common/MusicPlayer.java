package com.window.common;
// 导入swing库

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// 创建一个MusicPlayer类，继承自JFrame
public class MusicPlayer extends JFrame {

    // 定义一些组件
    private JTextField searchField; // 搜索框
    private JButton searchButton; // 搜索按钮
    private JList<String> menuList; // 菜单列表
    private JLabel playlistLabel; // 播放列表标签
    private JLabel albumLabel; // 专辑标签
    private JLabel songLabel; // 歌曲标签
    private JLabel artistLabel; // 艺术家标签
    private JLabel coverLabel; // 封面标签
    private JTable songTable; // 歌曲表格
    private JButton playButton; // 播放按钮
    private JButton prevButton; // 上一首按钮
    private JButton nextButton; // 下一首按钮
    private JSlider progressSlider; // 进度条
    private JLabel currentTimeLabel; // 当前时间标签
    private JLabel totalTimeLabel; // 总时间标签
    private JButton settingsButton; // 设置按钮
    private JButton notificationsButton; // 通知按钮

    // 定义一些常量
    private static final int WIDTH = 800; // 窗口宽度
    private static final int HEIGHT = 600; // 窗口高度
    private static final String TITLE = "QQ音乐"; // 窗口标题
    private static final String[] MENU_ITEMS = {"音乐库", "电台", "播放列表", "上传的歌单", "喜欢"}; // 菜单项
    private static final String PLAYLIST_NAME = "QQ音乐5023首"; // 播放列表名称
    private static final String ALBUM_NAME = "My Everything"; // 专辑名称
    private static final String SONG_NAME = "One Last Time"; // 歌曲名称
    private static final String ARTIST_NAME = "Ariana Grande"; // 艺术家名称
    private static final String COVER_PATH = "cover.jpg"; // 封面图片路径
    private static final String[] COLUMN_NAMES = {"", "歌曲", "歌手", "专辑"}; // 表格列名
    private static final Object[][] DATA = { // 表格数据
            {"", "One Last Time", "Ariana Grande", "My Everything"},
            {"", "DINOSAUR", "AKMU", "SUMMER EPISODE"},
            {"", "Call of Silence", "進撃の巨人", "Season 2 オリジナルサウンドトラック"}
    };
    private static final String PLAY_ICON = "play.png"; // 播放图标路径
    private static final String PAUSE_ICON = "pause.png"; // 暂停图标路径
    private static final String PREV_ICON = "prev.png"; // 上一首图标路径
    private static final String NEXT_ICON = "next.png"; // 下一首图标路径
    private static final String SETTINGS_ICON = "settings.png"; // 设置图标路径
    private static final String NOTIFICATIONS_ICON = "notifications.png"; // 通知图标路径

    // 构造方法
    public MusicPlayer() {
        // 设置窗口属性
        setSize(WIDTH, HEIGHT);
        setTitle(TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 创建搜索框和搜索按钮
        searchField = new JTextField(20);
        searchButton = new JButton("搜索");

        // 创建菜单列表
        menuList = new JList<>(MENU_ITEMS);
        menuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        menuList.setSelectedIndex(0);

        // 创建播放列表标签和专辑标签
        playlistLabel = new JLabel(PLAYLIST_NAME);
        playlistLabel.setFont(new Font("宋体", Font.BOLD, 16));
        albumLabel = new JLabel(ALBUM_NAME);
        albumLabel.setFont(new Font("宋体", Font.PLAIN, 14));

        // 创建歌曲标签和艺术家标签
        songLabel = new JLabel(SONG_NAME);
        songLabel.setFont(new Font("宋体", Font.BOLD, 18));
        artistLabel = new JLabel(ARTIST_NAME);
        artistLabel.setFont(new Font("宋体", Font.PLAIN, 16));

        // 创建封面标签
        coverLabel = new JLabel();
        coverLabel.setIcon(new ImageIcon(COVER_PATH));

        // 创建歌曲表格
        songTable = new JTable(DATA, COLUMN_NAMES);
        songTable.setRowHeight(40);
        songTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        songTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        songTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        songTable.getColumnModel().getColumn(3).setPreferredWidth(200);

        // 创建播放按钮，上一首按钮，下一首按钮
        playButton = new JButton();
        playButton.setIcon(new ImageIcon(PLAY_ICON));
        prevButton = new JButton();
        prevButton.setIcon(new ImageIcon(PREV_ICON));
        nextButton = new JButton();
        nextButton.setIcon(new ImageIcon(NEXT_ICON));

        // 创建进度条，当前时间标签，总时间标签
        progressSlider = new JSlider(0, 100, 0);
        currentTimeLabel = new JLabel("00:00");
        totalTimeLabel = new JLabel("03:17");

        // 创建设置按钮和通知按钮
        settingsButton = new JButton();
        settingsButton.setIcon(new ImageIcon(SETTINGS_ICON));
        notificationsButton = new JButton();
        notificationsButton.setIcon(new ImageIcon(NOTIFICATIONS_ICON));

        // 添加组件到窗口
        add(createTopPanel(), BorderLayout.NORTH);
        add(createLeftPanel(), BorderLayout.WEST);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
        add(createRightPanel(), BorderLayout.EAST);

        // 设置窗口可见
        setVisible(true);
    }

    // 创建顶部面板
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(searchField);
        panel.add(searchButton);
        return panel;
    }

    // 创建左侧面板
    private JPanel createLeftPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(menuList, BorderLayout.CENTER);
        return panel;
    }

    // 创建中间面板
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createPlaylistPanel(), BorderLayout.NORTH);
        panel.add(new JScrollPane(songTable), BorderLayout.CENTER);
        return panel;
    }

    // 创建播放列表面板
    private JPanel createPlaylistPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(coverLabel, BorderLayout.WEST);
        panel.add(createInfoPanel(), BorderLayout.CENTER);
        return panel;
    }

    // 创建信息面板
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.add(playlistLabel);
        panel.add(albumLabel);
        panel.add(songLabel);
        panel.add(artistLabel);
        return panel;
    }

    // 创建底部面板
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createControlPanel(), BorderLayout.CENTER);
        panel.add(createTimePanel(), BorderLayout.EAST);
        return panel;
    }

    // 创建控制面板
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(prevButton);
        panel.add(playButton);
        panel.add(nextButton);
        panel.add(progressSlider);
        return panel;
    }

    // 创建时间面板
    private JPanel createTimePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.add(currentTimeLabel);
        panel.add(totalTimeLabel);
        return panel;
    }

    // 创建右侧面板
    private JPanel createRightPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.add(settingsButton);
        panel.add(notificationsButton);
        return panel;
    }

    // 主方法
    public static void main(String[] args) {
        // 创建一个MusicPlayer对象
        new MusicPlayer();
    }
}

