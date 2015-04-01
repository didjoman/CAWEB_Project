<%-- 
    Document   : layout
    Created on : 26 mars 2015, 18:11:15
    Author     : Alexandre Rupp
--%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
    
<%@tag description="caweb template" pageEncoding="UTF-8"%>
    
<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="title"%>
<%@attribute name="logged"%>
<%-- <%@attribute name="head" fragment="true" %> --%>
    
<%-- any content can be specified here: --%>
<html>
    <head>
        <title>${title}</title>
            
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
            
        -->
        <link rel="stylesheet" href="https://bootswatch.com/spacelab/bootstrap.min.css">
        
        <!-- jquery UI -->           
        <!-- SCRIPTS -->
    </head>
    <body>
        <t:nav_bar logged="false"/>
            
        <div class="container-fluid">
            <div class="row">
                <t:side_menu currentPage="${currentPage}"/>
                    
                <div id="content-wrapper" 
                     class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main hidden"
                     >
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
        <!-- Our Js : -->
        <script src="${pageContext.request.contextPath}/js/app.js"></script>
        <noscript>
        <style>
            .hidden{ display: block !important; }
        </style>
        </noscript>
    </body>
</html>