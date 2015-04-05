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
public class ContractBuilder {
    
    private int idContrat;
    private Producer offreur;
    private Consummer demandeur;
    private Date dateContrat;
    private String nonProduitContrat;
    private int duree;
    private Quantity quantite;
    private int nbLots;
    
    private Date dateDebut;
    private boolean aRenouveler;
    
    public ContractBuilder setIdContrat(int idContrat) {
        this.idContrat = idContrat;
        return this;
    }
    
    public ContractBuilder setOffreur(Producer offreur) {
        this.offreur = offreur;
        return this;
    }
    
    public ContractBuilder setDemandeur(Consummer demandeur) {
        this.demandeur = demandeur;
        return this;
    }
    
    public ContractBuilder setDateContrat(Date dateContrat) {
        this.dateContrat = dateContrat;
        return this;
    }
    
    public ContractBuilder setNonProduitContrat(String nonProduitContrat) {
        this.nonProduitContrat = nonProduitContrat;
        return this;
    }
    
    public ContractBuilder setDuree(int duree) {
        this.duree = duree;
        return this;
    }
    
    public ContractBuilder setQuantite(Quantity quantite) {
        this.quantite = quantite;
        return this;
    }
    
    public ContractBuilder setNbLots(int nbLots) {
        this.nbLots = nbLots;
        return this;
    }
    
    public ContractBuilder setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }
    
    public ContractBuilder setaRenouveler(boolean aRenouveler) {
        this.aRenouveler = aRenouveler;
        return this;
    }
    
    public Contract build(){
        return ContractFactory.createContract(idContrat, offreur, demandeur, 
                dateContrat, nonProduitContrat, duree, quantite, nbLots, 
                dateDebut, aRenouveler);
    }
    
    
}
