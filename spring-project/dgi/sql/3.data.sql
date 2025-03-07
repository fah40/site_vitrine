INSERT INTO user_ (id, email, name, firstname, validation_date, password, id_admin)
VALUES ('b49f1f09-6798-421d-9546-f1771d952f39', 
        'j.irina.m.andrianjamanantena@gmail.com', 
        '', 
        'Admin', 
        CURRENT_DATE, 
        crypt('123', gen_salt('bf')), 
        true);

INSERT INTO user_ (id, email, name, firstname, validation_date, password, id_admin)
VALUES ('b49f1f09-6798-421d-9546-f1792d952f39', 
        'fanasinamanantsoa30@gmail.com', 
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

INSERT INTO langue (id,nom) 
VALUES 
(1, 'MLG'),
(2, 'FRA'),
(3, 'ENG');

INSERT INTO general_info (id, cle, lien, id_general_info)
VALUES
(DEFAULT, 'a_propos', NULL, NULL),
(DEFAULT, 'dgi', NULL, NULL),
(DEFAULT, 'mot_du_dgi', NULL, NULL),
(DEFAULT, 'legislation', NULL, NULL),
(DEFAULT, 'ressources', NULL, NULL),
(DEFAULT, 'analytiques_fiscales', NULL, NULL),
(DEFAULT, 'historique', NULL, NULL),
(DEFAULT, 'vision', NULL, NULL),
(DEFAULT, 'attributions', NULL, NULL);

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
('Fiscal analytics', 'Tax statistics', 3, 6),
('Tantara', 'Tantara ny DGI', 1, 7), 
('Historique', 'Historique de la DGI', 2, 7), 
('History', 'History of the DGI', 3, 7),
('Fahitana', 'Fahitana ny DGI', 1, 8),
('Vision', 'Vision de la DGI', 2, 8),
('Vision', 'Vision of the DGI', 3, 8),
('Adidy', 'Adidy sy andraikitra', 1, 9),
('Attributions', 'Attributions et responsabilités', 2, 9),
('Responsibilities', 'Duties and responsibilities', 3, 9);

INSERT INTO general_info (id, cle, lien, id_general_info)
VALUES
(DEFAULT, 'e_service', NULL, NULL),
(DEFAULT, 'votre_avis', NULL, NULL),
(DEFAULT, 'centre_contact', NULL, NULL);

INSERT INTO general_info_valeur (titre, valeur, entete, bouton, id_langue, id_general_info)
VALUES
-- Données pour la clé 'e_service' (id_general_info = 10)
('E-Service', 'Accédez à nos services en ligne.', 'Services en ligne', 'Accéder', 2, 10), -- Français
('E-Service', 'Access our online services.', 'Online Services', 'Access', 3, 10), -- Anglais
('E-Service', 'Midiraho amin''ny tolotra an-tserasera.', 'Tolotra an-tserasera', 'Midira', 1, 10), -- Malgache

-- Données pour la clé 'votre_avis' (id_general_info = 11)
('Votre Avis', 'Donnez-nous votre avis.', 'Votre opinion compte', 'Donner mon avis', 2, 11), -- Français
('Your Opinion', 'Give us your feedback.', 'Your opinion matters', 'Give feedback', 3, 11), -- Anglais
('Hevitrao', 'Omeo hevitra aminay.', 'Ny hevitrao dia zava-dehibe', 'Omeo hevitra', 1, 11), -- Malgache

-- Données pour la clé 'centre_contact' (id_general_info = 12)
('Centre de Contact', 'Contactez-nous pour toute question.', 'Nous sommes là pour vous', 'Nous contacter', 2, 12), -- Français
('Contact Center', 'Reach out to us for any inquiries.', 'We are here for you', 'Contact us', 3, 12), -- Anglais
('Ivontoerana Fifandraisana', 'Mifandraisa aminay raha misy fanontaniana.', 'Miaraka aminay ianao', 'Mifandraisa aminay', 1, 12); -- Malgache