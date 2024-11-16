-- MySQL dump 10.13  Distrib 8.0.39, for Win64 (x86_64)
--
-- Host: localhost    Database: pokemondb
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `pokemon`
--
CREATE DATABASE IF NOT EXISTS PokemonDB;
USE PokemonDB;

DROP TABLE IF EXISTS `pokemon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pokemon` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `height` decimal(4,2) NOT NULL,
  `weight` decimal(5,2) NOT NULL,
  `base_experience` int NOT NULL,
  `type_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `type_id` (`type_id`),
  CONSTRAINT `pokemon_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `pokemontype` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pokemon`
--

LOCK TABLES `pokemon` WRITE;
/*!40000 ALTER TABLE `pokemon` DISABLE KEYS */;
INSERT INTO `pokemon` VALUES (1,'Bulbasaur',0.70,6.90,64,3),(2,'Charmander',0.60,8.50,62,1),(3,'Squirtle',0.50,9.00,63,2),(4,'Pikachu',0.40,6.00,112,4),(5,'Pidgey',0.30,1.80,50,5),(6,'Gardevoir',1.60,48.40,233,6),(7,'Mantyke',1.00,65.00,69,2),(8,'Chikorita',0.90,6.40,64,3),(9,'Poliwrath',1.30,54.00,230,11),(11,'Wartortle',1.00,22.50,142,2),(14,'Ivysaur',1.00,13.00,142,3),(15,'Venusaur',2.00,100.00,236,2),(16,'Charmeleon',1.10,19.00,142,1),(17,'Charizard',1.70,90.50,240,1),(19,'Blastoise',1.60,85.50,239,2);
/*!40000 ALTER TABLE `pokemon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pokemonability`
--

DROP TABLE IF EXISTS `pokemonability`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pokemonability` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ability_name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pokemonability`
--

LOCK TABLES `pokemonability` WRITE;
/*!40000 ALTER TABLE `pokemonability` DISABLE KEYS */;
INSERT INTO `pokemonability` VALUES (1,'Overgrow'),(2,'Blaze'),(3,'Torrent'),(4,'Static'),(5,'Keen Eye'),(6,'Run Away'),(7,'Adaptability'),(8,'Aerilate'),(9,'Aftermath');
/*!40000 ALTER TABLE `pokemonability` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pokemonabilitymap`
--

DROP TABLE IF EXISTS `pokemonabilitymap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pokemonabilitymap` (
  `pokemon_id` int NOT NULL,
  `ability_id` int NOT NULL,
  PRIMARY KEY (`pokemon_id`,`ability_id`),
  KEY `ability_id` (`ability_id`),
  CONSTRAINT `pokemonabilitymap_ibfk_1` FOREIGN KEY (`pokemon_id`) REFERENCES `pokemon` (`id`),
  CONSTRAINT `pokemonabilitymap_ibfk_2` FOREIGN KEY (`ability_id`) REFERENCES `pokemonability` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pokemonabilitymap`
--

LOCK TABLES `pokemonabilitymap` WRITE;
/*!40000 ALTER TABLE `pokemonabilitymap` DISABLE KEYS */;
INSERT INTO `pokemonabilitymap` VALUES (1,1),(2,2),(3,3),(4,4),(5,5),(5,6);
/*!40000 ALTER TABLE `pokemonabilitymap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pokemontype`
--

DROP TABLE IF EXISTS `pokemontype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pokemontype` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type_name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pokemontype`
--

LOCK TABLES `pokemontype` WRITE;
/*!40000 ALTER TABLE `pokemontype` DISABLE KEYS */;
INSERT INTO `pokemontype` VALUES (1,'Fire'),(2,'Water'),(3,'Grass'),(4,'Electric'),(5,'Flying'),(6,'Psychic'),(7,'Bug'),(8,'Dark'),(9,'Dragon'),(10,'Fairy'),(11,'Fighting'),(12,'Ghost'),(13,'Ground'),(14,'Ice'),(15,'Normal'),(16,'Poison'),(17,'Rock'),(18,'Steel');
/*!40000 ALTER TABLE `pokemontype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user` varchar(20) NOT NULL,
  `password` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Genaro','1234'),(2,'Alex','alex1234');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-15 12:51:54
