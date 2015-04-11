<%-- 
    Document   : contract_read_all
    Created on : 9 avr. 2015, 22:52:43
    Author     : florian
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
    
<t:layout title="Tous les contrats">        
    <jsp:body>
        <div class="page-header">
            <h1>Tous les contrats</h1>
        </div>
        <table class="table table-striped table-hover">
            <thead>
                <tr>
                    <th>Produit</th>
                    <th>Date de signature</th>
                    <th>Date de debut</th>
                    <th>Durée</th>
                    <th>Nombre de lot</th>
                    <th>Quantité d'un lot</th>
                    <th>Prix d'un lot</th>
                    <th>Offreur</th>
                    <th>Demandeur</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${reqs}" var="req">
                    <tr>
                        <td>${req.nonProduitContrat}</td>
                        <td>${req.dateContrat}</td>
                        <td>${req.getDateDebut()}</td>
                        <td>${req.duree}</td>
                        <td>${req.nbLots}</td>
                        <td>${req.quantite.qte}${req.quantite.uniteQte}</td>
                        <td>${req.quantite.prix}</td>
                        <td>${req.offreur.pseudo}</td>
                        <td>${req.demandeur.pseudo}</td>
                    </tr>
                </c:forEach> 
                                    
            </tbody>
        </table>
    </jsp:body>
</t:layout>   