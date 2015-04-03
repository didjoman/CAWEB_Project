<%-- 
    Document   : connection_form
    Created on : 2 avr. 2015, 16:36:30
    Author     : Alexandre Rupp
--%>
    
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<form id="connection-form" action="${pageContext.request.contextPath}/session" method="post">
    <div>
        <label for="Login">Login :</label><br />
        <input type="text" value="${param.login}"
               name="login" id="login" placeholder="Identifiant" />
    </div>
                                        
    <div>
        <label for="password">Mot de passe :</label><br />
        <input type="password" name="password" id="password" placeholder="Mot de passe" />
        <input type="hidden" name="connexion" value="1" />
    </div>
    <br />
    <input class="submit btn btn-primary" type="submit" value="Se connecter" id="submit" />
</form>