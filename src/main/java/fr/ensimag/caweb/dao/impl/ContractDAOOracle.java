/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.caweb.dao.impl;

import fr.ensimag.caweb.dao.ContractDAO;
import fr.ensimag.caweb.dao.DAOException;
import fr.ensimag.caweb.dao.DAOFactory;
import fr.ensimag.caweb.models.Contract.Contract;
import fr.ensimag.caweb.models.Contract.ContractBuilder;
import fr.ensimag.caweb.models.Contract.ContractFactory;
import fr.ensimag.caweb.models.Quantity;
import fr.ensimag.caweb.models.User.Consummer;
import fr.ensimag.caweb.models.User.Producer;
import fr.ensimag.caweb.models.User.User;
import fr.ensimag.caweb.models.User.UserFactory;
import fr.ensimag.caweb.models.Week;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alexandre Rupp
 */
public class ContractDAOOracle implements ContractDAO {

    private final DAOFactory daoFactory;

    private static final String selectQuery
            = "SELECT * "
            + "FROM Contrat "
            + "JOIN Utilisateur "
            + "ON (pseudo = offreur OR pseudo = demandeur) "
            + "WHERE idContrat = ? ";

    private static final String updateQuery
            = "UPDATE Contrat "
            + "SET offreur = ?, demandeur = ?, "
            + "dateContrat = ?, nomProduitContrat = ?, prixLotContrat = ?, "
            + "dureeContrat = ?, qteLotContrat = ?, uniteContrat = ?, nbLots = ?, "
            + "dateDebutLivraison = ?, aRenouveler = ? "
            + "WHERE idContrat = ?";

    private static final String updateToReNewQuery
            = "UPDATE Contrat "
            + "SET aRenouveler = 1"
            + "WHERE idContrat = ?"
            + "AND demandeur=?";

    private static final String updateValidateQuery
            = "UPDATE Contrat "
            + "SET dateContrat = ?, "
            + "dateDebutLivraison = ?, "
            + "aRenouveler = 0 "
            + "WHERE idContrat = ?";
    private static final String selectAllQuery
            = "SELECT * "
            + "FROM Contrat c "
            + "JOIN Utilisateur "
            + "ON (pseudo=offreur OR pseudo=demandeur) "
            + "WHERE (demandeur=? OR offreur=?) ";

    private static final String selectAllRequestsQuery = selectAllQuery
            + "AND dateDebutLivraison IS NULL "
            + "AND (aRenouveler IS NULL OR aRenouveler = 0) "
            + "AND (refuse = 0 OR refuse IS NULL) ";

    private static final String selectAllRefusedRequestsQuery = selectAllQuery
            + "AND refuse = 1 ";

    private static final String selectAllContractsQuery
            = "SELECT * FROM ("
            + "SELECT * "
            + "FROM Contrat c "
            + "JOIN Utilisateur "
            + "ON (pseudo=offreur OR pseudo=demandeur) "
            + "WHERE (demandeur=? OR offreur=?) "
            + "AND dateDebutLivraison IS NOT NULL "
            + "AND (aRenouveler IS NULL OR aRenouveler = 0)"
            + "AND (refuse = 0 OR refuse IS NULL) ) "
            + "NATURAL JOIN (SELECT idContrat, (dateDebutLivraison+dureeContrat) AS dateFin FROM Contrat c2) "
            + "ORDER BY dateFin DESC";

    private static final String selectAllContractsToRenewQuery = selectAllQuery
            + "AND (refuse = 0 OR refuse IS NULL)"
            + "AND aRenouveler = 1";

    private static final String deleteQuery = "UPDATE CONTRAT "
            + "SET refuse = 1 "
            + "WHERE idContrat = ? ";

