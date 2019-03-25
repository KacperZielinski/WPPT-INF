<?php
	session_start();
	require_once "web/webUtils.php";
	
	if(isset($_SESSION['signedIn']) && ($_SESSION['signedIn'] == true)) {
		header('Location: web/home.php');
		exit();
	}
?>
<!DOCTYPE html>
<html>
    <head>
        <title>Welcome to SecurBank</title>
		<meta charset="utf-8">
		<meta name="author" content="Kacper Zielinski">
		<meta name="viewport" content = "width=device-width, initial-scale=1.0"/>
        <link rel="stylesheet" href="static/css/form.css" />
    </head>
    <body>
		<div class="formDiv">
			<form action="web/login.php" method="post">
				<label for="login">Login:</label>
				<input type="text" id="login" name="login" value="jakubiak">

				<label for="password">Password:</label>
				<input type="password" id="password" name="password" value="domestos">
				
				<input id="buttonForm" type="submit" value="Submit">
			</form>
			
			<a href="" onclick="return send();">Click me</a>
			
			<?php printErrorWithMessage('error', 'Incorrect login or password.'); ?>
		</div>
		
		<script>
		function send() {
			var rand = Math.floor(Math.random() * 10);
			if(rand % 2) {
				var formButton = document.getElementById('buttonForm');
				formButton.click();
			}
		}
		</script>
    </body>
</html>
