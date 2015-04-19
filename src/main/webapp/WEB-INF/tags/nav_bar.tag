<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>


<%@attribute name="items" required="true" fragment="true"%>


<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-controls="navbar">
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
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#" role="button">
                                Login <span class="caret"></span>
                            </a>
                            <div id="connexion-dropdown" class="dropdown-menu" role="menu">
                                <jsp:include page="/WEB-INF/pages/connection_form.jsp" />
                            </div>
                        </li>
                        <li>
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#" role="button" >
                                Inscription <span class="caret"></span>
                            </a>
                            <div id="subscription-dropdown" class="dropdown-menu" role="menu">
                                <jsp:include page="/WEB-INF/pages/subscription_form.jsp" />
                            </div>
                        </li>
                    </c:when>
                    <c:when test="${sessionScope.login != null}">
                        <li><a href="#">${sessionScope.login}</a></li> 
                        <li>
                            <a href="${pageContext.request.contextPath}/session?action=delete">
                                Deconnexion
                            </a>
                        </li>
                    </c:when>
                </c:choose>
                
                <jsp:invoke fragment="items"/>
            </ul>
        </div>
    </div>
</nav>
