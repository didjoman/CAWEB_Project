/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.models.Contract;

import java.util.Date;

/**
 *
 * @author Alexandre Rupp
 */
class ContractInRenew extends ContractState {
    
    public ContractInRenew(Contract contract) {
        super(contract);
    }
    
    @Override
    public void validate(Date dateDebut){
        setDateDebut(dateDebut);
        contract.setState(contract.VALIDATED);
    }
}
