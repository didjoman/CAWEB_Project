<%-- 
    Document   : layout
    Created on : 26 mars 2015, 18:11:15
    Author     : Alexandre Rupp
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
    
<%@tag description="caweb template" pageEncoding="UTF-8"%>
    
<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="title"%>
<%@attribute name="error"%>
<%-- <%@attribute name="head" fragment="true" %> --%>
    
<%-- any content can be specified here: --%>
<html>
    <head>
        <title>${title}</title>
        <link rel="icon" type="image/png" href="img/smile.png" />
        <!-- META INFOS -->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            
        <!-- STYLE -->
        <!-- normalize -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/3.0.2/normalize.min.css">
        <!-- our style -->    
        <link rel="stylesheet" media="screen" type="text/css" title="Design"
              href="${pageContext.request.contextPath}/css-min/css/styles.css" />
        
        <!-- jquery UI style -->
        <!--<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">-->
        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/cupertino/jquery-ui.css">
        <!-- Bootstrap style -->
            
        <!--   
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">
        <link rel="stylesheet" href="https://bootswatch.com/cerulean/bootstrap.min.css">
        <link rel="stylesheet" href="https://bootswatch.com/lumen/bootstrap.min.css">
        <link rel="stylesheet" href="https://bootswatch.com/spacelab/bootstrap.min.css">
        -->
        <link rel="stylesheet" href="https://bootswatch.com/cerulean/bootstrap.min.css">
            
        <!-- jquery UI -->           
        <!-- SCRIPTS -->
    </head>
    <body>
        <t:nav_bar>
            <jsp:attribute name="items">
                <t:menu />
            </jsp:attribute>
        </t:nav_bar>
            
            
        <div class="container-fluid">
            <div class="row">
                
                <div class="col-sm-3 col-md-2 sidebar navbar-collapse collapse">
                    <ul class="nav nav-sidebar">
                        <t:menu />
                    </ul>
                </div>
                
                <div id="content-wrapper" 
                     class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main hidden"
                     >
                    <c:if test="${error != null}">
                        <div class="alert alert-danger" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            ${error}
                        </div>
                    </c:if>
                    
                    <c:if test="${success != null}">
                        <div class="alert alert-success" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            ${success}
                        </div>
                    </c:if>
                    
                    <c:if test="${info != null}">
                        <div class="alert alert-success" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            ${info}
                        </div>
                    </c:if>
                    
                    <c:if test="${warning != null}">
                        <div class="alert alert-success" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            ${warning}
                        </div>
                    </c:if>
                    
                    <jsp:doBody/>    
                </div>
            </div>
        </div>
        
        <!-- jquery : -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <!-- jquery UI : -->
        <script src="https://code.jquery.com/ui/1.11.3/jquery-ui.min.js"></script>
        <!-- Bootstrap : -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/validator.js"></script>
        <!-- Plugin Jquery for week-selection in jquery calendar -->
        <!--<script src="${pageContext.request.contextPath}/js/jquery.weekpicker.js"></script>-->
        <!-- Our Js : -->
        <script src="${pageContext.request.contextPath}/js/app.js"></script>
        <noscript>
        <style>
            .hidden{ display: block !important; }
        </style>
        </noscript>
        
    </body>
</html>