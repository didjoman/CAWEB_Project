SET AUTOCOMMIT OFF;

CREATE TABLE Utilisateur(
    pseudo VARCHAR(20) NOT NULL,
    motDePasse VARCHAR(20) NOT NULL,
    eMail VARCHAR(40) NOT NULL, 
    adresse VARCHAR(50) NOT NULL,
    nom VARCHAR(20) NOT NULL,
    prenom VARCHAR(20) NOT NULL,
    tel VARCHAR(15) NOT NULL,
    roleUtilisateur VARCHAR(4),
    CONSTRAINT pk_Utilisateur PRIMARY KEY (pseudo),
    CONSTRAINT chk_Utilisateur CHECK (roleUtilisateur IN ('PROD', 'CONS', 'RESP'))
);

-- /!\ idOffre is a - surrogate (or synthetic) key -,
-- while (createur, nomProduit, prixOffre, dureeOffre) is the businessKey 
CREATE TABLE Offre(
    idOffre NUMBER,
    createur VARCHAR(20) NOT NULL,
    nomProduit VARCHAR(30) NOT NULL,
    dureeOffre NUMBER  NOT NULL,
    CONSTRAINT pk_Offre PRIMARY KEY (idOffre),
    CONSTRAINT uk_Offre UNIQUE (createur, nomProduit, dureeOffre),
    CONSTRAINT fk_Offre FOREIGN KEY (createur)
    REFERENCES Utilisateur(pseudo) 
    ON DELETE CASCADE,
    CONSTRAINT chk_Offre_duree CHECK(dureeOffre > 0)
);

-- Trigger d'auto incrément de l'id d'offre
CREATE SEQUENCE OffreID
        START WITH 1
        INCREMENT BY 1
        CACHE 100;
/
CREATE OR REPLACE TRIGGER trigger_incr_Offre_ID
    BEFORE INSERT ON Offre
    FOR EACH ROW
BEGIN
    SELECT OffreID.nextval
    INTO   :new.idOffre
    FROM   dual;
END;
/

CREATE TABLE Quantite(
    idOffrePrecisee NUMBER NOT NULL,   
    qte NUMBER NOT NULL,
    uniteQte VARCHAR(20) NOT NULL,
    prix NUMBER NOT NULL,
    CONSTRAINT pk_Quantite PRIMARY KEY (idOffrePrecisee, qte, uniteQte, prix),
    CONSTRAINT fk_Quantite FOREIGN KEY (idOffrePrecisee)
    REFERENCES Offre(idOffre)
    ON DELETE CASCADE,
    CONSTRAINT chk_Quantite_prix CHECK(prix >= 0)
);

CREATE TABLE Contrat(
    idContrat NUMBER NOT NULL,
    offreur VARCHAR(20) NOT NULL,
    demandeur VARCHAR(20) NOT NULL,
    dateContrat DATE NOT NULL,
    nomProduitContrat VARCHAR(30) NOT NULL,
    prixLotContrat NUMBER NOT NULL,
    dureeContrat NUMBER NOT NULL,
    qteLotContrat NUMBER NOT NULL,
    uniteContrat VARCHAR(20) NOT NULL,
    nbLots NUMBER NOT NULL,
    dateDebutLivraison DATE, -- translation of the 1st inheritance by PUSH-UP
    aRenouveler NUMBER(1,0), -- translation of the 2nd inheritance by PUSH-UP
    CONSTRAINT pk_Contrat PRIMARY KEY(idContrat),
    CONSTRAINT fk_Contrat_Util_deman FOREIGN KEY (demandeur)
    REFERENCES Utilisateur(pseudo)
    ON DELETE CASCADE,
    CONSTRAINT fk_Contrat_Util_off FOREIGN KEY (offreur)
    REFERENCES Utilisateur(pseudo)
    ON DELETE CASCADE,
    CONSTRAINT chk_Contrat_prix CHECK(prixLotContrat >= 0),
    CONSTRAINT chk_Contrat_duree CHECK(dureeContrat > 0),
    CONSTRAINT chk_Contrat_qte CHECK(qteLotContrat > 0)
);

-- Trigger d'auto incrément de l'id du contrat
CREATE SEQUENCE ContratID
        START WITH 1
        INCREMENT BY 1
        CACHE 100;
/
CREATE OR REPLACE TRIGGER trigger_incr_Contrat_ID
        BEFORE INSERT ON Contrat
        FOR EACH ROW
BEGIN
        :new.idContrat := ContratID.nextval;
END;
/

-- Trigger qui vérifie que les attributs du contrat proviennent d'une offre à la création.
CREATE OR REPLACE TRIGGER trigger_verif_offre
        BEFORE INSERT ON Contrat
        FOR EACH ROW
