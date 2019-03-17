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
		<script src='https://www.google.com/recaptcha/api.js'></script>
    </head>
    <body>
		<div class="formDiv">
			<form action="web/login.php" method="post">
				<label for="login">Login:</label>
				<input type="text" id="login" name="login" placeholder="Your login..">

				<label for="password">Password:</label>
				<input type="password" id="password" name="password" placeholder="Your password..">
				
				<div class="g-recaptcha" data-sitekey="6LdNGX4UAAAAAO5nKjKGC8UT6O6_u3WLCZytdY3n"></div>
				<input type="submit" value="Submit">
			</form>
			
			<p>If you don't remember password, please <a href="web/forgotPassword.php" class="link">reset</a>.</p>
			<p>If you don't have a login, please <a href="web/register.php" class="link">register</a>.</p>
			
			<?php printErrorWithMessage('error', 'Incorrect login or password.'); ?>
			<?php printErrorWithMessage('invalidCaptcha', 'Invalid captcha.'); ?>
		</div>
    </body>
</html>
