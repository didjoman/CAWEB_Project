<%-- 
    Document   : popup
    Created on : 4 avr. 2015, 21:28:01
    Author     : Alexandre Rupp
--%>
    
<%@tag description="popup" pageEncoding="UTF-8"%>
    
<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="classes"%>
<%@attribute name="type" required="true"%> <!-- modal-lg / modal-sm-->
<%@attribute name="title" required="true"%>
<%@attribute name="footer" fragment="true" %>
    
<div class="modal fade ${classes}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog ${type}">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">${title}</h4>
            </div>
            <div class="modal-body">
                <jsp:doBody/>
            </div>
            <div class="modal-footer">
                <jsp:invoke fragment="footer"/>
            </div>
        </div>
    </div>
</div>