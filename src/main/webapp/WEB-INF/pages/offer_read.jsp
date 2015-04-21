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
                                            <td>${quantity.prix}</td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </li>
                        </ul>
                        <p>
                            <c:if test="${status == 'CONS'}">
                                <a href="#" class="btn btn-primary" role="button" data-toggle="modal" data-target=".modal-create-request">
                                    Emettre une demande
                                </a>
                                <t:request_creation_popup 
                                    producer="${offer.createur}"
                                    product="${offer.nomProduit}"
                                    duration="${offer.duree}"
                                    quantities="${offer.quantities}"
                                    prixUnite="${offer.quantities.get(0).prix}"
                                    uniteQte="${offer.quantities.get(0).uniteQte}"
                                    action="create">                                  
                                </t:request_creation_popup>
                            </c:if>
                        </p>

                    </div>
                </div>
            </c:forEach>



        </div>

        <hr />
        <nav>
            <ul class="pagination">
                <li>
                    <a href="#" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <li><a href="#">1</a></li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">4</a></li>
                <li><a href="#">5</a></li>
                <li>
                    <a href="#" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
    </jsp:body>
</t:layout>   

