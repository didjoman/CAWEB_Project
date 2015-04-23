/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.controllers;

import fr.ensimag.caweb.controllers.errors.CAWEBServletException;
import com.google.gson.Gson;
import static com.google.gson.internal.bind.TypeAdapters.URL;
import fr.ensimag.caweb.controllers.errors.CAWEB_AccessRightsException;
import fr.ensimag.caweb.controllers.errors.CAWEB_DatabaseAccessException;
import fr.ensimag.caweb.dao.DAOException;
import fr.ensimag.caweb.dao.DAOFactory;
import fr.ensimag.caweb.models.User.User;
import fr.ensimag.caweb.models.User.UserStatus;
import fr.ensimag.caweb.models.Week;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
@WebServlet(name = "PermanencyServlet", urlPatterns = {"/permanency"})
public class PermanencyServlet extends HttpServlet {
    
    
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
        // Get the params :
        String async = request.getParameter("async");
        String weekNumParam = request.getParameter("weekNum");
        String yearParam = request.getParameter("year");
        
        
        // Get the status of the user :
        HttpSession session = request.getSession(false);
        String login = null;
        String status = null;
        if(session != null && session.getAttribute("login") != null
                && session.getAttribute("status") != null){
            login = (String)session.getAttribute("login");
            status = (String)session.getAttribute("status");
        } else
            throw new CAWEB_AccessRightsException(request.getRequestURI());
        
