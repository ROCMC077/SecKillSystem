-- MySQL dump 10.19  Distrib 10.3.37-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: seckill
-- ------------------------------------------------------
-- Server version	10.3.39-MariaDB-0ubuntu0.20.04.2

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
-- Table structure for table `t_goods`
--

DROP TABLE IF EXISTS `t_goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `goods_name` varchar(45) DEFAULT NULL COMMENT '商品名稱',
  `goods_title` varchar(45) DEFAULT NULL COMMENT '商品標題',
  `goods_img` varchar(45) DEFAULT NULL COMMENT '商品圖片',
  `goods_detail` longtext DEFAULT NULL COMMENT '商品細節',
  `goods_price` decimal(10,2) DEFAULT 0.00 COMMENT '商品價格',
  `goods_stock` int(11) DEFAULT 0 COMMENT '商品庫存,-1表示沒有限制',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci COMMENT='商品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_goods`
--

LOCK TABLES `t_goods` WRITE;
/*!40000 ALTER TABLE `t_goods` DISABLE KEYS */;
INSERT INTO `t_goods` VALUES (1,'IPHONE 13','IPHONE 13 128GB','/img/iphone13.png','IPHONE 13 128GB',14590.00,100),(2,'IPHONE 13 128GB PRO','IPHONE 13 128GB PRO','/img/iphone13pro.png','IPHONE 13 128GB PRO',17680.00,100);
/*!40000 ALTER TABLE `t_goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_order`
--

DROP TABLE IF EXISTS `t_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '訂單ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用戶ID',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品ID',
  `delivery_addr_id` int(11) DEFAULT NULL COMMENT '收貨地址ID',
  `goods_name` varchar(45) DEFAULT NULL COMMENT '冗余過來的商品名稱',
  `goods_count` int(11) DEFAULT 0 COMMENT '商品數量',
  `goods_price` decimal(10,2) DEFAULT 0.00 COMMENT '商品價格',
  `order_channel` tinyint(4) DEFAULT 0 COMMENT '1:pc 2:android 3:ios',
  `status` tinyint(4) DEFAULT 0 COMMENT '0:新建未付款 1:已付款 2:已發貨 3:已收貨 4:已退款 5:已完成',
  `create_date` datetime DEFAULT NULL COMMENT '訂單創建時間',
  `pay_date` datetime DEFAULT NULL COMMENT '付款時間',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci COMMENT='訂單表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_order`
--

LOCK TABLES `t_order` WRITE;
/*!40000 ALTER TABLE `t_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_seckill_goods`
--

DROP TABLE IF EXISTS `t_seckill_goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_seckill_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '秒殺商品ID',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品ID',
  `seckill_price` decimal(10,2) DEFAULT 0.00 COMMENT '秒殺價格',
  `stock_count` int(11) DEFAULT 0 COMMENT '庫存數量',
  `start_date` datetime DEFAULT NULL COMMENT '秒殺開始時間',
  `end_date` datetime DEFAULT NULL COMMENT '秒殺結束時間',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='秒殺商品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_seckill_goods`
--

LOCK TABLES `t_seckill_goods` WRITE;
/*!40000 ALTER TABLE `t_seckill_goods` DISABLE KEYS */;
INSERT INTO `t_seckill_goods` VALUES (1,1,1459.00,9,'2024-09-04 10:31:00','2024-11-15 10:31:40'),(2,2,1768.00,10,'2024-07-07 08:00:00','2024-07-07 09:00:00');
/*!40000 ALTER TABLE `t_seckill_goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_seckill_order`
--

DROP TABLE IF EXISTS `t_seckill_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_seckill_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '秒殺訂單ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用戶ID',
  `order_id` int(11) DEFAULT NULL COMMENT '訂單ID',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `seckill_uid_gid` (`user_id`,`goods_id`) USING BTREE COMMENT '用戶id 加 商品id 的唯一索引,解決同一用戶重複秒殺商品'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='秒殺訂單表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_seckill_order`
--

LOCK TABLES `t_seckill_order` WRITE;
/*!40000 ALTER TABLE `t_seckill_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_seckill_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL COMMENT '用戶id,電話號碼',
  `nickname` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL COMMENT 'MD5(MD5(pass明文+固定salt)+salt)',
  `salt` varchar(45) DEFAULT NULL,
  `head` varchar(45) DEFAULT NULL COMMENT '頭像',
  `register_date` datetime DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `login_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci COMMENT='User';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` VALUES (1234567890,'admin','5989b39462175829d9b503c035b3f26e','seckill',NULL,'2024-09-03 15:19:53',NULL,NULL);
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-12 10:02:04
