<%-- 
    Document   : subscription_form
    Created on : 2 avr. 2015, 16:35:56
    Author     : Alexandre Rupp
--%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<form role="form" id="subscription-form" data-toggle="validator" action="user" method="post">
    <div>
        <label for="login">*Login :</label>
        <input type="text" value="${param.login}"
               name="login" id="login" placeholder="Identifiant" required/>
    </div>
        
    <div>
        <label for="password">*Mot de passe :</label>
        <input type="password" name="password" id="password" placeholder="Mot de passe" required/>
        <input type="hidden" name="connexion" value="1" />
    </div>
    <div>
        <label for="password-verif-field">*Mot de passe(2) :</label>
        <input type="password" name="passwordverif" id="password-verif-field" placeholder="Verif Mot de passe" required/>
        <input type="hidden" name="connexion" value="1" />
    </div>
    <div>
        <label for="courriel-field">*Courriel :</label>
        <input type="email"  value="${param.email}"
               name="email" id="courriel-field" placeholder="courriel" required/>
    </div>
    <div>
        <label for="adresse-field">*Adresse :</label>
        <input type="text"  value="${param.address}"
               name="address" id="adresse-field" placeholder="adresse" required/>
    </div>
    <div>
        <label for="nom-field">*Nom :</label>
        <input type="text"  value="${param.name}"
               name="name" id="nom-field" placeholder="Nom" required/>
    </div>
    <div>
        <label for="prenom-field">*Prenom :</label>
        <input type="text"  value="${param.firstname}"
               name="firstname" id="prenom-field" placeholder="Prenom" required/>
    </div>
    <div>
        <label for="tel-field">*Tel :</label>
        <input type="text"  value="${param.tel}"
               name="tel" id="tel-field" placeholder="telephone" required/>
    </div>
    <div>        
        <label>*statut :</label>
        <ul id="status-radio">
            <li>
                <input type="radio" name="status" value="cons" <c:if test="${param.status == 'cons'}">checked</c:if>>
                           Consommateur
            </li>
            <li>
                <input type="radio" name="status" value="prod" <c:if test="${param.status == 'prod'}">checked</c:if>> Producteur
            </li>
        </ul>
    </div>
    <br />
    <input class="submit btn btn-primary" type="submit" value="S'inscrire" id="submit" /><br />
    * : Champs obligatoires
</form>