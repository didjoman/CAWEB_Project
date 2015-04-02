
$(function() {
    // Prevent the close of dropdown when clicking on it
    $('.dropdown-menu').click(function(e) {
        e.stopPropagation(); 
    });
    
    $(".hidden").css('display','none').removeClass('hidden');

    $( "#content-wrapper" ).fadeIn(500);
    $( "#validation-panel" ).css('display','none').removeClass('hidden')
            .show('drop', null, 1000, null );
});


$( "#datepicker" ).datepicker({
    showButtonPanel: true
});


// On submit of the connection form, we submit it in an asynchronous way.
/*
var connec = $('#connection-form');
connec.submit(function (ev) {
    // Adds data to the form, to tell the backend we are doing an AJAX request
    var dataToSend = {
        'asynchronous' : 'true'
    };
    dataToSend = connec.serialize() + '&' + $.param(dataToSend);
    
    $.ajax({
        type: connec.attr('method'),
        url: connec.attr('action'),
        data: dataToSend,
        success: function (data) {
            if(data !== null){
                // Update the navbar with 
            }
                
        }
    });

    ev.preventDefault();
});
 */



