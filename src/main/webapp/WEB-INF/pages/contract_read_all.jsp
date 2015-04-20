<%-- 
    Document   : contract_read_all
    Created on : 9 avr. 2015, 22:52:43
    Author     : florian
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                    <th>Date de fin</th>
                    <th>Nombre de lot</th>
                    <th>Quantité d'un lot</th>
                    <th>Prix d'un lot</th>
                    <th>Prix total</th>
                    <c:if test="${status=='CONS'}">
                        <th>Offreur</th>
                        <th>Renouveller</th>
                    </c:if>
                    <c:if test="${status=='PROD'}">
                        <th>Demandeur</th>
                    </c:if>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${reqs}" var="req">
                    <c:choose>
                        <c:when test="${req.getDateFin().getTime()<now.getTime()}">
                            <tr class="warning">
                        </c:when>
                        <c:otherwise>
                            <tr>
                        </c:otherwise>
                    </c:choose>
                    
                        <td>${req.nonProduitContrat}</td>
                        <td>${req.dateContrat}</td>
                        <td>${req.getDateDebut()}</td>
                        <td> <fmt:formatDate pattern="yyyy-MM-dd" value="${req.getDateFin()}"/></td>
                        <td>${req.nbLots}</td>
                        <td>${req.quantite.qte}${req.quantite.uniteQte}</td>
                        <td>${req.quantite.prix}€</td>
                        <td>${req.quantite.prix*req.nbLots}€</td>
                             <c:if test="${status == 'CONS'}">
                                <td>
                                <a href="#" role="button" data-toggle="modal" data-target=".modal-create-${req.idContrat}">
                                    ${req.offreur.pseudo}
                                </a>
                                    <t:info_producer_popup
                                    pseudo="${req.offreur.pseudo}"
                                    idContrat="${req.idContrat}"
                                    tel="${req.offreur.tel}"
                                    mail="${req.offreur.email}"
                                    adresse="${req.offreur.adresse}">   
                                </t:info_producer_popup>
                                </td>
                                            <jsp:useBean id="now" class="java.util.Date" />
                                <td>
                                <c:if test="${req.getDateFin().getTime()<now.getTime()}">
                                <a href="#" role="button" data-toggle="modal" data-target=".modal-renew-${req.idContrat}">
                                    Renouveller
                                </a>
                                <t:renew_contrat_popup
                                    idContrat="${req.idContrat}"
                                    produit="${req.nonProduitContrat}"
                                    dateSign="${req.dateContrat}"
                                    dateDeb="${req.getDateDebut()}"
                                    dateFin="${req.getDateFin()}"
                                    nbLot="${req.nbLots}"
                                    qteLot="${req.quantite.qte}${req.quantite.uniteQte}"
                                    prixLot="${req.quantite.prix}"
                                    prixSem="${req.quantite.prix*req.nbLots}"
                                    offreur="${req.offreur.pseudo}">
                                </t:renew_contrat_popup>                                    
                                </c:if>
                                </td>
                             </c:if>
                               <c:if test="${status=='PROD'}">
                                 <td>
                                <a href="#" role="button" data-toggle="modal" data-target=".modal-create-${req.idContrat}">
                                    ${req.demandeur.pseudo}
                                </a>
                                <t:info_producer_popup
                                    pseudo="${req.demandeur.pseudo}"
                                    idContrat="${req.idContrat}"
                                    tel="${req.demandeur.tel}"
                                    mail="${req.demandeur.email}"
                                    adresse="${req.demandeur.adresse}">
                                </t:info_producer_popup>
                                </td>
                             </c:if>
                    </tr>
                </c:forEach> 
                    
            </tbody>
        </table>
    </jsp:body>
</t:layout>   