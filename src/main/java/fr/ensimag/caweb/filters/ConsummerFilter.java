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
@WebFilter(filterName = "ConsummerFilter", urlPatterns = {"/request"})
public class ConsummerFilter extends StatusFilter {
    
    @Override
    public UserStatus getStatus() {
        return UserStatus.CONS;
    }
    
    
    
}
