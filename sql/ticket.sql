-- MySQL dump 10.13  Distrib 8.1.0, for macos13 (x86_64)
--
-- Host: 127.0.0.1    Database: ticketSystem
-- ------------------------------------------------------
-- Server version	8.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `airCompany`
--

DROP TABLE IF EXISTS `airCompany`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `airCompany`
(
    `companyID`   int         NOT NULL AUTO_INCREMENT,
    `companyName` varchar(50) NOT NULL,
    PRIMARY KEY (`companyID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='航空公司表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `airCompany`
--

LOCK
TABLES `airCompany` WRITE;
/*!40000 ALTER TABLE `airCompany` DISABLE KEYS */;
INSERT INTO `airCompany`
VALUES (1, '中国南方航空'),
       (2, '四川航空'),
       (3, '长龙航空'),
       (4, '中国东方航空'),
       (5, '厦门航空');
/*!40000 ALTER TABLE `airCompany` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `Cabin`
--

DROP TABLE IF EXISTS `Cabin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Cabin`
(
    `canbinID`      int NOT NULL AUTO_INCREMENT,
    `flightDate`    date          DEFAULT NULL,
    `grade`         varchar(20)   DEFAULT NULL COMMENT '座位等级',
    `seats`         int           DEFAULT NULL,
    `availableSeat` int           DEFAULT NULL,
    `fullPrice`     decimal(8, 2) DEFAULT NULL COMMENT '航空价格',
    `flightID`      varchar(50)   DEFAULT NULL,
    PRIMARY KEY (`canbinID`),
    KEY             `flight` (`flightID`),
    CONSTRAINT `flight` FOREIGN KEY (`flightID`) REFERENCES `Flight` (`flightID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='舱位表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cabin`
--

LOCK
TABLES `Cabin` WRITE;
/*!40000 ALTER TABLE `Cabin` DISABLE KEYS */;
INSERT INTO `Cabin`
VALUES (1, '2023-10-21', '头等舱', 30, 30, 5000.00, '1'),
       (2, '2023-10-21', '经济舱', 50, 50, 3000.00, '1'),
       (3, '2023-10-21', '商务舱', 100, 100, 1500.00, '1'),
       (4, '2023-10-21', '头等舱', 30, 30, 5000.00, '2'),
       (5, '2023-10-21', '经济舱', 50, 50, 3000.00, '2'),
       (6, '2023-10-21', '商务舱', 100, 100, 1500.00, '2'),
       (7, '2023-10-21', '头等舱', 20, 20, 5000.00, '3'),
       (8, '2023-10-21', '经济舱', 40, 40, 3000.00, '3'),
       (9, '2023-10-21', '商务舱', 80, 80, 1500.00, '3'),
       (10, '2023-10-21', '头等舱', 30, 30, 5000.00, '4'),
       (11, '2023-10-21', '经济舱', 50, 50, 3000.00, '4'),
       (12, '2023-10-21', '商务舱', 100, 100, 1500.00, '4'),
       (14, '2023-10-21', '头等舱', 30, 30, 5000.00, '5'),
       (15, '2023-10-21', '经济舱', 50, 50, 3000.00, '5'),
       (16, '2023-10-21', '商务舱', 100, 100, 1500.00, '5'),
       (17, '2023-10-29', '头等舱', 30, 10, 5000.00, '6'),
       (18, '2023-10-29', '经济舱', 50, 20, 3000.00, '6'),
       (19, '2023-10-29', '商务舱', 100, 66, 1500.00, '6');
/*!40000 ALTER TABLE `Cabin` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `Flight`
--

DROP TABLE IF EXISTS `Flight`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Flight`
(
    `flightID`      varchar(50) NOT NULL,
    `companyID`     int         DEFAULT NULL,
    `model`         varchar(50) DEFAULT NULL COMMENT '飞机型号',
    `fromCity`      varchar(50) DEFAULT NULL,
    `toCity`        varchar(50) DEFAULT NULL,
    `mileAge`       int         DEFAULT NULL,
    `departureDate` date        DEFAULT NULL,
    `departureTime` time        DEFAULT NULL,
    PRIMARY KEY (`flightID`),
    KEY             `companyID` (`companyID`),
    CONSTRAINT `companyID` FOREIGN KEY (`companyID`) REFERENCES `airCompany` (`companyID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='航班表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Flight`
--

LOCK
TABLES `Flight` WRITE;
/*!40000 ALTER TABLE `Flight` DISABLE KEYS */;
INSERT INTO `Flight`
VALUES ('1', 1, 'CZ3401', '广州', '成都', 2340, '2023-10-21', '08:00:00'),
       ('2', 2, '3U1067', '广州', '成都', 2340, '2023-10-21', '06:40:00'),
       ('3', 3, 'GJ5635', '广州', '成都', 2340, '2023-10-21', '21:40:00'),
       ('4', 4, 'MF8430', '昆明', '厦门', 1829, '2023-10-21', '12:18:00'),
       ('5', 5, 'MU3991', '昆明', '厦门', 1829, '2023-10-21', '19:50:00'),
       ('6', 5, 'MU5783', '昆明', '厦门', 1829, '2023-10-26', '04:20:00'),
       ('7', 1, 'CZ3401', '广州', '成都', 2340, '2023-10-23', '08:43:00');
/*!40000 ALTER TABLE `Flight` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `Passenger`
--

DROP TABLE IF EXISTS `Passenger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Passenger`
(
    `identityID` varchar(20) NOT NULL COMMENT '身份证',
    `name`       varchar(20) NOT NULL,
    `gender`     varchar(4)  DEFAULT NULL,
    `birthday`   date        DEFAULT NULL,
    `tel`        varchar(20) DEFAULT NULL,
    `pwd`        varchar(20) DEFAULT NULL,
    PRIMARY KEY (`identityID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Passenger`
--

LOCK
TABLES `Passenger` WRITE;
/*!40000 ALTER TABLE `Passenger` DISABLE KEYS */;
INSERT INTO `Passenger`
VALUES ('11111111', 'ckq', '男', '2003-09-21', '123456789', '1234567890'),
       ('213618273817281', 'ckq', '女', '2003-08-21', '15527702527', '12345'),
       ('440681200303140043', '廖欣茵', '女', '2003-03-14', '13118899333', '140043');
/*!40000 ALTER TABLE `Passenger` ENABLE KEYS */;
UNLOCK
TABLES;

--
-- Table structure for table `Ticket`
--

DROP TABLE IF EXISTS `Ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Ticket`
(
    `ordersID`   int NOT NULL AUTO_INCREMENT COMMENT '订单编号',
    `flightDate` date          DEFAULT NULL,
    `grade`      varchar(20)   DEFAULT NULL COMMENT '舱位等级',
    `seat`       varchar(20)   DEFAULT NULL COMMENT '座位号',
    `price`      decimal(8, 2) DEFAULT NULL COMMENT '购买价格',
    `gate`       int           DEFAULT NULL,
    `identityID` varchar(20)   DEFAULT NULL,
    `flightID`   varchar(50)   DEFAULT NULL,
    `line`       varchar(20)   DEFAULT NULL COMMENT '行',
    PRIMARY KEY (`ordersID`),
    KEY          `identityID` (`identityID`),
    KEY          `flightID` (`flightID`),
    CONSTRAINT `flightID` FOREIGN KEY (`flightID`) REFERENCES `Flight` (`flightID`),
    CONSTRAINT `identityID` FOREIGN KEY (`identityID`) REFERENCES `Passenger` (`identityID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='机票';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Ticket`
--

LOCK
TABLES `Ticket` WRITE;
/*!40000 ALTER TABLE `Ticket` DISABLE KEYS */;
INSERT INTO `Ticket`
VALUES (14, '2023-10-21', '头等舱', 'A(靠窗)', NULL, 34, '440681200303140043', '1', '13'),
       (16, '2023-10-21', '头等舱', 'A(靠窗)', NULL, 10, '440681200303140043', '1', '114'),
       (17, '2023-10-21', '头等舱', 'A(靠窗)', NULL, 10, '440681200303140043', '2', '7'),
       (18, '2023-10-21', '经济舱', 'B(中间)', NULL, 25, '440681200303140043', '2', '49'),
       (19, '2023-10-21', '头等舱', 'A(靠窗)', NULL, 5, '440681200303140043', '2', '9'),
       (20, '2023-10-21', '头等舱', 'A(靠窗)', NULL, 13, '11111111', '2', '13');
/*!40000 ALTER TABLE `Ticket` ENABLE KEYS */;
UNLOCK
TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-22 16:06:37
