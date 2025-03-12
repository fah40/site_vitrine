-- Activation des extensions nécessaires
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Insertion dans la table user_
INSERT INTO public.user_ (id, email, name, firstname, validation_date, password, id_admin) VALUES
('b49f1f09-6798-421d-9546-f1792d952f39', 'fanasinamanantsoa30@gmail.com', '', 'Admin', '2025-03-12 00:00:00', '$2a$06$pCXEF8jtguYwXZEk3qy8vuWqt8ZgsTIfGp2ZajYvSp2qakZ1Kt9Qq', true);

-- Insertion dans la table actualite (aucune donnée dans le dump)
-- Pas d'insertion nécessaire car la table est vide dans le dump

-- Insertion dans la table attempts
INSERT INTO public.attempts (id, date_next_attempt, count_attempt, id_user) VALUES
(1, NULL, 0, 'b49f1f09-6798-421d-9546-f1792d952f39');

-- Insertion dans la table configuration
INSERT INTO public.configuration (id, keys, valeurs) VALUES
(1, 'pin_expiration_minute', '10'),
(2, 'pin_length', '6'),
(3, 'sending_email_pin_content', 'Nous vous envoyons un pin de connexion.'),
(4, 'count_attempt', '3'),
(5, 'next_attempt_minute', '10');

-- Insertion dans la table general_info
INSERT INTO public.general_info (id, cle, icone, lien, id_general_info) VALUES
(1, 'a_propos', NULL, NULL, NULL),
(7, '/historique', NULL, NULL, NULL),
(13, 'navigation', NULL, NULL, NULL),
(14, 'nav_accueil', NULL, '#top', 13),
(15, 'nav_a_propos', NULL, '#apropos', 13),
(16, 'nav_documentations', NULL, '#documentations', 13),
(17, 'nav_e_service', NULL, '#safi', 13),
(18, 'nav_actualites', NULL, '#actualites', 13),
(19, 'nav_espace', NULL, '#torohy', 13),
(20, 'nav_contact', NULL, '#contact', 13),
(21, 'actualites', NULL, NULL, NULL),
(22, 'partenaire', NULL, NULL, NULL),
(26, 'appeler', NULL, '#appeler', NULL),
(27, 'ecrire', NULL, '#ecrire', NULL),
(28, 'visiter', NULL, '#visiter', NULL),
(29, '/vision/valeurs et engagements', NULL, '/admin/explorer?path=/vision/valeurs et engagements', 8),
(30, '/vision/projets de modernisation', NULL, '/admin/explorer?path=/vision/projets de modernisation', 8),
(8, '/vision', NULL, NULL, NULL),
(9, '/attributions', NULL, NULL, NULL),
(34, '/legislation/Calendrier Fiscal', NULL, '/admin/explorer?path=/legislation/Calendrier Fiscal', 4),
(35, '/legislation/Conventions et R├®gimes Sp├®ciaux', NULL, '/admin/explorer?path=/legislation/Conventions et R├®gimes Sp├®ciaux', 4),
(31, '/legislation/Recuiel des textes', NULL, 'https://portal.impots.mg/textes/', 4),
(32, '/legislation/Rescrit fiscal', NULL, 'https://portal.impots.mg/rescrit/', 4),
(33, '/legislation/Brochures et guides', NULL, '/admin/explorer?path=/legislation/Brochures et guides', 4),
(36, '/legislation/Codes et Manuels', NULL, '/admin/explorer?path=/legislation/Codes et Manuels', 4),
(3, 'mot_du_dgi', NULL, NULL, NULL),
(4, '/legislation', NULL, NULL, NULL),
(5, '/ressources', NULL, NULL, NULL),
(40, '/ressources/Recrutements', NULL, '/admin/explorer?path=/ressources/Recrutements', 5),
(41, '/ressources/Budget de la DGI', NULL, '/admin/explorer?path=/ressources/Budget de la DGI', 5),
(6, '/analytiques_fiscales', NULL, NULL, NULL),
(42, '/ressources/RCTVA', NULL, '/admin/explorer?path=/ressources/RCTVA', 5),
(23, 'bureau', NULL, NULL, NULL),
(37, '/ressources/SIRH', NULL, 'https://sysinfo.mef.gov.mg:9101/ROHI/index.php/accueil/login', 5),
(38, '/ressources/G-formation', NULL, 'https://portal.impots.mg/portal/utilisateur', 5),
(24, 'contribuable', NULL, NULL, NULL),
(25, 'recette', NULL, NULL, NULL),
(39, '/ressources/Annuaire', NULL, '/admin/explorer?path=/ressources/Annuaire', 5),
(10, '/e_service', NULL, NULL, NULL),
(11, '/votre_avis', NULL, NULL, NULL),
(44, '/e_service/HetraOnline', NULL, 'https://hetraonline.impots.mg/', 10),
(12, 'centre_contact', NULL, NULL, NULL),
(45, '/e_service/Valeur administrative des immeubles', NULL, 'https://immo.impots.mg/', 10),
(46, '/e_service/Valeur administrative des v├®hicules', NULL, 'https://vehicule.impots.mg/', 10),
(47, '/e_service/Attestation de destination', NULL, 'http://www.a2d.impots.mg/', 10),
(48, '/e_service/Simulateur de calcul d''imp├┤t', NULL, 'https://www.impots.mg/fr/simulator', 10),
(49, '/e_service/DConline', NULL, 'https://entreprises.impots.mg/dconline/', 10),
(50, '/e_service/Annexe TVA', NULL, 'https://entreprises.impots.mg/nifonline/annexe_tva.php?bash=02122#accueil_menu_annexe_tva', 10),
(43, '/e_service/Ehetra', NULL, 'https://e-hetra.impots.mg/', 10),
(2, 'dgi', NULL, NULL, NULL);

