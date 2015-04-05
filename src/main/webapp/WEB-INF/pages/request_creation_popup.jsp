<%-- 
    Document   : request_creation_popup
    Created on : 4 avr. 2015, 22:02:54
    Author     : Alexandre Rupp
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:popup title="Créer une demande" classes="modal-create-request" type="modal-lg">
    <jsp:attribute name="footer">
        <button type="button" class="btn btn-primary">Emettre la demande</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
    </jsp:attribute>
    <jsp:body>
        <c:set var="producer" value="3"></c:set>
        <jsp:include page="request_creation_form.jsp"></jsp:include>
    </jsp:body>
</t:popup> 
