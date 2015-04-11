<%-- 
    Document   : subscription_form
    Created on : 2 avr. 2015, 16:35:56
    Author     : Alexandre Rupp
--%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
    
<form id="subscription-form" action="user" method="post" role="form" data-toggle="validator">
    <div class="form-group">
        <label for="login" class="control-label">*Login :</label>
        <input type="text" value="${param.login}" class="form-control input-sm"
               name="login" id="login" placeholder="Identifiant" required/>
    </div>
        
    <div class="form-group">
        <label for="password" class="control-label">*Mot de passe :</label>
        <input type="password" name="password" id="password" class="form-control input-sm" placeholder="Mot de passe" required/>
    </div>
    <div class="form-group">
        <label for="password-verif-field" class="control-label">*Mot de passe(2) :</label>
        <input type="password" name="passwordverif" id="password-verif-field" 
               data-match="#password" 
               data-error="Veuillez confirmer le mot de passe." 
               data-match-error="Mdp(2) doit être identique au mdp ..." 
               class="form-control input-sm"  placeholder="Verif Mot de passe" required/>
        <div class="help-block with-errors"></div>
    </div>
    <div class="form-group">
        <label for="courriel-field" class="control-label">*Courriel :</label>
        <input type="email"  value="${param.email}"
               name="email" id="courriel-field" class="form-control input-sm" 
               data-error="Veuillez saisir une adresse email valide SVP."
               placeholder="courriel" required/>
    </div>
    <div class="form-group">
        <label for="adresse-field" class="control-label">*Adresse :</label>
        <input type="text"  value="${param.address}"
               name="address" id="adresse-field" class="form-control input-sm"  placeholder="adresse" required/>
    </div>
    <div class="form-group">
        <label for="nom-field" class="control-label">*Nom :</label>
        <input type="text"  value="${param.name}"
               name="name" id="nom-field" class="form-control input-sm"  placeholder="Nom" required/>
    </div>
    <div class="form-group">
        <label for="prenom-field" class="control-label">*Prenom :</label>
        <input type="text"  value="${param.firstname}"
               name="firstname" id="prenom-field" class="form-control input-sm"  placeholder="Prenom" required/>
    </div>
    <div class="form-group">
        <label for="tel-field" class="control-label">*Tel :</label>
        <input type="text"  value="${param.tel}"
               name="tel" id="tel-field" class="form-control input-sm"  placeholder="telephone" required/>
    </div>
    <div class="form-group">        
        <label class="control-label">*statut :</label>
        <ul id="status-radio">
            <li>
                <input type="radio" name="status"  value="cons" <c:if test="${param.status == 'cons'}">checked</c:if>>
                    Consommateur
                </li>
                <li>
                    <input type="radio" name="status" value="prod" <c:if test="${param.status == 'prod'}">checked</c:if>> Producteur
            </li>
        </ul>
    </div>
    * : Champs obligatoires
        
    <br />
    <input class="submit btn btn-primary" type="submit" value="S'inscrire" id="submit" /><br />
</form>