<%-- 
    Document   : request_creation_popup
    Created on : 4 avr. 2015, 22:02:54
    Author     : Alexandre Rupp
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@tag description="info producer popup" pageEncoding="UTF-8"%>

<%@attribute name="pseudo" required="true"%>
<%@attribute name="idContrat" required="true"%>
<%@attribute name="tel" required="true"%>
<%@attribute name="mail" required="true"%>
<%@attribute name="adresse" required="true"%>

<t:popup title="Informations utisateurs" classes="modal-create-${idContrat}" type="modal-lg">
    <jsp:attribute name="footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Fermer</button>
    </jsp:attribute>
    <jsp:body>
        <div><label>Pseudo :</label> ${pseudo}</div>
        <div><label>Tel :</label> ${tel}</div>
        <div><label>Email :</label> ${mail}</div>
        <div><label>Adresse :</label>${adresse}</div>
    </jsp:body>
</t:popup> 
