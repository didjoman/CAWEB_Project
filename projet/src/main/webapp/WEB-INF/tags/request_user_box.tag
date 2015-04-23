<%-- 
    Document   : request_user_box
    Created on : 18 avr. 2015, 20:48:58
    Author     : Alexandre Rupp
--%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="Print a description box of a user" pageEncoding="UTF-8"%>
    
<%@attribute name="user" required="true" type="fr.ensimag.caweb.models.User.User"%>
<%@attribute name="h3" required="true"%>
    
<div class="col-sm-6 col-md-4">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">${h3}</h3>
        </div>
        <div class="panel-body">
            <ul id="consumer-info">
                <li>
                    <strong>pseudo:</strong> ${user.pseudo}
                </li>
                <li>
                    <strong>tel:</strong> ${user.tel}
                </li>
                <li>
                    <strong>email:</strong> <a href="mailto:#">${user.email}</a>
                </li>
                <li>
                    <strong>adresse:</strong>${user.adresse}
                </li>
            </ul>
        </div>
    </div>
</div>