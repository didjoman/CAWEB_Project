/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.filters;

import fr.ensimag.caweb.models.UserTypes;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Alexandre Rupp
 */
@WebFilter(filterName = "ConsommateurFilter", urlPatterns = {"/request"})
public class ConsommateurFilter extends StatusFilter {
    
    @Override
    public UserTypes getStatus() {
        return UserTypes.CONS;
    }
    
    
    
}
