# Application de Gestion de Compagnie Aérienne ✈️

Application JavaFX de gestion complète d'une compagnie aérienne permettant la gestion des vols, pilotes, avions, passagers et réservations.

## 📋 Table des matières

1. [Description générale](#description-générale)
2. [Fonctionnalités](#fonctionnalités)
3. [Technologies utilisées](#technologies-utilisées)
4. [Prérequis](#prérequis)
5. [Configuration de la base de données](#configuration-de-la-base-de-données)
6. [Installation](#installation)
7. [MVC avec javaFX et Gradle](#structure-du-projet)
8. [Gestion](#utilisation)
9. [TAS Tom](#auteur)

## Description générale

Cette application desktop développée en Java avec JavaFX permet de gérer l'ensemble des opérations d'une compagnie aérienne. Elle offre une interface graphique intuitive pour effectuer des opérations CRUD (Create, Read, Update, Delete) sur les différentes entités du système.


## 🎯 Fonctionnalités

### Gestion des Vols
- ✅ Création de nouveaux vols avec informations complètes
- ✅ Modification des détails des vols existants
- ✅ Suppression de vols
- ✅ Affichage de tous les vols dans un tableau
- ✅ Association automatique avec pilotes et avions
- ✅ Gestion des statuts (En cours, Terminé, Annulé, Retardé)

### Gestion des Pilotes
- ✅ Ajout de nouveaux pilotes
- ✅ Modification des informations des pilotes
- ✅ Suppression de pilotes
- ✅ Suivi de l'expérience des pilotes
- ✅ Affichage de la liste complète

### Gestion des Avions
- ✅ Enregistrement de nouveaux avions
- ✅ Modification des caractéristiques des avions
- ✅ Suppression d'avions
- ✅ Gestion de la capacité des avions

### Gestion des Passagers
- ✅ Ajout de passagers avec leurs informations personnelles
- ✅ Modification des données des passagers
- ✅ Suppression de passagers
- ✅ Gestion de la nationalité

### Gestion des Réservations
- ✅ Création de réservations liant passagers et vols
- ✅ Suppression de réservations
- ✅ Affichage des réservations actives
- ✅ Gestion des dates de réservation



## 💻 Technologies utilisées

- **Langage** : Java 17+
- **Framework UI** : JavaFX
- **Build Tool** : Gradle 8.10
- **Base de données** : MySQL
- **Architecture** : MVC (Model-View-Controller) avec pattern DAO
- **JDBC** : Pour la connexion à la base de données

## 📦 Prérequis

Avant d'installer l'application, assurez-vous d'avoir :

- Java JDK 17 ou supérieur
- Gradle 8.x ou supérieur
- MySQL 8.0 ou supérieur
- Un IDE Java (IntelliJ IDEA, Eclipse, VS Code recommandé)

### Base de données
- ✅ Base de données MySQL
- ✅ Table des vols
- ✅ Table des pilotes
- ✅ Table des avions
- ✅ Table des passagers
- ✅ Table des réservations
- ✅ Importer volsdb.sql dans *AMP


## 🚀 Installation

Pour installer et lancer l'application, suivez ces étapes :

1. Clonez ce dépôt sur votre machine locale en utilisant Git :
```bash
git clone https://github.com/TasTom/Application_gestion_compagnie_vol
```

2. Assurez-vous d'avoir Java JDK 17 ou supérieur installé sur votre machine.

3. Assurez-vous d'avoir Gradle 8.x ou supérieur installé sur votre machine.

4. Assurez-vous d'avoir MySQL 8.0 ou supérieur installé sur votre machine.

5. Assurez-vous d'avoir un environnement de développement Java (IDE) installé sur votre machine.

6. Exécutez la commande suivante pour installer les dépendances et construire l'application :
```bash
./gradlew build
```

7. Lancez l'application en utilisant la commande suivante :
```bash
./gradlew run
``` 
