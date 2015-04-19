/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.models.Contract;

import fr.ensimag.caweb.models.Quantity;
import fr.ensimag.caweb.models.User.Consummer;
import fr.ensimag.caweb.models.User.Producer;
import java.util.Date;

/**
 *
 * @author Alexandre Rupp
 */
public class ContractFactory {
    public static Contract createContract(int idContrat, Producer offreur, Consummer demandeur, Date dateContrat,
            String nonProduitContrat, int duree, Quantity quantite, int nbLots){
        return new Contract(idContrat, offreur, demandeur, dateContrat, nonProduitContrat, duree, quantite, nbLots);
    }
    
    public static Contract createContract(int idContrat, Producer offreur, Consummer demandeur, Date dateContrat,
            String nonProduitContrat, int duree, Quantity quantite, int nbLots, Date dateDebut){
        Contract contract;
        contract = createContract(idContrat, offreur, demandeur, dateContrat, nonProduitContrat, duree, quantite, nbLots);
        
        if(dateDebut != null)
            contract.validate(dateDebut);
        return contract;
    }
    
    public static Contract createContract(int idContrat, Producer offreur, Consummer demandeur, Date dateContrat,
            String nonProduitContrat, int duree, Quantity quantite, int nbLots, Date dateDebut, Boolean aRenouveler){
        Contract contract = createContract(idContrat, offreur, demandeur,
                dateContrat, nonProduitContrat, duree, quantite, nbLots, dateDebut);
        if(aRenouveler != null && aRenouveler)
            contract.setToReNew();
        return contract;
    }
    
    public static Contract createContract(int idContrat, Producer offreur, Consummer demandeur, Date dateContrat,
            String nonProduitContrat, int duree, Quantity quantite, int nbLots, Date dateDebut, Boolean aRenouveler, Boolean refuse){
        Contract contract = createContract(idContrat, offreur, demandeur,
                dateContrat, nonProduitContrat, duree, quantite, nbLots, dateDebut, aRenouveler);
        if(refuse != null && refuse)
            contract.refuse();
        return contract;
    }
}
