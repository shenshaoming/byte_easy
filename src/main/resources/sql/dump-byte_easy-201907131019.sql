-- MySQL dump 10.13  Distrib 5.7.21, for Linux (x86_64)
--
-- Host: localhost    Database: byte_easy
-- ------------------------------------------------------
-- Server version	5.7.21-1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `persistent_logins`
--

DROP TABLE IF EXISTS `persistent_logins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persistent_logins` (
  `series` varchar(255) NOT NULL,
  `last_used` datetime DEFAULT NULL,
  `token` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`series`),
  UNIQUE KEY `UK_bqa9l0go97v49wwyx4c0u3kpd` (`token`),
  UNIQUE KEY `UK_f8t9fsfwc17s6qcbx0ath6l3h` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persistent_logins`
--

LOCK TABLES `persistent_logins` WRITE;
/*!40000 ALTER TABLE `persistent_logins` DISABLE KEYS */;
/*!40000 ALTER TABLE `persistent_logins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_generator`
--

DROP TABLE IF EXISTS `sys_generator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_generator` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `moduleName` varchar(80) DEFAULT NULL COMMENT '模块名称',
  `tableName` varchar(80) DEFAULT NULL COMMENT '表名称',
  `ignoreFlag` int(11) DEFAULT NULL COMMENT '是否忽略前缀1：是',
  `ignorePrefix` varchar(20) DEFAULT NULL COMMENT '前缀',
  `createRest` int(11) DEFAULT NULL COMMENT '是否生成rest接口1:是',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_generator`
--

LOCK TABLES `sys_generator` WRITE;
/*!40000 ALTER TABLE `sys_generator` DISABLE KEYS */;
INSERT INTO `sys_generator` VALUES (3,'admin','t_person',1,'t_',1,'2019-07-13 10:06:35','2019-07-13 10:06:35');
/*!40000 ALTER TABLE `sys_generator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_organization`
--

DROP TABLE IF EXISTS `sys_organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `available` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmeds2u6ae5usj0ko0bqj3k0eo` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_organization`
--

LOCK TABLES `sys_organization` WRITE;
/*!40000 ALTER TABLE `sys_organization` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_organization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_resource`
--

DROP TABLE IF EXISTS `sys_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `available` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `order_num` int(11) NOT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3fekum3ead5klp7y4lckn5ohi` (`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=158 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_resource`
--

LOCK TABLES `sys_resource` WRITE;
/*!40000 ALTER TABLE `sys_resource` DISABLE KEYS */;
INSERT INTO `sys_resource` VALUES (1,'\0','顶级栏目',100,NULL,0,NULL,0),(2,'\0','权限配置',8,'',0,'',1),(3,'\0','角色管理',102,'/role',0,'/role',2),(4,'\0','权限管理',103,'/resource',0,'/resource',2),(5,'\0','用户管理',101,'/user',0,'/user',2),(6,'\0','编辑',100,'/role/editor-role',1,'/role/editor-role',3),(7,'\0','添加权限节点',100,'/resource/add-permission',1,'/resource/add-permission',4),(8,'\0','添加管理员',100,'/user/add-user',1,'/user/add-user',5),(9,'\0','添加角色',100,'/role/add-role',1,'/role/add-role',3),(10,'\0','删除角色',100,'/role/delete',1,'/role/delete',3),(11,'\0','删除用户',100,'/user/delete-user',1,'/user/delete-user',5),(12,'\0','删除权限',100,'/resource/delete',1,'/resource/delete',4),(13,'\0','启用',100,'/user/available-user',1,'/user/available-user',3),(14,'\0','修改管理员密码',100,'/user/modify-password',1,'/user/modify-password',5),(16,NULL,'权限编辑',100,'/resource/editor-permission',1,'/resource/editor-permission',4),(150,'\0','编辑管理员信息',100,'/user/edit-user',1,'/user/edit-user',5),(154,NULL,'代码生成工具',1,'/generator/sysGenerator',0,'/generator/sysGenerator',1),(155,NULL,'基础页面生成',1,'/generator/sysGenerator',0,'/generator/sysGenerator',154),(156,NULL,'微信用户管理',1,'/admin/person',0,'/admin/person',157),(157,NULL,'微信用户',1,'',0,'',1);
/*!40000 ALTER TABLE `sys_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `available` bit(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,NULL,'管理员','管理员');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_resources`
--

DROP TABLE IF EXISTS `sys_role_resources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role_resources` (
  `sys_role_id` bigint(20) NOT NULL,
  `resources_id` bigint(20) NOT NULL,
  KEY `FKog6jj4v6yh9e1ilxk2mwuk75a` (`resources_id`),
  KEY `FKsqkqfd2hpr5cc2kbrtgoced2w` (`sys_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_resources`
--

LOCK TABLES `sys_role_resources` WRITE;
/*!40000 ALTER TABLE `sys_role_resources` DISABLE KEYS */;
INSERT INTO `sys_role_resources` VALUES (1,2),(1,3),(1,6),(1,9),(1,10),(1,13),(1,4),(1,7),(1,12),(1,16),(1,5),(1,8),(1,11),(1,14),(1,150),(1,154),(1,155),(1,157),(1,156);
/*!40000 ALTER TABLE `sys_role_resources` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createtime` datetime DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `available` bit(1) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `sex_type` int(11) DEFAULT NULL COMMENT '性别(0.男,1.女)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'2017-07-11 17:42:18','$2a$10$SIU57gfkh/TsIVYALXBNAeDnQzkm652FT9cg4h8wtEfC306uliyYa','2019-06-20 14:19:58','admin','','1191134106@qq.com','15030103078',1);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_roles`
--

DROP TABLE IF EXISTS `sys_user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user_roles` (
  `sys_user_id` bigint(20) NOT NULL,
  `roles_id` bigint(20) NOT NULL,
  KEY `FKdpvc6d7xqpqr43dfuk1s27cqh` (`roles_id`),
  KEY `FKd0ut7sloes191bygyf7a3pk52` (`sys_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_roles`
--

LOCK TABLES `sys_user_roles` WRITE;
/*!40000 ALTER TABLE `sys_user_roles` DISABLE KEYS */;
INSERT INTO `sys_user_roles` VALUES (1,1);
/*!40000 ALTER TABLE `sys_user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_person`
--

DROP TABLE IF EXISTS `t_person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `cre_time` datetime DEFAULT NULL COMMENT '创建时间',
  `file_path` varchar(100) DEFAULT NULL COMMENT '头像',
  `descrption` text COMMENT '简介',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_person`
--

LOCK TABLES `t_person` WRITE;
/*!40000 ALTER TABLE `t_person` DISABLE KEYS */;
INSERT INTO `t_person` VALUES (1,'ce',NULL,'/fileSuffix/20190713/20190713101256498_WAQIS2.jpg','<p>123123123<br/></p>'),(2,'123','2019-07-17 00:00:00','/fileSuffix/20190713/20190713101555845_CP6UAN.jpg','<p>123<br/></p>'),(3,'123','2019-07-13 00:02:00','/fileSuffix/20190713/20190713101609759_GONYUD.jpg','<p>123123<br/></p>');
/*!40000 ALTER TABLE `t_person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'byte_easy'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-07-13 10:19:03
