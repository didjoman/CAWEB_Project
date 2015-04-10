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
                        
                        <div id="accordion">
                            <h3>Modifier les permanences</h3>
                            <div>
                                <form role="form" id="subscription-form" data-toggle="validator" action="user" method="post">
                                    <div>
                                        <label for="field-perm1">Perm1 :</label>
                                        <input type="text" name="perm1" id="field-perm1" placeholder="Permanencier 1"/>
                                    </div>
                                    <div>
                                        <label for="field-perm2">Perm 2:</label>
                                        <input type="text" name="perm2" id="field-perm2" placeholder="Permanencier 2"/>
                                    </div>
                                    <br />
                                    <input class="submit btn btn-primary" type="submit" value="Enregistrer" id="submit" /><br />
                                </form>
                            </div>
                        </div>
                    </div>
                        
                        
                </div>
            </div>
        </div>
            
        <script>
            
            var consummers = [
                
                
            ];
            
            var listPermSet = [
            <c:forEach items="${weeks}" var="week">
                    <c:if test="${week.getPermanencier1() != null || week.getPermanencier2() != null}">
                            "${week.getFirstDate()}",
                </c:if>
            </c:forEach>
                ];
                
                var listPermFullySet = [
            <c:forEach items="${weeks}" var="week">
                    <c:if test="${week.getPermanencier1() != null && week.getPermanencier2() != null}">
                            "${week.getFirstDate()}",
                </c:if>
            </c:forEach>
                ];
                
                var listDispos = [
            <c:forEach items="${weeks}" var="week">
                    <c:if test="${!week.getEstDisponible().isEmpty()}">
                            "${week.getFirstDate()}",
                </c:if>
            </c:forEach>
                ];
                
                var listUndispos = [
            <c:forEach items="${weeks}" var="week">
                    <c:if test="${!week.getEstIndisponible().isEmpty()}">
                            "${week.getFirstDate()}",
                </c:if>
            </c:forEach>
                ];
        </script>
        
    </jsp:body>
</t:layout>   