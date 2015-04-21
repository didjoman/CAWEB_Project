/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.dao.impl;

import fr.ensimag.caweb.dao.DAOException;
import fr.ensimag.caweb.dao.DAOFactory;
import fr.ensimag.caweb.dao.OfferDAO;
import fr.ensimag.caweb.models.Contract.Contract;
import fr.ensimag.caweb.models.Offer;
import fr.ensimag.caweb.models.Quantity;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author laguerrr
 */

public class OfferDAOOracle implements OfferDAO {
    
    private final DAOFactory daoFactory;
    
    private static final String selectAll
            = "SELECT * FROM Offre";
    
    private static final String selectQuantities
            = "SELECT * FROM Quantite WHERE idOffrePrecisee = ?";
    
    private static final String insertQuery
            = "INSERT INTO Contrat (offreur, demandeur, dateContrat, nomProduitContrat, "
            + " prixLotContrat, dureeContrat, qteLotContrat, uniteContrat, nbLots, aRenouveler) "
            + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, 0)";
    
    
    
    public OfferDAOOracle(DAOFactory daoFactory) {
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
            insertPrep.setDate(3, (Date)contrat.getDateContrat());
            insertPrep.setString(4, contrat.getNomProduitContrat());
            insertPrep.setDouble(5, contrat.getPrixTotal());
            insertPrep.setInt(6, contrat.getDuree());
            insertPrep.setInt(7, contrat.getNbLots());
            insertPrep.setString(8, contrat.getQuantite().getUniteQte());
            insertPrep.setInt(9, contrat.getNbLots());
            insertPrep.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ContractDAOOracle.class.getName()).log(Level.SEVERE, null, ex);
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
    public Offer read(int id) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<Offer> readAll() throws DAOException {
        List<Offer> result = new ArrayList<Offer>();
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement selectPrep = null;
        try {
            conn = daoFactory.getConnection();
            Statement st = conn.createStatement();
            rs = st.executeQuery(selectAll);
            while (rs.next()) {
                //Get the quantities
                List<Quantity> quantities = new ArrayList<Quantity>();
                selectPrep = conn.prepareStatement(selectQuantities);
                selectPrep.setInt(1, rs.getInt("idOffre"));
                ResultSet rs_qte = selectPrep.executeQuery();
                while (rs_qte.next()) {
                    int qte = rs_qte.getInt("qte");
                    String uniteQte = rs_qte.getString("uniteQte");
                    int prix = rs_qte.getInt("prix");
                    quantities.add(new Quantity(qte, uniteQte, prix));
                }
                Offer offer = new Offer(rs.getInt("idOffre"), rs.getString("createur"), rs.getString("nomProduit"),
                        rs.getInt("dureeOffre"), quantities);
                result.add(offer);
            }
        } catch (SQLException e) {
            throw new DAOException("Database access error " + e.getMessage(), e);
        } finally {
            daoFactory.closeConnection(conn);
        }
        return result;
    }
    
    
    @Override
    public Offer update(Offer obj) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void delete(Offer obj) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