-- Insertion dans la table langue
INSERT INTO public.langue (id, nom, icon) VALUES
(1, 'MLG', NULL),
(2, 'FRA', NULL),
(3, 'ENG', NULL);

-- Insertion dans la table general_info_valeur
INSERT INTO public.general_info_valeur (id, titre, valeur, entete, bouton, id_langue, id_general_info) VALUES
(1, 'Fandraisana', 'Fandraisana', '', '', 1, 14),
(2, 'Momba ny', 'Momba ny', '', '', 1, 15),
(3, 'Tahirin-kevitra', 'Tahirin-kevitra', '', '', 1, 16),
(4, 'E-Service', 'E-Service', '', '', 1, 17),
(5, 'Vaovao', 'Vaovao', '', '', 1, 18),
(6, 'Espace citoyen', 'Espace citoyen', '', '', 1, 19),
(7, 'Ivontoerana fifandraisana', 'Ivontoerana fifandraisana', '', '', 1, 20),
(8, 'Vaovao', '', '', '', 1, 21),
(9, 'Mpiara-miasa', '', '', '', 1, 22),
(13, 'Accueil', 'Accueil', '', '', 2, 14),
(14, 'A propos', 'A propos', '', '', 2, 15),
(15, 'Documentations', 'Documentations', '', '', 2, 16),
(16, 'E-Services', 'E-Services', '', '', 2, 17),
(17, 'ActualitÔÇÜs', 'ActualitÔÇÜs', '', '', 2, 18),
(18, 'Espace citoyen', 'Espace citoyen', '', '', 2, 19),
(19, 'Centre de contact', 'Centre de contact', '', '', 2, 20),
(20, 'actualitÔÇÜs', '', '', '', 2, 21),
(21, 'partenaires', '', '', '', 2, 22),
(25, 'Home', 'Home', '', '', 3, 14),
(26, 'About', 'About', '', '', 3, 15),
(27, 'Documentations', 'Documentations', '', '', 3, 16),
(28, 'E-Services', 'E-Services', '', '', 3, 17),
(29, 'News', 'News', '', '', 3, 18),
(30, 'Citizen Space', 'Citizen Space', '', '', 3, 19),
(31, 'Contact Center', 'Contact Center', '', '', 3, 20),
(32, 'news', '', '', '', 3, 21),
(33, 'partners', '', '', '', 3, 22),
(37, 'Mikasika', 'Mikasika', '', '', 1, 1),
(38, 'A propos', 'A propos', '', '', 2, 1),
(39, 'About', 'About', '', '', 3, 1),
(40, 'Tantara', 'Tantara ny DGI', '', '', 1, 7),
(41, 'Historique', 'Historique de la DGI', '', '', 2, 7),
(42, 'History', 'History of the DGI', '', '', 3, 7),
(46, '<p>Adidy</p>', '<p>Adidy sy andraikitra</p>', '', '', 1, 9),
(48, '<p>Responsibilities</p>', '<p>Duties and responsibilities</p>', '', '', 3, 9),
(59, '<p>E-Service</p>', '<p>Simplifiez vos obligations fiscales avec SAFI, la plateforme digitale de la Direction G├®n├®rale des Imp├┤ts </p><p>qui vous permet de d├®clarer, payer et suivre vos imp├┤ts en ligne,</p><p> avec un syst├¿me d''alertes personnalis├®es pour </p><p>une gestion optimale de votre compte.</p>', 'D├®marches fiscales', 'Se connecter', 2, 10),
(61, '<p>Legislation</p>', '<p>Retrouvez ici l''ensemble des textes et r├®glementations en vigueur r├®gissant la fiscalit├® malgache, r├®guli├¿rement actualis├®s pour vous permettre de mieux comprendre vos droits et obligations fiscales.</p>', '', 'show more', 3, 4),
(53, '<p>Loharano</p>', '<p>Acc├®dez ├á toutes les informations sur l''organisation de la Direction G├®n├®rale des Imp├┤ts : son ├®quipe dirigeante, ses responsables et ses structures op├®rationnelles.</p>', '', 'hijery', 1, 5),
(54, '<p>Ressources</p>', '<p>Acc├®dez ├á toutes les informations sur l''organisation de la Direction G├®n├®rale des Imp├┤ts : son ├®quipe dirigeante, ses responsables et ses structures op├®rationnelles.</p>', '', 'voir plus', 2, 5),
(10, '<p>Bureaux op├®rationnels</p>', '<p><br></p>', '153', '', 1, 23),
(22, '<p>Bureaux op├®rationnels</p>', '<p><br></p>', '153', '', 2, 23),
(34, '<p>Bureaux op├®rationnels</p>', '<p><br></p>', '153', '', 3, 23),
(11, '<p>contribuables enregistr├®s</p>', '<p><br></p>', '1585274', '', 1, 24),
(23, '<p>contribuables enregistr├®s</p>', '<p><br></p>', '1585274', '', 2, 24),
(35, '<p>contribuables enregistr├®s</p>', '<p><br></p>', '1585274', '', 3, 24),
(12, '<p>recettes collect├®es</p>', '<p><br></p>', '4754124452922', '', 1, 25),
(24, '<p>recettes collect├®es</p>', '<p><br></p>', '4754124452922', '', 2, 25),
(36, '<p>recettes collect├®es</p>', '<p><br></p>', '4754124452922', '', 3, 25),
(56, '<p>Your Opinion</p>', '<p>Exprimez votre avis sur nos services et contribuez ├á la lutte contre la fraude fiscale ├á travers notre plateforme s├®curis├®e de signalement.</p>', 'espace citoyen', 'Give feedback', 3, 11),
(60, '<p>L├®gislation</p>', '<p>Retrouvez ici l''ensemble des textes et r├®glementations en vigueur r├®gissant la fiscalit├® malgache, r├®guli├¿rement actualis├®s pour vous permettre de mieux comprendre vos droits et obligations fiscales.</p>', '', 'voir plus', 2, 4),
(49, '<p>Votre Avis</p>', '<p>Exprimez votre avis sur nos services et contribuez ├á la lutte contre la fraude fiscale ├á travers notre plateforme s├®curis├®e de signalement.</p>', 'espace citoyen', 'Donner mon avis', 2, 11),
(57, '<p>Hevitrao</p>', '<p>Exprimez votre avis sur nos services et contribuez ├á la lutte contre la fraude fiscale ├á travers notre plateforme s├®curis├®e de signalement.</p>', 'espace citoyen', 'Omeo hevitra', 1, 11),
(50, '<p>Centre de Contact</p>', '<p>Bient├┤t, notre centre d''appel sera disponible pour toute information sur vos d├®marches fiscales. Nos conseillers seront ├á votre ├®coute, 24h/24 et 7j/7.</p>', 'Nous sommes lÔÇª pour vous', 'Nous contacter', 2, 12),
(51, '<p>Contact Center</p>', '<p>Bient├┤t, notre centre d''appel sera disponible pour toute information sur vos d├®marches fiscales. Nos conseillers seront ├á votre ├®coute, 24h/24 et 7j/7.</p>', 'We are here for you', 'Contact us', 3, 12),
(52, '<p>Ivontoerana Fifandraisana</p>', '<p>Bient├┤t, notre centre d''appel sera disponible pour toute information sur vos d├®marches fiscales. Nos conseillers seront ├á votre ├®coute, 24h/24 et 7j/7.</p>', 'Miaraka aminay ianao', 'Mifandraisa aminay', 1, 12),
(73, 'Appeler', 'SecrÔÇÜtariat DGI : +261 20 85 287 08<br>Cellule Communication DGI : +261 32 12 015 03<br>Cellule eHetra : +261 32 12 015 04<br>Hotline SSIF : +261 32 12 011 74 / +261 34 49 431 52', 'NOUS CONTACTER', '', 2, 26),
(74, 'Ecrire', 'SecrÔÇÜtariat DGI : dgimpots@moov.mg<br>Cellule Communication DGI : communication.dgimpots@gmail.com<br>Hotline SSIF : impot.ssif.hotline@gmail.com', '', '', 2, 27),
(75, 'Visiter', 'Immeuble de l''ÔÇÜconomie et des Finances<br>4┼áme ÔÇÜtage - porte 420<br>Antaninarenina Antananarivo', '', '', 2, 28),
(76, 'Call', 'DGI Secretariat: +261 20 85 287 08<br>DGI Communication Unit: +261 32 12 015 03<br>eHetra Unit: +261 32 12 015 04<br>SSIF Hotline: +261 32 12 011 74 / +261 34 49 431 52', 'CONTACT US', '', 3, 26),
(77, 'Write', 'DGI Secretariat: dgimpots@moov.mg<br>DGI Communication Unit: communication.dgimpots@gmail.com<br>SSIF Hotline: impot.ssif.hotline@gmail.com', '', '', 3, 27),
(78, 'Visit', 'Ministry of Economy and Finance Building<br>4th floor - door 420<br>Antaninarenina Antananarivo', '', '', 3, 28),
(79, 'Antsoy', 'Sekreterian''ny DGI : +261 20 85 287 08<br>Sampan''asa Fampandraharahana DGI : +261 32 12 015 03<br>Sampan''asa eHetra : +261 32 12 015 04<br>Hotline SSIF : +261 32 12 011 74 / +261 34 49 431 52', 'Hifandray aminay', '', 1, 26),
(80, 'Hanoratra', 'Sekreterian''ny DGI : dgimpots@moov.mg<br>Sampan''asa Fampandraharahana DGI : communication.dgimpots@gmail.com<br>Hotline SSIF : impot.ssif.hotline@gmail.com', '', '', 1, 27),
(81, 'Hitsidika', 'Tranon''ny Toekarena sy ny Finansa<br>4┼áme ÔÇÜtage - varavarana 420<br>Antaninarenina Antananarivo', '', '', 1, 28),
(82, '', '', '', '', 1, 29),
(83, '', '', '', '', 2, 29),
(84, '', '', '', '', 3, 29),
(85, '', '', '', '', 1, 30),
(86, '', '', '', '', 2, 30),
(87, '', '', '', '', 3, 30),
(103, '<p><br></p>', '<p>Consultez ici l''ensemble des codes fiscaux et manuels de r├®f├®rence.</p>', '', '', 1, 36),
(47, '<p>Attributions</p>', '<p>Explorer ici l''organisation </p><p>d├®taill├®e de la DGI </p><p>de Madagascar.</p><p><br></p>', '', '', 2, 9),
(97, '', '', '', '', 1, 34),
(98, '', '', '', '', 2, 34),
(99, '', '', '', '', 3, 34),
(100, '', '', '', '', 1, 35),
(101, '', '', '', '', 2, 35),
(102, '', '', '', '', 3, 35),
(88, '<p><br></p>', '<p><br></p>', '', '', 1, 31),
(89, '<p><br></p>', '<p><br></p>', '', '', 2, 31),
(90, '<p><br></p>', '<p><br></p>', '', '', 3, 31),
(91, '<p><br></p>', '<p><br></p>', '', '', 1, 32),
(92, '<p><br></p>', '<p><br></p>', '', '', 2, 32),
(93, '<p><br></p>', '<p><br></p>', '', '', 3, 32),
(94, '<p><br></p>', '<p>Retrouvez tous nos guides et brochures d''information pour vous accompagner dans la compr├®hension et l''accomplissement de vos obligations fiscales.</p>', '', '', 1, 33),
(95, '<p><br></p>', '<p>Retrouvez tous nos guides et brochures d''information pour vous accompagner dans la compr├®hension et l''accomplissement de vos obligations fiscales.</p>', '', '', 2, 33),
(43, '<p>Fahitana</p>', '<p>T├®l├®chargez ici l''ensemble des documents fondateurs </p><p>qui d├®finissent notre engagement d''excellence </p><p>au service des contribuables et du d├®veloppement ├®conomique.</p>', '', '', 1, 8),
(104, '<p><br></p>', '<p>Consultez ici l''ensemble des codes fiscaux et manuels de r├®f├®rence.</p>', '', '', 2, 36),
(105, '<p><br></p>', '<p>Consultez ici l''ensemble des codes fiscaux et manuels de r├®f├®rence.</p>', '', '', 3, 36),
(66, '<p>E-Service</p>', '<p>Simplifiez vos obligations fiscales avec SAFI, la plateforme digitale de la Direction G├®n├®rale des Imp├┤ts </p><p>qui vous permet de d├®clarer, payer et suivre vos imp├┤ts en ligne,</p><p> avec un syst├¿me d''alertes personnalis├®es pour </p><p>une gestion optimale de votre compte.</p>', 'D├®marches fiscales', 'Se connecter', 1, 10),
(63, '<p>Resources</p>', '<p>Acc├®dez ├á toutes les informations sur l''organisation de la Direction G├®n├®rale des Imp├┤ts : son ├®quipe dirigeante, ses responsables et ses structures op├®rationnelles.</p>', '', 'show more', 3, 5),
(64, '<p>Famakafakana ara-bola</p>', '<p>Consultez dans cette rubrique les documents d''analyse approfondis retra├ºant ses performances en mati├¿re de mobilisation des recettes fiscales, statistiques p├®riodiques des r├®alisations, ├®tudes sectorielles, publications ├®conomiques, rapports d├®taill├®s dÔÇÖactivit├®s, etc.</p>', '', 'hijery', 1, 6),
(70, '<p>Analytiques fiscales</p>', '<p>Consultez dans cette rubrique les documents d''analyse approfondis retra├ºant ses performances en mati├¿re de mobilisation des recettes fiscales, statistiques p├®riodiques des r├®alisations, ├®tudes sectorielles, publications ├®conomiques, rapports d├®taill├®s dÔÇÖactivit├®s, etc.</p>', '', 'voir plus', 2, 6),
(67, '<p>Tale jeneralin''ny hetra</p>', '<p>Germain</p>', 'Ny mombamomba', '', 1, 2),
(68, '<p>Directeur G├®n├®ral des Imp├┤ts</p>', '<p>Germain</p>', '├á propos', '', 2, 2),
(69, '<p>Director General of Taxes</p>', '<p>Germain</p>', 'about', '', 3, 2),
(62, '<p>Lal├ána</p>', '<p>Retrouvez ici l''ensemble des textes et r├®glementations en vigueur r├®gissant la fiscalit├® malgache, r├®guli├¿rement actualis├®s pour vous permettre de mieux comprendre vos droits et obligations fiscales.</p>', '', 'hijery', 1, 4),
(65, '<p>E-Service</p>', '<p>Simplifiez vos obligations fiscales avec SAFI, la plateforme digitale de la Direction G├®n├®rale des Imp├┤ts </p><p>qui vous permet de d├®clarer, payer et suivre vos imp├┤ts en ligne,</p><p> avec un syst├¿me d''alertes personnalis├®es pour </p><p>une gestion optimale de votre compte.</p>', 'D├®marches fiscales', 'Se connecter', 3, 10),
(96, '<p><br></p>', '<p>Retrouvez tous nos guides et brochures d''information pour vous accompagner dans la compr├®hension et l''accomplissement de vos obligations fiscales.</p>', '', '', 3, 33),
(45, '<p>Vision</p>', '<p>T├®l├®chargez ici l''ensemble des documents fondateurs </p><p>qui d├®finissent notre engagement d''excellence </p><p>au service des contribuables et du d├®veloppement ├®conomique.</p>', '', '', 3, 8),
(44, '<p>Vision</p>', '<p>T├®l├®chargez ici l''ensemble des documents fondateurs </p><p>qui d├®finissent notre engagement d''excellence </p><p>au service des contribuables et du d├®veloppement ├®conomique.</p>', '', '', 2, 8),
(55, '<p>Tenin''i DGI</p>', '<p>Engag├®e dans une transformation digitale majeure ├á travers son nouveau Syst├¿me d''Administration Fiscale Int├®gr├® (SAFI), la DGI vous accueille sur son portail en ligne, incarnant sa vision d''une administration fiscale<strong> innovante, transparente et pilier de l''├®mergence</strong> . D├®couvrez nos services modernes, con├ºus pour garantir votre satisfaction et pr├®server un climat de confiance, conform├®ment ├á lÔÇÖimage dÔÇÖun gouvernement <strong>┬½ manakaiky vahoaka ┬╗</strong>.</p>', 'DGI', '', 1, 3),
(58, '<p>Mot du DGI</p>', '<p>Engag├®e dans une transformation digitale majeure ├á travers son nouveau Syst├¿me d''Administration Fiscale Int├®gr├® (SAFI), la DGI vous accueille sur son portail en ligne, incarnant sa vision d''une administration fiscale<strong> innovante, transparente et pilier de l''├®mergence</strong> . D├®couvrez nos services modernes, con├ºus pour garantir votre satisfaction et pr├®server un climat de confiance, conform├®ment ├á lÔÇÖimage dÔÇÖun gouvernement <strong>┬½ manakaiky vahoaka ┬╗</strong>.</p>', 'DGI', '', 2, 3),
(72, '<p>Word of the DGI</p>', '<p>Engag├®e dans une transformation digitale majeure ├á travers son nouveau Syst├¿me d''Administration Fiscale Int├®gr├® (SAFI), la DGI vous accueille sur son portail en ligne, incarnant sa vision d''une administration fiscale<strong> innovante, transparente et pilier de l''├®mergence</strong> . D├®couvrez nos services modernes, con├ºus pour garantir votre satisfaction et pr├®server un climat de confiance, conform├®ment ├á lÔÇÖimage dÔÇÖun gouvernement <strong>┬½ manakaiky vahoaka ┬╗</strong>.</p>', 'DGI', '', 3, 3),
(115, '', '', '', '', 1, 40),
(116, '', '', '', '', 2, 40),
(117, '', '', '', '', 3, 40),
(118, '', '', '', '', 1, 41),
(119, '', '', '', '', 2, 41),
(120, '', '', '', '', 3, 41),
(71, '<p>Fiscal analytics</p>', '<p>Consultez dans cette rubrique les documents d''analyse approfondis retra├ºant ses performances en mati├¿re de mobilisation des recettes fiscales, statistiques p├®riodiques des r├®alisations, ├®tudes sectorielles, publications ├®conomiques, rapports d├®taill├®s dÔÇÖactivit├®s, etc.</p>', '', 'show more', 3, 6),
(121, '', '', '', '', 1, 42),
(122, '', '', '', '', 2, 42),
(123, '', '', '', '', 3, 42),
(106, '<p><br></p>', '<p><br></p>', '', '', 1, 37),
(107, '<p><br></p>', '<p><br></p>', '', '', 2, 37),
(108, '<p><br></p>', '<p><br></p>', '', '', 3, 37),
(109, '<p><br></p>', '<p><br></p>', '', '', 1, 38),
(110, '<p><br></p>', '<p><br></p>', '', '', 2, 38),
(111, '<p><br></p>', '<p><br></p>', '', '', 3, 38),
(112, '<p><br></p>', '<p>Acc├®dez aux coordonn├®es compl├¿tes de nos services centraux et r├®gionaux pour faciliter vos contacts et d├®marches aupr├¿s de la DGI.</p>', '', '', 1, 39),
(113, '<p><br></p>', '<p>Acc├®dez aux coordonn├®es compl├¿tes de nos services centraux et r├®gionaux pour faciliter vos contacts et d├®marches aupr├¿s de la DGI.</p>', '', '', 2, 39),
(114, '<p><br></p>', '<p>Acc├®dez aux coordonn├®es compl├¿tes de nos services centraux et r├®gionaux pour faciliter vos contacts et d├®marches aupr├¿s de la DGI.</p>', '', '', 3, 39),
(127, '', '', '', '', 1, 44),
(128, '', '', '', '', 2, 44),
(129, '', '', '', '', 3, 44),
(130, '', '', '', '', 1, 45),
(131, '', '', '', '', 2, 45),
(132, '', '', '', '', 3, 45),
(133, '', '', '', '', 1, 46),
(134, '', '', '', '', 2, 46),
(135, '', '', '', '', 3, 46),
(136, '', '', '', '', 1, 47),
(137, '', '', '', '', 2, 47),
(138, '', '', '', '', 3, 47),
(139, '', '', '', '', 1, 48),
(140, '', '', '', '', 2, 48),
(141, '', '', '', '', 3, 48),
(142, '', '', '', '', 1, 49),
(143, '', '', '', '', 2, 49),
(144, '', '', '', '', 3, 49),
(145, '', '', '', '', 1, 50),
(146, '', '', '', '', 2, 50),
(147, '', '', '', '', 3, 50),
(124, '<p><br></p>', '<p><br></p>', '', '', 1, 43),
(125, '<p><br></p>', '<p><br></p>', '', '', 2, 43),
(126, '<p><br></p>', '<p><br></p>', '', '', 3, 43);

