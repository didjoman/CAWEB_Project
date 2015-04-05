/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.dao;

import fr.ensimag.caweb.dao.DAOException;
import fr.ensimag.caweb.models.Contract.Contract;
import java.util.List;

/**
 *
 * @author Alexandre Rupp
 */
public interface ContractDAO {
    public abstract Contract create(Contract obj)  throws DAOException ;
    
    public abstract Contract read(int id)  throws DAOException ;
    
    public abstract List<Contract> readAll()  throws DAOException ;

    public abstract Contract update(Contract obj)  throws DAOException ;
    
    public abstract void delete(Contract obj)  throws DAOException ;
}
