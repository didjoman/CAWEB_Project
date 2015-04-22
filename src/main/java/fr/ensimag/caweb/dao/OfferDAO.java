/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.caweb.dao;

import fr.ensimag.caweb.models.Contract.Contract;
import fr.ensimag.caweb.models.Offer;
import fr.ensimag.caweb.models.Quantity;
import java.util.List;

/**
 *
 * @author laguerrr
 */
public interface OfferDAO {
    public abstract Offer create(Offer offer, Quantity quantity)  throws DAOException ;
    
    public abstract List<Offer> read(String id)  throws DAOException ;
    
    public abstract List<Offer> readAll()  throws DAOException ;

    public abstract Offer update(Offer obj)  throws DAOException ;
    
    public abstract void delete(Offer obj)  throws DAOException ;
}
