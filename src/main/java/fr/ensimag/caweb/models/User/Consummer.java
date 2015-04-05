/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.caweb.models.User;

/**
 *
 * @author Alexandre Rupp
 */
public class Consummer extends User {

    public Consummer(String pseudo, String motDePasse, String email, String adresse, String nom, String prenom, String tel) {
        super(pseudo, motDePasse, email, adresse, nom, prenom, tel);
    }

    public Consummer(User u) {
        super(u);
    }
    
    @Override
    public UserStatus getRole() {
        return UserStatus.CONS;
    }
}
