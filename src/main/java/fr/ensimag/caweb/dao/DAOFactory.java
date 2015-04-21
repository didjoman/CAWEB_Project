/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.dao;

import fr.ensimag.caweb.dao.impl.DAOFactoryOracle;
import fr.ensimag.caweb.dao.impl.UserDAOOracle;
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
public abstract class DAOFactory {
    /*
    * ****** Patterns : *******
    * - Factory Method pattern & Abstract Factory pattern
    * for the DAOFactory
    * - DAO pattern related to the Broker pattern for the DAO objects
    * - Transfer pattern (store DB content into objects in the model)
    * see : http://www.oracle.com/technetwork/java/dataaccessobject-138824.html
    */
    
    public static DAOFactory getInstance(){
        return getInstance(DAOFactoryType.ORACLE);
    }
    
    public static DAOFactory getInstance(DAOFactoryType type){
        switch(type){
            case ORACLE:
                return DAOFactoryOracle.getInstance();
            default:
                return DAOFactoryOracle.getInstance();
        }
    }
    
    public abstract UserDAO getUserDAO();
    public abstract WeekDAO getWeekDAO();
    public abstract ContractDAO getContractDAO();
    
    public abstract Connection getConnection();
    
    public abstract void closeConnection(Connection c) throws DAOException;
    
}
