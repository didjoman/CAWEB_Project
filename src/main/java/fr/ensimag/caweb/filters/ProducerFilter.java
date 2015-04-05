/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.filters;

import fr.ensimag.caweb.models.User.UserStatus;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 *
 * @author Alexandre Rupp
 */
@WebFilter(filterName = "ProducerFilter", urlPatterns = {"/offer"})
public class ProducerFilter extends StatusFilter {
    
    @Override
    public UserStatus getStatus() {
        return UserStatus.PROD;
    }
    
}
