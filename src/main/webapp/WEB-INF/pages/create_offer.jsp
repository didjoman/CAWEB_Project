               
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Liste des offres">        
    <jsp:body>
        <div class="col-md-4">
            <form id="create-offer-form" action="offer" method="post" role="form" data-toggle="validator">
                <div class="form-group">
                    <label for="produit" class="control-label">*Produit :</label>
                    <input type="text" class="form-control input-sm"
                           name="produit" id="produit" placeholder="Produit" required/>
                </div>   

                <div class="form-group">
                    <label for="duree" class="control-label">*Durée :</label>
                    <input type="text" class="form-control input-sm"
                           name="duree" id="duree" placeholder="Durée" required/>
                </div>

                <div class="form-group">
                    <label for="quantite" class="control-label">*Quantité  :</label>
                    <input type="number"  class="form-control input-sm"
                           name="qte" id="qte" placeholder="qte" required/>
                    <input type="text"  class="form-control input-sm"
                           name="unite" id="unite" placeholder="unite" required/>
                </div>

                <div class="form-group">
                    <label for="prix" class="control-label">*Prix  :</label>
                    <input type="number"  class="form-control input-sm"
                           name="prix" id="prix" placeholder="prix" required/>                    
                </div>

                <br />
                <input class="submit btn btn-primary" type="submit" value="Créer" id="create" /><br />
            </form>
        </div>
    </jsp:body>
</t:layout>   