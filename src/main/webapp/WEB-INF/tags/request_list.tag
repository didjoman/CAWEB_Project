<%-- 
    Document   : request_read_all
    Created on : 18 avr. 2015, 19:37:09
    Author     : Alexandre Rupp
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="Print a list of requests" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@attribute name="items" required="true" type="java.util.List" %>

<table class="table table-striped table-hover">
    <thead>
        <tr>
            <th>Date Signature</th>
            <th>
                <c:if test="${sessionScope.status == 'CONS'}">
                    Producteur
                </c:if>
                <c:if test="${sessionScope.status == 'PROD'}">
                    Consommateur
                </c:if>
            </th>
            <th>produit</th>
            <th>Voir</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${items}" var="req">
            <tr>
                <td><fmt:formatDate value="${req.dateContrat}" pattern="dd/MM/yyyy" /></td>
                <td>
                    <c:if test="${sessionScope.status == 'CONS'}">
                        ${req.offreur.pseudo}
                    </c:if>
                    <c:if test="${sessionScope.status == 'PROD'}">
                        ${req.demandeur.pseudo}
                    </c:if>
                </td>
                <td>${req.getNomProduitContrat()}</td>
                <td><a class="btn btn-primary" href="request?id=${req.idContrat}" role="button">Voir</a></td>
            </tr>
        </c:forEach> 
            
    </tbody>
</table>