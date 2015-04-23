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
public class UserFactory {
    public static User createUser(String pseudo, String motDePasse, String email,
            String adresse, String nom, String prenom, String tel, String status){
        if(status.equals(UserStatus.PROD.toString()))
            return new Producer(pseudo, motDePasse, email, adresse, nom, prenom, tel);
        else if(status.equals(UserStatus.CONS.toString()))
            return new Consummer(pseudo, motDePasse, email, adresse, nom, prenom, tel);
        else if(status.equals(UserStatus.RESP.toString()))
            return new Responsible(pseudo, motDePasse, email, adresse, nom, prenom, tel);
        
        return null; // Never happens
    }
    
    public static User createUser(String pseudo, String motDePasse, String email,
            String adresse, String nom, String prenom, String tel, UserStatus status){
        return UserFactory.createUser(pseudo, motDePasse, email, adresse, nom, prenom, tel, status.toString());
    }
}
