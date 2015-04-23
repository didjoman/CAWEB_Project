<%-- 
    Document   : request_get
    Created on : 27 mars 2015, 22:04:21
    Author     : Alexandre Rupp
--%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
    
    
<c:if test="${sessionScope.status == 'PROD' && req.refuse != true}">
    <c:set var="frag">
        <div class="panel panel-default hidden" id="validation-panel">
            <div class="panel-heading">
                <h3 class="panel-title">Valider ou refuser le contrat</h3>
            </div>
            <div class="panel-body">
                Souhaitez vous accepter le contrat ? 
                &nbsp; <span class="clickable glyphicon glyphicon-ok" aria-hidden="true" data-toggle="modal" data-target=".modal-accept-request"></span>
                &nbsp; <span class="clickable glyphicon glyphicon-remove" aria-hidden="true" data-toggle="modal" data-target=".modal-refuse-request"></span>
                    
                <!-- [POP-UP] Formulaire d'acceptation du contrat -->
                <t:popup title="Accepter le contrat" classes="modal-accept-request" type="modal-lg">
                    <jsp:attribute name="footer">
                        <input type="submit" form="contract-validation-form" class="btn btn-primary" value="Valider" />
                        <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    </jsp:attribute>
                    <jsp:body>
                        <form id="contract-validation-form" action="request" method="post" role="form" data-toggle="validator">
                            <label for="datepicker" class="control-label" > Veuillez sélectionner une date de début de livraison :</label>
                            <div class="form-group input-group">
                                <span class="input-group-addon" id="shipping-date">
                                    <span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
                                </span>
                                <input type="text" class="form-control" id="datepicker" name="begin" placeholder="date" aria-describedby="shipping-date">
                                <input type="hidden" name="contractId" value="${req.idContrat}">
                            </div>
                        </form>
                    </jsp:body>
                </t:popup> 
                    
                <!-- [POP-UP] Formulaire de refus du contrat -->
                <t:popup title="Refuser le contrat" classes="modal-refuse-request" type="modal-sm">
                    <jsp:attribute name="footer">
                        <input type="submit" form="contract-deletion-form" class="btn btn-danger" value="Refuser">
                        <button type="submit" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    </jsp:attribute>
                    <jsp:body>
                        <form id="contract-deletion-form" action="request?action=delete" method="post" role="form" data-toggle="validator">
                            <div class="form-group input-group">
                                <label>Etes-vous sûr de vouloir refuser le contrat ?</label>
                                <input type="hidden" name="contractId" value="${req.idContrat}">
                            </div>
                        </form>
                            
                    </jsp:body>
                </t:popup>    
            </div>
        </div>
    </c:set>
</c:if>
    
    
<t:request_read>
    <jsp:attribute name="validationPanel">
        ${frag}
    </jsp:attribute>
</t:request_read>