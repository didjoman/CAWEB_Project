/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.caweb.models;

/**
 *
 * @author Alexandre Rupp
 */
public enum UserTypes {
    PROD ("PROD"),
    CONS ("CONS"),
    RESP ("RESP");

    private final String type;
    
    UserTypes(String type){
        this.type = type;
    }
    
    @Override
    public String toString() {
        return this.type;
    }
    
    
}
