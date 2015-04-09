<%-- 
    Document   : request_get
    Created on : 27 mars 2015, 22:04:21
    Author     : Alexandre Rupp
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Demandes de contrat">        
    <jsp:body>
        
        
        <div class="page-header">
            <h1>Demande de contrat n°${id} <small>(à valider)</small></h1>
        </div>
            
        <ol class="breadcrumb">
            <li><a href="${pageContext.request.contextPath}/request">Demandes de contrats</a></li>
            <li class="active">Demande n°${id}</li>
        </ol>
            
            
        <div class="row">
            
            <!-- [PANNEL] Détails consommateur -->
            <div class="col-sm-6 col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Consommateur</h3>
                    </div>
                    <div class="panel-body">
                        <ul id="consumer-info">
                            <li>
                                <strong>pseudo:</strong> ${req.demandeur.pseudo}
                            </li>
                            <li>
                                <strong>tel:</strong> ${req.demandeur.tel}
                            </li>
                            <li>
                                <strong>email:</strong> <a href="mailto:#">${req.demandeur.email}</a>
                            </li>
                            <li>
                                <strong>adresse:</strong>${req.demandeur.adresse}
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            
            <!-- [PANNEL] Détails consommateurdu contrat -->
            <div class="col-sm-6 col-md-8">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Informations sur le contrat</h3>
                    </div>
                    <div id="contract-info" class="panel-body">
                        <ul>
                            <li><strong>Produit:</strong> ${req.nonProduitContrat}</li>
                            <li><strong>Quantité:</strong> ${req.quantite.qte} <em>${req.quantite.uniteQte}</em></li>
                            <li><strong>Prix:</strong> 3${req.quantite.prix}€</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- [PANNEL] Valider ou refuser le contrat ?-->
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
                        <button type="button" class="btn btn-primary">Valider</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    </jsp:attribute>
                    <jsp:body>
                        Veuillez sélectionner une date de début de livraison :
                        
                        <div class="input-group">
                            <span class="input-group-addon" id="shipping-date">
                                <span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
                            </span>
                            <input type="text" class="form-control" id="datepicker" placeholder="date" aria-describedby="shipping-date">
                        </div>
                    </jsp:body>
                </t:popup> 
                    
                <!-- [POP-UP] Formulaire de refus du contrat -->
                <t:popup title="Refuser le contrat" classes="modal-refuse-request" type="modal-sm">
                    <jsp:attribute name="footer">
                        <button type="button" class="btn btn-danger">Refuser</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    </jsp:attribute>
                    <jsp:body>
                        Etes-vous sûr de vouloir refuser le contrat ?
                    </jsp:body>
                </t:popup> 
                    
            </div>
        </div>
            
            
    </jsp:body>
</t:layout>   