    private static final String insertQuery
            = "INSERT INTO Contrat (offreur, demandeur, dateContrat, nomProduitContrat,"
            + " prixLotContrat, dureeContrat, qteLotContrat, uniteContrat, nbLots, aRenouveler)\n"
            + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, 0)";

    public ContractDAOOracle(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public Contract create(Contract contrat) throws DAOException {
        Connection connec = daoFactory.getConnection();
        PreparedStatement insertPrep = null;
        try {
            insertPrep = connec.prepareStatement(insertQuery);
            insertPrep.setString(1, contrat.getOffreur().getPseudo());
            insertPrep.setString(2, contrat.getDemandeur().getPseudo());
            insertPrep.setDate(3, (Date) contrat.getDateContrat());
            insertPrep.setString(4, contrat.getNomProduitContrat());
            insertPrep.setDouble(5, contrat.getQuantite().getPrix());
            insertPrep.setInt(6, contrat.getDuree());
            insertPrep.setDouble(7, contrat.getQuantite().getQte());
            insertPrep.setString(8, contrat.getQuantite().getUniteQte());
            insertPrep.setInt(9, contrat.getNbLots());
            insertPrep.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ContractDAO.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            throw new DAOException("Erreur BD " + ex.getMessage(), ex);
        } finally {
            try {
                if (insertPrep != null) {
                    insertPrep.close();
                }
                daoFactory.closeConnection(connec);
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new DAOException("Erreur BD " + ex.getMessage(), ex);
            } finally {
                return contrat;
            }
        }
    }

    @Override
    public Contract read(int id) throws DAOException {
        Connection connec = daoFactory.getConnection();

        Contract contract = null;
        PreparedStatement selectPrep = null;

        ResultSet rs = null;
        try {
            selectPrep = connec.prepareStatement(selectQuery);
            selectPrep.setInt(1, id);
            rs = selectPrep.executeQuery();

            int line = 0;
            ContractBuilder builder = new ContractBuilder();
            while (rs.next()) {
                // With the first line we set the attribute of the contract
                if (line == 0) {
                    builder.setIdContrat(rs.getInt("idContrat"))
                            .setDateContrat(rs.getDate("dateContrat"))
                            .setNonProduitContrat(rs.getString("nomProduitContrat"))
                            .setDuree(rs.getInt("dureeContrat"))
                            .setQuantite(new Quantity(rs.getInt("qteLotContrat"),
                                            rs.getString("uniteContrat"),
                                            rs.getDouble("prixLotContrat")))
                            .setNbLots(rs.getInt("nbLots"))
                            .setDateDebut(rs.getDate("dateDebutLivraison"))
                            .setaRenouveler((rs.getInt("aRenouveler") == 1))
                            .setRefuse((rs.getInt("refuse") == 1));
                }

                // Then we set either the "offreur" or the "demandeur"
                if (rs.getString("offreur").equals(rs.getString("pseudo"))) {
                    builder.setOffreur(
                            (Producer) UserFactory.createUser(rs.getString("pseudo"),
                                    rs.getString("motDePasse"),
                                    rs.getString("email"),
                                    rs.getString("adresse"),
                                    rs.getString("nom"),
                                    rs.getString("prenom"),
                                    rs.getString("tel"),
                                    rs.getString("roleUtilisateur")
                            )
                    );
                } else if (rs.getString("demandeur").equals(rs.getString("pseudo"))) {
                    builder.setDemandeur(
                            (Consummer) UserFactory.createUser(rs.getString("pseudo"),
                                    rs.getString("motDePasse"),
                                    rs.getString("email"),
                                    rs.getString("adresse"),
                                    rs.getString("nom"),
                                    rs.getString("prenom"),
                                    rs.getString("tel"),
                                    rs.getString("roleUtilisateur")
                            )
                    );
                }

                // When we have read the 2 lines, we can build the contract :
                if (line == 1) {
                    contract = builder.build();
                }

                ++line;
            }
        } catch (SQLException ex) {
            throw new DAOException("Erreur BD " + ex.getMessage(), ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (selectPrep != null) {
                    selectPrep.close();
                }
                daoFactory.closeConnection(connec);
            } catch (SQLException ex) {
                throw new DAOException("Erreur BD " + ex.getMessage(), ex);
            }
        }

        return contract;
    }

    private List<Contract> readAll(String query, String userPseudo) throws DAOException {
        Connection connec = daoFactory.getConnection();

        List<Contract> reqs = new ArrayList<>();

        PreparedStatement selectPrep = null;
        ResultSet rs = null;
        try {
            selectPrep
                    = connec.prepareStatement(query);
            selectPrep.setString(1, userPseudo);
            selectPrep.setString(2, userPseudo);
            rs = selectPrep.executeQuery();

            int line = 0;
            ContractBuilder builder = new ContractBuilder();
            while (rs.next()) {
                // With the first line we set the attribute of the contract
                if (line == 0) {
                    builder.setIdContrat(rs.getInt("idContrat"))
                            .setDateContrat(rs.getDate("dateContrat"))
                            .setNonProduitContrat(rs.getString("nomProduitContrat"))
                            .setDuree(rs.getInt("dureeContrat"))
                            .setQuantite(new Quantity(rs.getInt("qteLotContrat"),
                                            rs.getString("uniteContrat"),
                                            rs.getDouble("prixLotContrat")))
                            .setNbLots(rs.getInt("nbLots"))
                            .setDateDebut(rs.getDate("dateDebutLivraison"))
                            .setaRenouveler((rs.getInt("aRenouveler") == 1))
                            .setRefuse((rs.getInt("refuse") == 1));
                }

                // Then we set either the "offreur" or the "demandeur"
                if (rs.getString("offreur").equals(rs.getString("pseudo"))) {
                    builder.setOffreur(
                            (Producer) UserFactory.createUser(rs.getString("pseudo"),
                                    rs.getString("motDePasse"),
                                    rs.getString("email"),
                                    rs.getString("adresse"),
                                    rs.getString("nom"),
                                    rs.getString("prenom"),
                                    rs.getString("tel"),
                                    rs.getString("roleUtilisateur")
                            )
                    );
                } else if (rs.getString("demandeur").equals(rs.getString("pseudo"))) {
                    builder.setDemandeur(
                            (Consummer) UserFactory.createUser(rs.getString("pseudo"),
                                    rs.getString("motDePasse"),
                                    rs.getString("email"),
                                    rs.getString("adresse"),
                                    rs.getString("nom"),
                                    rs.getString("prenom"),
                                    rs.getString("tel"),
                                    rs.getString("roleUtilisateur")
                            )
                    );
                }

                // When we have read the 2 lines, we can build the contract :
                if (line == 1) {
                    reqs.add(builder.build());
                    line = 0;
                    builder = new ContractBuilder();
                } else {
                    ++line;
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Erreur BD " + ex.getMessage(), ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (selectPrep != null) {
                    selectPrep.close();
                }
                daoFactory.closeConnection(connec);
            } catch (SQLException ex) {
                throw new DAOException("Erreur BD " + ex.getMessage(), ex);
            }
        }

        return reqs;
    }

    @Override
    public List<Contract> readAll(String userPseudo) throws DAOException {
        return readAll(selectAllQuery, userPseudo);
    }

    @Override
    public List<Contract> readAllContractRequests(String userPseudo) throws DAOException {
        return readAll(selectAllRequestsQuery, userPseudo);
    }

    @Override
    public List<Contract> readAllRefusedContractRequests(String userPseudo) throws DAOException {
        return readAll(selectAllRefusedRequestsQuery, userPseudo);
    }

    @Override
    public List<Contract> readAllValidatedContracts(String userPseudo) throws DAOException {
        return readAll(selectAllContractsQuery, userPseudo);
    }

    @Override
    public List<Contract> readAllContractsToRenew(String userPseudo) throws DAOException {
        return readAll(selectAllContractsToRenewQuery, userPseudo);
    }

    @Override
    public void updateToReNew(int id, String login) throws DAOException {
        Connection connec = daoFactory.getConnection();

        PreparedStatement updatePrep = null;

        try {
            updatePrep = connec.prepareStatement(updateToReNewQuery);
            updatePrep.setInt(1, id);
            updatePrep.setString(2, login);
            updatePrep.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("erreur");
            ex.printStackTrace();
            throw new DAOException("Erreur BD " + ex.getMessage(), ex);
        } finally {
            try {
                if (updatePrep != null) {
                    updatePrep.close();
                }
                daoFactory.closeConnection(connec);
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new DAOException("Erreur BD " + ex.getMessage(), ex);
            }
        }
    }

    @Override
    public void updateValidate(int id, Date dateCont, Date begin) throws DAOException {
        Connection connec = daoFactory.getConnection();

        PreparedStatement updatePrep = null;

        try {
            updatePrep = connec.prepareStatement(updateValidateQuery);
            updatePrep.setDate(1, dateCont);
            updatePrep.setDate(2, begin);
            updatePrep.setInt(3, id);
            updatePrep.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("erreur");
            ex.printStackTrace();
            throw new DAOException("Erreur BD " + ex.getMessage(), ex);
        } finally {
            try {
                if (updatePrep != null) {
                    updatePrep.close();
                }
                daoFactory.closeConnection(connec);
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new DAOException("Erreur BD " + ex.getMessage(), ex);
            }
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        Connection connec = daoFactory.getConnection();

        PreparedStatement deletePrep = null;

        try {
            deletePrep = connec.prepareStatement(deleteQuery);
            deletePrep.setInt(1, id);
            deletePrep.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("erreur");
            ex.printStackTrace();
            throw new DAOException("Erreur BD " + ex.getMessage(), ex);
        } finally {
            try {
                if (deletePrep != null) {
                    deletePrep.close();
                }
                daoFactory.closeConnection(connec);
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new DAOException("Erreur BD " + ex.getMessage(), ex);
            }
        }
    }

}
