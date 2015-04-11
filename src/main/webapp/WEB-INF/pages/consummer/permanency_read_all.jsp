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
            <div class="thumbnail col-sm-6 col-md-4">
                <div id="permanency-picker" class="consummer-week-picker"></div>
                <div class="caption">
                    <h4>Légende :</h4>
                    <ul style="list-style-type: none;">
                        <li>
                            <div class="fullperms" style="width: 20px; height: 20px; float: left;"></div> &nbsp;: permanence fixée
                        </li>
                        <li>
                            <div class="dispos" style="width: 20px; height: 20px; float: left;"></div> &nbsp;: consommateurs disponibles
                        </li>
                        <li>
                            <div class="undispos" style="width: 20px; height: 20px; float: left;"></div> &nbsp;: consommateurs indisponibles
                        </li>
                        <li><br /></li>
                        <li>
                            <div class="ui-state-active" style="width: 20px; height: 20px; float: left;"></div> &nbsp;: Semaine sélectionnée <img src="img/Cursor_hand.png" alt="cursor"/>
                        </li>
                    </ul>
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
                            <li><strong>Disponibilité : </strong><br />
                                
                                <form id="disponiblity-form" style="display: none;" role="form" data-toggle="validator" action="permanency" method="post">
                                    <div>
                                        <ul id="status-radio">
                                            <li>
                                                <input type="radio" id="field-dispo" name="dispo" value="true" > disponnible
                                            </li>
                                            <li>
                                                <input type="radio" id="field-dispo" name="dispo" value="false"> Indisponnible
                                            </li>
                                            <li>
                                                <input type="radio" id="field-dispo" name="dispo" value="maybe"> Indéterminé
                                            </li>
                                            <li><input class="submit btn btn-primary btn-sm" type="submit" value="Enregistrer" id="submit" /></li>
                                        </ul>
                                    </div>
                                </form>
                                <div id="disponiblity-info" style="display: none;">
                                    Vous êtes de permanence cette semaine.
                                </div>
                            </li>
                        </ul>
                    </div>
                        
                </div>
            </div>
        </div>
            
            
            <script>
                
                var listPermSet = [];
                var listPermFullySet = [
                <c:forEach items="${weeks}" var="week">
                    <c:if test="${(week.getPermanencier1() != null &&  week.getPermanencier1().getPseudo().equals(login)) ||
                                  (week.getPermanencier2() != null &&  week.getPermanencier2().getPseudo().equals(login))}">
                                "${week.getFirstDate()}",
                    </c:if>
                </c:forEach>
                    ];
                    
                    var listDispos = [
                <c:forEach items="${weeks}" var="week">
                    <c:if test="${!week.getEstDisponible().contains(login)}">
                            "${week.getFirstDate()}",
                    </c:if>
                </c:forEach>
                    ];
                    
                    var listUndispos = [
                <c:forEach items="${weeks}" var="week">
                    <c:if test="${!week.getEstIndisponible().contains(login)}">
                            "${week.getFirstDate()}",
                    </c:if>
                </c:forEach>
                    ];
            </script>
                
        </jsp:body>
    </t:layout>   