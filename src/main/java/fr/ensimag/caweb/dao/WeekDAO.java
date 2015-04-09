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
    
    public abstract Week update(Week obj)  throws DAOException ;
    
    public abstract void delete(Week obj)  throws DAOException ;
}
