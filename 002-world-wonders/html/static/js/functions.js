$(document).ready(function(){

  // Bootstrap Switch
  $("[name='switch']").bootstrapSwitch({
    onText   : 'Old',
    offText  : 'New',
    onColor  : 'success',
    offColor : 'danger'
  });

  $('.td-ancient').click(function(){
    return false;
  })

  // Editable Table
  $('#mainTable').editableTableWidget({

  });

  $('table td').on('change', function(evt, newValue) {

    if ($(this).hasClass('td-native')) {
      var splitValue = newValue.split(' ');
      var output = '';
      splitValue.forEach(function(item){
        output += '<span class="label label-default">'+item+'</span>';
      });
      $(this).html(output);

    } else if ($(this).hasClass('td-image')) {
      console.log(newValue);
      $(this).html('<img height="70" class="image-border js-tooltip" data-toggle="tooltip" data-placement="top" title="Click to change url of image" src="'+newValue+'" />');
      $('.js-tooltip').tooltip();
    } else if ($(this).hasClass('td-ancient')) {
      editor: false;
      return false;
    }

    /*$.post( 'test.php', { name: 'John', time: '2pm' } );*/

  });

  $('input[name="switch"]').on('switchChange.bootstrapSwitch', function(event, state) {
    console.log(this); // DOM element
    console.log(event); // jQuery event
    console.log(state); // true | false
  });

  // Tooltip
  $('.js-tooltip').tooltip();

});