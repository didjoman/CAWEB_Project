/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.controllers;

import fr.ensimag.caweb.controllers.errors.CAWEB_AccessRightsException;
import fr.ensimag.caweb.controllers.errors.CAWEB_DatabaseAccessException;
import fr.ensimag.caweb.dao.DAOException;
import fr.ensimag.caweb.dao.DAOFactory;
import fr.ensimag.caweb.dao.impl.ContractDAOSqlPlus;
import fr.ensimag.caweb.models.Contract.Contract;
import fr.ensimag.caweb.models.Week;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Alexandre Rupp
 */
@WebServlet(name = "ContractServlet", urlPatterns = {"/contract"})
public class ContractServlet extends HttpServlet {
    
    
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
        HttpSession session = request.getSession(false);
        String login, status;
        if(session != null && session.getAttribute("login") != null
           && session.getAttribute("status") != null){
            login = (String)session.getAttribute("login");
            status = (String)session.getAttribute("status");
        } else
            throw new CAWEB_AccessRightsException(request.getRequestURI());
        
        List<Contract> reqs;
        try {
            reqs = DAOFactory.getInstance().getContractDAO().readAllValidatedContracts(login);
            request.setAttribute("reqs", reqs);
            request.setAttribute("status",status);
        } catch (DAOException ex) {
            Logger.getLogger(ContractServlet.class.getName()).log(Level.SEVERE, null, ex);
            throw new CAWEB_DatabaseAccessException();
        }
        
        RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/contract_read_all.jsp");
        view.forward(request, response);
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

        HttpSession session = request.getSession(false);
        String login, status;
        int idContrat;
        if (session != null && session.getAttribute("login") != null
                && session.getAttribute("status") != null) {

            login = (String) session.getAttribute("login");
            status = (String) session.getAttribute("status");
            idContrat = Integer.parseInt(request.getParameter("idContrat"));
        } else {
            throw new CAWEB_AccessRightsException(request.getRequestURI());
        }
        try {
            DAOFactory.getInstance().getContractDAO().updateToReNew(idContrat, login);
            doGet(request, response);
        } catch (DAOException ex) {
            Logger.getLogger(ContractServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
}
