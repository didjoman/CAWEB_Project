/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.controllers;

import fr.ensimag.caweb.dao.DAOException;
import fr.ensimag.caweb.dao.DAOFactory;
import fr.ensimag.caweb.models.ConnectionForm;
import fr.ensimag.caweb.models.SubscriptionForm;
import fr.ensimag.caweb.models.User;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "SessionServlet", urlPatterns = {"/session"})
public class SessionServlet extends HttpServlet {
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if(action != null){
            switch (action) {
                case "post":
                    doPost(request, response);
                    break;
                case "put":
                    doPut(request, response);
                    break;
                case "delete":
                    doDelete(request, response);
                    break;
                default:
                    break;
            }
        }
    }
    
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
        processRequest(request, response);
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
        ConnectionForm form = new ConnectionForm();
        
        // User to create in the Data Base
        User u = form.logUser(request);
        
        // If the user is logged :
        if(u != null){
            // Update the session :
            HttpSession session = request.getSession(true);
            session.setAttribute("login", u.getPseudo());
            session.setAttribute("status", u.getRole().toString());
        }
        
        // We print the login page.
        request.setAttribute("error", form.getError());
        request.setAttribute("success", form.getSuccess());
        
        RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/accueil.jsp");
        view.forward(request, response);
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session != null)
            session.invalidate();
        request.setAttribute("info", "Vous êtes désormais déconnectés.");
        RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/accueil.jsp");
        view.forward(request, response);
    }
    
}
