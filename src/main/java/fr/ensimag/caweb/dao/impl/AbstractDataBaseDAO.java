/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.caweb.dao.impl;

import fr.ensimag.caweb.dao.DAOException;
import java.sql.*;
import javax.sql.DataSource;

/**
 *
 * @author laguerrr
 */
public class AbstractDataBaseDAO {

    protected final DataSource dataSource;

    protected AbstractDataBaseDAO(DataSource ds) {
        this.dataSource = ds;
    }

    protected Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    
    /* fermeture d'une connexion
     * @param c la connexion à fermer
     * @throws DAOException si problème lors de la fermeture de la connexion
     */
    protected void closeConnection(Connection c) throws DAOException {
        if (c != null) {
            try {
                c.close();
            } catch (SQLException sqle) {
                throw new DAOException("Problem occurs when clonsing the connection whith the database ", sqle);
            }
        }
    }
}
