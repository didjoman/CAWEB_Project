/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.controllers.errors;

/**
 *
 * @author Alexandre Rupp
 */
public class CAWEB_DatabaseAccessException extends CAWEBServletException {
    private static final String ERROR_MSG = "Une erreur s'est produite lors de "
            + "l'accés à la base de donnée.";
    
    public CAWEB_DatabaseAccessException() {
        super(ERROR_MSG);
    }
    
    public CAWEB_DatabaseAccessException(String error) {
        super(ERROR_MSG + error);
    }
    
    public CAWEB_DatabaseAccessException(String error, Throwable throwable) {
        super(ERROR_MSG + error, throwable);
    }
    
}
