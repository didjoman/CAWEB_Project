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
        
        <!-- Formulaire de recherche -->
        <div>Rechercher un produit en particulier :
            <div class="row">
                <div class="col-xs-12 col-sm-8 col-md-4 col-lg-4">
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="Search for...">
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="button">Go!</button>
                        </span>
                    </div>
                </div>
            </div>
        </div>
        <hr />
        
        <!-- Affichage des offres : -->
        <h3>Produits proposés</h3>
        <div class="row">
            <div class="offre col-sm-4 col-md-3">
                  <c:forEach items="${offers}" var="offer">
                <div class="thumbnail"> 
                    
                    
                   
                    <h3>${offer.nomProduit}</h3>
                    <hr />
                    <ul>
                        <li><strong>Producteur: </strong> M. Machin</li>
                        <li><strong>duréee contrat: </strong> ${offer.duree}</li>
                        <li>
                            <strong>Qtés proposées:</strong><br />
                            <table class="table">
                                <tr class="active">
                                    <th>qté</th>
                                    <th>prix</th>
                                </tr>
                                <tr>
                                    <td>5 kg</td>
                                    <td>20€</td>
                                </tr>
                                <tr>
                                    <td>10 kg</td>
                                    <td>30€</td>
                                </tr>
                            </table>
                        </li>
                    </ul>
                    <p>
                        <c:if test="${status == 'CONS'}">
                            <a href="#" class="btn btn-primary" role="button" data-toggle="modal" data-target=".modal-create-request">
                                Emettre une demande
                            </a>
                            <t:request_creation_popup 
                                producer="${producer}"
                                product="${offer.nomProduit}"
                                duration="${offer.duree}"
                                quantities="${quantities}">
                            </t:request_creation_popup>
                        </c:if>
                    </p>
                     
                </div>
                         </c:forEach>
            </div>
            
            
            
        </div>
        
        <hr />
        <div class="btn-group" role="group" aria-label="page numbers">
            <button  type="button" class="btn btn-default" disabled="disabled"> Pages:</button>
            <button type="button" class="active btn btn-default"><a href="#">1</a></button>
            <button type="button" class="btn btn-default"><a href="#">2</a></button>
            <button type="button" class="btn btn-default"><a href="#">3</a></button>
        </div>
    </jsp:body>
</t:layout>   

