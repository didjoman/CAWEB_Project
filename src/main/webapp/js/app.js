
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
    showButtonPanel: true,
    dateFormat: "dd-mm-yy"
});

$(function() {
    $( "#accordion" ).accordion({
        collapsible: true,
        active: false
    });
});

$( ".permConsummers" ).autocomplete({
    source: consummers
});

$(function() {
    var startDate;
    var endDate;
    
    //var listPermSet = $.map($('#dates-perm').html().split("\n"), $.trim).filter(function(e){return e;});
    //var listDispo = $.map($('#dates-dispo').html().split("\n"), $.trim).filter(function(e){return e;});
    //var listUnDispo = $.map($('#dates-undispo').html().split("\n"), $.trim).filter(function(e){return e;});
    

    
    function getDateOfWeek(w, y) {
        return new Date(y, 0, 1 + (w - 1) * 7);
    }

    var selectCurrentWeek = function() {
        window.setTimeout(function () {
            $('.week-picker .ui-datepicker-current-day a').addClass('ui-state-active');
        }, 1);
    };
    
    var weekMouseMoveHandler = function(){
        window.setTimeout(function () {
            $('.week-picker .ui-datepicker-calendar tr').mousemove(function(){
                $(this).find('td a').addClass('ui-state-hover'); 
            });
        }, 1);
    };
    
    var weekMouseLeaveHandler = function(){
        window.setTimeout(function () {
            $('.week-picker .ui-datepicker-calendar tr').mouseleave(function(){
                $(this).find('td a').removeClass('ui-state-hover'); 
            });
        }, 1);
    };
    
    
    
    var printDate = function(date){
        function pad(n) {
            return (n < 10) ? ("0" + n) : n;
        }
        return pad(date.getDate())+'/'+pad(date.getMonth()+1)+'/'+date.getFullYear();
    };
    
    var getStartDate = function(date){
        var day = date.getDate() - ((date.getDay() === 0) ? 6 : date.getDay() - 1);
        return new Date(date.getFullYear(), date.getMonth(), day);
    };
    
    var getEndDate = function(date){
        var day = date.getDate() - ((date.getDay() === 0) ? 6 : date.getDay() - 1) + 6;
        return new Date(date.getFullYear(), date.getMonth(), day);
    };
    
    var isPerm = function(date, array){
        var startDay = getStartDate(date);
        return $.inArray(printDate(startDay), array);
    };
    console.log(isPerm(new Date(2015,00,05), listPermSet));
    
    var updateView = function(start, end, weekNum, year){
        $.get('permanency', {async: "true", weekNum: weekNum, year: year}, 
        function(data){
            // Update of the view with static values : 
            $('#week-info #week-info-num').html(weekNum);
            $('#week-info #week-info-year').html(year);
            $('#week-info #week-info-start').html(printDate(start));
            $('#week-info #week-info-end').html(printDate(end));
            
            // If there is no data for this week, we set values to default in the view
            if(data === null){
                $('#week-info #week-info-perm1').html("/");
                $('#week-info #week-info-dispo').html("/");
                $('#week-info #week-info-indispo').html("/");
                return;
            }
            
            // Update of the view with values got from the database :
            var perm1 = (!data.permanencier1) ? "" : data.permanencier1.pseudo;
            $('#week-info #week-info-perm1').html(perm1+"<br />");
            $('#field-perm1').val(perm1);
            
            var perm2 = (!data.permanencier2) ? "" : data.permanencier2.pseudo;
            $('#week-info #week-info-perm2').html(perm2+"<br />");
            $('#field-perm2').val(perm2);

            if(perm1 === "" && perm2 ==="")
                $('#week-info #week-info-perm1').html("/");
            
            
            var dispo ="";
            for(var i = 0; i < data.estDisponible.length; ++i)
                dispo += data.estDisponible[i].pseudo + "<br />";
            if(dispo === "")
                dispo = "/";
            $('#week-info #week-info-dispo').html(dispo);
            
            var indispo ="";
            for(var i = 0; i < data.estIndisponible.length; ++i)
                indispo += data.estIndisponible[i].pseudo + "<br />";
            if(indispo === "")
                indispo = "/";
            $('#week-info #week-info-indispo').html(indispo);
        });
        
        
    };
    
    var renderColoredDays = function(date){

        var cssClass = '';
        //Colorates the dates of permanence/dispo/undispo set.
        // /!\ listPermSet is a sort of volatile variable generated in java.
        if(isPerm(date, listPermFullySet) !== -1)
            cssClass = 'perms';
        else if(isPerm(date, listDispos) !== -1)
            cssClass = 'dispos';
        else if(isPerm(date, listUndispos) !== -1)
            cssClass = 'undispos';
        else if(isPerm(date, listPermSet) !== -1)
            cssClass = 'perms';
        
        // Colorates the days of the selected week :
        if(date >= startDate && date <= endDate)
            cssClass = 'ui-datepicker-current-day';
        return [true, cssClass];
    };
    
    $('.week-picker').datepicker( {
        showOtherMonths: true,
        selectOtherMonths: true,
        dateFormat: "dd-mm-yy",
        altField: "#datepicker",
        closeText: 'Fermer',
        firstDay: 1 ,
        onSelect: function(dateText, inst) { 
            // Get the current date, and week info of the selected date.
            var date = $(this).datepicker('getDate');
            var weekNumber = $.datepicker.iso8601Week(new Date(date));
            console.log(weekNumber);
            startDate = new Date(getStartDate(date));
            endDate = new Date(getEndDate(date));
            // Update textual informations about the week selected :
            updateView(startDate, endDate, weekNumber, startDate.getFullYear());
            selectCurrentWeek();
            // Re-set the "hover effects" handlers
            weekMouseMoveHandler();
            weekMouseLeaveHandler();
        },
        beforeShowDay: renderColoredDays,
        onChangeMonthYear: function(year, month, inst) {
            selectCurrentWeek();
        }
    });
    $.datepicker.setDefaults( $.datepicker.regional[ "fr" ] ); // texte en french

    weekMouseMoveHandler();
    weekMouseLeaveHandler();

    
});


