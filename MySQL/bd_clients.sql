-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: localhost    Database: bd_clients
-- ------------------------------------------------------
-- Server version	5.6.24-log

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
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clients` (
  `IdClient` int(11) NOT NULL AUTO_INCREMENT,
  `Email` varchar(254) DEFAULT NULL,
  `Nom` varchar(50) DEFAULT NULL,
  `Prenom` varchar(50) DEFAULT NULL,
  `Mdp` varchar(50) DEFAULT NULL,
  `Adresse` varchar(200) DEFAULT NULL,
  `CodePostal` varchar(4) DEFAULT NULL,
  `Pays` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`IdClient`),
  UNIQUE KEY `Email` (`Email`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES (1,'client1@gmail.com','Client1','Louis','yjhykl',NULL,NULL,NULL),(2,'client2@gmail.com','Client2','Marc','jtktt',NULL,NULL,NULL),(3,'client3@gmail.com','Client3','Laurent','azaede',NULL,NULL,NULL),(4,'client4@gmail.com','Client4','Louis','yjhykl',NULL,NULL,NULL),(5,'client5@gmail.com','Client5','Marc','jtktt',NULL,NULL,NULL),(6,'client6@gmail.com','Client6','Laurent','azaede',NULL,NULL,NULL),(7,'client7@gmail.com','Client7','Louis','yjhykl',NULL,NULL,NULL),(8,'client8@gmail.com','Client8','Marc','jtktt',NULL,NULL,NULL),(9,'client9@gmail.com','Client9','Laurent','azaede',NULL,NULL,NULL);
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comptes`
--

DROP TABLE IF EXISTS `comptes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comptes` (
  `IdCompte` int(11) NOT NULL AUTO_INCREMENT,
  `NumCompte` varchar(200) DEFAULT NULL,
  `ClientID` int(11) NOT NULL,
  `Fiable` tinyint(1) DEFAULT NULL,
  `Montant` float DEFAULT NULL,
  PRIMARY KEY (`IdCompte`),
  UNIQUE KEY `NumCompte` (`NumCompte`),
  KEY `ClientID` (`ClientID`),
  CONSTRAINT `comptes_ibfk_1` FOREIGN KEY (`ClientID`) REFERENCES `clients` (`IdClient`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comptes`
--

LOCK TABLES `comptes` WRITE;
/*!40000 ALTER TABLE `comptes` DISABLE KEYS */;
INSERT INTO `comptes` VALUES (1,'222-6655-6655',1,1,30000),(2,'255-8885-7896',1,1,500),(3,'289-1114-6589',2,1,20000),(4,'879-6555-7894',3,0,100000000),(5,'222-6655-6651',1,1,30000),(6,'255-8885-7892',1,1,500),(7,'289-1114-6583',2,1,20000),(8,'879-6555-7897',3,0,100000000),(9,'222-6655-6689',1,1,30000),(10,'255-8885-7890',1,1,500),(11,'289-1114-6594',2,1,20000),(12,'879-6555-7804',3,0,100000000);
/*!40000 ALTER TABLE `comptes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operations`
--

DROP TABLE IF EXISTS `operations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operations` (
  `IdOp` int(11) NOT NULL AUTO_INCREMENT,
  `DateOp` date DEFAULT NULL,
  `ClientID` int(11) NOT NULL,
  `CompteID` int(11) NOT NULL,
  `TypeOp` varchar(100) DEFAULT NULL,
  `Traitee` tinyint(1) DEFAULT NULL,
  `Montant` float DEFAULT NULL,
  PRIMARY KEY (`IdOp`),
  KEY `CompteID` (`CompteID`),
  KEY `ClientID` (`ClientID`),
  CONSTRAINT `operations_ibfk_1` FOREIGN KEY (`CompteID`) REFERENCES `comptes` (`IdCompte`),
  CONSTRAINT `operations_ibfk_2` FOREIGN KEY (`ClientID`) REFERENCES `clients` (`IdClient`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operations`
--

LOCK TABLES `operations` WRITE;
/*!40000 ALTER TABLE `operations` DISABLE KEYS */;
INSERT INTO `operations` VALUES (1,'2015-01-06',1,1,'d├®bit',1,1000),(2,'2015-02-25',1,2,'d├®bit',0,2500),(3,'2015-03-19',2,3,'cr├®dit',1,3000),(4,'2014-04-20',3,4,'cr├®dit',0,4000),(5,'2015-01-07',1,1,'d├®bit',1,1000),(6,'2015-02-23',1,2,'d├®bit',0,2500),(7,'2015-03-18',2,3,'cr├®dit',1,3000),(8,'2014-04-15',3,4,'cr├®dit',0,4000),(9,'2015-01-08',1,1,'cr├®dit',1,1000),(10,'2015-02-13',1,2,'cr├®dit',0,2500),(11,'2015-03-14',2,3,'cr├®dit',1,3000),(12,'2014-04-21',3,4,'cr├®dit',0,4000);
/*!40000 ALTER TABLE `operations` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-04-27  0:12:48
