<!DOCTYPE html>
<html lang="en">
<head>
    <meta content="text/html; charset=utf-8" http-equiv="content-type">
    <title>Periodic Table of Elements</title>
    
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">    
</head>
<body>

<div class="container">

<h1>Periodic Table of Elements</h1>
<div class="offset1 span3">


<table id="elements" class="table table-striped table-bordered">
  <thead>
    <tr>
      <th class="span1">Periodic number</th>
      <th class="span2">Element name</th>
    </tr>
  </thead>
  <tbody>
<?php foreach ($elements as $element): ?>
    <tr>
      <td class="element-number text-right"><?php echo $element->number; ?></td>
      <td class="element-name text-center"><?php 
        echo htmlspecialchars($element->name); 
      ?></td>
    </tr>
<?php endforeach; ?>
  </tbody>
</table>

</div>
</div>

<script src="/static/js/jquery.min.js"></script>
<script src="/static/js/mindmup-editabletable.js"></script>
<script src="/static/js/bootstrap.min.js"></script>

<script type="text/javascript">
$(function(){
  $('#elements').editableTableWidget();

  function isValidElementNumber(value) {
    var tmp = parseInt(Number(value), 10);
    return Number.isSafeInteger(tmp) && tmp >= 1 && tmp <= 999;
  }

  $('.element-number').on('validate', function(evt, value) {
    return isValidElementNumber(value);   
  });
  
  $('.element-number').on('change', function(evt, value) {
    if (!isValidElementNumber(value)) return false;

    $.ajax({
      url: '/elements/' + value,
      type: 'PUT',
      data: {
        number: value
      }
      success: function(result) {
        console.log(result);
      },
      error: function(result) {
        console.log(result);
      }
    })    
  });

  /*
  $('.el-row').hover(function(){    
  });
  $('span.el-name').click(function(){    

  
  
  
    var div = $('<div/>');
    
    var input = $('<input type="text"/>');
    input.val($(this).text());
    
    var button = $('<input type="text"/>');
    
    div.append(input);
    div.append(button);
    
    $(this).replaceWith(input);
  });
  
  */
});
</script>

</body>
</html>
