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
            <h1>Demande de contrat n°${id} <small>(validé)</small></h1>
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
                                <strong>pseudo:</strong> didjo
                            </li>
                            <li>
                                <strong>tel:</strong> 06-00-00-00-00
                            </li>
                            <li>
                                <strong>email:</strong> <a href="mailto:#">didjo@gmail.com</a>
                            </li>
                            <li>
                                <strong>adresse:</strong> 36 rue de là bas
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
                            <li><strong>Produit:</strong> Carottes</li>
                            <li><strong>Quantité:</strong> 50 <em>unités</em></li>
                            <li><strong>Prix:</strong> 34.99€</li>
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
                <div class="modal fade modal-accept-request" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="myModalLabel">Accepter le contrat</h4>
                            </div>
                            <div class="modal-body">
                                Veuillez sélectionner une date de début de livraison :
                                    
                                <div class="input-group">
                                    <span class="input-group-addon" id="shipping-date">
                                        <span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
                                    </span>
                                    <input type="text" class="form-control" id="datepicker" placeholder="date" aria-describedby="shipping-date">
                                </div>
                                    
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-primary">Valider</button>
                                <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                            </div>
                        </div>
                    </div>
                </div>
                    
                <!-- [POP-UP] Formulaire de refus du contrat -->
                <div class="modal fade modal-refuse-request" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-sm">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="myModalLabel">Refuser le contrat</h4>
                            </div>
                            <div class="modal-body">
                                Etes-vous sûr de vouloir refuser le contrat ?
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-danger">Refuser</button>
                                <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                            </div>
                        </div>
                    </div>
                </div>
                    
            </div>
        </div>
            
            
    </jsp:body>
</t:layout>   