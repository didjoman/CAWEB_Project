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
class ContractValidated extends ContractState {
    
    public ContractValidated(Contract contract) {
        super(contract);
    }
    
    @Override
    public void reNew(){
        contract.setState(contract.IN_RENEW);
    }
    
    @Override
    public Date getDateDebut(){
        return dateDebut;
    }
    
    @Override
    public Date getDateFin() {
        return dateFin;
    }
    
}
