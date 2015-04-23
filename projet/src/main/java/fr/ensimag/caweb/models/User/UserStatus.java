/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.caweb.models.User;

/**
 *
 * @author Alexandre Rupp
 */
public enum UserStatus {
    PROD ("PROD"),
    CONS ("CONS"),
    RESP ("RESP");

    private final String status;
    
    UserStatus(String status){
        this.status = status;
    }
    
    @Override
    public String toString() {
        return this.status;
    }
    
    
}
