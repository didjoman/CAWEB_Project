/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.caweb.controllers;

import fr.ensimag.caweb.controllers.errors.CAWEB_DatabaseAccessException;
import fr.ensimag.caweb.dao.DAOException;
import fr.ensimag.caweb.dao.impl.OfferDAOOracle;
import fr.ensimag.caweb.dao.DAOFactory;
import fr.ensimag.caweb.models.Offer;
import fr.ensimag.caweb.models.Quantity;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author Alexandre Rupp
 */
@WebServlet(name = "OfferServlet", urlPatterns = {"/offer"})
public class OfferServlet extends HttpServlet {

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
        String id = request.getParameter("id");
        String idCreate = request.getParameter("idCreate");
        if (id != null) {
            try {
                List<Offer> offers = DAOFactory.getInstance().getOfferDAO().read(id);
                request.setAttribute("offers", offers);
                request.setAttribute("isProd", "true");
                RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/offer_read.jsp");
                view.forward(request, response);
            } catch (DAOException ex) {
                Logger.getLogger(OfferServlet.class.getName()).log(Level.SEVERE, null, ex);
                throw new CAWEB_DatabaseAccessException();
            }
        } else if (idCreate != null) {
            request.setAttribute("creater", idCreate);
            RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/create_offer.jsp");
            view.forward(request, response);
        } else {
            try {
                List<Offer> offers = DAOFactory.getInstance().getOfferDAO().readAll();
                request.setAttribute("offers", offers);
                RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/offer_read.jsp");
                view.forward(request, response);
            } catch (DAOException ex) {
                Logger.getLogger(OfferServlet.class.getName()).log(Level.SEVERE, null, ex);
                throw new CAWEB_DatabaseAccessException();
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
        //CREATE AN OFFER
        String produit = request.getParameter("produit");
        String duree = request.getParameter("duree");
        String qte0 = request.getParameter("qte0");
        String unite0 = request.getParameter("unite0");
        String prix0 = request.getParameter("prix0");
        String nbQte = request.getParameter("nbQte");
        HttpSession session = request.getSession(false);
        String createur = (String) session.getAttribute("login");
        

        if (produit != null && duree != null && qte0 != null && unite0 != null
                && prix0 != null && nbQte != null) {
            Offer offer = new Offer(0, createur, produit, Integer.parseInt(duree), null);
            List<Quantity> quantities = new ArrayList<Quantity>();         
            quantities.add(new Quantity(Double.parseDouble(qte0), unite0, Integer.parseInt(prix0)));
            for (int i = 1; i < Integer.parseInt(nbQte); i++){
                String qte = request.getParameter("qte"+i);
                String unite = request.getParameter("unite"+i);
                String prix = request.getParameter("prix"+i);
                quantities.add(new Quantity(Double.parseDouble(qte), unite, Integer.parseInt(prix)));
            }
            try {
                DAOFactory.getInstance().getOfferDAO().create(offer, quantities);
                
            } catch (DAOException ex) {
                Logger.getLogger(OfferServlet.class.getName()).log(Level.SEVERE, null, ex);
                throw new CAWEB_DatabaseAccessException();
            }
            // Forwarding :
            request.setAttribute("success", "La création d'offre a été effectuée.");
            try {
                List<Offer> offers = DAOFactory.getInstance().getOfferDAO().readAll();
                request.setAttribute("offers", offers);
                RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/offer_read.jsp");
                view.forward(request, response);
            } catch (DAOException ex) {
                Logger.getLogger(OfferServlet.class.getName()).log(Level.SEVERE, null, ex);
                throw new CAWEB_DatabaseAccessException();}
        }
    }
}
