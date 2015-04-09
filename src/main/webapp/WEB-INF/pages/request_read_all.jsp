<%-- 
    Document   : contracts_asks_get
    Created on : 27 mars 2015, 19:35:24
    Author     : Alexandre Rupp
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
    
<t:layout title="Demandes de contrat">        
    <jsp:body>
        <div class="page-header">
            <h1>Demandes de contrats</h1>
        </div>
        <table class="table table-striped table-hover">
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Consommateur</th>
                    <th>Voir</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${reqs}" var="req">
                    <tr>
                        <td>${req.dateContrat}</td>
                        <td>${req.demandeur.pseudo}</td>
                        <td><a class="btn btn-primary" href="request?id=${req.idContrat}" role="button">Voir</a></td>
                    </tr>
                </c:forEach> 
                                    
            </tbody>
        </table>
    </jsp:body>
</t:layout>   