/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.caweb.dao.impl;

import fr.ensimag.caweb.dao.ContractDAO;
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

    private static final String selectProducer
            = "SELECT * FROM Offre WHERE createur = ?";
    private static final String selectQuantities
            = "SELECT * FROM Quantite WHERE idOffrePrecisee = ?";

    private static final String insertQueryOffer
            = "INSERT INTO Offre (createur, nomProduit, dureeOffre)"
            + " VALUES(?,?,?)";

    private static final String selectOffer
            = "SELECT idOffre FROM Offre WHERE createur = ? "
            + " AND nomProduit = ?"
            + " AND dureeOffre = ?";

    private static final String insertQueryQuantity
            = "INSERT INTO Quantite (idOffrePrecisee, qte, uniteQte, prix)"
            + " VALUES(?,?,?,?)";

    public OfferDAOOracle(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public Offer create(Offer offer, Quantity quantity) throws DAOException {
        Connection connec = daoFactory.getConnection();
        PreparedStatement insertPrepOffer = null;
        PreparedStatement selectPrepOffer = null;
        PreparedStatement insertPrepQuantity = null;
        ResultSet rs = null;
        try {
            insertPrepOffer = connec.prepareStatement(insertQueryOffer);
            insertPrepOffer.setString(1, offer.getCreateur());
            insertPrepOffer.setString(2, offer.getNomProduit());
            insertPrepOffer.setInt(3, offer.getDuree());
            insertPrepOffer.executeUpdate();
            selectPrepOffer = connec.prepareStatement(selectOffer);
            selectPrepOffer.setString(1, offer.getCreateur());
            selectPrepOffer.setString(2, offer.getNomProduit());
            selectPrepOffer.setInt(3, offer.getDuree());
            rs = selectPrepOffer.executeQuery();
            //CREATE QUANTITIES
            while (rs.next()) {
                insertPrepQuantity = connec.prepareStatement(insertQueryQuantity);
                insertPrepQuantity.setInt(1, rs.getInt("idOffre"));
                insertPrepQuantity.setDouble(2, quantity.getQte());
                insertPrepQuantity.setString(3, quantity.getUniteQte());
                insertPrepQuantity.setInt(4, quantity.getPrix());
                insertPrepQuantity.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ContractDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new DAOException("Erreur BD " + ex.getMessage(), ex);
        } finally {
            try {
                if (insertPrepOffer != null) {
                    insertPrepOffer.close();
                }
                if (insertPrepQuantity != null) {
                    insertPrepQuantity.close();
                }
                if (selectPrepOffer != null) {
                    selectPrepOffer.close();
                }
                daoFactory.closeConnection(connec);
            } catch (SQLException ex) {               
                throw new DAOException("Erreur BD " + ex.getMessage(), ex);
            } finally {
                return offer;
            }
        }
    }

    @Override
    public List<Offer> read(String id) throws DAOException {
        List<Offer> result = new ArrayList<Offer>();
        ResultSet rs = null;
        Connection conn = daoFactory.getConnection();;
        PreparedStatement selectPrep = null;
        try {
            selectPrep = conn.prepareStatement(selectProducer);
            selectPrep.setString(1, id);
            rs = selectPrep.executeQuery();
            while (rs.next()) {
                //Get the quantities
                List<Quantity> quantities = new ArrayList<Quantity>();
                selectPrep = conn.prepareStatement(selectQuantities);
                selectPrep.setInt(1, rs.getInt("idOffre"));
                ResultSet rs_qte = selectPrep.executeQuery();
                while (rs_qte.next()) {
                    double qte = rs_qte.getDouble("qte");
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
                    double qte = rs_qte.getDouble("qte");
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
