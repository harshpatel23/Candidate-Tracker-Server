-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: candidate_tracker
-- ------------------------------------------------------
-- Server version	8.0.20

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
-- Table structure for table `candidate`
--

DROP TABLE IF EXISTS `candidate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candidate` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `recruiter_id` int NOT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `contact` varchar(10) DEFAULT NULL,
  `address` text,
  `preferred_loc` varchar(45) DEFAULT NULL,
  `ectc` int DEFAULT NULL,
  `ctct` int DEFAULT NULL,
  `source` varchar(45) DEFAULT NULL,
  `cv` mediumblob,
  `current_round` int DEFAULT NULL,
  `status` enum('ready','hold','hired','rejected') DEFAULT NULL,
  `last_updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_recruiter_id_idx` (`recruiter_id`),
  CONSTRAINT `fk_recruiter_id` FOREIGN KEY (`recruiter_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidate`
--

LOCK TABLES `candidate` WRITE;
/*!40000 ALTER TABLE `candidate` DISABLE KEYS */;
/*!40000 ALTER TABLE `candidate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `candidate_skills`
--

DROP TABLE IF EXISTS `candidate_skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candidate_skills` (
  `candidate_id` int NOT NULL,
  `skill_id` int NOT NULL,
  PRIMARY KEY (`candidate_id`,`skill_id`),
  KEY `fk_skill_id_idx` (`skill_id`),
  CONSTRAINT `fk_candidate_id` FOREIGN KEY (`candidate_id`) REFERENCES `candidate` (`id`),
  CONSTRAINT `fk_skill_id` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`skill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidate_skills`
--

LOCK TABLES `candidate_skills` WRITE;
/*!40000 ALTER TABLE `candidate_skills` DISABLE KEYS */;
/*!40000 ALTER TABLE `candidate_skills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interview`
--

DROP TABLE IF EXISTS `interview`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `interview` (
  `interview_id` int NOT NULL AUTO_INCREMENT,
  `candidate_id` int NOT NULL,
  `interviewer_id` int NOT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `feedback` text,
  `updated_by` int DEFAULT NULL,
  `round_no` int DEFAULT NULL,
  PRIMARY KEY (`interview_id`),
  KEY `fk_interviewer_id_idx` (`interviewer_id`),
  KEY `fk_candidate_id_idx` (`candidate_id`),
  KEY `fk_updated_by_idx` (`updated_by`),
  CONSTRAINT `fk_interview_candidate_id` FOREIGN KEY (`candidate_id`) REFERENCES `candidate` (`id`),
  CONSTRAINT `fk_interview_interviewer_id` FOREIGN KEY (`interviewer_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_updated_by` FOREIGN KEY (`updated_by`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interview`
--

LOCK TABLES `interview` WRITE;
/*!40000 ALTER TABLE `interview` DISABLE KEYS */;
/*!40000 ALTER TABLE `interview` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interviewer_skills`
--

DROP TABLE IF EXISTS `interviewer_skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `interviewer_skills` (
  `interviewer_id` int NOT NULL,
  `skill_id` int NOT NULL,
  PRIMARY KEY (`interviewer_id`,`skill_id`),
  KEY `fk_skill_id_idx` (`skill_id`),
  CONSTRAINT `fk_interviewer_id` FOREIGN KEY (`interviewer_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_skill_interviewer_id` FOREIGN KEY (`skill_id`) REFERENCES `skills` (`skill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interviewer_skills`
--

LOCK TABLES `interviewer_skills` WRITE;
/*!40000 ALTER TABLE `interviewer_skills` DISABLE KEYS */;
/*!40000 ALTER TABLE `interviewer_skills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `role` varchar(20) NOT NULL,
  `role_string` varchar(30) NOT NULL,
  PRIMARY KEY (`role`),
  UNIQUE KEY `role_UNIQUE` (`role`),
  UNIQUE KEY `role_string_UNIQUE` (`role_string`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skills`
--

DROP TABLE IF EXISTS `skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skills` (
  `skill_id` int NOT NULL AUTO_INCREMENT,
  `skill_name` varchar(45) NOT NULL,
  PRIMARY KEY (`skill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skills`
--

LOCK TABLES `skills` WRITE;
/*!40000 ALTER TABLE `skills` DISABLE KEYS */;
/*!40000 ALTER TABLE `skills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(20) NOT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `contact` varchar(10) DEFAULT NULL,
  `isActive` tinyint DEFAULT '1',
  `manager_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk_role_idx` (`role`),
  KEY `fk_manager_id_idx` (`manager_id`),
  CONSTRAINT `fk_manager_id` FOREIGN KEY (`manager_id`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_role` FOREIGN KEY (`role`) REFERENCES `roles` (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_closure`
--

DROP TABLE IF EXISTS `user_closure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_closure` (
  `mapping_id` int NOT NULL,
  `parent_id` int DEFAULT NULL,
  `child_id` int DEFAULT NULL,
  `depth` int DEFAULT NULL,
  PRIMARY KEY (`mapping_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_closure`
--

LOCK TABLES `user_closure` WRITE;
/*!40000 ALTER TABLE `user_closure` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_closure` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-04  1:30:38
