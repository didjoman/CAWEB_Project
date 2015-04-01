-- TODO : creer un trigger qui créé une semaine au moment de l'insertion dans EstDisponible et EstPermanence si il n'y en a pas.
/*
CREATE TABLE Semaine(
    idSemaine NUMBER NOT NULL,
    numSemaine NUMBER NOT NULL,
    annee NUMBER NOT NULL,
    CONSTRAINT pk_Semaine PRIMARY KEY(idSemaine),
    CONSTRAINT uk_Semaine UNIQUE (numSemaine, annee)
);


-- Trigger d'auto incrément de l'id d'offre
CREATE SEQUENCE SemaineID
        START WITH 1
        INCREMENT BY 1
        CACHE 100;
/
CREATE OR REPLACE TRIGGER trigger_incr_Semaine_ID
        BEFORE INSERT ON Semaine
        FOR EACH ROW
BEGIN
        :new.idSemaine := SemaineID.nextval;
END;
/
*/



FUNCTION Get_or_create_Semaine(numSem IN NUMBER, anneeSem IN NUMBER) 
RETURN NUMBER
IS
    id number := -1;
BEGIN
   -- Si la semaine a déja été crée, on récupère l'id
   SELECT idSemaine INTO id
   FROM Semaine
   WHERE numSemaine = numSem
   AND annee = anneeSem;
   RETURN id;
EXCEPTION
   WHEN no_data_found THEN
        -- 1) on insert la semaine
        INSERT INTO Semaine(idSemaine, numSemaine, annee)
        VALUES('', numSem, anneeSem);
        -- 2) On récupère l'id de la semaine :
        SELECT idSemaine INTO id
        FROM Semaine
        WHERE numSemaine = numSem
        AND annee = anneeSem;
        RETURN id;
END; 


-- Trigger qui vérifie que la semaine est créé, en créé une sinon
CREATE OR REPLACE TRIGGER trigger_verif_Semaine
        INSTEAD OF INSERT ON AssurePermanence
        FOR EACH ROW
DECLARE
BEGIN
        INSERT INTO AssurePermanence (IdSemainePerm, Permanencier) 
        VALUES (
END;
/



/*
CREATE TABLE EstIndisponible(
    idSemaineIndispo NUMBER,
    consoIndispo VARCHAR(20),
    CONSTRAINT pk_EstIndisponible PRIMARY KEY (idSemaineIndispo, consoIndispo)
    CONSTRAINT fk_EstIndisponible_Semaine FOREIGN KEY (idSemaineIndispo)
    REFERENCES Semaine(idSemaine) 
    CONSTRAINT fk_EstIndisponible_Utilisateur FOREIGN KEY (consoIndispo)
    REFERENCES Utilisateur(pseudo) 
);
*/
