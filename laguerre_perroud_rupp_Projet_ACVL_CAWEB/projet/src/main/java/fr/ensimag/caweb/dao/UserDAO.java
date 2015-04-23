/*
* Usero change this license header, choose License Headers in Project Properties.
* Usero change this template file, choose Userools | Useremplates
* and open the template in the editor.
*/
package fr.ensimag.caweb.dao;

import fr.ensimag.caweb.models.User.User;
import fr.ensimag.caweb.models.User.UserStatus;
import java.util.List;

/**
 *
 * @author Alexandre Rupp
 */
public interface UserDAO{
    public abstract User create(User obj)  throws DAOException ;
    
    public abstract User read(String pseudo)  throws DAOException ;
    
    public abstract List<User> readAll()  throws DAOException ;
    
    public List<User> readAllWithStatus(UserStatus status) throws DAOException;

    public abstract User update(User obj)  throws DAOException ;
    
    public abstract void delete(User obj)  throws DAOException ;
    
    public User read(String pseudo, String pwd) throws DAOException;
}
