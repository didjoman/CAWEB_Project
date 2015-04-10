/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.dao.impl;

import fr.ensimag.caweb.dao.DAOException;
import fr.ensimag.caweb.dao.DAOFactory;
import fr.ensimag.caweb.dao.WeekDAO;
import fr.ensimag.caweb.models.User.Consummer;
import fr.ensimag.caweb.models.User.User;
import fr.ensimag.caweb.models.User.UserFactory;
import fr.ensimag.caweb.models.Week;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alexandre Rupp
 */
public class WeekDAOSqlPlus implements WeekDAO{
    private final DAOFactory daoFactory;
    
    private static final String selectAllQuery = "SELECT * " +
            "FROM EstDisponible d " +
            "FULL OUTER JOIN AssurePermanence p " +
            "ON (d.annee = p.annee AND d.numSemaine = p.numSemaine) " +
            "JOIN Utilisateur " +
            "ON (pseudo = permanencier1 OR pseudo = permanencier2 OR pseudo = consoDispo) ";
    
    private static final String selectAllPermsQuery = "SELECT * " +
            "FROM AssurePermanence p " +
            "JOIN Utilisateur " +
            "ON (pseudo = permanencier1 OR pseudo = permanencier2) ";
    
    private static final String selectQuery = selectAllQuery +
            "WHERE d.numSemaine = ? "
            + "AND d.annee = ? ";
    

    public WeekDAOSqlPlus(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    @Override
    public Week create(Week obj) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    @Override
    public Week read(int weekNum, int year) throws DAOException {
        Connection connec = daoFactory.getConnection();
        
        PreparedStatement selectPrep = null;
        
        ResultSet rs = null;
        Week week = null;
        Week tmp;
        try {
            selectPrep = connec.prepareStatement(selectQuery);
            selectPrep.setInt(1, weekNum);
            selectPrep.setInt(2, year);
            rs = selectPrep.executeQuery();
            while(rs.next()){
                week = new Week(rs.getInt("numSemaine"), rs.getInt("annee"));
                
                // Fetch the last week attributes :
                String consoPseudo;
                if((consoPseudo = rs.getString("pseudo")) != null){
                    Consummer consummer = (Consummer) UserFactory.createUser(rs.getString("pseudo"),
                            rs.getString("motDePasse"),
                            rs.getString("email"),
                            rs.getString("adresse"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("tel"),
                            rs.getString("roleUtilisateur"));
                    
                    if(consoPseudo.equals(rs.getString("permanencier1")) &&
                            week.getPermanencier1() == null)
                        week.setPermanencier1(consummer);
                    
                    if(consoPseudo.equals(rs.getString("permanencier2")) &&
                            week.getPermanencier2() == null)
                        week.setPermanencier2(consummer);
                    
                    if(consoPseudo.equals(rs.getString("consoDispo")))
                        if(rs.getInt("estDispo") == 1)
                            week.addDispo(consummer);
                        else
                            week.addIndispo(consummer);
                }
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
        return week;
    }
    
    @Override
    public List<Week> readAll() throws DAOException {
        Connection connec = daoFactory.getConnection();
        
        List<Week> weeks = new ArrayList<>();
        PreparedStatement selectPrep = null;
        
        ResultSet rs = null;
        Week week = null;
        Week tmp;
        try {
            selectPrep = connec.prepareStatement(selectAllQuery);
            rs = selectPrep.executeQuery();
            while(rs.next()){
                tmp = new Week(rs.getInt("numSemaine"), rs.getInt("annee"));
                
                // Add the last fetched week to the list, and create a new one.
                if(!tmp.equals(week)){
                    // If we fetched an other week before we add it to the list.
                    if(week != null)
                        weeks.add(week);
                    // Now we begin the fetching of a new week :
                    week = tmp;
                }
                
                // Fetch the last week attributes :
                String consoPseudo;
                if((consoPseudo = rs.getString("pseudo")) != null){
                    Consummer consummer = (Consummer) UserFactory.createUser(rs.getString("pseudo"),
                            rs.getString("motDePasse"),
                            rs.getString("email"),
                            rs.getString("adresse"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("tel"),
                            rs.getString("roleUtilisateur"));
                    
                    if(consoPseudo.equals(rs.getString("permanencier1")) &&
                            week.getPermanencier1() == null)
                        week.setPermanencier1(consummer);
                    
                    if(consoPseudo.equals(rs.getString("permanencier2")) &&
                            week.getPermanencier2() == null)
                        week.setPermanencier2(consummer);
                    
                    if(consoPseudo.equals(rs.getString("consoDispo")))
                        if(rs.getInt("estDispo") == 1)
                            week.addDispo(consummer);
                        else
                            week.addIndispo(consummer);
                }
            }
            // Add the last week :
            if(week != null)
                weeks.add(week);
            
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
        return weeks;
    }
    
//    @Override
    public List<Week> readAllPerms() throws DAOException {
        Connection connec = daoFactory.getConnection();
        
        List<Week> weeks = new ArrayList<>();
        PreparedStatement selectPrep = null;
        
        ResultSet rs = null;
        Week week = null;
        Week tmp;
        try {
            selectPrep = connec.prepareStatement(selectAllQuery);
            rs = selectPrep.executeQuery();
            while(rs.next()){
                tmp = new Week(rs.getInt("numSemaine"), rs.getInt("annee"));
                
                // Add the last fetched week to the list, and create a new one.
                if(!tmp.equals(week)){
                    // If we fetched an other week before we add it to the list.
                    if(week != null)
                        weeks.add(week);
                    // Now we begin the fetching of a new week :
                    week = tmp;
                }
                
                // Fetch the last week attributes :
                String consoPseudo;
                if((consoPseudo = rs.getString("pseudo")) != null){
                    Consummer consummer = (Consummer) UserFactory.createUser(rs.getString("pseudo"),
                            rs.getString("motDePasse"),
                            rs.getString("email"),
                            rs.getString("adresse"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("tel"),
                            rs.getString("roleUtilisateur"));
                    
                    if(consoPseudo.equals(rs.getString("permanencier1")) &&
                            week.getPermanencier1() == null)
                        week.setPermanencier1(consummer);
                    
                    if(consoPseudo.equals(rs.getString("permanencier2")) &&
                            week.getPermanencier2() == null)
                        week.setPermanencier2(consummer);
                }
            }
            // Add the last week :
            if(week != null)
                weeks.add(week);
            
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
        return weeks;
    }
    
    @Override
    public Week update(Week obj) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void delete(Week obj) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
