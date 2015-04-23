<%-- 
    Document   : offres_get
    Created on : 4 avr. 2015, 19:00:29
    Author     : Alexandre Rupp
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Liste des offres">        
    <jsp:body>
        <div class="page-header">
            <h1>Liste des offres</h1>
        </div>
        
        <!-- Affichage des offres : -->
        <h3>${offer.nomProduit}</h3>
        <div class="row">
            <c:forEach items="${offers}" var="offer">
                
                <div class="offre col-sm-4 col-md-3">
                    <div class="thumbnail"> 
                        
                        
                        
                        <h3>${offer.nomProduit}</h3>
                        <hr />
                        <ul>
                            <li><strong>Producteur: </strong>${offer.createur}</li>
                            <li><strong>duréee contrat: </strong> ${offer.duree}</li>
                            <li>
                                <strong>Qtés proposées:</strong><br />
                                <table class="table">
                                    <tr class="active">
                                        <th>qté</th>
                                        <th>prix</th>
                                    </tr>
                                    <c:forEach items="${offer.quantities}" var="quantity">
                                        <tr>
                                            <td>${quantity.qte} ${quantity.uniteQte}</td>
                                            <td>${quantity.prix} €</td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </li>
                        </ul>
                        <p>
                            <c:if test="${status == 'CONS'}">
                                <a href="#" class="btn btn-primary" role="button" data-toggle="modal" data-target=".modal-create-request${offer.id}">
                                    Emettre une demande
                                </a>
                                <t:request_creation_popup 
                                    producer="${offer.createur}"
                                    product="${offer.nomProduit}"
                                    duration="${offer.duree}"
                                    quantities="${offer.quantities}"
                                    prixUnite="${offer.quantities.get(0).prix}"
                                    uniteQte="${offer.quantities.get(0).uniteQte}"
                                    id="${offer.id}"
                                    action="create">                                  
                                </t:request_creation_popup>
                            </c:if>
                        </p>
                        
                    </div>
                </div>
            </c:forEach>
        </div>
        
    </jsp:body>
</t:layout>   

