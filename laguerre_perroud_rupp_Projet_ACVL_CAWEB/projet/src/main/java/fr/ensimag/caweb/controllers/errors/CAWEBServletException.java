/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.caweb.controllers.errors;

import fr.ensimag.caweb.models.*;
import javax.servlet.ServletException;

/**
 *
 * @author Alexandre Rupp
 */
public class CAWEBServletException extends ServletException{
    public CAWEBServletException(String error) {
        super(error);
    }

    public CAWEBServletException(String error, Throwable throwable) {
        super(error, throwable);
    }
}
