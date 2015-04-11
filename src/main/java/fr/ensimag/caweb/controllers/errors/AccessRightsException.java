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
public class AccessRightsException extends CAWEBServletException{
    private static final String ERROR_MSG = "Vous n'avez pas le droit d'accéder "
                        + "à la ressource ";
    public AccessRightsException(String uri) {
        super(ERROR_MSG+ uri);
    }

    public AccessRightsException(String error, Throwable throwable) {
        super(ERROR_MSG+ error, throwable);
    }
    
    
}
