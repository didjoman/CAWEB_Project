/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.controllers;

import fr.ensimag.caweb.dao.DAOException;
import fr.ensimag.caweb.dao.impl.OfferDAOSqlPlus;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author Alexandre Rupp
 */
@WebServlet(name = "OfferServlet", urlPatterns = {"/offer"})
public class OfferServlet extends HttpServlet {
    
    @Resource(name = "jdbc/pool/MyAppDB")
    private DataSource ds;
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            OfferDAOSqlPlus offerDAO = new OfferDAOSqlPlus(ds);
            request.setAttribute("offers", offerDAO.readAll());
            RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/offer_read.jsp");
            view.forward(request, response);
        } catch (DAOException ex) {
            Logger.getLogger(OfferServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
    
}
