/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.dao;

import fr.ensimag.caweb.dao.DAOException;
import fr.ensimag.caweb.models.Contract.Contract;
import fr.ensimag.caweb.models.User.Consummer;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Alexandre Rupp
 */
public interface ContractDAO {
    public abstract Contract create(Contract obj)  throws DAOException ;
    
    public abstract Contract read(int id)  throws DAOException ;
    
    public abstract List<Contract> readAll(String userPseudo)  throws DAOException ;
    
    public List<Contract> readAllContractRequests(String offreurPseudo) throws DAOException;
    
    public List<Contract> readAllValidatedContracts(String userPseudo) throws DAOException;
    
    public List<Contract> readAllContractsToRenew(String userPseudo) throws DAOException;

    public void updateToReNew(int id) throws DAOException;
    
    public void updateValidate(int id, Date dateCont, Date begin) throws DAOException;
    
    public abstract void delete(Contract obj)  throws DAOException ;
}
