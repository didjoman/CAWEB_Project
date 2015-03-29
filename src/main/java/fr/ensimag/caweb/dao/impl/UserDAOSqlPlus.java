/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.dao.impl;



import fr.ensimag.caweb.dao.DAOException;
import fr.ensimag.caweb.dao.DAOFactory;
import fr.ensimag.caweb.dao.UserDAO;
import fr.ensimag.caweb.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author Alexandre Rupp
 */
public class UserDAOSqlPlus implements UserDAO{
    private DAOFactory daoFactory;

    private static final String selectQuery = 
            "SELECT * FROM Users WHERE pseudo = ? AND password = ?";

    public UserDAOSqlPlus(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public User create(User user) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User read(String pseudo, String pwd) throws DAOException {
        Connection connec = daoFactory.getConnection();
        
        User user = null;
        PreparedStatement selectPrep = null;
        
        ResultSet rs = null;
        try {
            selectPrep = connec.prepareStatement(selectQuery);
            selectPrep.setString(1, pseudo);
            selectPrep.setString(2, pwd);
            rs = selectPrep.executeQuery();
            
            if(rs.next()){
                user = new User(rs.getString("pseudo"), rs.getString("password"));
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
        return user;    
    }

    @Override
    public List<User> readAll() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User update(User user) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(User user) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
