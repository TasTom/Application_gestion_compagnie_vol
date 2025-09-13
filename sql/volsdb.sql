-- Création de la base
CREATE DATABASE IF NOT EXISTS volsdb;
USE volsdb;

-- Table avion
CREATE TABLE avion (
                       id_avion INT AUTO_INCREMENT PRIMARY KEY,
                       modele VARCHAR(100) NOT NULL,
                       capacite INT NOT NULL
);

-- Table pilote
CREATE TABLE pilote (
                        id_pilote INT AUTO_INCREMENT PRIMARY KEY,
                        nom VARCHAR(100) NOT NULL,
                        prenom VARCHAR(100) NOT NULL,
                        experience INT NOT NULL
);

-- Table vol
CREATE TABLE vol (
                     id_vol INT AUTO_INCREMENT PRIMARY KEY,
                     numero_vol VARCHAR(10) NOT NULL,
                     id_avion INT,
                     id_pilote INT,
                     ville_depart VARCHAR(100) NOT NULL,
                     ville_arrivee VARCHAR(100) NOT NULL,
                     date_depart DATETIME NOT NULL,
                     date_arrivee DATETIME NOT NULL,
                     statut VARCHAR(20),
                     FOREIGN KEY (id_avion) REFERENCES avion(id_avion),
                     FOREIGN KEY (id_pilote) REFERENCES pilote(id_pilote)
);

-- Table passager
CREATE TABLE passager (
                          id_passager INT AUTO_INCREMENT PRIMARY KEY,
                          nom VARCHAR(100) NOT NULL,
                          prenom VARCHAR(100) NOT NULL,
                          nationalite VARCHAR(50)
);

-- Table reservation
CREATE TABLE reservation (
                             id_reservation INT AUTO_INCREMENT PRIMARY KEY,
                             id_passager INT,
                             id_vol INT,
                             date_reservation DATETIME NOT NULL,
                             FOREIGN KEY (id_passager) REFERENCES passager(id_passager),
                             FOREIGN KEY (id_vol) REFERENCES vol(id_vol)
);

-- =============================
-- Données fictives
-- =============================

-- Avions
INSERT INTO avion (modele, capacite) VALUES
                                         ('Airbus A320', 180),
                                         ('Boeing 737', 160),
                                         ('Boeing 787 Dreamliner', 250);

-- Pilotes
INSERT INTO pilote (nom, prenom, experience) VALUES
                                                 ('Dupont', 'Jean', 12),
                                                 ('Martin', 'Claire', 8),
                                                 ('Nguyen', 'Bao', 15);

-- Vols
INSERT INTO vol (numero_vol, id_avion, id_pilote, ville_depart, ville_arrivee, date_depart, date_arrivee, statut) VALUES
                                                                                                                      ('AF101', 1, 1, 'Paris', 'Rome', '2025-09-01 08:00:00', '2025-09-01 10:00:00', 'prévu'),
                                                                                                                      ('BA202', 2, 2, 'Londres', 'Madrid', '2025-09-02 14:00:00', '2025-09-02 16:30:00', 'retardé'),
                                                                                                                      ('LH303', 3, 3, 'Berlin', 'New York', '2025-09-03 09:00:00', '2025-09-03 18:00:00', 'prévu');

-- Passagers
INSERT INTO passager (nom, prenom, nationalite) VALUES
                                                    ('Durand', 'Alice', 'Française'),
                                                    ('Smith', 'John', 'Américaine'),
                                                    ('Lopez', 'Maria', 'Espagnole');

-- Réservations
INSERT INTO reservation (id_passager, id_vol, date_reservation) VALUES
                                                                    (1, 1, '2025-08-15 10:00:00'),
                                                                    (2, 2, '2025-08-16 11:30:00'),
                                                                    (3, 3, '2025-08-17 14:45:00');