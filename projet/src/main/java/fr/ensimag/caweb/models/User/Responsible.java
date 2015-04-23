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
public class Responsible extends User{

    public Responsible(String pseudo, String motDePasse, String email, String adresse, String nom, String prenom, String tel) {
        super(pseudo, motDePasse, email, adresse, nom, prenom, tel);
    }

    public Responsible(User u) {
        super(u);
    }
    
   @Override
    public UserStatus getRole() {
        return UserStatus.RESP;
    }
    
}
