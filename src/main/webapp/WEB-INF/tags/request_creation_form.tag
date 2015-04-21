<%-- 
    Document   : request_creation_form
    Created on : 4 avr. 2015, 21:25:14
    Author     : Alexandre Rupp
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@tag description="request creation form" pageEncoding="UTF-8"%>

<%@attribute name="hasSubmitBtn"%> <!-- true/false -->
<%@attribute name="producer" required="true"%>
<%@attribute name="product" required="true"%>
<%@attribute name="duration" required="true"%>
<%@attribute name="quantities" type="java.util.List" required="true"%>
<%@attribute name="prixUnite" required="true"%>
<%@attribute name="uniteQte" required="true"%>
<%@attribute name="action" required="true"%>
<%@attribute name="id" required="true"%>


<form role="form" id="request-creation-form${id}" data-toggle="validator" action="request" method="post">
    <div>
        <label for="producer-field">*Producteur :</label>
        <input type="text" name="producer" id="producer-field" value="${producer}" readonly required/> 
    </div>
    <div>
        <label for="product-field">*Produit :</label>
        <input type="text" name="product" id="producer-field" value="${product}" readonly required/> 
    </div>
    <div>
        <label for="duration-field">*Durée du contrat :</label>
        <input type="text" name="duration" id="duration-field" value="${duration}" readonly required/> 
    </div>
    <div>
        <label for="nbLots-field">*Nombre de lots :</label>
        <input type="number" name="nbLots"  id="nbLots-field" value="1" required/> 
    </div>
    <div>
        <label for="quantity-field">*Lots de quantité :</label>
         <select name="quantity" size="1" id="quantity-field" required>
            <c:forEach items="${quantities}" var="quantity">
                <option value="${quantity.qte}" data-unit="${quantity.uniteQte}">
                    ${quantity.qte} ${quantity.uniteQte}
                </option>
            </c:forEach>           
        </select>
    </div>
    <div>
        <label for="unit-price-field">*Prix à l'unité : </label>
        <input type="text" name="price" id="unit-price-field" value="${prixUnite}" readonly><br />
    </div>
    <div>
        <label for="total-price-field">*Prix total :</label>
        <input type="text" name="total-price-label" id="total-price-field" value="42" readonly><br />
    </div>
    <input type="hidden" name="uniteQte" id="unit-field" value="${uniteQte}" />
    <input type="hidden" name="action" id="unit-field" value="create" />
    <br />
    <c:if test="${hasSubmitBtn == null ||  hasSubmitBtn == true}">
        <input class="submit btn btn-primary" type="submit" value="S'inscrire" id="submit" /><br />
    </c:if>
    * : Champs obligatoires
</form>
