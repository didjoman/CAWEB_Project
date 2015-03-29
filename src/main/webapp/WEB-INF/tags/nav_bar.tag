<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@attribute name="logged" %>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}">CAWEB : <br />Producteur/Consommateur</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                
                <c:choose>
                    <c:when test="${sessionScope.login == null}">
                        <li role="presentation" class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-expanded="false">
                                Login <span class="caret"></span>
                            </a>
                            <div id="connexion-dropdown" class="dropdown-menu" role="menu">
                                <form action="user" method="post">
                                    <div>
                                        <label for="Login">Login :</label><br />
                                        <input type="text" value="${param.login}"
                                               name="login" id="login" placeholder="Identifiant" />
                                        <span class="erreur">${erreurs['login']}</span>
                                    </div>
                                    
                                    <div>
                                        <label for="password">Mot de passe :</label><br />
                                        <input type="password" name="password" id="password" placeholder="Mot de passe" />
                                        <span class="erreur">${erreurs['password']}</span> <input
                                            type="hidden" name="connexion" value="1" />
                                    </div>
                                    <br />
                                    <input class="submit btn btn-primary" type="submit" value="Se connecter" id="submit" />
                                    <p style="${empty erreurs ? 'color: green;' : 'color: red;'}">${resultat}</p>
                                </form>
                            </div>
                        </li>
                        <li><a href="#">Inscription</a></li>
                        </c:when>
                        <c:when test="${sessionScope.login != null}">
                        <li><a href="#">${sessionScope.login}</a></li> 
                        <li><a href="#">Deconnexion</a></li>
                        </c:when>
                    </c:choose>
            </ul>
        </div>
    </div>
</nav>
