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
(DEFAULT, '/legislation', NULL, NULL),
(DEFAULT, '/ressources', NULL, NULL),
(DEFAULT, '/analytiques_fiscales', NULL, NULL),
(DEFAULT, '/historique', NULL, NULL),
(DEFAULT, '/vision', NULL, NULL),
(DEFAULT, '/attributions', NULL, NULL);

INSERT INTO general_info (id, cle, lien, id_general_info)
VALUES
(DEFAULT, '/e_service', NULL, NULL),
(DEFAULT, '/votre_avis', NULL, NULL),
(DEFAULT, 'centre_contact', NULL, NULL),
(DEFAULT, 'navigation', NULL, NULL);

INSERT INTO general_info (id, cle, lien, id_general_info)
VALUES
(DEFAULT, 'nav_accueil', '#top', 13),
(DEFAULT, 'nav_a_propos', '#apropos', 13),
(DEFAULT, 'nav_documentations', '#documentations', 13),
(DEFAULT, 'nav_e_service', '#safi', 13),
(DEFAULT, 'nav_actualites', '#actualites', 13),
(DEFAULT, 'nav_espace', '#torohy', 13),
(DEFAULT, 'nav_contact', '#contact', 13);

INSERT INTO general_info_valeur (id, titre, valeur, entete, bouton, id_langue, id_general_info) VALUES
(DEFAULT, 'Fandraisana', 'Fandraisana', '', '', 1, 14),          -- Accueil
(DEFAULT, 'Momba ny', 'Momba ny', '', '', 1, 15),                -- A propos
(DEFAULT, 'Tahirin-kevitra', 'Tahirin-kevitra', '', '', 1, 16),  -- Documentations
(DEFAULT, 'E-Service', 'E-Service', '', '', 1, 17),              -- E-Services
(DEFAULT, 'Vaovao', 'Vaovao', '', '', 1, 18),                    -- Actualités
(DEFAULT, 'Espace citoyen', 'Espace citoyen', '', '', 1, 19),    -- Espace citoyen
(DEFAULT, 'Ivontoerana fifandraisana', 'Ivontoerana fifandraisana', '', '', 1, 20); -- Centre de contact

INSERT INTO general_info_valeur (id, titre, valeur, entete, bouton, id_langue, id_general_info) VALUES
(DEFAULT, 'Accueil', 'Accueil', '', '', 2, 14),
(DEFAULT, 'A propos', 'A propos', '', '', 2, 15),
(DEFAULT, 'Documentations', 'Documentations', '', '', 2, 16),
(DEFAULT, 'E-Services', 'E-Services', '', '', 2, 17),
(DEFAULT, 'Actualités', 'Actualités', '', '', 2, 18),
(DEFAULT, 'Espace citoyen', 'Espace citoyen', '', '', 2, 19),
(DEFAULT, 'Centre de contact', 'Centre de contact', '', '', 2, 20);

INSERT INTO general_info_valeur (id, titre, valeur, entete, bouton, id_langue, id_general_info) VALUES
(DEFAULT, 'Home', 'Home', '', '', 3, 14),          -- Accueil
(DEFAULT, 'About', 'About', '', '', 3, 15),         -- A propos
(DEFAULT, 'Documentations', 'Documentations', '', '', 3, 16), -- Documentations
(DEFAULT, 'E-Services', 'E-Services', '', '', 3, 17), -- E-Services
(DEFAULT, 'News', 'News', '', '', 3, 18),           -- Actualités
(DEFAULT, 'Citizen Space', 'Citizen Space', '', '', 3, 19), -- Espace citoyen
(DEFAULT, 'Contact Center', 'Contact Center', '', '', 3, 20); -- Centre de contact

