/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.caweb.controllers;

import fr.ensimag.caweb.controllers.errors.CAWEBServletException;
import fr.ensimag.caweb.controllers.errors.CAWEB_AccessRightsException;
import fr.ensimag.caweb.controllers.errors.CAWEB_DatabaseAccessException;
import fr.ensimag.caweb.dao.DAOException;
import fr.ensimag.caweb.dao.DAOFactory;
import fr.ensimag.caweb.models.Contract.Contract;
import fr.ensimag.caweb.models.Contract.ContractFactory;
import fr.ensimag.caweb.models.Offer;
import fr.ensimag.caweb.models.Quantity;
import fr.ensimag.caweb.models.User.Consummer;
import fr.ensimag.caweb.models.User.Producer;
import fr.ensimag.caweb.models.User.UserStatus;
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
public class RequestServlet extends HttpServlet {
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
        String idParam = request.getParameter("id");

        // READ 1 contract request
        if (idParam != null) {
            // Get Contract Request Id <id>
            Integer id = Integer.parseInt(idParam);
            request.setAttribute("id", id);

            // Check the access rights :
            HttpSession session = request.getSession(false);
            String login = null;
            String status = null;
            if ((session != null && session.getAttribute("login") != null)) {
                login = (String) session.getAttribute("login");
                status = (String) session.getAttribute("status");
            } else {
                throw new CAWEB_AccessRightsException(request.getRequestURI());
            }

            // We get the list of contracts in request for the producer :
            Contract req = null;
            try {
                req = DAOFactory.getInstance().getContractDAO().read(id);
            } catch (DAOException ex) {
                Logger.getLogger(PermanencyServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Checks if the contract exists
            if (req == null) {
                throw new CAWEBServletException("La demande de contrat n'existe pas (ou plus).");
            } // Check access rights (2) (possessor of the contract = user)
            else if ((status.equals(UserStatus.PROD.toString())
                    && !req.getOffreur().getPseudo().equals(login))
                    || (status.equals(UserStatus.CONS.toString())
                    && !req.getDemandeur().getPseudo().equals(login))) {
                throw new CAWEB_AccessRightsException(request.getRequestURI());
            }

            request.setAttribute("req", req);

            RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/request_read.jsp");
            view.forward(request, response);
        } // READ ALL contract requests :
        else {
            // Check the status of the user :
            HttpSession session = request.getSession(false);
            String login = null;
            String status = null;
            if ((session != null && session.getAttribute("login") != null)) {
                login = (String) session.getAttribute("login");
                status = (String) session.getAttribute("status");
            } else {
                throw new CAWEB_AccessRightsException(request.getRequestURI());
            }

            List<Contract> reqs = null;
            List<Contract> reqsRefused = null;
            List<Contract> contractsToRenew = null;

            try {
                // We get the list of contracts in request for the producer :
                reqs = DAOFactory.getInstance().getContractDAO().readAllContractRequests(login);

                // We get the list of contracts in request for the producer :
                reqsRefused = DAOFactory.getInstance().getContractDAO().readAllRefusedContractRequests(login);

                // We get the list of contracts in request for the producer :
                contractsToRenew = DAOFactory.getInstance().getContractDAO().readAllContractsToRenew(login);
            } catch (DAOException ex) {
                Logger.getLogger(PermanencyServlet.class.getName()).log(Level.SEVERE, null, ex);
                throw new CAWEB_DatabaseAccessException();
            }

            request.setAttribute("reqs", reqs);
            request.setAttribute("reqsRefused", reqsRefused);
            request.setAttribute("contractsToRenew", contractsToRenew);

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
        String action = request.getParameter("action");
        String producer = request.getParameter("producer");
        String product = request.getParameter("product");
        String duration = request.getParameter("duration");
        String quantity = request.getParameter("quantity");
        String nbLots = request.getParameter("nbLots");
        String uniteQte = request.getParameter("uniteQte");
        String price = request.getParameter("price");

        /*if (idParam == null) {
         throw new CAWEBServletException("Id de contrat incorrect");
         }*/
        if (idParam != null) {
            // Get Contract Request Id <id>
            Integer id = Integer.parseInt(idParam);

            // Check that the producer is allowed to modify the contract (=owner)
            checkAccessRights(request, id);

            // REQUEST DELETE :
            if (action != null && action.equals("delete")) {
                // Update of the contract in DB :
                try {
                    DAOFactory.getInstance().getContractDAO().delete(id);
                } catch (DAOException ex) {
                    Logger.getLogger(PermanencyServlet.class.getName()).log(Level.SEVERE, null, ex);
                    throw new CAWEB_DatabaseAccessException();
                }

                // Forwarding :
                request.setAttribute("success", "La demande de contrat a été refusée");
                this.doGet(request, response);
            } // REQUEST VALIDATE :
            else if (idParam != null && dateBeginParam != null) {
                // Parse and check date :
                Date dateBegin = checkDate(request);

                // Update of the contract in DB :
                try {
                    DAOFactory.getInstance().getContractDAO().updateValidate(id, new Date(System.currentTimeMillis()), dateBegin);
                } catch (DAOException ex) {
                    Logger.getLogger(PermanencyServlet.class.getName()).log(Level.SEVERE, null, ex);
                    throw new CAWEB_DatabaseAccessException();
                }

                // Forwarding :
                request.setAttribute("success", "Le contrat a été validé.");
                this.doGet(request, response);

            }
        } //REQUEST CREATION
        else if (producer != null && product != null && duration != null
                && quantity != null && nbLots != null && price != null && 
                uniteQte != null && action != null && action.equals("create")) {
            //Create Producer object      
            Producer producerObj = new Producer();
            producerObj.setPseudo(producer);
            //Get consummer name and create consummer object
            HttpSession session = request.getSession(false);
            String consummer = (String) session.getAttribute("login");
            Consummer consummerObj = new Consummer();
            consummerObj.setPseudo(consummer);
            //Create a new contract in request
            Contract contract = ContractFactory.createContract(0, producerObj, consummerObj,
                    new Date(System.currentTimeMillis()), product, Integer.parseInt(duration),
                    new Quantity(Double.parseDouble(quantity),uniteQte, Integer.parseInt(price)),
                    Integer.parseInt(nbLots));
            try {
                DAOFactory.getInstance().getContractDAO().create(contract);
            } catch (DAOException ex) {
                Logger.getLogger(PermanencyServlet.class.getName()).log(Level.SEVERE, null, ex);
                throw new CAWEB_DatabaseAccessException();
            }

            // Forwarding :
            request.setAttribute("success", "La demande de contrat a été effectuée.");
            try {
                List<Offer> offers = DAOFactory.getInstance().getOfferDAO().readAll();
                request.setAttribute("offers", offers);
                RequestDispatcher view = request.getRequestDispatcher("./WEB-INF/pages/offer_read.jsp");
                view.forward(request, response);
            } catch (DAOException ex) {
                Logger.getLogger(OfferServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private Date checkDate(HttpServletRequest request) throws ServletException {
        Date dateBegin = null;
        // Parsing of the date of beginning :
        try {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            dateBegin = new Date(df.parse(request.getParameter("begin")).getTime());
        } catch (ParseException ex) {
            throw new CAWEBServletException("Erreur lors de la mise à jour du contrat (date de début incorrecte).");
        }

        // Check if the beginning date is > today :
        if (dateBegin.before(new Date(System.currentTimeMillis()))) {
            throw new CAWEBServletException("Erreur lors de la mise à jour du contrat (date de début antérieur à la date actuelle).");
        }

        return dateBegin;
    }

    private void checkAccessRights(HttpServletRequest request, int idContract) throws ServletException {
        Contract req = null;
        try {
            req = DAOFactory.getInstance().getContractDAO().read(idContract);

        } catch (DAOException ex) {
            Logger.getLogger(PermanencyServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
            throw new CAWEB_DatabaseAccessException();
        }

        if (req == null) {
            throw new CAWEBServletException("Le contrat que vous tentez de valider n'existe pas (ou plus).");
        }

        HttpSession session = request.getSession(false);
        if (!(session != null && req.getOffreur() != null
                && req.getOffreur().getPseudo().equals(session.getAttribute("login")))) {
            throw new CAWEB_AccessRightsException(request.getRequestURI());
        }
    }

}
