<%-- 
    Document   : permanency_read_all
    Created on : 7 avr. 2015, 09:06:31
    Author     : Alexandre Rupp
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Permanences">        
    <jsp:body>
        <div class="page-header">
            <h1>Permanences</h1>
        </div>
        
        <div class="row">
            <div class="col-sm-6 col-md-4">
                <div id="permanency-picker" class="week-picker"></div>
                <div style="display: none;" id="dates-perm">
                    05/01/2015
                    04/04/2015
                    05/04/2015
                </div>
                <div style="display: none;" id="dates-dispo">
                    {"date" : "05/01/2015", "pers" :"truc"}
                </div>
            </div>
            <div class="col-sm-6 col-md-8">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Informations sur la semaine</h3>
                    </div>
                    <div id="week-info" class="panel-body">
                        <h4>Semaine <span id="week-info-num"></span> de <span id="week-info-year"></span></h4>
                        <ul>
                            <li><strong>Début : </strong><span id="week-info-start"></span></li>
                            <li><strong>Fin : </strong><span id="week-info-end"></span></li>
                            <li>
                                <strong>Permanences : </strong> <br />
                                <span id="week-info-perm1"></span>
                                <span id="week-info-perm2"></span>
                            </li>
                            <li>
                                <strong>Disponibles : </strong><br />
                                <div id="week-info-dispo"></div>
                            </li>
                            <li>
                                <strong>Indisponibles : </strong><br />
                                <div id="week-info-indispo"></div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
            
        <script>
            var listPermSet = ["12/01/2015", "20/04/2015", "27/04/2015"];
        </script>
        <c:forEach items="${weeks}" var="week">
            ${week}<br />
        </c:forEach>
        
        
    </jsp:body>
</t:layout>   