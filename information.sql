-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: information
-- ------------------------------------------------------
-- Server version	8.0.20

--
-- Table structure for table `project`
--
DROP TABLE IF EXISTS `admin`;
CREATE TABLE admin (
    username VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255)
);
DROP TABLE IF EXISTS `notice`;
CREATE TABLE notice (
    title VARCHAR(255) PRIMARY KEY,
    content TEXT,
    createTime DATETIME,
    creator VARCHAR(255),
    type VARCHAR(255),
    modifier VARCHAR(255)
);

DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` int DEFAULT NULL,
  `title` varchar(20) DEFAULT NULL,
  `initiator` varchar(20) DEFAULT NULL,
  `studentName` varchar(20) DEFAULT NULL,
  `mark` int DEFAULT NULL
) ;
DROP TABLE IF EXISTS `open`;
CREATE TABLE open (
    ifopen INT,
    flag INT PRIMARY KEY
);

DROP TABLE IF EXISTS `sport`;
CREATE TABLE sport (
    sportId VARCHAR(255) PRIMARY KEY,
    sportName VARCHAR(255),
    startTime VARCHAR(50),
    endTime VARCHAR(50),
    type VARCHAR(50)
);
DROP TABLE IF EXISTS `sportinformation`;
CREATE TABLE sportinformation (
    id VARCHAR(255) PRIMARY KEY,
    sportId VARCHAR(255),
    mark INT,
    startTime VARCHAR(50),
    adminUsername VARCHAR(255)
);
--
-- Dumping data for table `project`
--

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `sex` varchar(4) DEFAULT NULL,
  `clazz` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL
  `isRegister` int
) ;


--
-- Dumping data for table `student`
--



--
-- Table structure for table `titletable`
--

DROP TABLE IF EXISTS `titletable`;

CREATE TABLE `titletable` (
  `title` varchar(20) DEFAULT NULL,
  `initiator` varchar(20) DEFAULT NULL,
  `clazz` varchar(20) DEFAULT NULL,
  `id` int DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `date` varchar(20) DEFAULT NULL,
  `classroom` varchar(20) DEFAULT NULL
) ;

--
-- Dumping data for table `titletable`
--


INSERT INTO `titletable` VALUES ('数据结构','赵四','二年级',1102,'李土土','星期一','1-111'),('Java','王五','一年级',1101,'张三',NULL,NULL),('Java','王五',NULL,NULL,NULL,NULL,NULL),('c++','张三',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `titletable` ENABLE KEYS */;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;

CREATE TABLE `teacher` (
  `id` int NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `title` varchar(10) DEFAULT NULL,
  `sex` varchar(5) DEFAULT NULL,
  `phone` int DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;


-- Dump completed on 2020-10-23 16:38:45
-- 插入数据到 admin 表
INSERT INTO admin (username, password) VALUES ('管理员1', '密码1'), ('管理员2', '密码2');

-- 插入数据到 notice 表
INSERT INTO notice (title, content, createTime, creator, type, modifier)
VALUES ('通知1', '这是第一条通知内容', '2023-01-01 10:00:00', '用户1', '类型1', '修改者1'),
       ('通知2', '这是第二条通知内容', '2023-01-02 11:00:00', '用户2', '类型2', '修改者2');

-- 插入数据到 project 表
INSERT INTO project (id, title, initiator, studentName, mark)
VALUES (1, '项目1', '发起人1', '学生1', 90),
       (2, '项目2', '发起人2', '学生2', 85);

-- 插入数据到 open 表
INSERT INTO open (ifopen, flag) VALUES (1, 101), (0, 102);

-- 插入数据到 sport 表
INSERT INTO sport (sportId, sportName, startTime, endTime, type)
VALUES ('S1', '篮球', '2023-01-01 10:00:00', '2023-01-01 12:00:00', '团队'),
       ('S2', '游泳', '2023-01-02 11:00:00', '2023-01-02 13:00:00', '个人');

-- 插入数据到 sportinformation 表
INSERT INTO sportinformation (id, sportId, mark, startTime, adminUsername)
VALUES ('SI1', 'S1', 95, '2023-01-01 10:00:00', '管理员1'),
       ('SI2', 'S2', 90, '2023-01-02 11:00:00', '管理员2');

-- 插入数据到 student 表
INSERT INTO student (id, name, sex, clazz, password, isRegister)
VALUES (1, 'student1', '男', '班级A', '密码1', 1),
       (2, 'student2', '女', '班级B', '密码2', 0);

-- 插入数据到 teacher 表
INSERT INTO teacher (id, name, title, sex, phone, password)
VALUES (1, 'teacher1', '教授', '男', 1234567890, '密码1'),
       (2, 'teacher2', '副教授', '女', 9876543210, '密码2');