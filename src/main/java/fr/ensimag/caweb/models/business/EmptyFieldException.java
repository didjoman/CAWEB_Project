/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.caweb.models.business;

/**
 *
 * @author Alexandre Rupp
 */
public class EmptyFieldException extends Exception{
    public EmptyFieldException(String fieldName) {
        super("Le champ "+ fieldName +" n'est pas rempli");
    }

    public EmptyFieldException(String fieldName, Throwable throwable) {
        super("Le champ "+ fieldName +" n'est pas rempli", throwable);
    }
}
