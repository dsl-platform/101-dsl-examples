// wonderData - JS Object representation of a single Wonder
// wonderJson - JSON representation of a single Wonder
// wonderHtml - HTML table entry for a single Wonder

var rest = new RestClient('127.0.0.1', 9988)
var templater = new Templater();
var manager = new Manager(rest, templater);

$(document).ready(function(){
  $('.td-ancient').click(function(){
    return false;
  })

  // Editable Table
  $('#mainTable').editableTableWidget({
  });

  // Ignore click
  $('table').on('click', '.td-ancient', function(evt) {
    return false;
  });

  // Convert native name spans to text for editing.
  $('table').on('click', '.td-native', function(evt) {
    var nameStr = '';
    var nameArr = $(this).find('span').map(function(index, elem) {
      var text = $(this).text();
      if (nameStr === '') {
        nameStr += text;
      } else {
        nameStr += ', ' + text;
      }
    });

    $(this).html(nameStr);
  });

  // Convert images to text for editing.
  $('table').on('click', '.td-image', function(evt) {
    var url = $(this).find('img').attr('src');
    $(this).html(url);
  });

  // Ignore click
  $('table').on('click', '.td-delete', function(evt) {
    return false;
  });

  // After editing, convert text back to spans or images.
  $('table').on('change', 'td', function(evt, newValue) {
    var ret = null;
    
    if ($(this).hasClass('td-ancient')) {
      editor: false;
      ret = false;
    } else if ($(this).hasClass('td-english')) {
    } else if ($(this).hasClass('td-native')) {
      var splitValue = newValue.split(/\s*,\s*/g);
      var output = '';
      splitValue.forEach(function(item){
        output += '<span class="label label-default">'+item+'</span>';
      });
      $(this).html(output);
    } else if ($(this).hasClass('td-image')) {
      $(this).html('<img height="70" class="image-border js-tooltip" data-toggle="tooltip" data-placement="top" title="Click to change url of image" src="'+newValue+'" />');
      $('.js-tooltip').tooltip();
    } else if ($(this).hasClass('td-delete')) {
      editor: false;
      ret = false;
    }

    /*
    var row = $(this).parent();
    var id = row.attr('id');
    var newWonderData = templater.htmlToData(row);
    manager.updateWonder(id, newWonderData);*/
    return ret;
  });

/*
  $('input[name="switch"]').on('switchChange.bootstrapSwitch', function(event, state) {
    console.log(this); // DOM element
    console.log(event); // jQuery event
    console.log(state); // true | false
  });
*/
  // Tooltip
  $('.js-tooltip').tooltip();

  manager.init();
  manager.findAll();


  // Bootstrap Switch

  $('table').on('DOMNodeInserted', function (evt, a) {
    var chbox = $(evt.target).find('td.td-ancient > input');
    chbox.bootstrapSwitch({
      onText   : 'Old',
      offText  : 'New',
      onColor  : 'success',
      offColor : 'danger'
    });
  });

  $('.reset-all').click(function() {
    manager.resetAll();
  });

  $('.insert-new').click(function() {
    manager.insertNew();
  });

  $('table').on('click', '.td-delete > input', function(evt, newValue) {
    var row = $(this).parent().parent();
    var id = row.attr('id');
    manager.deleteWonder(id);
  });
});
