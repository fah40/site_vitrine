CREATE DATABASE dgi;
\c dgi;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE user_(
   id UUID DEFAULT uuid_generate_v4(),
   email TEXT NOT NULL,
   name VARCHAR(50) ,
   firstname VARCHAR(50) ,
   validation_date TIMESTAMP,
   password TEXT NOT NULL,
   id_admin bool DEFAULT false,
   PRIMARY KEY(id),
   UNIQUE(email)
);

CREATE TABLE user_token(
   id SERIAL,
   token UUID NOT NULL DEFAULT uuid_generate_v4(),
   creation_date TIMESTAMP NOT NULL,
   expiration_date TIMESTAMP,
   id_user UUID NOT NULL,
   PRIMARY KEY(id),
   UNIQUE(token),
   FOREIGN KEY(id_user) REFERENCES user_(id)
);

CREATE TABLE attempts(
   id SERIAL,
   date_next_attempt TIMESTAMP,
   count_attempt SMALLINT,
   id_user UUID NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_user) REFERENCES user_(id)
);

CREATE TABLE user_pin(
   id SERIAL,
   pin TEXT NOT NULL,
   creation_date TIMESTAMP NOT NULL,
   expiration_date TIMESTAMP,
   id_user UUID NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_user) REFERENCES user_(id)
);

CREATE TABLE configuration(
   id SERIAL,
   keys VARCHAR(50) ,
   valeurs TEXT,
   PRIMARY KEY(id),
   unique(keys) 
);

CREATE TABLE langue(
   id SERIAL,
   nom VARCHAR(50),
   icon TEXT,
   PRIMARY KEY(id)
);

CREATE TABLE actualite(
   id SERIAL,
   titre TEXT NOT NULL,
   description TEXT NOT NULL,
   date_ajout TIMESTAMP NOT NULL,
   id_user UUID NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_user) REFERENCES user_(id)
);

CREATE TABLE piece_jointe(
   id SERIAL,
   url_fichier TEXT NOT NULL,
   id_actualite INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_actualite) REFERENCES actualite(id)
);

CREATE TABLE general_info(
   id SERIAL,
   cle TEXT NOT NULL,
   icone TEXT,
   lien TEXT,
   id_general_info INTEGER,
   PRIMARY KEY(id),
   FOREIGN KEY(id_general_info) REFERENCES general_info(id)
);

CREATE TABLE general_info_valeur(
   id SERIAL,
   titre TEXT NOT NULL,
   valeur TEXT NOT NULL,
   id_langue INTEGER NOT NULL,
   id_general_info INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_langue) REFERENCES langue(id),
   FOREIGN KEY(id_general_info) REFERENCES general_info(id),
   UNIQUE(id_langue, id_general_info)
);