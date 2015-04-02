/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.dao.impl;


import fr.ensimag.caweb.dao.DAOException;
import fr.ensimag.caweb.dao.DAOFactory;
import fr.ensimag.caweb.dao.UserDAO;
import fr.ensimag.caweb.dao.impl.UserDAOSqlPlus;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Alexandre Rupp
 */
public class DAOFactorySqlPlus extends DAOFactory {
    private static final String DB_DATASRC_REF = "java:comp/env/jdbc/pool/MyAppDB";
    private final DataSource dataSource;
    
    private DAOFactorySqlPlus(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public static DAOFactorySqlPlus getInstance(){
        DataSource dataSource = null;
        try {
            Context ctx = new InitialContext();
            dataSource = (DataSource)ctx.lookup(DB_DATASRC_REF);
        } catch (NamingException ex) {
            Logger.getLogger(DAOFactorySqlPlus.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new DAOFactorySqlPlus(dataSource);
    }
    
    @Override
    public Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(DAOFactorySqlPlus.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public void closeConnection(Connection c) throws DAOException {
        if(c != null)
            try {
                c.close();
            } catch (SQLException ex) {
                throw new DAOException("Problème fermeture de connexion avec la BD ", ex);
            }
    }
    
    @Override
    public UserDAO getUserDAO(){
        return new UserDAOSqlPlus(getInstance());
    }
}
