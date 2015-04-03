/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.models;

import fr.ensimag.caweb.models.User;

/**
 *
 * @author Alexandre Rupp
 */
public class Producteur extends User{

    public Producteur(String pseudo, String motDePasse, String email, String adresse, String nom, String prenom, String tel) {
        super(pseudo, motDePasse, email, adresse, nom, prenom, tel);
    }

    public Producteur(User u) {
        super(u);
    }
    
    @Override
    public UserTypes getRole() {
        return UserTypes.PROD;
    }
    
}
