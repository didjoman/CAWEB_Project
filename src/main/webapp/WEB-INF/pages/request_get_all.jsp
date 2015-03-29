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
                    <th>Etat</th>
                    <th>Voir</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>10-11-1992</td>
                    <td>M. Machin</td>
                    <td><span class="glyphicon glyphicon-minus" aria-hidden="true"></span></td>
                    <td><a class="btn btn-primary" href="request?id=3" role="button">Voir</a></td>
                </tr>
                <tr>
                    <td>10-11-1992</td>
                    <td>M. Machin</td>
                    <td><span class="success glyphicon glyphicon-ok" aria-hidden="true"></span></td>
                    <td><a class="btn btn-primary" href="request?id=3" role="button">Voir</a></td>
                </tr>
                <tr>
                    <td>10-11-1992</td>
                    <td>M. Machin</td>
                    <td><span class="success glyphicon glyphicon-remove" aria-hidden="true"></span></td>
                    <td><a class="btn btn-primary" href="request?id=3" role="button">Voir</a></td>
                </tr>
            </tbody>
        </table>
    </jsp:body>
</t:layout>   