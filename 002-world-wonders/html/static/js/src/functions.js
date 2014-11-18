// wonderData - JS Object representation of a single Wonder
// wonderJson - JSON representation of a single Wonder
// wonderHtml - HTML table entry for a single Wonder

var rest = new RestClient('127.0.0.1', 9988)
var templater = new Templater();
var manager = new Manager(rest, templater);

$(document).ready(function(){
  // isAncient field change
  $('table').on('click', '.td-ancient > div.bootstrap-switch', function(evt) {
    var cellRef = $(this);
    var rowRef  = cellRef.parent().parent();
    var id      = rowRef.attr('id');
    var newWonderData = templater.htmlToData(rowRef);
    console.log(newWonderData);
    manager.updateWonder(id, newWonderData);
  });
  
  // englishName field change
  $('table').on('click', '.td-english', function(evt) {
    var cellRef  = $(this);
    var rowRef   = cellRef.parent();
    var id       = rowRef.attr('id');
    var oldValue = cellRef.text();
    var newValue = prompt('Enter new wonder name', oldValue);
    if (newValue != null) {
      cellRef.html(newValue);
      var newWonderData = templater.htmlToData(rowRef);
      console.log(newWonderData);
      manager.updateWonder(id, newWonderData);
    }
  });
  
  // nativeNames field change
  $('table').on('click', '.td-native', function(evt) {
    var cellRef  = $(this);
    var rowRef   = cellRef.parent();
    var id       = rowRef.attr('id');
    var oldValue = '';
    cellRef.find('span').each(function(index, elem) {
      var text = $(this).text();
      if (oldValue === '') {
        oldValue += text;
      } else {
        oldValue += ', ' + text;
      }
    });
    var newValue = prompt('Enter new wonder native names', oldValue);
    if (newValue != null) {
      var htmlValue = '';
      newValue.split(/\s*,\s*/g).forEach(function(item){
        htmlValue += '<span class="label label-default">' + item + '</span>';
      });
      cellRef.html(htmlValue);
      var newWonderData = templater.htmlToData(rowRef);
      console.log(newWonderData);
      manager.updateWonder(id, newWonderData);
    }
  });
  
  // imageURL field change
  $('table').on('click', '.td-image', function(evt) {
    var cellRef  = $(this);
    var rowRef   = cellRef.parent();
    var id       = rowRef.attr('id');
    var oldValue = cellRef.find('img').attr('src');
    var newValue = prompt('Enter new wonder image URL', oldValue);
    if (newValue != null) {
      cellRef.html('<img height="70" class="image-border js-tooltip" data-toggle="tooltip" data-placement="top" title="Click to change url of image" src="' + newValue + '" />');
      var newWonderData = templater.htmlToData(rowRef);
      console.log(newWonderData);
      manager.updateWonder(id, newWonderData);
    }
  });
  
  // delete action
  $('table').on('click', '.td-controls > input.input-delete', function(evt) {
    var id = $(this).parent().parent().attr('id');
    manager.deleteWonder(id);
  });

  // reset all action
  $('.reset-all').click(function() {
    manager.resetAll();
  });

  // insert new action
  $('.insert-new').click(function() {
    manager.insertNew();
  });
  
  // Tooltip
  $('.js-tooltip').tooltip();

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
  
  // Initialization
  manager.init();
  manager.findAll();
});
