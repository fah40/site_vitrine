INSERT INTO user_ (id, email, name, firstname, validation_date, password, id_admin)
VALUES ('b49f1f09-6798-421d-9546-f1771d952f39', 
        'j.irina.m.andrianjamanantena@gmail.com', 
        '', 
        'Admin', 
        CURRENT_DATE, 
        crypt('123', gen_salt('bf')), 
        true);

INSERT INTO configuration (keys, valeurs) VALUES 
('pin_expiration_minute', '10'),
('pin_length', '6'),
('sending_email_pin_content', 'Nous vous envoyons un pin de connexion.'),
('count_attempt', '3'),
('next_attempt_minute', '10');

INSERT INTO langue (nom) 
VALUES 
(1, 'MLG'),
(2, 'FRA'),
(3, 'ENG');

INSERT INTO general_info (id, cle, lien, id_general_info)
VALUES
(1, 'a_propos', NULL, NULL),
(2, 'dgi', NULL, NULL),
(3, 'mot_du_dgi', NULL, NULL),
(4, 'legislation', NULL, NULL),
(5, 'ressources', NULL, NULL),
(6, 'analytiques_fiscales', NULL, NULL);

INSERT INTO general_info_valeur (titre, valeur, id_langue, id_general_info)
VALUES
('Mikasika', 'Mikasika', 1, 1),
('A propos', 'A propos', 2, 1),
('About', 'About', 3, 1),
('Direktora', 'Germain', 1, 2),
('Directeur', 'Germain', 2, 2),
('Director', 'Germain', 3, 2),
('Tenin''i DGI', 'Tsara ny asanay', 1, 3),
('Mot du DGI', 'On fait du bon travail', 2, 3),
('Word of the DGI', 'We''re doing great', 3, 3),
('Lalàna', 'Lalàna momba ny hetra', 1, 4),
('Législation', 'Législation fiscale', 2, 4),
('Legislation', 'Tax legislation', 3, 4),
('Loharano', 'Boky sy torolalana', 1, 5),
('Ressources', 'Livres et guides', 2, 5),
('Resources', 'Books and guides', 3, 5),
('Famakafakana ara-bola', 'Antontanisa ara-bola', 1, 6),
('Analytiques fiscales', 'Statistiques fiscales', 2, 6),
('Fiscal analytics', 'Tax statistics', 3, 6);