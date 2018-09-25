-- MySQL dump 10.13  Distrib 5.7.22, for Linux (x86_64)
--
-- Host: localhost    Database: shared_parking
-- ------------------------------------------------------
-- Server version	5.7.22-0ubuntu0.16.04.1

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
-- Current Database: `shared_parking`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `shared_parking` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `shared_parking`;

--
-- Table structure for table `car`
--

DROP TABLE IF EXISTS `car`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `car` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `licenseplate` varchar(50) DEFAULT NULL,
  `userid` int(11) NOT NULL,
  `active` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `userid` (`userid`),
  CONSTRAINT `car_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car`
--

LOCK TABLES `car` WRITE;
/*!40000 ALTER TABLE `car` DISABLE KEYS */;
/*!40000 ALTER TABLE `car` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contribution`
--

DROP TABLE IF EXISTS `contribution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contribution` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `time` datetime NOT NULL,
  `doesexist` tinyint(1) NOT NULL,
  `licenseplate` varchar(50) DEFAULT NULL,
  `userid` int(11) NOT NULL,
  `reportid` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `userid` (`userid`),
  KEY `reportid` (`reportid`),
  CONSTRAINT `contribution_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user` (`ID`),
  CONSTRAINT `contribution_ibfk_2` FOREIGN KEY (`reportid`) REFERENCES `report` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contribution`
--

LOCK TABLES `contribution` WRITE;
/*!40000 ALTER TABLE `contribution` DISABLE KEYS */;
/*!40000 ALTER TABLE `contribution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parkingoffer`
--

DROP TABLE IF EXISTS `parkingoffer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parkingoffer` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `price` int(11) NOT NULL,
  `start_dt` datetime NOT NULL,
  `end_dt` datetime NOT NULL,
  `parkingspaceid` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `parkingspaceid` (`parkingspaceid`),
  CONSTRAINT `parkingoffer_ibfk_1` FOREIGN KEY (`parkingspaceid`) REFERENCES `parkingspace` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parkingoffer`
--

LOCK TABLES `parkingoffer` WRITE;
/*!40000 ALTER TABLE `parkingoffer` DISABLE KEYS */;
/*!40000 ALTER TABLE `parkingoffer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parkingspace`
--

DROP TABLE IF EXISTS `parkingspace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parkingspace` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `postcode` int(11) NOT NULL,
  `city` varchar(50) NOT NULL,
  `street` varchar(50) NOT NULL,
  `number` int(11) NOT NULL,
  `lat` double(10,6) NOT NULL,
  `lng` double(10,6) NOT NULL,
  `userid` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `userid` (`userid`),
  CONSTRAINT `parkingspace_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parkingspace`
--

LOCK TABLES `parkingspace` WRITE;
/*!40000 ALTER TABLE `parkingspace` DISABLE KEYS */;
/*!40000 ALTER TABLE `parkingspace` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parkingtrade`
--

DROP TABLE IF EXISTS `parkingtrade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parkingtrade` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `price` int(11) NOT NULL,
  `start_dt` datetime NOT NULL,
  `end_dt` datetime NOT NULL,
  `tenantid` int(11) NOT NULL,
  `parkingspaceid` int(11) NOT NULL,
  `carid` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `tenantid` (`tenantid`),
  KEY `parkingspaceid` (`parkingspaceid`),
  KEY `carid` (`carid`),
  CONSTRAINT `parkingtrade_ibfk_2` FOREIGN KEY (`tenantid`) REFERENCES `user` (`ID`),
  CONSTRAINT `parkingtrade_ibfk_3` FOREIGN KEY (`parkingspaceid`) REFERENCES `parkingspace` (`ID`),
  CONSTRAINT `parkingtrade_ibfk_4` FOREIGN KEY (`carid`) REFERENCES `car` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parkingtrade`
--

LOCK TABLES `parkingtrade` WRITE;
/*!40000 ALTER TABLE `parkingtrade` DISABLE KEYS */;
/*!40000 ALTER TABLE `parkingtrade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rating`
--

DROP TABLE IF EXISTS `rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rating` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ratingbytenant` int(11) DEFAULT NULL,
  `ratingbylandlord` int(11) DEFAULT NULL,
  `textbytenant` text,
  `textbylandlord` text,
  `parkingtradeid` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `parkingtradeid` (`parkingtradeid`),
  CONSTRAINT `rating_ibfk_1` FOREIGN KEY (`parkingtradeid`) REFERENCES `parkingtrade` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rating`
--

LOCK TABLES `rating` WRITE;
/*!40000 ALTER TABLE `rating` DISABLE KEYS */;
/*!40000 ALTER TABLE `rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `report` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `istrade` tinyint(1) NOT NULL,
  `parkingtradeid` int(11) DEFAULT NULL,
  `parkingspaceid` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `parkingtradeid` (`parkingtradeid`),
  KEY `parkingspaceid` (`parkingspaceid`),
  CONSTRAINT `report_ibfk_1` FOREIGN KEY (`parkingtradeid`) REFERENCES `parkingtrade` (`ID`),
  CONSTRAINT `report_ibfk_2` FOREIGN KEY (`parkingspaceid`) REFERENCES `parkingspace` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `mail` varchar(50) NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `register_dt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `auth_token` varchar(50) DEFAULT NULL,
  `balance` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `mail` (`mail`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-08-30 13:40:04