/* French initialisation for the jQuery UI date picker plugin. */
/* Written by Keith Wood (kbwood{at}iinet.com.au),
Stéphane Nahmani (sholby@sholby.net),
Stéphane Raimbault <stephane.raimbault@gmail.com> */
(function( factory ) {
    if ( typeof define === "function" && define.amd ) {
        // AMD. Register as an anonymous module.
        define([ "../datepicker" ], factory );
    } else {
        // Browser globals
        factory( jQuery.datepicker );
    }
}(function( datepicker ) {
    datepicker.regional['fr'] = {
        closeText: 'Fermer',
        prevText: 'Précédent',
        nextText: 'Suivant',
        currentText: 'Aujourd\'hui',
        monthNames: ['janvier', 'février', 'mars', 'avril', 'mai', 'juin',
            'juillet', 'août', 'septembre', 'octobre', 'novembre', 'décembre'],
        monthNamesShort: ['janv.', 'févr.', 'mars', 'avr.', 'mai', 'juin',
            'juil.', 'août', 'sept.', 'oct.', 'nov.', 'déc.'],
        dayNames: ['dimanche', 'lundi', 'mardi', 'mercredi', 'jeudi', 'vendredi', 'samedi'],
        dayNamesShort: ['dim.', 'lun.', 'mar.', 'mer.', 'jeu.', 'ven.', 'sam.'],
        dayNamesMin: ['D','L','M','M','J','V','S'],
        weekHeader: 'Sem.',
        dateFormat: 'dd/mm/yy',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: ''};
    datepicker.setDefaults(datepicker.regional['fr']);
    return datepicker.regional['fr'];
}));
// On submit of the connection form, we submit it in an asynchronous way.
/*
var connec = $('#connection-form');
connec.submit(function (ev) {
    // Adds data to the form, to tell the backend we are doing an AJAX request
    var dataToSend = {
        'async' : 'true'
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


