CREATE DATABASE  IF NOT EXISTS `air_parking` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `air_parking`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: air_parking
-- ------------------------------------------------------
-- Server version	5.7.18-log

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
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `booking` (
  `bookingID` int(11) NOT NULL AUTO_INCREMENT,
  `Date` datetime NOT NULL,
  `ScheduleID` int(11) NOT NULL,
  `OfferID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `TransactionID` varchar(45) NOT NULL,
  PRIMARY KEY (`bookingID`),
  UNIQUE KEY `TransactionID_UNIQUE` (`TransactionID`),
  KEY `scheduleKey3_idx` (`ScheduleID`),
  KEY `offerKey3_idx` (`OfferID`),
  KEY `userKey3_idx` (`userID`),
  CONSTRAINT `offerKey3` FOREIGN KEY (`OfferID`) REFERENCES `offer` (`offerID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `scheduleKey3` FOREIGN KEY (`ScheduleID`) REFERENCES `schedule` (`scheduleID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `userKey3` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comp_schedule`
--

DROP TABLE IF EXISTS `comp_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comp_schedule` (
  `comp_scheduleID` int(11) NOT NULL AUTO_INCREMENT,
  `STimeOfDay` time DEFAULT NULL,
  `ETimeOfDay` time DEFAULT NULL,
  `SDayOfWeek` int(1) DEFAULT NULL,
  `EDayOfWeek` int(1) DEFAULT NULL,
  `SMonthOfYear` int(2) DEFAULT NULL,
  `EMonthOfYear` int(2) DEFAULT NULL,
  `ScheduleID` int(11) NOT NULL,
  PRIMARY KEY (`comp_scheduleID`),
  KEY `scheduleKey4_idx` (`ScheduleID`),
  CONSTRAINT `scheduleKey4` FOREIGN KEY (`ScheduleID`) REFERENCES `schedule` (`scheduleID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comp_schedule`
--

LOCK TABLES `comp_schedule` WRITE;
/*!40000 ALTER TABLE `comp_schedule` DISABLE KEYS */;
/*!40000 ALTER TABLE `comp_schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `offer`
--

DROP TABLE IF EXISTS `offer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `offer` (
  `offerID` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  `scheduleID` int(11) NOT NULL,
  PRIMARY KEY (`offerID`),
  KEY `scheduleKey2_idx` (`scheduleID`),
  CONSTRAINT `scheduleKey2` FOREIGN KEY (`scheduleID`) REFERENCES `schedule` (`scheduleID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offer`
--

LOCK TABLES `offer` WRITE;
/*!40000 ALTER TABLE `offer` DISABLE KEYS */;
/*!40000 ALTER TABLE `offer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parking`
--

DROP TABLE IF EXISTS `parking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parking` (
  `parkingID` int(11) NOT NULL AUTO_INCREMENT,
  `Lat` decimal(8,6) NOT NULL,
  `Lng` decimal(8,6) NOT NULL,
  `Address` varchar(255) NOT NULL,
  PRIMARY KEY (`parkingID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parking`
--

LOCK TABLES `parking` WRITE;
/*!40000 ALTER TABLE `parking` DISABLE KEYS */;
/*!40000 ALTER TABLE `parking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedule` (
  `scheduleID` int(11) NOT NULL AUTO_INCREMENT,
  `StartDateTime` datetime DEFAULT NULL,
  `EndDateTime` datetime DEFAULT NULL,
  `isParkingSchedule` tinyint(4) NOT NULL,
  `isSimple` tinyint(4) NOT NULL,
  `parkingID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  PRIMARY KEY (`scheduleID`),
  KEY `parkingKaey1_idx` (`parkingID`),
  KEY `userKey1_idx` (`userID`),
  CONSTRAINT `parkingKey1` FOREIGN KEY (`parkingID`) REFERENCES `parking` (`parkingID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `userKey1` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `userID` int(11) NOT NULL AUTO_INCREMENT,
  `FName` varchar(45) DEFAULT NULL,
  `LName` varchar(45) DEFAULT NULL,
  `UserName` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `Account` varchar(45) DEFAULT NULL,
  `Bank` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
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

-- Dump completed on 2017-05-22 16:24:12
