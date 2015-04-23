/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.models.Contract;

/**
 *
 * @author Alexandre Rupp
 */
public class ContractFinished extends ContractValidated{
    public ContractFinished(Contract contract) {
        super(contract);
    }
    
    @Override
    public void setToReNew(){
        contract.setState(contract.IN_RENEW);
    }
    
}
