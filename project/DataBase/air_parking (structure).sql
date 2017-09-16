-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Sep 16, 2017 at 10:26 AM
-- Server version: 5.7.18-0ubuntu0.16.04.1
-- PHP Version: 7.0.18-0ubuntu0.16.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `air_parking`
--
CREATE DATABASE IF NOT EXISTS `air_parking` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `air_parking`;

-- --------------------------------------------------------

--
-- Table structure for table `advancedschedule`
--

CREATE TABLE `advancedschedule` (
  `advschID` int(11) NOT NULL,
  `parkingID` int(11) NOT NULL,
  `sTimeOfDay` time DEFAULT NULL,
  `eTimeOfDay` time DEFAULT NULL,
  `sDayOfWeek` int(1) DEFAULT NULL,
  `eDayOfWeek` int(1) DEFAULT NULL,
  `sMonthOfYear` int(2) DEFAULT NULL,
  `eMonthOfYear` int(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `availability`
--

CREATE TABLE `availability` (
  `parkingID` int(11) NOT NULL,
  `issimple` tinyint(4) NOT NULL,
  `startdatetime` datetime DEFAULT NULL,
  `enddatetime` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE `booking` (
  `bookingID` int(11) NOT NULL,
  `parkingID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `offerID` int(11) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `bookingperiod`
--

CREATE TABLE `bookingperiod` (
  `bookingID` int(11) NOT NULL,
  `isSimple` tinyint(4) NOT NULL,
  `startdatetime` datetime DEFAULT NULL,
  `enddatetime` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `offer`
--

CREATE TABLE `offer` (
  `offerID` int(11) NOT NULL,
  `type` varchar(10) NOT NULL,
  `value` decimal(4,2) NOT NULL,
  `parkingID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `owner`
--

CREATE TABLE `owner` (
  `userID` int(11) NOT NULL,
  `bank` varchar(45) NOT NULL,
  `account` varchar(45) NOT NULL,
  `taxregistrationnumber` varchar(45) NOT NULL,
  `taxoffice` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `parking`
--

CREATE TABLE `parking` (
  `parkingID` int(11) NOT NULL,
  `ownerID` int(11) NOT NULL,
  `address` varchar(45) NOT NULL,
  `lat` decimal(10,8) NOT NULL,
  `lng` decimal(10,8) NOT NULL,
  `photo` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `review`
--

CREATE TABLE `review` (
  `reviewID` int(11) NOT NULL,
  `comment` varchar(532) DEFAULT NULL,
  `rating` int(1) DEFAULT NULL,
  `parkingID` int(11) NOT NULL,
  `userID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `userID` int(11) NOT NULL,
  `fName` varchar(45) DEFAULT NULL,
  `lName` varchar(45) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `advancedschedule`
--
ALTER TABLE `advancedschedule`
  ADD PRIMARY KEY (`advschID`),
  ADD KEY `availabilityKey5_idx` (`parkingID`);

--
-- Indexes for table `availability`
--
ALTER TABLE `availability`
  ADD PRIMARY KEY (`parkingID`),
  ADD KEY `parkingKey6_idx` (`parkingID`);

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`bookingID`),
  ADD KEY `parkingKey4_idx` (`parkingID`),
  ADD KEY `userKey3_idx` (`userID`),
  ADD KEY `offerKey1_idx` (`offerID`);

--
-- Indexes for table `bookingperiod`
--
ALTER TABLE `bookingperiod`
  ADD PRIMARY KEY (`bookingID`),
  ADD KEY `bookingID_idx` (`bookingID`);

--
-- Indexes for table `offer`
--
ALTER TABLE `offer`
  ADD PRIMARY KEY (`offerID`),
  ADD KEY `parkingKey2_idx` (`parkingID`);

--
-- Indexes for table `owner`
--
ALTER TABLE `owner`
  ADD PRIMARY KEY (`userID`);

--
-- Indexes for table `parking`
--
ALTER TABLE `parking`
  ADD PRIMARY KEY (`parkingID`),
  ADD KEY `ownerID_idx` (`ownerID`);

--
-- Indexes for table `review`
--
ALTER TABLE `review`
  ADD PRIMARY KEY (`reviewID`),
  ADD KEY `parkingKey1_idx` (`parkingID`),
  ADD KEY `userKey1_idx` (`userID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `advancedschedule`
--
ALTER TABLE `advancedschedule`
  MODIFY `advschID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=66;
--
-- AUTO_INCREMENT for table `booking`
--
ALTER TABLE `booking`
  MODIFY `bookingID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;
--
-- AUTO_INCREMENT for table `offer`
--
ALTER TABLE `offer`
  MODIFY `offerID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=107;
--
-- AUTO_INCREMENT for table `parking`
--
ALTER TABLE `parking`
  MODIFY `parkingID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=80;
--
-- AUTO_INCREMENT for table `review`
--
ALTER TABLE `review`
  MODIFY `reviewID` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `advancedschedule`
--
ALTER TABLE `advancedschedule`
  ADD CONSTRAINT `availabilityKey5` FOREIGN KEY (`parkingID`) REFERENCES `parking` (`parkingID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `availability`
--
ALTER TABLE `availability`
  ADD CONSTRAINT `parkingKey6` FOREIGN KEY (`parkingID`) REFERENCES `parking` (`parkingID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `offerKey1` FOREIGN KEY (`offerID`) REFERENCES `offer` (`offerID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `parkingKey4` FOREIGN KEY (`parkingID`) REFERENCES `parking` (`parkingID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `userKey3` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `bookingperiod`
--
ALTER TABLE `bookingperiod`
  ADD CONSTRAINT `bookingKey1` FOREIGN KEY (`bookingID`) REFERENCES `booking` (`bookingID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `offer`
--
ALTER TABLE `offer`
  ADD CONSTRAINT `parkingKey2` FOREIGN KEY (`parkingID`) REFERENCES `parking` (`parkingID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `owner`
--
ALTER TABLE `owner`
  ADD CONSTRAINT `userKey2` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `parking`
--
ALTER TABLE `parking`
  ADD CONSTRAINT `ownerID` FOREIGN KEY (`ownerID`) REFERENCES `owner` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `review`
--
ALTER TABLE `review`
  ADD CONSTRAINT `parkingKey1` FOREIGN KEY (`parkingID`) REFERENCES `parking` (`parkingID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `userKey1` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
