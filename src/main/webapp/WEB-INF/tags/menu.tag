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
    
    
<c:set var="page">
    ${pageContext.request.requestURL.substring(pageContext.request.requestURL.lastIndexOf("/")+1, pageContext.request.requestURL.lastIndexOf(".jsp"))}
</c:set>
    
    
    
<c:if test="${sessionScope.status == null}">
    <li class="onlyOnLowRes <c:if test="${page == 'index'}">active</c:if>">
        <a href="${pageContext.request.contextPath}/index">
            Accueil
        </a>
    </li> 
</c:if>
            
<c:if test="${sessionScope.status == 'RESP' || sessionScope.status == 'CONS'}">
    <li class="onlyOnLowRes <c:if test="${page == 'permanency_read_all'}">active</c:if>">
        <a href="${pageContext.request.contextPath}/permanency">
            Permanences
        </a>
    </li>
</c:if>
<li class="onlyOnLowRes <c:if test="${page == 'offer_read'}">active</c:if>">
    <a href="${pageContext.request.contextPath}/offer">
        Liste des offres
    </a>
</li>
<c:if test="${sessionScope.status == 'PROD' || sessionScope.status == 'CONS'}">
    
    <c:if test="${sessionScope.status == 'PROD'}">
        <li class="onlyOnLowRes <c:if test="${page == 'product_read'}">active</c:if>">
            <a href="${pageContext.request.contextPath}/offer">
                Offres proposés
            </a>
        </li>
    </c:if>
    <li class="onlyOnLowRes <c:if test="${page == 'request_read_all' || page == 'request_read' || page == 'request_create'}">active</c:if>">
        <a href="${pageContext.request.contextPath}/request">
            Demandes de contrats
        </a>
    </li>
    <li class="onlyOnLowRes <c:if test="${page == 'contract_read_all'}">active</c:if>">
        <a href="${pageContext.request.contextPath}/contract">
            Contrats
        </a>
    </li>
</c:if>



</ul>

</div>