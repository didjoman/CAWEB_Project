<%-- 
    Document   : request_creation_popup
    Created on : 4 avr. 2015, 22:02:54
    Author     : Alexandre Rupp
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@tag description="info producer popup" pageEncoding="UTF-8"%>

<%@attribute name="idContrat" required="true"%>
<%@attribute name="produit" required="true"%>
<%@attribute name="dateSign" required="true"%>
<%@attribute name="dateDeb" required="true"%>
<%@attribute name="dateFin" required="true"%>
<%@attribute name="nbLot" required="true"%>
<%@attribute name="qteLot" required="true"%>
<%@attribute name="prixLot" required="true"%>
<%@attribute name="prixSem" required="true"%>
<%@attribute name="offreur" required="true"%>

<t:popup title="Souhaitez vous renouveller ce contrat?" classes="modal-renew-${idContrat}" type="modal-lg">
    <jsp:attribute name="footer">
        <input form="renew-form" class="submit btn btn-primary" type="submit" value="Oui" id="submit" />
        <button type="button" class="btn btn-primary" data-dismiss="modal">Non</button>
    </jsp:attribute>
    <jsp:body>
        <div><label>Produit :</label> ${produit}</div>
        <div><label>Date de signature :</label> ${dateSign}</div>
        <div><label>Date de debut :</label> ${dateDeb}</div>
        <div><label>Date de fin :</label><fmt:formatDate pattern="yyyy-MM-dd" value="${dateFin}" /></div>
        <div><label>Nombre de lot :</label>${nbLot}</div>
        <div><label>Quantité d'un lot :</label>${qteLot}</div>
        <div><label>Prix d'un lot :</label>${prixLot}€</div>
        <div><label>Prix par semaine :</label>${prixSem}€</div>
        <div><label>Offreur :</label>${offreur}</div>
        <form class="hidden" role="form" id="renew-form" data-toggle="validator" action="contrat" method="post">    
        <input type="hidden" name="idContrat" id="idContrat" value="${idContrat}" readonly>
        </form>
    </jsp:body>
</t:popup> 
