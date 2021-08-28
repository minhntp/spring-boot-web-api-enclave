CREATE DATABASE  IF NOT EXISTS `enclave_spring_boot_exercise` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `enclave_spring_boot_exercise`;
-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: enclave_spring_boot_exercise
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(100) NOT NULL,
  `full_name` varchar(45) DEFAULT NULL,
  `role_id` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `role_id_idx` (`role_id`),
  CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin1','$2a$10$jyz8GaaPJvJXQ0x4wXn89ODvn1x.nXw828gnV1dgddsI3YWUi3mq6','Admin 1',1),(2,'admin2','$2a$10$0VkhOCC/t7hRMO03XQcId.VecP9crU4gckbcxTb6HQcGpjbHAPeXy','Admin 2',1),(3,'user2','$2a$10$aPWGHgKnvvP5MmpPvikn.ejoWm2OrWGr6M1VUaIjZxYTJKFkcv.Ay','User 2',3),(4,'author1','$2a$10$uv3lXkXcjOofHFSP8J8lnOFPxF8xI/VKlIl82keJpyFLBsItWsNeO','Author 1',2),(5,'author2','$2a$10$7wfdDrGU6b15pU4a2bVj2OjqU3jIckUn4x73Xfs9uQwI67PGcKEye','Author 2',2),(6,'admin5','$2a$10$26tkazWvaMms3kgIUEcwSe6gwIo/ppIDLkPypXY4ihHwxJdiQW5iy','User 1',1),(27,'use2r1','$2a$10$LRB2ARKHIPB0Vfl/aT/E0emRItj4HDeT72YK4lklKP7idn4iIt17.','Author 12',2),(29,'use21','$2a$10$vDKpqjC30QNXj8i61XhAU.Cm/cjSK0rlB49TmaRv4bqygwVkVNbF.','Author 12',2),(32,'us123','$2a$10$3igLV0SFLl8Rle2te6FgBO1azG7jbMUdIKyljBk7OTMayqUDkbHRq','',1),(34,'us413','$2a$10$1fnO/5wp72E/KHY6tQiyNOd4q3nN8Dr7vTft95uC0uL8anM/KUDga','',3),(36,'uuuuu','$2a$10$Tug1zSbgUc7FUMWI0ZYUc.sk61v1cyL6z5cwR5C3.a63593he87Xu','',2),(37,'admin0','$2a$10$4uoR9Hbm9D32b54etxHFT.Rt8p1MBNeDdOe.xWmiRmj08lsJV6HbC','Admin 0',1),(38,'user0','$2a$10$7GfpX3h9tYmOvG2kMKDY8ePMTAKphLmZedfS2PPHgVwQ/Ao82EfZ.','User 0',3),(39,'author0','$2a$10$lxE.9IivFZ9iPBbKXLyTZe1jbBBiAMlvfK4TJ8wbFRHoRNj9QuJSO','Author 0',2);
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

-- Dump completed on 2021-08-24 17:08:56
