/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.dao.impl;



import fr.ensimag.caweb.dao.DAOException;
import fr.ensimag.caweb.dao.DAOFactory;
import fr.ensimag.caweb.dao.UserDAO;
import fr.ensimag.caweb.models.User.Consummer;
import fr.ensimag.caweb.models.User.Producer;
import fr.ensimag.caweb.models.User.Responsible;
import fr.ensimag.caweb.models.User.User;
import fr.ensimag.caweb.models.User.UserFactory;
import fr.ensimag.caweb.models.User.UserStatus;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Alexandre Rupp
 */
public class UserDAOOracle implements UserDAO{
    private final DAOFactory daoFactory;
    
    private static final String selectQuery =
            "SELECT * FROM Utilisateur WHERE pseudo = ?";
    
    private static final String selectAllWithStatusQuery =
            "SELECT * FROM Utilisateur WHERE roleUtilisateur = ?";
    
    private static final String insertQuery =
            "INSERT INTO Utilisateur (pseudo, motDePasse, eMail, adresse, nom, prenom, tel, roleUtilisateur)\n" +
            "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    
    public UserDAOOracle(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    @Override
    public User create(User user) throws DAOException {
        Connection connec = daoFactory.getConnection();
        
        PreparedStatement insertPrep = null;

        try {
            insertPrep = connec.prepareStatement(insertQuery);
            insertPrep.setString(1, user.getPseudo());
            insertPrep.setString(2, user.getMotDePasse());
            insertPrep.setString(3, user.getEmail());
            insertPrep.setString(4, user.getAdresse());
            insertPrep.setString(5, user.getNom());
            insertPrep.setString(6, user.getPrenom());
            insertPrep.setString(7, user.getTel());
            insertPrep.setString(8, user.getRole().toString()); // ROLE
            insertPrep.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("erreur");
            ex.printStackTrace();
            throw new DAOException("Erreur BD " + ex.getMessage(), ex);
        } finally {
            try {
                if(insertPrep != null)
                    insertPrep.close();
                daoFactory.closeConnection(connec);
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new DAOException("Erreur BD " + ex.getMessage(), ex);
            }
        }
        return user;
    }
    
    @Override
    public User read(String pseudo) throws DAOException {
        Connection connec = daoFactory.getConnection();
        
        User user = null;
        PreparedStatement selectPrep = null;
        
        ResultSet rs = null;
        try {
            selectPrep = connec.prepareStatement(selectQuery);
            selectPrep.setString(1, pseudo);
            rs = selectPrep.executeQuery();
            
            if(rs.next()){
                user = UserFactory.createUser(rs.getString("pseudo"),
                        rs.getString("motDePasse"),
                        rs.getString("email"),
                        rs.getString("adresse"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("tel"),
                        rs.getString("roleUtilisateur")
                );
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
    public List<User> readAllWithStatus(UserStatus status) throws DAOException {
        Connection connec = daoFactory.getConnection();
        
        List<User> users = new ArrayList<>();
        User user = null;
        PreparedStatement selectPrep = null;
        
        ResultSet rs = null;
        try {
            selectPrep = connec.prepareStatement(selectAllWithStatusQuery);
            selectPrep.setString(1, status.toString());
            rs = selectPrep.executeQuery();
            
            while(rs.next()){
                users.add(UserFactory.createUser(rs.getString("pseudo"),
                        rs.getString("motDePasse"),
                        rs.getString("email"),
                        rs.getString("adresse"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("tel"),
                        rs.getString("roleUtilisateur")
                ));
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
        return users;
    }
    
    
    @Override
    public User update(User user) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void delete(User user) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    @Override
    public User read(String pseudo, String pwd){
        try {
            User u = UserDAOOracle.this.read(pseudo);
            return (u != null && UserDAOOracle.this.read(pseudo).getMotDePasse().equals(pwd)) ? u : null;
        } catch (DAOException ex) {
            Logger.getLogger(UserDAOOracle.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
    }
}
