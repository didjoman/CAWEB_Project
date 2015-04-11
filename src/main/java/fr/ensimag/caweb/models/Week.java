/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.models;

import fr.ensimag.caweb.models.User.Consummer;
import fr.ensimag.caweb.models.User.User;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
    private Consummer permanencier1;
    private Consummer permanencier2;
    
    public Week(int numSemaine, int annee) {
        this.numSemaine = numSemaine;
        this.annee = annee;
        estDisponible = new ArrayList<>();
        estIndisponible = new ArrayList<>();
    }
    
    public Week(int numSemaine, int annee, List<Consummer> estDisponible,
            List<Consummer> estIndisponible, Consummer perm1, Consummer perm2) {
        this.numSemaine = numSemaine;
        this.annee = annee;
        this.estDisponible = estDisponible;
        this.estIndisponible = estIndisponible;
        this.permanencier1 = perm1;
        this.permanencier2 = perm2;
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
    
    public Consummer getPermanencier1() {
        return permanencier1;
    }
    
    public void setPermanencier1(Consummer permanencier1) {
        this.permanencier1 = permanencier1;
    }
    
    public Consummer getPermanencier2() {
        return permanencier2;
    }
    
    public void setPermanencier2(Consummer permanencier2) {
        this.permanencier2 = permanencier2;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }
    
    public boolean addDispo(Consummer e) {
        return estDisponible.add(e);
    }
    
    public boolean addIndispo(Consummer e) {
        return estIndisponible.add(e);
    }
    
    public boolean isDispo(String cons){
        for(User u : estDisponible)
            if(u.getPseudo().equals(cons))
                return true;
        return false;
    }
    
    public boolean isUndispo(String cons){
        for(User u : estIndisponible)
            if(u.getPseudo().equals(cons))
                return true;
        return false;
    }
    
    public String getFirstDate(){
        Calendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        gregorianCalendar.setMinimalDaysInFirstWeek(4);
        // Corresponds to the week having the first thirsday of the month (see ISO8601)
        
        gregorianCalendar.set(Calendar.YEAR , this.annee);
        gregorianCalendar.set(Calendar.WEEK_OF_YEAR , this.numSemaine);
        gregorianCalendar.set(Calendar.DAY_OF_WEEK , Calendar.MONDAY);
        
        return "" + String.format("%02d",gregorianCalendar.get(Calendar.DAY_OF_MONTH)) + "/" +
                String.format("%02d",(gregorianCalendar.get(Calendar.MONTH)+1 )) + "/" +
                gregorianCalendar.get(Calendar.YEAR);
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Week other = (Week) obj;
        if (this.numSemaine != other.numSemaine) {
            return false;
        }
        if (this.annee != other.annee) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Week{" + "numSemaine=" + numSemaine + ", annee=" + annee + ", estDisponible=" + estDisponible.toString() + ", estIndisponible=" + estIndisponible.toString() + ", permanencier1=" + permanencier1 + ", permanencier2=" + permanencier2 + "}\n";
    }
    
    
    
}
