
$(function() {
    $(".hidden").css('display','none').removeClass('hidden');

    $( "#content-wrapper" ).fadeIn(400);
    $( "#validation-panel" ).css('display','none').removeClass('hidden')
            .show('drop', null, 1000, null );
});


$( "#datepicker" ).datepicker({
    showButtonPanel: true
});