        // READ :
        if(weekNumParam != null && yearParam != null) {
            // We get the data about the week :
            Week week = null;
            int weekNum = Integer.parseInt(weekNumParam);
            int year = Integer.parseInt(yearParam);
            
            // Get the week from the database ;
            try {
                week =  DAOFactory.getInstance().getWeekDAO().read(weekNum, year);
            } catch (DAOException ex) {
                Logger.getLogger(PermanencyServlet.class.getName()).log(Level.SEVERE, null, ex);
                throw new CAWEB_DatabaseAccessException();
            }
            
            // Response to an AJAX request :
            if(async != null && async.equals("true")){
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                Gson gson = new Gson();
                
                
                // READ for RESPONSIBLE
                if(status.equals(UserStatus.RESP.toString())){
                    if(week != null)
                        response.getWriter().write(week.getJSON());
                }
                // READ for CONSUMMER
                else if(status.equals(UserStatus.CONS.toString())){
                    User perm1 = week.getPermanencier1();
                    User perm2 = week.getPermanencier2();
                    
                    String returnVal = "{";
                    if(perm1 != null && !perm1.getPseudo().equals(login))
                        returnVal += "\"perm\" : " + perm1.getJSONContactInfo();
                    else if(perm2 != null && !perm2.getPseudo().equals(login))
                        returnVal += "\"perm\" : " + perm2.getJSONContactInfo();
                    returnVal += "}";
                    response.getWriter().write(returnVal);
                }
            }
        }
        // READ ALL
        else{
            // Get the weeks in the database :
            List<Week> weeks = null;
            
            // READ ALL for CONSUMMERS :
            if(status.equals(UserStatus.CONS.toString())){
                try {
                    weeks =  DAOFactory.getInstance().getWeekDAO().readAllWhereConsumerAppears(login);
                } catch (DAOException ex) {
                    Logger.getLogger(PermanencyServlet.class.getName()).log(Level.SEVERE, null, ex);
                    throw new CAWEB_DatabaseAccessException();
                }
                
                request.setAttribute("weeks", weeks);
                RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/consummer/permanency_read_all.jsp");
                view.forward(request, response);
            }
            // READ ALL for RESPONSIBLES :
            else if(status.equals(UserStatus.RESP.toString())){
                // Get the list of weeks (to print on the calendar, in the view)
                try {
                    weeks =  DAOFactory.getInstance().getWeekDAO().readAll();
                } catch (DAOException ex) {
                    Logger.getLogger(PermanencyServlet.class.getName()).log(Level.SEVERE, null, ex);
                    throw new CAWEB_DatabaseAccessException();
                }
                
                // Get the consummers list (to help the responsible choose a permanencier)
                List<User> users = null;
                try {
                    users =  DAOFactory.getInstance().getUserDAO().readAllWithStatus(UserStatus.CONS);
                } catch (DAOException ex) {
                    Logger.getLogger(PermanencyServlet.class.getName()).log(Level.SEVERE, null, ex);
                    throw new CAWEB_DatabaseAccessException();
                }
                
                // Get the statistics about the perms.
                List<Map.Entry<String, Integer>> stats = new ArrayList();
                try {
                    stats =  DAOFactory.getInstance().getWeekDAO().getNbPermsPerUser();
                } catch (DAOException ex) {
                    Logger.getLogger(PermanencyServlet.class.getName()).log(Level.SEVERE, null, ex);
                    throw new CAWEB_DatabaseAccessException();
                }
                
                List<Map.Entry<String, Double>> stats2 = new ArrayList();
                try {
                    stats2 =  DAOFactory.getInstance().getWeekDAO().getPermFreqPerActiveUser();
                } catch (DAOException ex) {
                    Logger.getLogger(PermanencyServlet.class.getName()).log(Level.SEVERE, null, ex);
                    throw new CAWEB_DatabaseAccessException();
                }
                
                request.setAttribute("weeks", weeks);
                request.setAttribute("consummers", users);
                request.setAttribute("stats", stats);
                request.setAttribute("stats2", stats2);
                
                RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/responsible/permanency_read_all.jsp");
                view.forward(request, response);
            }
            else
                throw new CAWEB_AccessRightsException(request.getRequestURI());
            
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
        String async = request.getParameter("async");
        String weekNumParam = request.getParameter("weekNum");
        String yearParam = request.getParameter("year");
        String dispo = request.getParameter("dispo");
        String perm1 = request.getParameter("perm1");
        String perm2 = request.getParameter("perm2");
        String setPerm = request.getParameter("setPerm");
        
        
        
        // Check : A consummer can only access to his own requests :
        String error = null;
        String login = null;
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute("login") != null)
            login = (String)session.getAttribute("login");
        else
            throw new CAWEB_AccessRightsException(request.getRequestURI());
        
        
        // UDPATE Disponibilities :
        if(weekNumParam != null && yearParam != null && dispo != null){
            int weekNum = Integer.parseInt(weekNumParam);
            int year = Integer.parseInt(yearParam);
            try {
                switch (dispo) {
                    case "maybe":
                        DAOFactory.getInstance().getWeekDAO().deleteDispo(weekNum, year, login);
                        break;
                    case "true":
                        DAOFactory.getInstance().getWeekDAO().updateSetDispo(weekNum, year, login, 1);
                        break;
                    case "false":
                        DAOFactory.getInstance().getWeekDAO().updateSetDispo(weekNum, year, login, 0);
                        break;
                }
            } catch (DAOException ex) {
                Logger.getLogger(PermanencyServlet.class.getName()).log(Level.SEVERE, null, ex);
                throw new CAWEB_DatabaseAccessException();
            }
            
            // Transmit confirmation to the client :
            if(async != null && async.equals("true")){
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                Gson gson = new Gson();
                response.getWriter().write("{\"OK\":\"true\"}");
            }
        }
        // UDPATE Permanencies
        else if(setPerm != null && setPerm.equals("true")){
            int weekNum = Integer.parseInt(weekNumParam);
            int year = Integer.parseInt(yearParam);
            try {
                DAOFactory.getInstance().getWeekDAO().updateSetPerm(weekNum, year, perm1, perm2);
            } catch (DAOException ex) {
                Logger.getLogger(PermanencyServlet.class.getName()).log(Level.SEVERE, null, ex);
                throw new CAWEB_DatabaseAccessException();
            }
            
            // Transmit confirmation to the client :
            if(async != null && async.equals("true")){
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                Gson gson = new Gson();
                response.getWriter().write("{\"OK\":\"true\"}");
            }
        }
        
        
    }
    
}
