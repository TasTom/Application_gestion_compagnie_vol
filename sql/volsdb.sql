-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mer. 01 oct. 2025 à 19:43
-- Version du serveur : 8.0.40
-- Version de PHP : 8.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `volsdb`
--

-- --------------------------------------------------------

--
-- Structure de la table `avion`
--

DROP TABLE IF EXISTS `avion`;
CREATE TABLE IF NOT EXISTS `avion` (
  `id_avion` int NOT NULL AUTO_INCREMENT,
  `modele` varchar(100) NOT NULL,
  `capacite` int NOT NULL,
  PRIMARY KEY (`id_avion`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `avion`
--

INSERT INTO `avion` (`id_avion`, `modele`, `capacite`) VALUES
(1, 'Airbus A320', 180),
(2, 'Boeing 737', 160),
(3, 'Boeing 787 Dreamliner', 250),
(4, 'Airbus 123456789', 600),
(5, 'Avion biplace', 1);

-- --------------------------------------------------------

--
-- Structure de la table `passager`
--

DROP TABLE IF EXISTS `passager`;
CREATE TABLE IF NOT EXISTS `passager` (
  `id_passager` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  `nationalite` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_passager`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `passager`
--

INSERT INTO `passager` (`id_passager`, `nom`, `prenom`, `nationalite`) VALUES
(1, 'Durand', 'Alice', 'Française'),
(2, 'Smith', 'John', 'Américaine'),
(3, 'Lopez', 'Maria', 'Espagnole'),
(4, 'DUPONT', 'JEAN', 'Française');

-- --------------------------------------------------------

--
-- Structure de la table `pilote`
--

DROP TABLE IF EXISTS `pilote`;
CREATE TABLE IF NOT EXISTS `pilote` (
  `id_pilote` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  `experience` int NOT NULL,
  PRIMARY KEY (`id_pilote`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `pilote`
--

INSERT INTO `pilote` (`id_pilote`, `nom`, `prenom`, `experience`) VALUES
(1, 'Dupont', 'Jean', 12),
(2, 'Martin', 'Claire', 8),
(3, 'Nguyen', 'Bao', 15),
(4, 'TAS', 'Tom', 15);

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
CREATE TABLE IF NOT EXISTS `reservation` (
  `id_reservation` int NOT NULL AUTO_INCREMENT,
  `id_passager` int DEFAULT NULL,
  `id_vol` int DEFAULT NULL,
  `date_reservation` datetime NOT NULL,
  PRIMARY KEY (`id_reservation`),
  KEY `id_passager` (`id_passager`),
  KEY `id_vol` (`id_vol`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `reservation`
--

INSERT INTO `reservation` (`id_reservation`, `id_passager`, `id_vol`, `date_reservation`) VALUES
(1, 1, 1, '2025-08-15 10:00:00'),
(2, 2, 2, '2025-08-16 11:30:00'),
(3, 3, 3, '2025-08-17 14:45:00'),
(5, 4, 1, '2025-10-11 00:00:00'),
(6, 4, 5, '2025-10-23 00:00:00'),
(7, 4, 3, '2025-10-08 00:00:00'),
(8, 1, 1, '2025-10-23 00:00:00'),
(9, 4, 6, '2025-10-21 00:00:00');

-- --------------------------------------------------------

--
-- Structure de la table `vol`
--

DROP TABLE IF EXISTS `vol`;
CREATE TABLE IF NOT EXISTS `vol` (
  `id_vol` int NOT NULL AUTO_INCREMENT,
  `numero_vol` varchar(10) NOT NULL,
  `id_avion` int DEFAULT NULL,
  `id_pilote` int DEFAULT NULL,
  `ville_depart` varchar(100) NOT NULL,
  `ville_arrivee` varchar(100) NOT NULL,
  `date_depart` datetime NOT NULL,
  `date_arrivee` datetime NOT NULL,
  `statut` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id_vol`),
  KEY `id_avion` (`id_avion`),
  KEY `id_pilote` (`id_pilote`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `vol`
--

INSERT INTO `vol` (`id_vol`, `numero_vol`, `id_avion`, `id_pilote`, `ville_depart`, `ville_arrivee`, `date_depart`, `date_arrivee`, `statut`) VALUES
(1, 'AF102', 1, 1, 'Paris', 'Rome', '2025-09-01 00:00:00', '2025-09-01 00:00:00', 'prévu'),
(2, 'BA202', 2, 2, 'Londres', 'Madrid', '2025-09-02 14:00:00', '2025-09-02 16:30:00', 'annulé'),
(3, 'LH303', 3, 3, 'Berlin', 'New York', '2025-09-03 09:00:00', '2025-09-03 18:00:00', 'prévu'),
(4, 'AF101', 1, 1, 'Paris', 'Rome', '2025-09-01 00:00:00', '2025-09-01 00:00:00', 'prévu'),
(5, 'AZ123', 4, 4, 'ST-DIE', 'PARIS', '2025-09-01 00:00:00', '2025-11-07 00:00:00', 'prévu'),
(6, 'AA111', 5, 4, 'ST DIE', 'ST DIE', '2025-10-24 00:00:00', '2025-10-24 00:00:00', 'Retardé');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