INSERT INTO general_info_valeur (id, titre, valeur, entete, bouton, id_langue, id_general_info) VALUES
(DEFAULT, 'Mikasika', 'Mikasika', '', '', 1, 1),
(DEFAULT, 'A propos', 'A propos', '', '', 2, 1),
(DEFAULT, 'About', 'About', '', '', 3, 1),
(DEFAULT, 'Tantara', 'Tantara ny DGI', '', '', 1, 7),
(DEFAULT, 'Historique', 'Historique de la DGI', '', '', 2, 7),
(DEFAULT, 'History', 'History of the DGI', '', '', 3, 7),
(DEFAULT, 'Fahitana', 'Fahitana ny DGI', '', '', 1, 8),
(DEFAULT, 'Vision', 'Vision de la DGI', '', '', 2, 8),
(DEFAULT, 'Vision', 'Vision of the DGI', '', '', 3, 8),
(DEFAULT, 'Adidy', 'Adidy sy andraikitra', '', '', 1, 9),
(DEFAULT, 'Attributions', 'Attributions et responsabilités', '', '', 2, 9),
(DEFAULT, 'Responsibilities', 'Duties and responsibilities', '', '', 3, 9),
(DEFAULT, '<p>Votre Avis</p>', '<p>Exprimez votre avis sur nos services et contribuez à la lutte contre la fraude fiscale à travers notre plateforme sécurisée de signalement.</p>', 'espace citoyen', 'Donner mon avis', 2, 11),
(DEFAULT, '<p>Centre de Contact</p>', '<p>Bientôt, notre centre d''appel sera disponible pour toute information sur vos démarches fiscales. Nos conseillers seront à votre écoute, 24h/24 et 7j/7.</p>', 'Nous sommes là pour vous', 'Nous contacter', 2, 12),
(DEFAULT, '<p>Contact Center</p>', '<p>Soon, our call center will be available for any information on your tax procedures. Our advisors will be at your disposal, 24/7.</p>', 'We are here for you', 'Contact us', 3, 12),
(DEFAULT, '<p>Ivontoerana Fifandraisana</p>', '<p>Tsy ho ela, ny foibe antsoy dia ho hita amin''ny fampahalalana rehetra momba ny fomba fiasanao. Ny mpanolo-tsainay dia ho eo amin''ny fanompoana anao, 24/7.</p>', 'Miaraka aminay ianao', 'Mifandraisa aminay', 1, 12),
(DEFAULT, '<p>Loharano</p>', '<p>Midira amin''ny fampahalalana rehetra momba ny fandaminana ny Direction General Tax: ny ekipan''ny mpitantana azy, ny mpitantana azy ary ny rafitra fiasany.</p>', '', 'hijery', 1, 5),
(DEFAULT, '<p>Ressources</p>', '<p>Accédez à toutes les informations sur l''organisation de la Direction Générale des Impôts : son équipe dirigeante, ses responsables et ses structures opérationnelles.</p>', '', 'voir plus', 2, 5),
(DEFAULT, '<p>Tenin''i DGI</p>', '<p>Mandray anjara amin''ny fanovana nomerika lehibe amin''ny alàlan''ny Rafitra fitantanana ara-bola vaovao (SAFI) ny DGI, mandray anao amin''ny vavahadin-tseraserany ny DGI, mametraka ny vinany amin''ny<strong> fitantanana ara-bola vaovao sy mangarahara ary andry hivoahana</strong>. Tadiavo ny serivisy maoderina, natao hiantohana ny fahafaham-ponao sy hitandrovana ny toetry ny fahatokisana, mifanaraka amin''ny endriky ny governemanta <strong>"akaiky ny vahoaka"</strong>.</p>', 'DGI', '', 1, 3),
(DEFAULT, '<p>Your Opinion</p>', '<p>Express your opinion on our services and contribute to the fight against tax fraud through our secure reporting platform.</p>', 'citizen space', 'Give feedback', 3, 11),
(DEFAULT, '<p>Hevitrao</p>', '<p>Asehoy ny hevitrao momba ny serivisy ary mandray anjara amin''ny ady amin''ny hosoka amin''ny hetra amin''ny alàlan''ny sehatra fanaovana tatitra azo antoka.</p>', 'hoan''ny olom-pirenena', 'Omeo hevitra', 1, 11),
(DEFAULT, '<p>Mot du DGI</p>', '<p>Engagée dans une transformation digitale majeure à travers son nouveau Système Intégré d''Administration Fiscale (SAFI), la DGI vous accueille sur son portail en ligne, incarnant sa vision d''une administration fiscale <strong>innovante, transparente et pilier de l''émergence</strong>. Découvrez nos services modernes, conçus pour garantir votre satisfaction et préserver un climat de confiance, conformément à l''image d''un gouvernement <strong>« proche du peuple ».</strong></p>', 'DGI', '', 2, 3),
(DEFAULT, '<p>E-Service</p>', '<p>Simplifiez vos obligations fiscales avec SAFI, la plateforme digitale de la Direction Générale des Impôts qui vous permet de déclarer, payer et suivre vos impôts en ligne, avec un système d''alertes personnalisées pour une gestion optimale de votre compte.</p>', 'Démarches fiscales', 'Accéder', 2, 10),
(DEFAULT, '<p>Législation</p>', '<p>Retrouvez ici l''ensemble des textes et réglementations en vigueur régissant la fiscalité malgache, régulièrement actualisés pour vous permettre de mieux comprendre vos droits et obligations fiscales.</p>', '', 'voir plus', 2, 4),
(DEFAULT, '<p>Legislation</p>', '<p>Find here all the texts and regulations in force governing Malagasy taxation, regularly updated to allow you to better understand your tax rights and obligations.</p>', '', 'show more', 3, 4),
(DEFAULT, '<p>Lalàna</p>', '<p>Tadiavo eto ny lahatsoratra sy fitsipika manan-kery mifehy ny hetra malagasy, havaozina tsy tapaka mba hahafahanao mahazo tsara kokoa ny zo sy adidy amin''ny hetra.</p>', '', 'hijery', 1, 4),
(DEFAULT, '<p>Resources</p>', '<p>Access all the information on the organization of the General Directorate of Taxes: its management team, its managers and its operational structures.</p>', '', 'show more', 3, 5),
(DEFAULT, '<p>Famakafakana ara-bola</p>', '<p>Jereo ato amin''ity fizarana ity ny antontan-taratasim-panadihadiana lalina izay mamerina ny zava-bitany amin''ny resaka fanetsiketsehana ny vola miditra amin''ny hetra, antontan''isa tsy tapaka momba ny zava-bita, fandalinana sehatra, famoahana ara-toekarena, tatitra amin''ny antsipiriany momba ny hetsika, sns.</p>', '', 'hijery', 1, 6),
(DEFAULT, '<p>E-Service</p>', '<p>Consolidate your tax obligations with SAFI, the digital platform of the Directorate General of Taxes that allows you to declare, pay and monitor your taxes online, with a special alert system for the proper management of your account.</p>', 'Tax procedures', 'Access', 3, 10),
(DEFAULT, '<p>E-Service</p>', '<p>Hamafiso ny adidinao amin''ny hetra miaraka amin''ny SAFI, sehatra nomerika an''ny Direction Général de Taxes izay ahafahanao manambara, mandoa ary manara-maso ny hetrao amin''ny Internet, miaraka amin''ny rafitra fanairana manokana ho an''ny fitantanana tsara ny kaontinao.</p>', 'Fitsipika momba ny hetra', 'Midira', 1, 10),
(DEFAULT, '<p>Tale jeneralin''ny hetra</p>', '<p>Germain</p>', '', '', 1, 2),
(DEFAULT, '<p>Directeur Général des impôts</p>', '<p>Germain</p>', '', '', 2, 2),
(DEFAULT, '<p>Director General of Taxes</p>', '<p>Germain</p>', '', '', 3, 2),
(DEFAULT, '<p>Analytiques fiscales</p>', '<p>Consultez dans cette rubrique les documents d''analyse approfondis retraçant ses performances en matière de mobilisation des recettes fiscales, statistiques périodiques des réalisations, études sectorielles, publications économiques, rapports détaillés d''activités, etc.</p>', '', 'voir plus', 2, 6),
(DEFAULT, '<p>Fiscal analytics</p>', '<p>Consult in this section the in-depth analysis documents tracing its performance in terms of mobilizing tax revenues, periodic statistics of achievements, sector studies, economic publications, detailed activity reports, etc.</p>', '', 'show more', 3, 6),
(DEFAULT, '<p>Word of the DGI</p>', '<p>Engaged in a major digital transformation through its new Integrated Fiscal Administration System (SAFI), the DGI welcomes you on its online portal, embodying its vision of an <strong>innovative, transparent fiscal administration and pillar of emergence</strong>. Discover our modern services, designed to guarantee your satisfaction and preserve a climate of trust, in accordance with the image of a government <strong>"close to the people"</strong>.</p>', 'DGI', '', 3, 3);