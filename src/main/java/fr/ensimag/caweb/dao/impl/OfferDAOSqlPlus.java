/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.caweb.dao.impl;

import fr.ensimag.caweb.dao.DAOException;
import fr.ensimag.caweb.dao.OfferDAO;
import fr.ensimag.caweb.models.Contract.Contract;
import fr.ensimag.caweb.models.Offer;
import fr.ensimag.caweb.models.Quantity;
import java.sql.Connection;
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
public class OfferDAOSqlPlus extends AbstractDataBaseDAO implements OfferDAO {

    public OfferDAOSqlPlus(DataSource ds) {
        super(ds);
    }

    @Override
    public Offer create(Contract obj) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Offer read(int id) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Offer> readAll() throws DAOException {
        List<Offer> result = new ArrayList<Offer>();
        ResultSet rs = null;
        String requeteSQL = "";
        Connection conn = null;
        try {           
            conn = getConnection();
            Statement st = conn.createStatement();
            requeteSQL = "select * from Offre";            
            rs = st.executeQuery(requeteSQL);
            while (rs.next()) {
                //Get the quantities
                /*
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
);*/
                List<Quantity> quantities = null;
                Statement st_qte = conn.createStatement();
                String requeteSQL_qte = "select * from Quantite where idOffrePrecisee = "+rs.getInt("idOffre");
                ResultSet rs_qte = st_qte.executeQuery(requeteSQL_qte);
                //while(rs_qte.next())
                Offer offer = new Offer(rs.getString("nomProduit"),
                        rs.getInt("dureeOffre"),
                        null);                
                result.add(offer);
            }          
        } catch (SQLException e) {
            throw new DAOException("Database access error " + e.getMessage(), e);
        } finally {           
                closeConnection(conn);          
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
