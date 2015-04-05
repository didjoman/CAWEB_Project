/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.models;

import fr.ensimag.caweb.models.User.User;
import fr.ensimag.caweb.models.User.Consummer;
import fr.ensimag.caweb.models.User.Producer;
import fr.ensimag.caweb.dao.DAOException;
import fr.ensimag.caweb.dao.DAOFactory;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Alexandre Rupp
 */
public class SubscriptionForm {
    // Parameters names :
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String PASSWORD_VERIF = "passwordverif";
    private static final String EMAIL = "email";
    private static final String ADDRESS = "address";
    private static final String NAME = "name";
    private static final String FIRSTNAME = "firstname";
    private static final String TEL = "tel";
    private static final String STATUS = "status";
    
    private String error;
    private String success;
    
    private final Map<String, String> errorDetails;
    
    public SubscriptionForm() {
        errorDetails = new HashMap<String, String>();
    }
    
    public String getError() {
        return error;
    }
    
    public String getSuccess() {
        return success;
    }
    
    public Map<String, String> getErrorDetails() {
        return errorDetails;
    }
    
    public User subscribeUser(HttpServletRequest request){
        // We get the parameters :
        String login = getParam(request, LOGIN);
        String password = getParam(request, PASSWORD);
        String passwordVerif = getParam(request, PASSWORD_VERIF);
        String email = getParam(request, EMAIL);
        String address = getParam(request, ADDRESS);
        String name = getParam(request, NAME);
        String firstname = getParam(request, FIRSTNAME);
        String tel = getParam(request, TEL);
        String status = getParam(request, STATUS);
        
        boolean emptyField = false;
        User user = null;
        
        // We check every field :
        try{
            checkLogin(login);
        }catch(EmptyFieldException e){
            emptyField = true;
            errorDetails.put(LOGIN, e.getMessage());
        } catch(Exception e) {
            errorDetails.put(LOGIN, e.getMessage());
        }
        
        try{
            checkPassword(password, passwordVerif);
        }catch(EmptyFieldException e){
            emptyField = true;
            errorDetails.put(PASSWORD, e.getMessage());
        } catch(Exception e) {
            errorDetails.put(PASSWORD, e.getMessage());
        }
        
        try{
            checkAddress(address);
        }catch(EmptyFieldException e){
            emptyField = true;
            errorDetails.put(ADDRESS, e.getMessage());
        } catch(Exception e) {
            errorDetails.put(ADDRESS, e.getMessage());
        }
        
        try{
            checkEmail(email);
        }catch(EmptyFieldException e){
            emptyField = true;
            errorDetails.put(EMAIL, e.getMessage());
        } catch(Exception e) {
            errorDetails.put(EMAIL, e.getMessage());
        }
        
        try{
            checkFirstName(firstname);
        }catch(EmptyFieldException e){
            emptyField = true;
            errorDetails.put(FIRSTNAME, e.getMessage());
        } catch(Exception e) {
            errorDetails.put(FIRSTNAME, e.getMessage());
        }
        
        try{
            checkName(name);
        }catch(EmptyFieldException e){
            emptyField = true;
            errorDetails.put(NAME, e.getMessage());
        } catch(Exception e) {
            errorDetails.put(NAME, e.getMessage());
        }
        
        try{
            checkTel(tel);
        }catch(EmptyFieldException e){
            emptyField = true;
            errorDetails.put(TEL, e.getMessage());
        } catch(Exception e) {
            errorDetails.put(TEL, e.getMessage());
        }
        
        try{
            checkStatus(status);
        }catch(EmptyFieldException e){
            emptyField = true;
            errorDetails.put(STATUS, e.getMessage());
        } catch(Exception e) {
            errorDetails.put(STATUS, e.getMessage());
        }
        
        // Creation of the user to return
        if(errorDetails.isEmpty()){
            if(status.equals("prod"))
                user = new Producer(login, password, email, address, name, firstname, tel);
            else
                user = new Consummer(login, password, email, address, name, firstname, tel);
            createUserInDB(user);
        }
        
        // Set general error/success messages
        if(errorDetails.isEmpty()){
            success = "Votre compte a bien été créé.<br />";
        }else{
            user = null;
            error = "Erreur : L'inscription a échoué.<br />";
            if(emptyField)
                error += "Erreur : Veuillez remplir tous les champs.<br />";
        }
        
        return user;
    }
    
    public void createUserInDB(User u){
// Create the new user in the database
        try {
            DAOFactory.getInstance().getUserDAO().create(u);
        } catch (DAOException ex) {
            errorDetails.put("bdd", "Erreur : Erreur lors de la création du compte.<br />");
        }
    }
    
    private void checkLogin(String login) throws Exception{
        if(login == null)
            throw new EmptyFieldException("login");
    }
    
    private void checkPassword(String pwd, String pwdVerif) throws Exception{
        if(pwd == null)
            throw new EmptyFieldException("Mot de passe");
        if(pwdVerif == null)
            throw new EmptyFieldException("Mot de passe (2)");
        
        if(!pwd.equals(pwdVerif))
            throw new Exception("Le mot de passe et sa vérification ne sont pas identiques.<br />");
    }
    
    private void checkEmail(String email) throws Exception{
        if(email == null)
            throw new EmptyFieldException("courriel");
        
        if (!email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
            throw new Exception("L'adresse email saisie n'est pas valide.<br />");
        }
    }
    
    private void checkAddress(String address) throws Exception{
        if(address == null)
            throw new EmptyFieldException("adresse");
    }
    
    private void checkName(String name) throws Exception{
        if(name == null)
            throw new EmptyFieldException("nom");
        if(name.length() < 2)
            throw new Exception("Nom trop court.<br />");
    }
    
    private void checkFirstName(String firstname) throws Exception{
        if(firstname == null)
            throw new EmptyFieldException("prénom");
        if(firstname.length() < 2)
            throw new Exception("Prénom trop court.<br />");
    }
    
    private void checkTel(String tel) throws Exception{
        if(tel == null)
            throw new EmptyFieldException("tel");
        if(tel.length() < 2)
            throw new Exception("Numéro de téléphone invalide.<br />");
    }
    
    private void checkStatus(String status) throws Exception{
        if(status == null)
            throw new EmptyFieldException("prénom");
        if(!status.equals("prod") && !status.equals("cons") &&
                !status.equals("resp"))
            throw new Exception("statut invalide.<br />");
    }
    
    
    private static String getParam(HttpServletRequest request, String nomChamp ) {
        String val = request.getParameter( nomChamp );
        return ( val == null || val.trim().length() == 0 ) ? null :  val.trim();
    }
}
