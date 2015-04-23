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
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 *
 * @author Alexandre Rupp
 */
public class WeekDAOOracle implements WeekDAO{
    private final DAOFactory daoFactory;
    
    private static final String selectAllQuery = "SELECT COALESCE(d.numSemaine, p.numSemaine) as numSemaine, " +
            "COALESCE(d.annee, p.annee) as annee, " +
            "d.consoDispo, d.estDispo," +
            "p.permanencier1, p.permanencier2, " +
            "u.* " +
            "FROM EstDisponible d " +
            "FULL OUTER JOIN AssurePermanence p " +
            "ON (d.annee = p.annee AND d.numSemaine = p.numSemaine) " +
            "JOIN Utilisateur u " +
            "ON (pseudo = permanencier1 OR pseudo = permanencier2 OR pseudo = consoDispo) ";
    
    private static final String selectAllWhereConsummerAppearsQuery = selectAllQuery +
            "WHERE pseudo = ? ";
    
    private static final String selectAllPermsQuery = "SELECT * " +
            "FROM AssurePermanence p " +
            "JOIN Utilisateur " +
            "ON (pseudo = permanencier1 OR pseudo = permanencier2) ";
    
    private static final String selectAllPermsFullySetQuery = "SELECT * " +
            "FROM AssurePermanence p " +
            "JOIN Utilisateur " +
            "ON (pseudo = permanencier1 AND pseudo = permanencier2) "
            + "WHERE permanencier1 IS NOT NULL "
            + "AND permanencier2 IS NOT NULL";
    
    private static final String selectAllDisposQuery = "SELECT * " +
            "FROM EstDisponible d " +
            "JOIN Utilisateur " +
            "ON pseudo = consoDispo "
            + "WHERE estDispo = 1";
    
    private static final String selectAllUndisposQuery = "SELECT * " +
            "FROM EstDisponible d " +
            "JOIN Utilisateur " +
            "ON pseudo = consoDispo "
            + "WHERE estDispo = 0";
    
    private static final String getNbPermsPerUser =
            "SELECT Pseudo , coalesce(C1 ,0) + coalesce(C2 ,0) NbPerms " +
            "FROM Utilisateur u " +
            "LEFT OUTER JOIN (SELECT Permanencier1 p1, COUNT(*) c1  FROM AssurePermanence GROUP BY Permanencier1) " +
            "ON p1 = u.pseudo " +
            "LEFT OUTER JOIN (SELECT Permanencier2 p2, COUNT(*) c2  FROM AssurePermanence GROUP BY Permanencier2) " +
            "ON p2 = u.pseudo " +
            "ORDER BY NbPerms DESC";
    
    private static final String getPermFreqPerUser =
            "SELECT DEMANDEUR, " +
            "       WEEKNUM,  " +
            "       YEAR,  " +
            "       NBSEMAINESACTIF, " +
            "       COUNT(A.NUMSEMAINE)  AS NBSEMAINESPERM,   " +
            "       1 - ((NBSEMAINESACTIF - COUNT(A.NUMSEMAINE))/ NBSEMAINESACTIF) FREQPERM " +
            "FROM (SELECT DEMANDEUR, " +
            "                  COALESCE(MAX(ROUND((SYSDATE - DATEDEBUTLIVRAISON)/7)), 0) AS NBSEMAINESACTIF, " +
            "                  to_number(to_char(to_date(MIN(DATEDEBUTLIVRAISON),'DD/MM/YYYY'),'WW')) AS WEEKNUM, " +
            "                  EXTRACT(YEAR FROM (MIN(DATEDEBUTLIVRAISON))) AS YEAR " +
            "           FROM CONTRAT " +
            "           WHERE DATEDEBUTLIVRAISON + DUREEContrat >= SYSDATE " +
            "           GROUP BY DEMANDEUR) " +
            "LEFT JOIN AssurePermanence A " +
            "ON (PERMANENCIER1 = DEMANDEUR OR PERMANENCIER2 = DEMANDEUR) " +
            "AND ANNEE = YEAR " +
            "AND A.NUMSEMAINE >= WEEKNUM " +
            "GROUP BY DEMANDEUR, WEEKNUM, YEAR,  NBSEMAINESACTIF " +
            "ORDER BY FREQPERM ASC ";
    
    /*
    
    SELECT DEMANDEUR, COUNT(*)
    FROM AssurePermanence a,
    (SELECT DEMANDEUR,
    MAX(ROUND((SYSDATE - DATEDEBUTLIVRAISON)/7)) AS NBSEMAINES,
    to_number(to_char(to_date(MIN(DATEDEBUTLIVRAISON),'DD/MM/YYYY'),'WW')) AS WEEKNUM,
    EXTRACT(YEAR FROM (MIN(DATEDEBUTLIVRAISON))) AS YEAR
    FROM CONTRAT
    WHERE DATEDEBUTLIVRAISON + DUREEContrat >= SYSDATE
    GROUP BY DEMANDEUR) C
    WHERE (A.Permanencier1 = C.DEMANDEUR
    OR A.Permanencier2 = C.DEMANDEUR)
    AND A.ANNEE = C.YEAR
    AND A.NUMSEMAINE >= C.WEEKNUM
    GROUP BY DEMANDEUR;
    */
    private static final String selectQuery = selectAllQuery +
            "WHERE (d.numSemaine = ? AND d.annee = ?) "
            + "OR (p.numSemaine = ? AND p.annee = ?)";
    
