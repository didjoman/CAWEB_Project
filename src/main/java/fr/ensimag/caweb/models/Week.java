/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.models;

import fr.ensimag.caweb.models.User.Consummer;
import java.util.List;

/**
 *
 * @author Alexandre Rupp
 */
public class Week {
    private int numSemaine;
    private int annee;
    private List<Consummer> estDisponible;
    private List<Consummer> estIndisponible;
    private List<Consummer> estDePermanence;

    public Week(int numSemaine, int annee, List<Consummer> estDisponible, List<Consummer> estIndisponible, List<Consummer> estDePermanence) {
        this.numSemaine = numSemaine;
        this.annee = annee;
        this.estDisponible = estDisponible;
        this.estIndisponible = estIndisponible;
        this.estDePermanence = estDePermanence;
    }

    public int getNumSemaine() {
        return numSemaine;
    }

    public void setNumSemaine(int numSemaine) {
        this.numSemaine = numSemaine;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public List<Consummer> getEstDisponible() {
        return estDisponible;
    }

    public void setEstDisponible(List<Consummer> estDisponible) {
        this.estDisponible = estDisponible;
    }

    public List<Consummer> getEstIndisponible() {
        return estIndisponible;
    }

    public void setEstIndisponible(List<Consummer> estIndisponible) {
        this.estIndisponible = estIndisponible;
    }

    public List<Consummer> getEstDePermanence() {
        return estDePermanence;
    }

    public void setEstDePermanence(List<Consummer> estDePermanence) {
        this.estDePermanence = estDePermanence;
    }
    
}
