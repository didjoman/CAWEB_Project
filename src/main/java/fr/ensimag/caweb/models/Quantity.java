/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.models;

/**
 *
 * @author Alexandre Rupp
 */
public class Quantity {
    private double qte;
    private String uniteQte;
    private int prix;

    public Quantity(double qte, String uniteQte, int prix) {
        this.qte = qte;
        this.uniteQte = uniteQte;
        this.prix = prix;
    }

    public double getQte() {
        return qte;
    }

    public void setQte(double qte) {
        this.qte = qte;
    }

    public String getUniteQte() {
        return uniteQte;
    }

    public void setUniteQte(String uniteQte) {
        this.uniteQte = uniteQte;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

}