    private static final String updateQuery = "begin updateUserDispo (?, ?, ?, ?); end;";
    
    private static final String updateSetPermQuery = "begin updatePerm(?, ?, ?, ?); end;";
    
    private static final String deleteDispoQuery = "DELETE FROM EstDisponible "
            + "WHERE numsemaine = ? "
            + "AND annee = ? "
            + "AND consoDispo = ?";
    
    public WeekDAOOracle(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    @Override
    public Week create(Week obj) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<Entry<String, Integer>> getNbPermsPerUser()  throws DAOException {
        Connection connec = daoFactory.getConnection();
        
        PreparedStatement selectPrep = null;
        ResultSet rs = null;
        List<Map.Entry<String, Integer>> ls = new ArrayList();
        
        try {
            selectPrep = connec.prepareStatement(getNbPermsPerUser);
            rs = selectPrep.executeQuery();
            while(rs.next())
                ls.add(new AbstractMap.SimpleEntry(rs.getString("pseudo"), rs.getInt("NbPerms")));
            
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
        return ls;
    }
    
    @Override
    public List<Entry<String, Double>> getPermFreqPerActiveUser()  throws DAOException {
        Connection connec = daoFactory.getConnection();
        
        PreparedStatement selectPrep = null;
        ResultSet rs = null;
        List<Map.Entry<String, Double>> ls = new ArrayList();
        
        try {
            selectPrep = connec.prepareStatement(getPermFreqPerUser);
            rs = selectPrep.executeQuery();
            while(rs.next())
                ls.add(new AbstractMap.SimpleEntry(rs.getString("demandeur"), rs.getDouble("FREQPERM")));
            
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
        return ls;
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
            selectPrep.setInt(3, weekNum);
            selectPrep.setInt(4, year);
            rs = selectPrep.executeQuery();
            while(rs.next()){
                if(week == null)
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
    
    @Override
    public List<Week> readAllWhereConsumerAppears(String consummerPseudo) throws DAOException {
        Connection connec = daoFactory.getConnection();
        
        List<Week> weeks = new ArrayList<>();
        PreparedStatement selectPrep = null;
        
        ResultSet rs = null;
        Week week = null;
        Week tmp;
        try {
            selectPrep = connec.prepareStatement(selectAllWhereConsummerAppearsQuery);
            selectPrep.setString(1, consummerPseudo);
            
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
                    
                    if(consoPseudo.equals(rs.getString("consoDispo"))){
                        if(rs.getInt("estDispo") == 1)
                            week.addDispo(consummer);
                        else
                            week.addIndispo(consummer);
                    }
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
    public void updateSetPerm(int numSemaine, int annee,
            String consummerPseudo1, String consummerPseudo2) throws DAOException  {
        Connection connec = daoFactory.getConnection();
        CallableStatement updateStmt = null;
        try {
            updateStmt = connec.prepareCall(updateSetPermQuery);
            updateStmt.setInt(1, numSemaine);
            updateStmt.setInt(2, annee);
            updateStmt.setString(3, consummerPseudo1);
            updateStmt.setString(4, consummerPseudo2);
            updateStmt.executeQuery();
        } catch (SQLException ex) {
            throw new DAOException("Erreur BD " + ex.getMessage(), ex);
        } finally {
            try {
                if(updateStmt != null)
                    updateStmt.close();
                daoFactory.closeConnection(connec);
            } catch (SQLException ex) {
                throw new DAOException("Erreur BD " + ex.getMessage(), ex);
            }
        }
    }
    
    @Override
    public void updateSetDispo(int numSemaine, int annee,
            String consummerPseudo, int estDispo) throws DAOException {
        Connection connec = daoFactory.getConnection();
        CallableStatement updateStmt = null;
        try {
            updateStmt = connec.prepareCall(updateQuery);
            updateStmt.setInt(1, numSemaine);
            updateStmt.setInt(2, annee);
            updateStmt.setString(3, consummerPseudo);
            updateStmt.setInt(4, estDispo);
            updateStmt.execute();
            
        } catch (SQLException ex) {
            throw new DAOException("Erreur BD " + ex.getMessage(), ex);
        } finally {
            try {
                if(updateStmt != null)
                    updateStmt.close();
                daoFactory.closeConnection(connec);
            } catch (SQLException ex) {
                throw new DAOException("Erreur BD " + ex.getMessage(), ex);
            }
        }
    }
    
    
    
    @Override
    public void deleteDispo(int numSemaine, int annee, String consummerPseudo) throws DAOException {
        Connection connec = daoFactory.getConnection();
        PreparedStatement deletePrep = null;
        
        try {
            deletePrep = connec.prepareStatement(deleteDispoQuery);
            deletePrep.setInt(1, numSemaine);
            deletePrep.setInt(2, annee);
            deletePrep.setString(3, consummerPseudo);
            deletePrep.executeQuery();
            
        } catch (SQLException ex) {
            throw new DAOException("Erreur BD " + ex.getMessage(), ex);
        } finally {
            try {
                if(deletePrep != null)
                    deletePrep.close();
                daoFactory.closeConnection(connec);
            } catch (SQLException ex) {
                throw new DAOException("Erreur BD " + ex.getMessage(), ex);
            }
        }
    }
    
}
