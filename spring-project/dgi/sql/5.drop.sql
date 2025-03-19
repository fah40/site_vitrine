-- Supprimer les vues
DROP VIEW IF EXISTS total_visites;

-- Supprimer les tables avec des dépendances de clés étrangères
DROP TABLE IF EXISTS general_info_valeur;
DROP TABLE IF EXISTS general_info;
DROP TABLE IF EXISTS piece_jointe;
DROP TABLE IF EXISTS actualite;
DROP TABLE IF EXISTS user_pin;
DROP TABLE IF EXISTS attempts;
DROP TABLE IF EXISTS user_token;

-- Supprimer les tables référencées
DROP TABLE IF EXISTS user_;
DROP TABLE IF EXISTS configuration;
DROP TABLE IF EXISTS langue;
DROP TABLE IF EXISTS visite;