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
class ContractInRequest extends ContractState {
    
    public ContractInRequest(Contract contract) {
        super(contract);
    }
    
    @Override
    public void validate(Date dateDebut){
        contract.setDateDebut(dateDebut);
        contract.setState(contract.VALIDATED);
    }
}