-- Insertion dans la table groupe (aucune donnée dans le dump)
-- Pas d'insertion nécessaire car la table est vide dans le dump

-- Insertion dans la table piece_jointe (aucune donnée dans le dump)
-- Pas d'insertion nécessaire car la table est vide dans le dump

-- Insertion dans la table user_pin
INSERT INTO public.user_pin (id, pin, creation_date, expiration_date, id_user) VALUES
(1, '922655', '2025-03-12 02:52:15.586787', '2025-03-12 03:02:15.586787', 'b49f1f09-6798-421d-9546-f1792d952f39'),
(2, '471457', '2025-03-12 08:53:21.965224', '2025-03-12 09:03:21.965224', 'b49f1f09-6798-421d-9546-f1792d952f39'),
(3, '376341', '2025-03-12 09:15:01.27241', '2025-03-12 09:25:01.27241', 'b49f1f09-6798-421d-9546-f1792d952f39');

-- Insertion dans la table user_token (aucune donnée dans le dump)
-- Pas d'insertion nécessaire car la table est vide dans le dump

-- Réinitialisation des séquences
SELECT pg_catalog.setval('public.actualite_id_seq', 1, false);
SELECT pg_catalog.setval('public.attempts_id_seq', 1, true);
SELECT pg_catalog.setval('public.configuration_id_seq', 5, true);
SELECT pg_catalog.setval('public.general_info_id_seq', 50, true);
SELECT pg_catalog.setval('public.general_info_valeur_id_seq', 147, true);
SELECT pg_catalog.setval('public.groupe_id_seq', 1, false);
SELECT pg_catalog.setval('public.langue_id_seq', 1, false);
SELECT pg_catalog.setval('public.piece_jointe_id_seq', 1, false);
SELECT pg_catalog.setval('public.user_pin_id_seq', 3, true);
SELECT pg_catalog.setval('public.user_token_id_seq', 1, false);