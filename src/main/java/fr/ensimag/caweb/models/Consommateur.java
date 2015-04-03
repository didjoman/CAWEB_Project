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
public class Consommateur extends User {

    public Consommateur(String pseudo, String motDePasse, String email, String adresse, String nom, String prenom, String tel) {
        super(pseudo, motDePasse, email, adresse, nom, prenom, tel);
    }

    public Consommateur(User u) {
        super(u);
    }
    
    @Override
    public UserTypes getRole() {
        return UserTypes.CONS;
    }
}
