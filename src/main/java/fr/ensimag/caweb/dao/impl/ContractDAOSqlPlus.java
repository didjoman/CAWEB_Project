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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Alexandre Rupp
 */
public class ContractDAOSqlPlus implements ContractDAO {
    private final DAOFactory daoFactory;
    
    private static final String selectQuery =
            "SELECT * "
            + "FROM Contrat "
            + "WHERE idContrat = ? "
            + "JOIN Producteur"
            + "ON (pseudo = offreur OR pseudo = demandeur)";
    
    
    public ContractDAOSqlPlus(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    @Override
    public Contract create(Contract obj) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
            if(rs.next()){
                // With the first line we set the attribute of the contract
                if(line == 0)
                    builder.setIdContrat(rs.getInt("idContrat"))
                            .setDateContrat(rs.getDate("dateContrat"))
                            .setNonProduitContrat(rs.getString("nomProduitContrat"))
                            .setDuree(rs.getInt("dureeContrat"))
                            .setQuantite(new Quantity(rs.getInt("qteLotContrat"),
                                    rs.getString("uniteContrat"),
                                    rs.getDouble("prixLotContrat")))
                            .setNbLots(rs.getInt("nbLots"))
                            .setDateDebut(rs.getDate("dateDebutLivraison"))
                            .setaRenouveler((rs.getInt("aRenouveler") == 0));
                
                // Then we set either the "offreur" or the "demandeur"
                if(rs.getString("offreur").equals(rs.getString("pseudo")))
                    builder.setOffreur(
                            (Producer)UserFactory.createUser(rs.getString("pseudo"),
                                    rs.getString("motDePasse"),
                                    rs.getString("email"),
                                    rs.getString("adresse"),
                                    rs.getString("nom"),
                                    rs.getString("prenom"),
                                    rs.getString("tel"),
                                    rs.getString("roleUtilisateur")
                            )
                    );
                else if(rs.getString("demandeur").equals(rs.getString("pseudo")))
                    builder.setDemandeur(
                            (Consummer)UserFactory.createUser(rs.getString("pseudo"),
                                    rs.getString("motDePasse"),
                                    rs.getString("email"),
                                    rs.getString("adresse"),
                                    rs.getString("nom"),
                                    rs.getString("prenom"),
                                    rs.getString("tel"),
                                    rs.getString("roleUtilisateur")
                            )
                    );
                
                // When we have read the 2 lines, we can build the contract :
                if(line == 1)
                    contract = builder.build();
                
                ++line;
            }
        } catch (SQLException ex) {
            throw new DAOException("Erreur BD " + ex.getMessage(), ex);
        } finally {
            try {
                if(rs != null)
                    rs.close();
                if(selectPrep != null)
                    selectPrep.close();
                daoFactory.closeConnection(connec);
            } catch (SQLException ex) {
                throw new DAOException("Erreur BD " + ex.getMessage(), ex);
            }
        }
        
        return contract;
    }
    
    @Override
    public List<Contract> readAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Contract update(Contract obj) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void delete(Contract obj) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
