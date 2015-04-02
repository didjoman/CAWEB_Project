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
                                <jsp:include page="connection_form.jsp" />
                            </div>
                        </li>
                        <li>
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-expanded="false">
                                Inscription <span class="caret"></span>
                            </a>
                            <div id="subscription-dropdown" class="dropdown-menu" role="menu">
                                <jsp:include page="subscription_form.jsp" />
                            </div>
                        </li>
                        </c:when>
                        <c:when test="${sessionScope.login != null}">
                        <li><a href="#">${sessionScope.login}</a></li> 
                        <li>
                            <a href="session?action=delete">
                                Deconnexion
                            </a>
                        </li>
                        </c:when>
                    </c:choose>
            </ul>
        </div>
    </div>
</nav>
