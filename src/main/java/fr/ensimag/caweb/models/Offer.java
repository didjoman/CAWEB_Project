/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.caweb.models;

import java.util.List;

/**
 *
 * @author Alexandre Rupp
 */
public class Offer {

    private String createur;
    private String nomProduit;
    private int duree;
    private List<Quantity> quantities;

    public Offer(String createur, String nomProduit, int duree, List<Quantity> quantities) {
        this.createur = createur;
        this.nomProduit = nomProduit;
        this.duree = duree;
        this.quantities = quantities;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public List<Quantity> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<Quantity> quantities) {
        this.quantities = quantities;
    }
    
    public String getCreateur() {
      return createur;
    }
    
    public void setCreateur(String createur) {
        this.createur = createur;
    }

}
