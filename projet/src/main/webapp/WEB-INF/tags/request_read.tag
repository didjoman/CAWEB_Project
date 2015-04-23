<%-- 
    Document   : request_read
    Created on : 18 avr. 2015, 20:56:48
    Author     : Alexandre Rupp
--%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="Read the content of a request" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
    
<%@attribute name="validationPanel" fragment="true"%>


<t:layout title="Demandes de contrat">        
    <jsp:body>
        
        <div class="page-header">
            <h1>
                Demande de contrat n°${id} 
                <small>
                    <c:choose>
                        <c:when test="${req.getState() == 'CONTRACTREFUSED'}">
                            (supprimée ...)
                        </c:when>
                        <c:when test="${req.getState() == 'CONTRACTINREQUEST'}">
                            (à valider)
                        </c:when>
                        <c:when test="${req.getState() == 'CONTRACTINRENEW'}">
                            (à renouveler)
                        </c:when>
                     </c:choose>
                </small>
            </h1>
        </div>
            
        <ol class="breadcrumb">
            <li><a href="${pageContext.request.contextPath}/request">Demandes de contrats</a></li>
            <li class="active">Demande n°${id}</li>
        </ol>
            
            
        <div class="row">
            
            <!-- [PANNEL] Détails consommateur -->
            <c:choose>
                <c:when test="${sessionScope.status == 'PROD'}">
                    <t:request_user_box user="${req.demandeur}" h3="Consommateur" />
                </c:when>
                <c:when test="${sessionScope.status == 'CONS'}">
                    <t:request_user_box user="${req.offreur}" h3="Producteur" />
                </c:when>
            </c:choose>
    
            <!-- [PANNEL] Détails consommateur du contrat -->
            <div class="col-sm-6 col-md-8">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Informations sur le contrat</h3>
                    </div>
                    <div id="contract-info" class="panel-body">
                        <ul>
                            <li><strong>Produit:</strong> ${req.nomProduitContrat}</li>
                            <li><strong>Quantité:</strong> ${req.quantite.qte} <em>${req.quantite.uniteQte}</em></li>
                            <li><strong>Prix:</strong> 3${req.quantite.prix}€</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
            
        <!-- [PANNEL] Valider ou refuser le contrat ?-->
        <jsp:invoke fragment="validationPanel"/>
            
    </jsp:body>
</t:layout>   