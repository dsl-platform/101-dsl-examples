$(document).ready(function(){

  // Editable Table
  $('#mainTable').editableTableWidget({
    editor: $('<input>')
  });

  /*$('table td').on('change', function(evt, newValue) {

    if (....) {
      return false; // reject change
    }
    $.post( 'test.php', { name: 'John', time: '2pm' } );

  });*/

  // Bootstrap Switch
  $("[name='switch']").bootstrapSwitch({
    onText   : 'Yes',
    offText  : 'No',
    onColor  : 'success',
    offColor : 'danger'
  });

});