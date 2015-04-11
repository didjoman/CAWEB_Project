/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.controllers;

import fr.ensimag.caweb.dao.DAOException;
import fr.ensimag.caweb.dao.DAOFactory;
import fr.ensimag.caweb.models.Contract.Contract;
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
@WebServlet(name = "ContractRequestServlet", urlPatterns = {"/request"},  loadOnStartup = 1)
public class ContractRequestServlet extends HttpServlet {
    
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
        String action = request.getParameter("action");
        String idParam = request.getParameter("id");
        
        // VIEW for REQUEST CREATION
        if(action != null){
            if(action.equals("create")){
                RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/request_create.jsp");
                view.forward(request, response);
            }
            
        }
        // READ 1 contract request
        else if(idParam != null){
            // Get Contract Request Id <id>
            Integer id = Integer.parseInt(idParam);
            request.setAttribute("id", id);
            
            // We get the list of contracts in request for the producer :
            Contract req = null;
            try {
                req =  DAOFactory.getInstance().getContractDAO().read(id);
            } catch (DAOException ex) {
                Logger.getLogger(PermanencyServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            String error = null;
            // Checks if the contract exists
            if(req == null)
                error = "La demande de contrat n'existe pas (ou plus).";
            else{
                // Check : A producer can only access to his own requests :
                HttpSession session = request.getSession(false);
                if(!(session != null && req.getOffreur() != null
                        && req.getOffreur().getPseudo().equals(session.getAttribute("login")))){
                    error = "Vous n'avez pas le droit d'accéder "
                            + "à la ressource "+ request.getRequestURI();
                }
            }
            request.setAttribute("error", error);
            if(error == null)
                request.setAttribute("req", req);
            
            RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/request_read.jsp");
            view.forward(request, response);
        }
        // READ ALL contract requests :
        else {
            // Check : A producer can only access to his own requests :
            HttpSession session = request.getSession(false);
            String login = null;
            if((session != null && session.getAttribute("login") != null))
                login = (String)session.getAttribute("login");
            else {
                String error = "Vous n'avez pas le droit d'accéder "
                        + "à la ressource "+ request.getRequestURI();
                request.setAttribute("error", error);
                RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/request_read_all.jsp");
                view.forward(request, response);
                return;
            }
            
            // We get the list of contracts in request for the producer :
            List<Contract> reqs = null;
            try {
                reqs =  DAOFactory.getInstance().getContractDAO().readAllRequests(login);
            } catch (DAOException ex) {
                Logger.getLogger(PermanencyServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            request.setAttribute("reqs", reqs);
            
            RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/request_read_all.jsp");
            view.forward(request, response);
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
        String idParam = request.getParameter("contractId");
        String dateBeginParam = request.getParameter("begin");
        
        String error = null;
        // COntract Validation :
        if(idParam != null && dateBeginParam != null){
            
            // Parsing of the date of beginning :
            Date dateBegin = null;
            try {
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                dateBegin = new Date(df.parse(dateBeginParam).getTime());
            } catch (ParseException ex) {
                Logger.getLogger(ContractRequestServlet.class.getName()).log(Level.SEVERE, null, ex);
                error += "Erreur lors de la mise à jour du contrat (date de début incorrecte).";
                request.setAttribute("error", error);
                RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/request_read.jsp");
                view.forward(request, response);
                return;
            }
            
            // Check if the beginning date is > today :
            if(dateBegin.before(new Date(System.currentTimeMillis()))){
                error += "Erreur lors de la mise à jour du contrat (date de début antérieur à la date actuelle).";
                request.setAttribute("error", error);
                RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/request_read.jsp");
                view.forward(request, response);
                return;
            }
                
            
            // Get Contract Request Id <id>
            Integer id = Integer.parseInt(idParam);
            
            // Check that the producer is allowed to modify the contract (=owner)
            // We get the list of contracts in request for the producer :
            Contract req = null;
            try {
                req =  DAOFactory.getInstance().getContractDAO().read(id);
            } catch (DAOException ex) {
                Logger.getLogger(PermanencyServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(req == null){
                error = "Le contrat que vous tentez de valider n'existe pas (ou plus).";
                request.setAttribute("error", error);
                RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/request_read.jsp");
                view.forward(request, response);
                return;
            }
            
            HttpSession session = request.getSession(false);
            String login = null;
            if((session != null && req.getOffreur() != null
                    && req.getOffreur().getPseudo().equals(session.getAttribute("login")))){
                login = (String)session.getAttribute("login");
            }else{
                error = "Vous n'avez pas le droit d'accéder "
                        + "à la ressource "+ request.getRequestURI();
                request.setAttribute("error", error);
                RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/request_read.jsp");
                view.forward(request, response);
                return;
            }
            
            // Update of the contract in DB :
            try {
                DAOFactory.getInstance().getContractDAO().updateValidate(id, new Date(System.currentTimeMillis()), dateBegin);
            } catch (DAOException ex) {
                Logger.getLogger(PermanencyServlet.class.getName()).log(Level.SEVERE, null, ex);
                error = "Erreur lors de la mise à jour du contrat.";
                request.setAttribute("error", error);
                RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/request_read.jsp");
                view.forward(request, response);
                return;
            }
            
            // message of success/failure
            if(error == null)
                request.setAttribute("success", "Le contrat a été validé.");
            
            // Forwarding :
            this.doGet(request, response);
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
