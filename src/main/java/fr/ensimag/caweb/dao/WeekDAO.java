/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.dao;

import fr.ensimag.caweb.models.Week;
import java.util.List;

/**
 *
 * @author Alexandre Rupp
 */
public interface WeekDAO {
    public abstract Week create(Week obj)  throws DAOException ;
    
    public abstract Week read(int weekNum, int year)  throws DAOException ;
    
    public abstract List<Week> readAll()  throws DAOException ;
    
    public List<Week> readAllPerms() throws DAOException;
    
    public List<Week> readAllWhereConsumerAppears(String consummerPseudo) throws DAOException;
    
    public List<Week> readAllPermsFullySet() throws DAOException;
    
    public List<Week> readAllDispos() throws DAOException;
    
    public List<Week> readAllUndispos() throws DAOException;
    
    public abstract Week update(Week obj)  throws DAOException ;
    
    public void updateSetPerm(int numSemaine, int annee,
            String consummerPseudo1, String consummerPseudo2) throws DAOException;
    
    public void updateSetDispo(int numSemaine, int annee,
            String consummerPseudo, int estDispo) throws DAOException;
    
    public void deleteDispo(int numSemaine, int annee, String consummerPseudo) throws DAOException;
}
