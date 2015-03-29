CREATE TABLE Utilisateur(
    pseudo VARCHAR(20),
    motDePasse VARCHAR(20) NOT NULL,
    eMail VARCHAR(40) NOT NULL, 
    adresse VARCHAR(50) NOT NULL,
    nom VARCHAR(20) NOT NULL,
    prenom VARCHAR(20) NOT NULL,
    tel VARCHAR(15) NOT NULL,
    role VARCHAR(4),
    CONSTRAINT pk_Utilisateur PRIMARY KEY (pseudo)
    CONSTRAINT chk_Utilisateur CHECK (role IN ('prod', 'cons', 'resp');
);

-- /!\ idOffre is a - surrogate (or synthetic) key -,
-- while (createur, nomProduit, prixOffre, dureeOffre) is the businessKey 
CREATE TABLE Offre(
    idOffre NUMBER,
    createur VARCHAR(20),
    nomProduit VARCHAR(30) NOT NULL,
    prixOffre NUMBER NOT NULL,
    dureeOffre NUMBER  NOT NULL,
    CONSTRAINT pk_Offre PRIMARY KEY (idOffre)
    CONSTRAINT uk_Offre UNIQUE (createur, nomProduit, prixOffre, dureeOffre)
    CONSTRAINT fk_Offre FOREIGN KEY (createur)
    REFERENCES Utilisateur(pseudo)
    CONSTRAINT chk_Offre_prix CHECK(prixOffre >= 0)
    CONSTRAINT chk_Offre_duree CHECK(dureeOffre > 0)
);

CREATE TABLE Quantite(
    idOffrePrecisee NUMBER,   
    qte NUMBER NOT NULL,
    uniteQte VARCHAR(20) NOT NULL,
    CONSTRAINT pk_Quantite PRIMARY KEY (idOffrePrecisee)
    CONSTRAINT fk_Quantite FOREIGN KEY (idOffrePrecisee)
    REFERENCES Offre(idOffre)
);

CREATE TABLE Demande(
    idOffreDemandee NUMBER,
    demandeur VARCHAR(20),
    dateDemande DATE NOT NULL,
    prixDemande NUMBER NOT NULL,
    dureeDemande NUMBER NOT NULL,
    qteDemande NUMBER NOT NULL,
    uniteDemande VARCHAR(20) NOT NULL,
    dateDebutLivraison DATE, -- translation of the 1st inheritance by PUSH-UP
    aRenouveler NUMBER, -- translation of the 2nd inheritance by PUSH-UP
    CONSTRAINT pk_Demande PRIMARY KEY(demandeur, idOffre)
    CONSTRAINT fk_Demande_Utilisateur FOREIGN KEY (demandeur)
    REFERENCES Utilisateur(pseudo)
    CONSTRAINT fk_Demande_Offre FOREIGN KEY (idOffreDemandee)
    REFERENCES Utilisateur(idOffre) 
    CONSTRAINT chk_Demande_prix CHECK(prixDemande >= 0)
    CONSTRAINT chk_Demande_duree CHECK(dureeDemande > 0)
    CONSTRAINT chk_Demande_qte CHECK(qteDemande > 0)
);

CREATE TABLE Semaine(
    idSemaine NUMBER,
    numSemaine NUMBER NOT NULL,
    annee NUMBER NOT NULL,
    CONSTRAINT pk_Semaine PRIMARY KEY(idSemaine) 
    CONSTRAINT uk_Semaine UNIQUE (numSemaine, annee)
);

CREATE TABLE AssurePermanence(
    idSemainePerm NUMBER,
    permanencier VARCHAR(20),
    CONSTRAINT pk_AssurePermanence PRIMARY KEY (idSemainePerm, permanencier)
    CONSTRAINT fk_AssurePermanence_Semaine FOREIGN KEY (idSemainePerm)
    REFERENCES Semaine(idSemaine) 
    CONSTRAINT fk_AssurePermanence_Utilisateur FOREIGN KEY (permanencier)
    REFERENCES Utilisateur(pseudo) 
);

CREATE TABLE EstDisponible(
    idSemaineDispo NUMBER,
    consoDispo VARCHAR(20),
    CONSTRAINT pk_EstDisponible PRIMARY KEY (idSemaineDispo, consoDispo)
    CONSTRAINT fk_EstDisponible_Semaine FOREIGN KEY (idSemaineDispo)
    REFERENCES Semaine(idSemaine) 
    CONSTRAINT fk_EstDisponible_Utilisateur FOREIGN KEY (consoDispo)
    REFERENCES Utilisateur(pseudo) 
);

CREATE TABLE EstIndisponible(
    idSemaineIndispo NUMBER,
    consoIndispo VARCHAR(20),
    CONSTRAINT pk_EstIndisponible PRIMARY KEY (idSemaineIndispo, consoIndispo)
    CONSTRAINT fk_EstIndisponible_Semaine FOREIGN KEY (idSemaineIndispo)
    REFERENCES Semaine(idSemaine) 
    CONSTRAINT fk_EstIndisponible_Utilisateur FOREIGN KEY (consoIndispo)
    REFERENCES Utilisateur(pseudo) 
);