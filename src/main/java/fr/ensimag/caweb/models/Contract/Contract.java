/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.models.Contract;

import fr.ensimag.caweb.models.User.Consummer;
import fr.ensimag.caweb.models.User.Producer;
import fr.ensimag.caweb.models.Quantity;
import java.util.Date;

/**
 *
 * @author Alexandre Rupp
 */
public class Contract{
    /*
    * /!\ NOTE : Contracts are implemented following the STATE design pattern.
    */
    
    public final ContractState IN_REQUEST = new ContractInRequest(this);
    public final ContractState VALIDATED = new ContractValidated(this);
    public final ContractState IN_RENEW = new ContractInRenew(this);
    
    private ContractState state;
    
    private int idContrat;
    private Producer offreur;
    private Consummer demandeur;
    private Date dateContrat;
    private String nonProduitContrat;
    private int duree;
    private Quantity quantite;
    private int nbLots;
    
    public Contract(int idContrat, Producer offreur, Consummer demandeur, Date dateContrat,
            String nonProduitContrat, int duree, Quantity quantite, int nbLots){
        this.state = IN_REQUEST;
        this.idContrat = idContrat;
        this.offreur = offreur;
        this.demandeur = demandeur;
        this.dateContrat = dateContrat;
        this.nonProduitContrat = nonProduitContrat;
        this.duree = duree;
        this.quantite = quantite;
        this.nbLots = nbLots;
    }
    
    public String getState(){
        return state.getState();
    }
    
    protected void setState(ContractState state){
        this.state = state;
    }
    
    public int getIdContrat() {
        return idContrat;
    }
    
    public void setIdContrat(int idContrat) {
        this.idContrat = idContrat;
    }
    
    public Producer getOffreur() {
        return offreur;
    }
    
    public void setOffreur(Producer offreur) {
        this.offreur = offreur;
    }
    
    public Consummer getDemandeur() {
        return demandeur;
    }
    
    public void setDemandeur(Consummer demandeur) {
        this.demandeur = demandeur;
    }
    
    public Date getDateContrat() {
        return dateContrat;
    }
    
    public void setDateContrat(Date dateContrat) {
        this.dateContrat = dateContrat;
    }
    
    public String getNonProduitContrat() {
        return nonProduitContrat;
    }
    
    public void setNonProduitContrat(String nonProduitContrat) {
        this.nonProduitContrat = nonProduitContrat;
    }
    
    public int getDuree() {
        return duree;
    }
    
    public void setDuree(int duree) {
        this.duree = duree;
    }
    
    public Quantity getQuantite() {
        return quantite;
    }
    
    public void setQuantite(Quantity quantite) {
        this.quantite = quantite;
    }
    
    public int getNbLots() {
        return nbLots;
    }
    
    public void setNbLots(int nbLots) {
        this.nbLots = nbLots;
    }
    
    public Date getDateDebut() {
        return state.getDateDebut();
    }
    
    public Date getDateFin(){
        return state.getDateFin();
    }
    
    public void validate(Date dateDebut) {
        state.validate(dateDebut);
    }
    
    public void reNew() {
        state.reNew();
    }
    
}
