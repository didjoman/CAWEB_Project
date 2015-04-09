/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.controllers;

import com.google.gson.Gson;
import static com.google.gson.internal.bind.TypeAdapters.URL;
import fr.ensimag.caweb.dao.DAOException;
import fr.ensimag.caweb.dao.DAOFactory;
import fr.ensimag.caweb.models.Week;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sun.misc.IOUtils;

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
        
        // READ ALL
        if(weekNumParam == null || yearParam == null){
            // Get the weeks in the database :
            List<Week> weeks = null;
            try {
                weeks =  DAOFactory.getInstance().getWeekDAO().readAll();
            } catch (DAOException ex) {
                Logger.getLogger(PermanencyServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            // Send the data to the client :
            if(async != null && async.equals("true")){
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                Gson gson = new Gson();
                response.getWriter().write(gson.toJson(weeks));
            } else {
                request.setAttribute("weeks", weeks);
                
                RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/permanency_read_all.jsp");
                view.forward(request, response);
            }
        }
        
        // READ :
        else {
            // We get the data about the week :
            Week week = null;
            int weekNum = Integer.parseInt(weekNumParam);
            int year = Integer.parseInt(yearParam);
            
            // Get the week from the database ; 
            try {
                week =  DAOFactory.getInstance().getWeekDAO().read(weekNum, year);
            } catch (DAOException ex) {
                Logger.getLogger(PermanencyServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // Transmit it to the client : 
            if(async != null && async.equals("true")){
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                Gson gson = new Gson();
                response.getWriter().write(gson.toJson(week));
                // TODO: to change absolutely !!!!!
                // DANGEROUS : We transmit EVERYTHING to the client !
                // Juste transmit the required info.
            }
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