DECLARE
        counter INTEGER := 0;
BEGIN
        SELECT count(idOffre) INTO counter
        FROM Offre
        JOIN Quantite 
        ON idOffre = idOffrePrecisee
        WHERE createur = :new.offreur
        AND nomProduit = :new.nomProduitContrat
        AND prix = :new.prixLotContrat
        AND dureeOffre = :new.dureeContrat
        AND qte = :new.qteLotContrat
        AND uniteQte = :new.uniteContrat;
        -- Then we check wether there was a result or not ... 
        If counter <> 1 then
                RAISE_APPLICATION_ERROR ( -20012, 'Erreur ; le contrat ne repond a aucune offre valide' ) ;
        End If;
END;
/




CREATE TABLE AssurePermanence(
    numSemaine NUMBER NOT NULL,
    annee NUMBER NOT NULL,
    permanencier1 VARCHAR(20),
    permanencier2 VARCHAR(20),
    CONSTRAINT pk_AssurePerm PRIMARY KEY (numSemaine, annee),
    CONSTRAINT fk_AssurePerm_Util1 FOREIGN KEY (permanencier1)
    REFERENCES Utilisateur(pseudo) 
    ON DELETE CASCADE,
    CONSTRAINT fk_AssurePerm_Util2 FOREIGN KEY (permanencier2)
    REFERENCES Utilisateur(pseudo) 
    ON DELETE CASCADE
);
-- Boolean pour savoir si dispo ou non


CREATE TABLE EstDisponible(
    consoDispo VARCHAR(20) NOT NULL,
    numSemaine NUMBER NOT NULL,
    annee NUMBER NOT NULL,
    estDispo NUMBER(1,0) NOT NULL,
    CONSTRAINT pk_EstDisponible PRIMARY KEY (consoDispo, numSemaine, annee),
    CONSTRAINT fk_EstDisponible_Utilisateur FOREIGN KEY (consoDispo)
    REFERENCES Utilisateur(pseudo) 
    ON DELETE CASCADE
);

--begin updateUserDispo(13,2015, 'test3', 1); end;

CREATE OR REPLACE PROCEDURE updateUserDispo 
(numSe IN NUMBER, anneeSe IN NUMBER, conso IN VARCHAR, dispo IN NUMBER)
IS
counterDispo INTEGER := 0;
BEGIN
   SELECT COUNT(*) INTO counterDispo
   FROM EstDisponible
   WHERE numSemaine = numSe
   AND annee = anneeSe
   AND consoDispo = conso;

   IF(counterDispo <> 0) THEN
      UPDATE EstDisponible
      SET estDispo = dispo
      WHERE numSemaine = numSe
      AND annee = anneeSe
      AND consoDispo = conso;
   ELSE
      INSERT INTO EstDisponible(numSemaine, annee, consoDispo, estDispo)
      VALUES(numSe, anneeSe, conso, dispo);
   END IF;
END updateUserDispo;
/

CREATE OR REPLACE PROCEDURE updatePerm 
(numSe IN NUMBER, anneeSe IN NUMBER, perm1 IN VARCHAR, perm2 IN VARCHAR)
IS
counterPerm INTEGER := 0;
BEGIN
   SELECT COUNT(*) INTO counterPerm
   FROM AssurePermanence
   WHERE numSemaine = numSe
   AND annee = anneeSe;

   IF(counterPerm <> 0) THEN
      IF(perm1 IS NULL AND perm2 IS NULL) THEN
         DELETE FROM AssurePermanence
         WHERE numSemaine = numSe
         AND annee = anneeSe;
      ELSE
         UPDATE AssurePermanence
         SET permanencier1 = perm1, permanencier2 = perm2
         WHERE numSemaine = numSe
         AND annee = anneeSe;
      END IF;
   ELSE
      INSERT INTO AssurePermanence(numSemaine, annee, permanencier1, permanencier2)
      VALUES(numSe, anneeSe, perm1, perm2);
   END IF;
END updatePerm;
/


-- trigger pour supprimer un enregistrement dans AssurePermanence si les deux mecs sont vides.
-- Trigger qui vérifie que les attributs du contrat proviennent d'une offre à la création.

CREATE OR REPLACE TRIGGER trigger_verif_perm
        AFTER INSERT ON AssurePermanence
        FOR EACH ROW
DECLARE
        counter INTEGER := 0;
BEGIN
    if(:new.permanencier1 IS NULL AND :new.permanencier1 IS NULL) THEN
        DELETE AssurePermanence 
        WHERE numSemaine = :new.numSemaine
        AND annee = :new.annee;
    END IF;
END;

/


COMMIT; 

SET AUTOCOMMIT ON;