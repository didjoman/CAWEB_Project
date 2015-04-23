    
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
                    <input type="text" class="form-control input-sm"
                           name="duree" id="duree" placeholder="Durée" required/>
                </div>


                <!-- ATTENTION : j'ai modifié les noms qte et unite pour être cohérent, ça ne va plus marcher avec la servlet ! -->
                <div class="form-group">
                    <label for="quantite" class="control-label">*Quantité  :</label>
                    <input type="number"  class="form-control input-sm"
                           name="qte0" id="qte0" placeholder="qte" required/>
                    <input type="text"  class="form-control input-sm"
                           name="unite0" id="unite0" placeholder="unite" required/>
                    <label for="prix" class="control-label">*Prix  (en €):</label>
                    <input type="number"  class="form-control input-sm"
                           name="prix0" id="prix" placeholder="prix" required/>
                </div>

                <a href="#" id="add_qte">ajouter une quantité</a>

                <br /><br />
                <input type="hidden" name="nbQte" value="1" >
                <input class="submit btn btn-primary" type="submit" value="Créer" id="create" /><br />
            </form>


            <div class="form-group new-qte" style="display: none">
                <label for="quantite" class="control-label">*Quantité  :</label> <span class="rm-btn clickable glyphicon glyphicon-remove"></span>
                <input type="number"  class="form-control input-sm"
                       name="qte" id="qte" placeholder="qte" required/>
                <input type="text"  class="form-control input-sm"
                       name="unite" id="unite" placeholder="unite" required/>
                <label for="prix" class="control-label">*Prix  (en €):</label>
                <input type="number"  class="form-control input-sm"
                       name="prix" id="prix" placeholder="prix" required/>
            </div>
        </div>
    </jsp:body>
</t:layout>   