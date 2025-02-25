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
('MLG'),
('FRA'),
('ENG');