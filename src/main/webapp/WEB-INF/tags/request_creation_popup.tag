<%-- 
    Document   : request_creation_popup
    Created on : 4 avr. 2015, 22:02:54
    Author     : Alexandre Rupp
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@tag description="request creation popup" pageEncoding="UTF-8"%>

<%@attribute name="producer" required="true"%>
<%@attribute name="product" required="true"%>
<%@attribute name="duration" required="true"%>
<%@attribute name="quantities" type="java.util.List" required="true"%>
<%@attribute name="prixUnite" required="true"%>
<%@attribute name="uniteQte" required="true"%>
<%@attribute name="action" required="true"%>
<%@attribute name="id" required="true"%> <!-- 1, 2 ... -->

<t:popup title="Créer une demande" classes="modal-create-request${id}" type="modal-lg">
    <jsp:attribute name="footer">
        <input form="request-creation-form${id}" class="submit btn btn-primary" type="submit" value="Emettre la demande" id="submit" />
        <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
    </jsp:attribute>
    <jsp:body>
        <t:request_creation_form
            hasSubmitBtn="false"
            producer="${producer}"
            product="${product}"
            duration="${duration}"
            quantities="${quantities}"
            prixUnite="${prixUnite}"
            uniteQte="${uniteQte}"
            action="${create}"
            id="${id}">
        </t:request_creation_form>
    </jsp:body>
</t:popup> 
