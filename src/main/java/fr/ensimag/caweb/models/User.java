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
public abstract class User {
    private String pseudo;
    private String motDePasse;
    private String email;
    private String adresse;
    private String nom;
    private String prenom;
    private String tel;
    
    public User(String pseudo, String motDePasse, String email, String adresse, String nom, String prenom, String tel) {
        this.pseudo = pseudo;
        this.motDePasse = motDePasse;
        this.email = email;
        this.adresse = adresse;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
    }
    
    public User(User u){
        this.pseudo = u.getPseudo();
        this.motDePasse = u.getMotDePasse();
        this.email = u.getEmail();
        this.adresse = u.getAdresse();
        this.nom = u.getNom();
        this.prenom = u.getPrenom();
        this.tel = u.tel;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "User{" + "pseudo=" + pseudo + ", motDePasse=" + motDePasse + ", email=" + email + ", adresse=" + adresse + ", nom=" + nom + ", prenom=" + prenom + ", tel=" + tel + '}';
    }
    
    public abstract UserTypes getRole();
}