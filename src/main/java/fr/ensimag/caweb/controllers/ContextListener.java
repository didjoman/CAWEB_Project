/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.controllers;

import fr.ensimag.caweb.dao.DAOFactory;
import fr.ensimag.caweb.dao.DAOFactoryType;
import fr.ensimag.caweb.dao.impl.DAOFactorySqlPlus;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;

/**
 *
 * @author Alexandre Rupp
 */
@WebListener
public class ContextListener implements ServletContextListener {
    private static final String CTX_ATT_DAO_FACT = "SQLPLUS_CLIENT";
    private DAOFactory daoFactory;
    
    @Override
    public void contextInitialized(ServletContextEvent servContextEvent) {
        // Get the DAO Factory
        daoFactory = DAOFactory.getInstance(DAOFactoryType.OracleSqlPlus);
        System.out.println("DAO Factory initialized successfully");
        // Add it to the context :
        ServletContext ctx = servContextEvent.getServletContext();
        ctx.setAttribute(CTX_ATT_DAO_FACT, daoFactory);
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
    
}
