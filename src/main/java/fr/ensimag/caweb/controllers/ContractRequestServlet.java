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
        String producer = request.getParameter("producer");
        String idParam = request.getParameter("id");
        String dateContParam = request.getParameter("dateCont");
        String dateBeginParam = request.getParameter("dateBegin");
        
        if(action != null){
            if(action.equals("create")){
                RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/request_create.jsp");
                view.forward(request, response);
                
            }
            
        }else if(idParam != null){
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
            // Chec si le contrat existe
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
        } else if(producer != null){
            // Check : A producer can only access to his own requests :
            HttpSession session = request.getSession(false);
            if(!(session != null && producer.equals(session.getAttribute("login")))){
                String error = "Vous n'avez pas le droit d'accéder "
                        + "à la ressource "+ request.getRequestURI();
                request.setAttribute("error", error);
                RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/request_read_all.jsp");
                view.forward(request, response);
            }
            
            // We get the list of contracts in request for the producer :
            List<Contract> reqs = null;
            try {
                reqs =  DAOFactory.getInstance().getContractDAO().readAllRequests(producer);
            } catch (DAOException ex) {
                Logger.getLogger(PermanencyServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            request.setAttribute("reqs", reqs);
            
            RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/request_read_all.jsp");
            view.forward(request, response);
            
            
        } else {
            // Get the list of Contract Requests.
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
        String action = request.getParameter("action");
        String producer = request.getParameter("producer");
        String idParam = request.getParameter("id");
        String dateContParam = request.getParameter("dateCont");
        String dateBeginParam = request.getParameter("dateBegin");
        
        String error = null;
        if(action != null &&
                action.equals("validate") && idParam != null &&
                dateContParam != null && dateBeginParam != null){
            
            
            // Parsing of the date of Contract :
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date dateCont = null;
            try {
                dateCont = new Date(df.parse(dateContParam).getTime());
            } catch (ParseException ex) {
                Logger.getLogger(ContractRequestServlet.class.getName()).log(Level.SEVERE, null, ex);
                error = "Erreur lors de la mise à jour du contrat.";
                request.setAttribute("error", error);
                RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/request_read.jsp");
                view.forward(request, response);
            }
            
            // Parsing of the date of beginning :
            Date dateBegin = null;
            try {
                dateBegin = new Date(df.parse(dateBeginParam).getTime());
            } catch (ParseException ex) {
                Logger.getLogger(ContractRequestServlet.class.getName()).log(Level.SEVERE, null, ex);
                error += "Erreur lors de la mise à jour du contrat (date de début incorrecte).";
                request.setAttribute("error", error);
                RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/request_read.jsp");
                view.forward(request, response);
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
            }
            
            HttpSession session = request.getSession(false);
            if(!(session != null && req.getOffreur() != null
                    && req.getOffreur().getPseudo().equals(session.getAttribute("login")))){
                error = "Vous n'avez pas le droit d'accéder "
                        + "à la ressource "+ request.getRequestURI();
                request.setAttribute("error", error);
                RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/request_read.jsp");
                view.forward(request, response);
            }
            
            // Update of the contract in DB :
            try {
                DAOFactory.getInstance().getContractDAO().updateValidate(id, dateCont, dateBegin);
            } catch (DAOException ex) {
                Logger.getLogger(PermanencyServlet.class.getName()).log(Level.SEVERE, null, ex);
                error = "Erreur lors de la mise à jour du contrat.";
                request.setAttribute("error", error);
                RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/request_read.jsp");
                view.forward(request, response);
            }
            
            // message of success/failure
            if(error == null)
                request.setAttribute("success", "Le contrat a été validé.");
            
            // Forwarding :
            RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/request_read.jsp");
            view.forward(request, response);
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
