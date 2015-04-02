/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.controllers;

import fr.ensimag.caweb.dao.DAOException;
import fr.ensimag.caweb.dao.DAOFactory;
import fr.ensimag.caweb.models.Consommateur;
import fr.ensimag.caweb.models.Producteur;
import fr.ensimag.caweb.models.User;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "UserServlet", urlPatterns = {"/user"})
public class UserServlet extends HttpServlet {
    
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
        
        // We get the parameters :
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String passwordVerif = request.getParameter("passwordverif");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String name = request.getParameter("name");
        String firstname = request.getParameter("firstname");
        String tel = request.getParameter("tel");
        String status = request.getParameter("status");
        
        // Parameters of the JSP
        String error = null;
        String success = null;
        
        // Checks wether password = verif password
        if(password != null && !password.equals(passwordVerif))
            error = "<strong>Erreur :</strong> Le mot de passe et sa vérification ne sont pas identiques";
        
        // Checks that every field have been fulfilled :
        else if(login == null || password == null || passwordVerif == null ||
                email == null || address == null || name == null ||
                firstname == null || tel == null || status == null)
            error = "Erreur : Veuillez remplir tous les champs";
        else {
            // User to create in the Data Base
            User user = null;
            if(status.equals("prod"))
                user = new Producteur(login, password, email, address, name, firstname, tel);
            else
                user = new Consommateur(login, password, email, address, name, firstname, tel);
            
            // Create the new user in the database
            try {
                DAOFactory.getInstance().getUserDAO().create(user);
            } catch (DAOException ex) {
                error = "Erreur : Erreur lors de la création du compte";
            }
            
            // Create a session directly :
            HttpSession session = request.getSession(true);
            session.setAttribute("login", login);
            success = "Votre compte a bien été créé";
            
            // Send a confirmation e-mail :
            // TODO
        }
        
        // We print the login page.
        request.setAttribute("error", error);
        request.setAttribute("success", success);
        
        RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/accueil.jsp");
        view.forward(request, response);
        
        
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
