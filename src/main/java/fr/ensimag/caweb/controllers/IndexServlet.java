package fr.ensimag.caweb.controllers;

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

import fr.ensimag.caweb.models.User.UserStatus;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
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
@WebServlet(name = "AccueilServlet", urlPatterns = {"/index"})
public class IndexServlet extends HttpServlet {
    
    
    @Override
    public void init() {
    }
    
    
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
        
        // Get the status of the user :
        HttpSession session = request.getSession(false);
        String login = null;
        String status = null;
        if(session != null && session.getAttribute("login") != null
                && session.getAttribute("status") != null){
            login = (String)session.getAttribute("login");
            status = (String)session.getAttribute("status");
        }
        if(status == null){
            RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/index.jsp");
            view.forward(request, response);
            return;
        }
        
        if(status.equals(UserStatus.CONS.toString()))
            response.sendRedirect("offer");
        else if(status.equals(UserStatus.PROD.toString()))
            response.sendRedirect("offer");
        else if(status.equals(UserStatus.RESP.toString()))
            response.sendRedirect("permanency");
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
        doGet(request, response);
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
