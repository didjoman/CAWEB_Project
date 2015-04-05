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
        if(dateContrat != null)
            contract.validate(dateContrat);
        return contract;
    }
    
    public static Contract createContract(int idContrat, Producer offreur, Consummer demandeur, Date dateContrat,
            String nonProduitContrat, int duree, Quantity quantite, int nbLots, Date dateDebut, Boolean aRenouveler){
        Contract contract = createContract(idContrat, offreur, demandeur,
                dateContrat, nonProduitContrat, duree, quantite, nbLots, dateDebut);
        if(aRenouveler != null && aRenouveler)
            contract.reNew();
        return contract;
    }
}
