

        
$(".positif").change(function(){
    if(($(".positif").val())<0)
        $('.positif').val(0);
});
        
$(".target").change(function(){
    $(".prix").val($('[name=quantity] option:selected').attr("data-prix"));
    $(".prixtot").val(($('[name=quantity] option:selected').attr("data-prix")*$('#nbLots-field').val()))
});
        

$(function() {
    // Little extension of jquery to test wether an object is empty :
    $.fn.exists = function () {
        return this.length !== 0;
    };
    
    // Prevent the close of dropdown when clicking on it
    $('.dropdown-menu').click(function(e) {
        e.stopPropagation(); 
    });
    
    $(".hidden").css('display','none').removeClass('hidden');

    $( "#content-wrapper" ).fadeIn(500);
    $( "#validation-panel" ).css('display','none').removeClass('hidden')
            .show('drop', null, 1000, null );
});




$(function() {
    // Date picker in request jsp :
    $( "#datepicker" ).datepicker({
        showButtonPanel: true,
        dateFormat: "dd-mm-yy",
        minDate: 0  // disables dates before today
    });

    $( "#accordion-modif-perm" ).accordion({
        collapsible: true,
        active: false
    });
    if($(".permConsummers").exists())
        $(".permConsummers").autocomplete({
            source: consummers
        });
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
            $('.ui-datepicker-current-day a').addClass('ui-state-active');
        }, 1);
    };
    
    var weekMouseMoveHandler = function(){
        window.setTimeout(function () {
            $('.ui-datepicker-calendar tr').mousemove(function(){
                $(this).find('td a').addClass('ui-state-hover'); 
            });
        }, 1);
    };
    
    var weekMouseLeaveHandler = function(){
        window.setTimeout(function () {
            $('.ui-datepicker-calendar tr').mouseleave(function(){
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
    
    var isInList = function(date, array){
        var startDay = getStartDate(date);
        return $.inArray(printDate(startDay), array);
    };
    console.log(isInList(new Date(2015,00,05), listPermSet));
    
    var updateView = function(start, end, weekNum, year){
        $.get('permanency', {async: "true", weekNum: weekNum, year: year})
                .always(function(){
                    $( "#accordion-modif-perm" ).show();
            // Update of the view with static values : 
            $('#week-info #week-info-num').html(weekNum);
            $('#week-info #week-info-year').html(year);
            $('#week-info #week-info-start').html(printDate(start));
            $('#week-info #week-info-end').html(printDate(end));
            
            // If there is no data for this week, we set values to default in the view
            $('#week-info #week-info-perm1').html("/");
            $('#week-info #week-info-dispo').html("/");
            $('#week-info #week-info-indispo').html("/");
            return;
        })
                .done(function(data){
                    // Update of the view with values got from the database :
            var perm1 = (!data.permanencier1) ? "" : data.permanencier1;
            $('#field-perm1').val(""+perm1);
            if(perm1 !== "")
                perm1 += "<br />";
            $('#week-info #week-info-perm1').html(perm1);
            
            var perm2 = (!data.permanencier2) ? "" : data.permanencier2;
            $('#field-perm2').val(""+perm2);
            if(perm2 !== "")
                perm2 += "<br />";
            $('#week-info #week-info-perm2').html(perm2);

            if(perm1 === "" && perm2 ==="")
                $('#week-info #week-info-perm1').html("/");
            
            
            var dispo ="";
            for(var i = 0; i < data.estDisponible.length; ++i)
                dispo += data.estDisponible[i] + "<br />";
            if(dispo === "")
                dispo = "/";
            $('#week-info #week-info-dispo').html(dispo);
            
            var indispo ="";
            for(var i = 0; i < data.estIndisponible.length; ++i)
                indispo += data.estIndisponible[i] + "<br />";
            if(indispo === "")
                indispo = "/";
            $('#week-info #week-info-indispo').html(indispo);
        });
        
    };
    
    var renderColoredDays = function(date){
        var cssClass = '';
        // Colorates the days of the selected week :
        if(date >= startDate && date <= endDate)
            cssClass += ' ui-datepicker-current-day ';
        //Colorates the dates of permanence/dispo/undispo set.
        // /!\ listPermSet is a sort of volatile variable generated in java.
        if(isInList(date, listPermFullySet) !== -1)
            cssClass += 'fullperms';
        else if(isInList(date, listPermSet) !== -1)
            cssClass += 'perms';
        else if(isInList(date, listDispos) !== -1)
            cssClass += 'dispos';
        else if(isInList(date, listUndispos) !== -1)
            cssClass += 'undispos';
        
        return [true, cssClass];
    };
    
    var onOnSelec = function(dateText, inst) { 
        // Get the current date, and week info of the selected date.
        var date = $(this).datepicker('getDate');
        var weekNumber = $.datepicker.iso8601Week(new Date(date));
        startDate = new Date(getStartDate(date));
        endDate = new Date(getEndDate(date));
        // Update textual informations about the week selected :
        updateView(startDate, endDate, weekNumber, startDate.getFullYear());
        selectCurrentWeek();
        // Re-set the "hover effects" handlers
        weekMouseMoveHandler();
        weekMouseLeaveHandler();
    };
    
    $('.week-picker').datepicker( {
        showOtherMonths: true,
        selectOtherMonths: true,
        dateFormat: "dd-mm-yy",
        altField: "#datepicker",
        closeText: 'Fermer',
        firstDay: 1 ,
        onSelect: onOnSelec,
        beforeShowDay: renderColoredDays,
        onChangeMonthYear: function(year, month, inst) {
            selectCurrentWeek();
        }
    });

    var onOnSelecConsummer = function(dateText, inst) { 
        var updateViewCons = function(){
            // Update textual info on the right
            $('#week-info #week-info-num').html(weekNumber);
            $('#week-info #week-info-year').html(startDate.getFullYear());
            $('#week-info #week-info-start').html(printDate(startDate));
            $('#week-info #week-info-end').html(printDate(endDate));
        
            // Hide/Show the Form Or Info about the perm
            if(isInList(date, listPermFullySet) !== -1){
                $('#disponibility-form').hide(200);
                $('.disponibility-info').show(200);
            }else {
                $('.disponibility-info').hide(200);
                $('#disponibility-form').show(200);
                if(isInList(date, listDispos) !== -1)
                    $('#dispo-radio input[value="true"]').prop('checked', true);
                else if(isInList(date, listUndispos) !== -1)
                    $('#dispo-radio input[value="false"]').prop('checked', true);
                else 
                    $('#dispo-radio input[value="maybe"]').prop('checked', true);
            }
            
            // Get info about the second permanency holder :
            $.get('permanency', 
            {async: "true", weekNum: weekNumber, year: startDate.getFullYear()}, 
            function(data){
                if(data.perm){
                    $('#perm-info-pseudo').html(data.perm.pseudo);
                    $('#perm-info-firstname').html(data.perm.prenom);
                    $('#perm-info-name').html(data.perm.nom);
                    $('#perm-info-phone').html(data.perm.tel);
                    $('#perm-info-email').html(data.perm.email);
                    $('#no-perm-info').hide(200);
                    $('#perm-info').show(200);
                } else {
                    $('#perm-info').hide(200);
                    $('#no-perm-info').show(200);
                }
            }, "json");
            
        };
        
        // Get the current date, and week info of the selected date.
        var date = $(this).datepicker('getDate');
        var weekNumber = $.datepicker.iso8601Week(new Date(date));
        startDate = new Date(getStartDate(date));
        endDate = new Date(getEndDate(date));
        // Update textual informations about the week selected :
        updateViewCons();
        selectCurrentWeek();
        // Re-set the "hover effects" handlers
        weekMouseMoveHandler();
        weekMouseLeaveHandler();
    };
    $('.consummer-week-picker').datepicker( {
        showOtherMonths: true,
        selectOtherMonths: true,
        dateFormat: "dd-mm-yy",
        altField: "#datepicker",
        closeText: 'Fermer',
        firstDay: 1 ,
        onSelect: onOnSelecConsummer,
        beforeShowDay: renderColoredDays,
        onChangeMonthYear: function(year, month, inst) {
            selectCurrentWeek();
        }
    });
    
    $.datepicker.setDefaults( $.datepicker.regional[ "fr" ] ); // texte en french

    weekMouseMoveHandler();
    weekMouseLeaveHandler();

    
    
    var dispoForm = $('#disponibility-form');
    dispoForm.submit(function (ev) {
        // Adds data to the form, to tell the backend we are doing an AJAX request
        var dataToSend = {'async' : 'true', weekNum : $('#week-info-num').html(), year : $('#week-info-year').html()};
        dataToSend = dispoForm.serialize() + '&' + $.param(dataToSend);
        $.ajax({
            type: dispoForm.attr('method'),
            url: dispoForm.attr('action'),
            data: dataToSend
        }).always(function( data ) {
            //console.log(data);
            //console.log("ok");
            if(data !== null){
                // Update the calendar with new data : 
                var date = $('.consummer-week-picker').datepicker('getDate');
                var dateLitteral = printDate(getStartDate(date));
                // Remove the date from the actual list : 
                if($.inArray(dateLitteral, listDispos) != -1)
                    listDispos.splice($.inArray(dateLitteral, listDispos),1);
                if($.inArray(dateLitteral, listUndispos) != -1)
                    listUndispos.splice($.inArray(dateLitteral, listUndispos),1);
                // Insert it to the new list : 
                var isDispo = dispoForm.find('input[name="dispo"]:checked').val();
                switch (isDispo){
                    case 'true':
                        listDispos.push(dateLitteral);
                        break;
                    case 'false':
                        listUndispos.push(dateLitteral);
                        break;
                    case 'maybe':
                        break;
                }
                // Refresh the datepicker :
                $('.consummer-week-picker').datepicker( "refresh" );
            }
        });
        ev.preventDefault();
    });
    
    var permForm = $('#permanency-form');
    permForm.submit(function (ev) {
        // Adds data to the form, to tell the backend we are doing an AJAX request
        var dataToSend = {'async' : 'true', setPerm : "true", weekNum : $('#week-info-num').html(), year : $('#week-info-year').html()};
        dataToSend = permForm.serialize() + '&' + $.param(dataToSend);
        $.ajax({
            type: permForm.attr('method'),
            url: permForm.attr('action'),
            data: dataToSend
        }).always(function( data ) {
            if(data !== null){
                // Update the calendar with new data : 
                var date = $('.week-picker').datepicker('getDate');
                var dateLitteral = printDate(getStartDate(date));
                // Remove the date from the actual list : 
                var index = $.inArray(dateLitteral, listPermFullySet);
                if(index != -1)
                    listPermFullySet.splice(index,1);
                
                index = $.inArray(dateLitteral, listPermSet)
                if(index != -1)
                    listPermSet.splice(index,1);
                // Insert it to the new list : 
                var perm1Set = $('#field-perm1').val() !== "";
                var perm2Set = $('#field-perm2').val() !== "";
                if(perm1Set && perm2Set)
                    listPermFullySet.push(dateLitteral);
                else if(perm1Set || perm2Set)
                    listPermSet.push(dateLitteral);
                
                // Refresh the datepicker :
                $('.week-picker').datepicker( "refresh" );
                
                // Send visual feedback to the user :
                $('#success-feedback').show(300).delay(1500).hide(300);
            }
        });
        ev.preventDefault();
    });
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

var id_qte = 0;


$('#add_qte').click(function(e){
    // Install the new quantity, with the correct attributes : 
    var newQte = $('.new-qte').clone().fadeIn(400).removeClass('new-qte');
    ++id_qte;
    newQte.attr('data-id',id_qte);
    newQte.find('[name=qte]').attr('name', 'qte'+(id_qte));
    newQte.find('[name=unite]').attr('name', 'unite'+(id_qte));
    newQte.find('[name=prix]').attr('name', 'prix'+(id_qte));
    newQte.find('.rm-btn').attr('name','rm'+(id_qte));
    $('#create-offer-form').find('[data-id='+(id_qte-1)+']').find('.rm-btn').hide();
    $('#add_qte').before(newQte);

    
    // Install a remove button on the new quantity : 
    $('.rm-btn').click(function(e){
        // Delete the new quantity field :
        if($(this).attr('name')=="rm"+id_qte)
            id_qte--;
        $(this).parent('div').fadeOut(400, function(){$(this).remove()});
        $('#create-offer-form').find('[data-id='+id_qte+']').find('.rm-btn').show();
        $('[name=nbQte]').val(id_qte+1);
        // Dicrease the number of quantity : 
    });
    
    // Increase the number of quantity
    $('[name=nbQte]').val(id_qte+1);
    e.preventDefault();
});


