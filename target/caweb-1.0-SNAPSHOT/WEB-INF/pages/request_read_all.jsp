<%-- 
    Document   : contracts_asks_get
    Created on : 27 mars 2015, 19:35:24
    Author     : Alexandre Rupp
--%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Demandes de contrat">        
    <jsp:body>
        <div class="page-header">
            <h1>Demandes de contrats</h1>
        </div>
        
        <h3>Demandes de contrats</h3>
        <t:request_list items="${reqs}" />
            
        <h3>Demandes de renouvellements</h3>
        <t:request_list items="${contractsToRenew}" />
        
        <h3>Demandes refusées</h3>
        <t:request_list items="${reqsRefused}" />
                 
    </jsp:body>
</t:layout>   