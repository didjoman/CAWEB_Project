-- Remplissage table Utilisateur :
SET AUTOCOMMIT OFF

INSERT INTO Utilisateur (pseudo, motDePasse, eMail, adresse, nom, prenom, tel, roleUtilisateur)
VALUES('test1', 'mdp1', 'test1@free.fr', 'FamilleTest', '50 rue d ici', 'MrTest', '06-00-05-03-76', 'PROD');

INSERT INTO Utilisateur (pseudo, motDePasse, eMail, adresse, nom, prenom, tel, roleUtilisateur)
VALUES('test2', 'mdp2', 'test2@free.fr', '52 rue d ici', 'FamilleTest', 'MrTest2', '06-13-43-89-12', 'PROD');

INSERT INTO Utilisateur (pseudo, motDePasse, eMail, adresse, nom, prenom, tel, roleUtilisateur)
VALUES('test3', 'mdp3', 'test3@free.fr', '53 rue d ici', 'FamilleTest', 'MrTest3', '06-12-84-23-01', 'CONS');

INSERT INTO Utilisateur (pseudo, motDePasse, eMail, adresse, nom, prenom, tel, roleUtilisateur)
VALUES('test31', 'mdp31', 'test31@free.fr', '531 rue d ici', 'FamilleTest', 'MrTest31', '06-12-84-23-01', 'CONS');

INSERT INTO Utilisateur (pseudo, motDePasse, eMail, adresse, nom, prenom, tel, roleUtilisateur)
VALUES('test4', 'mdp4', 'test4@free.fr', '54 rue d ici', 'FamilleTest', 'MrTest4', '06-23-43-65-13', 'RESP');


-- Remplissage table Offre
INSERT INTO Offre (idOffre, createur, nomProduit, dureeOffre)
VALUES ('', 'test1', 'pommes de terre', 60);

INSERT INTO Offre (idOffre, createur, nomProduit, dureeOffre)
VALUES ('', 'test1', 'tomate', 60);

INSERT INTO Offre (idOffre, createur, nomProduit, dureeOffre)
VALUES ('', 'test2', 'tomate', 60);

-- Remplissage Quantite
INSERT INTO Quantite(idOffrePrecisee, qte, uniteQte, prix)
VALUES (1, 2.5, 'kg', 3);

INSERT INTO Quantite(idOffrePrecisee, qte, uniteQte, prix)
VALUES (1, 5, 'kg', 5);

INSERT INTO Quantite(idOffrePrecisee, qte, uniteQte, prix)
VALUES (2, 1, 'kg', 5);

INSERT INTO Quantite(idOffrePrecisee, qte, uniteQte, prix)
VALUES (3, 1, 'kg', 5);

-- Remplissage Contrat :
INSERT INTO Contrat(idContrat, offreur, demandeur, dateContrat, nomProduitContrat, prixLotContrat, dureeContrat, qteLotContrat, uniteContrat, nbLots, dateDebutLivraison, aRenouveler)
VALUES ('', 'test1', 'test3', SYSDATE, 'tomate', 5, 60, 1, 'kg', 3, '', '');

INSERT INTO Contrat(idContrat, offreur, demandeur, dateContrat, nomProduitContrat, prixLotContrat, dureeContrat, qteLotContrat, uniteContrat, nbLots, dateDebutLivraison, aRenouveler)
VALUES ('', 'test1', 'test3', SYSDATE, 'pommes de terre', 3, 60, 2.5, 'kg', 3, '', '');


INSERT INTO Contrat(idContrat, offreur, demandeur, dateContrat, nomProduitContrat, prixLotContrat, dureeContrat, qteLotContrat, uniteContrat, nbLots, dateDebutLivraison, aRenouveler)
VALUES ('', 'test1', 'test3', SYSDATE, 'pommes de terre', 3, 60, 2.5, 'kg', 3, TO_DATE ('03/04/2015', 'DD.MM.YYYY'), '');

INSERT INTO Contrat(idContrat, offreur, demandeur, dateContrat, nomProduitContrat, prixLotContrat, dureeContrat, qteLotContrat, uniteContrat, nbLots, dateDebutLivraison, aRenouveler)
VALUES ('', 'test1', 'test3', SYSDATE, 'pommes de terre', 3, 60, 2.5, 'kg', 3, TO_DATE ('05/04/2015', 'DD.MM.YYYY'), '');

INSERT INTO Contrat(idContrat, offreur, demandeur, dateContrat, nomProduitContrat, prixLotContrat, dureeContrat, qteLotContrat, uniteContrat, nbLots, dateDebutLivraison, aRenouveler)
VALUES ('', 'test1', 'test3', SYSDATE, 'pommes de terre', 3, 60, 2.5, 'kg', 3, TO_DATE ('06/08/2014', 'DD.MM.YYYY'), 1);

SELECT * FROM CONTRAT JOIN UTILISATEUR ON(pseudo = offreur OR pseudo = demandeur) 
WHERE (demandeur = 'test1' OR offreur = 'test1') AND dateDebutLivraison IS NOT NULL;

-- Remplissage EstDisponible:
INSERT INTO EstDisponible (consoDispo, numSemaine, annee, estDispo)
VALUES ('test3', 8, 2015, 1);

INSERT INTO EstDisponible (consoDispo, numSemaine, annee, estDispo)
VALUES ('test3', 9, 2015, 1);

INSERT INTO EstDisponible (consoDispo, numSemaine, annee, estDispo)
VALUES ('test3', 10, 2015, 0);

INSERT INTO EstDisponible (consoDispo, numSemaine, annee, estDispo)
VALUES ('test3', 11, 2015, 0);

-- Remplissage AssurePermanence
INSERT INTO AssurePermanence (permanencier1, permanencier2, numSemaine, annee)
VALUES ('test3', '', 9, 2015);

COMMIT;

SET AUTOCOMMIT ON