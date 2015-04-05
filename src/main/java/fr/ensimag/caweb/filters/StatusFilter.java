/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package fr.ensimag.caweb.filters;

import fr.ensimag.caweb.models.User.UserStatus;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Alexandre Rupp
 */
public abstract class StatusFilter implements Filter{
    
    /**
     * Init method for this filter
     */
    @Override
    public void init(FilterConfig filterConfig) {
    }
    
    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        
        if(!isLogged(session)){
            // If the user is not logged we redirect him to the index + error msg:
            req.setAttribute("error", "Vous devez être identifié pour accéder "
                    + "à la ressource "+ req.getRequestURI());
            
            RequestDispatcher view = request.getRequestDispatcher("/error");
            view.forward(request, response);
            return;
        }
        
        if(!isLoggedWithCorrectStatus(session)){
            // If the user is logged but is not a <status> :
            req.setAttribute("error", "Vous n'avez pas le droit d'accéder "
                    + "à la ressource "+ req.getRequestURI());
            RequestDispatcher view = request.getRequestDispatcher("/error");
            view.forward(request, response);
            return;
        }
        
        // If the user is logged && is a <status> : OK
        chain.doFilter(request, response);
    }
    
    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy() {
    }
    
    private boolean isLogged(HttpSession session){
        return session != null && session.getAttribute("status") != null;
    }
    
    private boolean isLoggedWithCorrectStatus(HttpSession session){
        return isLogged(session) &&
                session.getAttribute("status").equals(getStatus().toString());
    }
    
    
    public abstract UserStatus getStatus();
}
