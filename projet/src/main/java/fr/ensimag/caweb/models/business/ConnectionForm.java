/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.models.business;

import fr.ensimag.caweb.models.User.User;
import fr.ensimag.caweb.controllers.UserServlet;
import fr.ensimag.caweb.dao.DAOException;
import fr.ensimag.caweb.dao.DAOFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Alexandre Rupp
 */
public class ConnectionForm {
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    
    private String error = null;
    private String success;
    
    public String getError() {
        return error;
    }
    
    public String getSuccess() {
        return success;
    }
    
    public User logUser(HttpServletRequest request){
        // Get the parameters
        String login = getParam(request, LOGIN);
        String password = getParam(request, PASSWORD);
        
        User loggedUser = null;
        
        // Check Login & password parameters
        try {
            checkLogin(login);
            checkPassword(password);
        } catch (Exception ex) {
            error = "Erreur : Login ou mot de passe incorrecte !";
        }
        
        // Verify in the DB if a user exists with this paswword
        if(error == null)
            loggedUser = getLoggedUser(login, password);
        
        // Updates the error/success messages
        if(loggedUser == null)
            error = "Erreur : Login ou mot de passe incorrecte !";
        else
            success = "Vous êtes désormais connectés.";
        
        return loggedUser;
    }
    
    private void checkLogin(String login) throws Exception{
        if(login == null)
            throw new EmptyFieldException("login");
    }
    
    private void checkPassword(String pwd) throws Exception{
        if(pwd == null)
            throw new EmptyFieldException("Mot de passe");
    }
    
    private User getLoggedUser(String login, String pwd){
        try {
            return DAOFactory.getInstance().getUserDAO().read(login, pwd);
        } catch (DAOException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private static String getParam(HttpServletRequest request, String nomChamp ) {
        String val = request.getParameter( nomChamp );
        return ( val == null || val.trim().length() == 0 ) ? null :  val.trim();
    }
}
