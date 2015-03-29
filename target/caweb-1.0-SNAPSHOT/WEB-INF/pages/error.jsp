<%-- 
    Document   : 404
    Created on : 27 mars 2015, 19:58:48
    Author     : Alexandre Rupp
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:layout title="Accueil">        
    <jsp:body>
        <h1>Vous avez tout cassé ...</h1>
        <c:choose>
            <c:when test="${statusCode != 500}">
                <h3>Error Details</h3>
                <strong>Status Code</strong>: ${statusCode} <br>
                <strong>Requested URI</strong>: ${requestUri}
                    
            </c:when>
            <c:otherwise>
                <h3>Exception Details</h3>
                <ul>
                    <li><strong>Servlet Name</strong>: ${servletName}</li>
                    <li><strong>Exception Name</strong>: ${throwable.getClass().getName()}</li>
                    <li><strong>Requested URI</strong>: ${requestUri}</li>
                    <li><strong>Exception Message</strong>: ${throwable.getMessage()}</li>
                </ul>
            </c:otherwise>
        </c:choose>
        
    </jsp:body>
</t:layout>    