<%-- 
    Document   : left_menu
    Created on : 26 mars 2015, 22:41:21
    Author     : Alexandre Rupp
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag import="java.util.HashMap"%>
<%@tag import="java.util.Map"%>
<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@attribute name="currentPage" %>

<c:set var="page">
    ${pageContext.request.requestURL.substring(pageContext.request.requestURL.lastIndexOf("/")+1, pageContext.request.requestURL.lastIndexOf(".jsp"))}
</c:set>

<div class="col-sm-3 col-md-2 sidebar">
    <ul class="nav nav-sidebar">
        <li class="<c:if test="${page == 'accueil'}">active</c:if>">
            <a href="${pageContext.request.contextPath}/accueil">
                Accueil
            </a>
        </li>
        <li class="<c:if test="${page == 'request_get_all' || page == 'request_get'}">active</c:if>">
            <a href="${pageContext.request.contextPath}/request">
                Demandes de contrats <span class="badge">3</span>
            </a>
        </li>
    </ul>
        
</div>