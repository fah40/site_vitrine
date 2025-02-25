-- Création de la fonction du trigger
CREATE OR REPLACE FUNCTION insert_attempt_on_user_insert()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO attempts (date_next_attempt, count_attempt, id_user)
    VALUES (NULL, 0, NEW.id);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Création du trigger
CREATE TRIGGER user_insert_trigger
AFTER INSERT ON user_
FOR EACH ROW
EXECUTE FUNCTION insert_attempt_on_user_insert();
