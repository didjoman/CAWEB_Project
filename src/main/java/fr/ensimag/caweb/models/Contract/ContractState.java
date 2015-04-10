/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.models.Contract;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Alexandre Rupp
 */
public abstract class ContractState {
    protected final Contract contract;
    
    public ContractState(Contract contract) {
        this.contract = contract;
    }
    
    public String getState(){
        String state = getClass().getName();
        return state.substring(state.lastIndexOf('.')+1).toUpperCase();
    }
    
    public void validate(Date dateDebut){}
    
    public void reNew(){}
    
    public Date getDateDebut(){
        return null;
    }
    
    public Date getDateFin(){
        return null;
    }
}
