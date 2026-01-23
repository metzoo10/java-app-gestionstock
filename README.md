# 📦 Application Java de gestion de stock

<h2 align="center">
  <img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL">
</h2>

---

## 📝 Description
Cette application est une application **desktop Java** développée avec **Java Swing** permettant la **gestion de stock, des produits, des ventes et des utilisateurs**.  
Elle est destinée à un usage académique et illustre la mise en pratique des concepts de **programmation orientée objet**, **architecture MVC**, et **connexion à une base de données MySQL** via JDBC.

L’application offre une interface graphique simple, ergonomique et fonctionnelle.

---

## 🎯 Fonctionnalités principales

### 🔐 Authentification
- Connexion sécurisée via nom d’utilisateur et mot de passe
- Accès complet aux fonctionnalités après authentification

### 📦 Gestion des produits
- Ajouter un produit
- Modifier un produit
- Supprimer un produit
- Affichage du stock restant
- Alerte visuelle en cas de stock faible
- Ajout de stock existant

### 💰 Gestion des ventes
- Enregistrement des ventes
- Vérification automatique du stock disponible
- Mise à jour du stock après chaque vente
- Historique des ventes avec date et produit concerné

### 👤 Gestion des utilisateurs
- Création de nouveaux utilisateurs
- Validation des champs et unicité du nom d’utilisateur

---

## 🧱 Architecture du projet

Le projet suit l’architecture **MVC (Model – View – Controller)** :

- **Model** : classes métier (`Produit`, `Vente`, `Utilisateur`)
- **View** : interfaces graphiques Swing (`MainFrame`, `ProduitFrame`, `VenteFrame`, etc.)
- **Controller / DAO** : accès aux données et requêtes SQL (`ProduitDAO`, `VenteDAO`, `UtilisateurDAO`)

Cette séparation permet une meilleure lisibilité, maintenabilité et évolutivité du code.

---

## 🛠️ Technologies utilisées

- **Java SE**
- **Java Swing** (interfaces graphiques)
- **JDBC**
- **MySQL**
- **XAMPP**
- **phpMyAdmin**
- **Architecture MVC**

---

## 🗄️ Base de données

- SGBD : **MySQL**
- Environnement local : **XAMPP**
- Gestion : **phpMyAdmin**

### Tables principales :
- `produits`
- `ventes`
- `utilisateurs`

---

## ▶️ Lancer l’application

### Prérequis
- Java JDK 8 ou supérieur
- XAMPP installé et MySQL démarré
- Base de données importée dans phpMyAdmin

### Étapes
1. Cloner le dépôt :
   ```bash
   git clone https://github.com/metzoo10/java-app-gestionstock.git
