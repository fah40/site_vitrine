CREATE OR REPLACE FUNCTION create_general_info_valeur_for_all_languages()
RETURNS TRIGGER AS $$
DECLARE
    lang_record RECORD;
BEGIN
    -- Boucler sur toutes les langues disponibles dans la table `langue`
    FOR lang_record IN SELECT id FROM langue LOOP
        -- Insérer un enregistrement dans `general_info_valeur` pour chaque langue
        INSERT INTO general_info_valeur (titre, valeur, entete, bouton, id_langue, id_general_info)
        VALUES ('', '', '', '', lang_record.id, NEW.id); -- NEW.id est l'ID du `general_info` inséré
    END LOOP;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_create_general_info_valeur
AFTER INSERT ON general_info
FOR EACH ROW
EXECUTE FUNCTION create_general_info_valeur_for_all_languages();