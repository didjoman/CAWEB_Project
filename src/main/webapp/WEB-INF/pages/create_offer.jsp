    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
    
<t:layout title="Liste des offres">        
    <jsp:body>
        <div class="page-header">
            <h1>Créer une offre</h1>
        </div>
            
        <div class="col-md-4">
            <form id="create-offer-form" action="offer" method="post" role="form" data-toggle="validator">
                <div class="form-group">
                    <label for="produit" class="control-label">*Produit :</label>
                    <input type="text" class="form-control input-sm"
                           name="produit" id="produit" placeholder="Produit" required/>
                </div>   
                    
                <div class="form-group">
                    <label for="duree" class="control-label">*Durée (en jours):</label>
                    <input type="number" min="0" class="form-control input-sm "
                           name="duree" id="duree" placeholder="Durée"
                           data-error="La durée doit être un nombre entier 
                           supérieur à 0, correspondant au nombre de jours." required/>
                </div>
                    
                    
                <!-- ATTENTION : j'ai modifié les noms qte et unite pour être cohérent, ça ne va plus marcher avec la servlet ! -->
                <div class="form-group">
                    <label for="quantite" class="control-label">*Quantité  :</label>
                    <input type="number" step="any" min="0" class="form-control input-sm positif"
                           name="qte0" id="qte" placeholder="qte"  
                           data-error="La quantité doit 
                           être un nombre réel, supérieur à 0.
                           (Attention, utilisez un point et non une virgule)" required/>
                    <input type="text"  class="form-control input-sm"
                           name="unite0" id="unite" placeholder="unite" required/>
                    <label for="prix"  class="control-label">*Prix  (en €):</label>
                    <input type="number" step="any" min="0" class="form-control input-sm positif"
                           name="prix0" id="prix" placeholder="prix" 
                           data-error="Le prix doit 
                           être un nombre réel, supérieur à 0. 
                           (Attention, utilisez un point et non une virgule)" required/>
                    <div class="help-block with-errors"></div>
                </div>
                
                <a href="#" id="add_qte">ajouter une quantité</a>
                    
                <br /><br />
                <input type="hidden" name="nbQte" value="1" >
                <input class="submit btn btn-primary" type="submit" value="Créer" id="create" /><br />
            </form>
                
                
            <div class="form-group new-qte" style="display: none">
                <label for="quantite" class="control-label">*Quantité  :</label> <span class="rm-btn clickable glyphicon glyphicon-remove"></span>
                <input type="number" step="any" min="0" class="form-control input-sm positif"
                       name="qte" id="qte" placeholder="qte"  
                       data-error="La quantité doit 
                       être un nombre réel, supérieur à 0.
                       (Attention, utilisez un point et non une virgule)" required/>
                <input type="text"  class="form-control input-sm"
                       name="unite" id="unite" placeholder="unite" required/>
                <label for="prix"  class="control-label">*Prix  (en €):</label>
                <input type="number" step="any" min="0" class="form-control input-sm positif"
                       name="prix" id="prix" placeholder="prix" 
                       data-error="Le prix doit 
                       être un nombre réel, supérieur à 0. 
                       (Attention, utilisez un point et non une virgule)" required/>
                <div class="help-block with-errors"></div>
            </div>
        </div>
    </jsp:body>
</t:layout>   