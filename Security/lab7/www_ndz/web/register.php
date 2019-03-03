<?php
	session_start();
	require_once "webUtils.php";

	if(isset($_POST['login'])) {
		$error = false;
		$login = $_POST['login'];
		$name = $_POST['name'];
		$email = $_POST['email'];
		$password1 = $_POST['password1'];
		$password2 = $_POST['password2'];

		if(!preg_match('/^[a-zA-Z0-9]*$/' , $login)) {
			$error = true;
			$_SESSION['errorLogin'] = "Login must only contain alphanumeric characters.";
		} else if(!preg_match('/^[a-zA-Z0-9]{3,20}$/' , $login)) {
			$error = true;
			$_SESSION['errorLogin'] = "Login must only contains between 3 and 20 characters.";
		} else {
			$_SESSION['saveLogin'] = $login;
		}

		if(!preg_match('/^[A-Za-ząćęłńóśźżł]+$/', $name)) {
			$error = true;
			$_SESSION['errorName'] = "Allowed only alphabetical characters.";
		} else {
			$_SESSION['saveName'] = $name;
		}

		$emailBool = filter_var($email, FILTER_VALIDATE_EMAIL);
		if(!$emailBool) {
			$error = true;
			$_SESSION['errorEmail'] = "Invalid email address.";
		} else {
			$_SESSION['saveEmail'] = $email;
		}

		if(strlen($password1) < 8 || strlen($password1) > 50) {
			$error = true;
			$_SESSION['errorPassword'] = "The password must have more than 8 characters.";
		} else if($password1 != $password2) {
			$error = true;
			$_SESSION['errorPassword'] = "Passwords don't match.";
		}

		if(!isset($_POST['checkbox1'])) {
			$error = true;
			$_SESSION['errorCheckbox'] = "You must accept terms and conditions!";
		}
		
		require_once "../includes/dbConnector.php";
		$connector = DbConnector::getInstance();

		if(!isset($_SESSION['errorEmail'])) {
			if($connector->hasEmail($email)) {
				$error = true;
				$_SESSION['errorEmail'] = "The email address already exists.";
			}
		}
		
		if(!isset($_SESSION['errorLogin'])) {
			if($connector->hasLogin($login)) {
				$error = true;
				$_SESSION['errorLogin'] = "Such a user already exists.";
			}
		}
		
		if(!$error) {
			$result = $connector->register($login, $password1, $name, $email);
			if($result) {
				$_SESSION['name'] = $name;
				header('Location: registerSuccessful.php');
			} else {
				echo "Registration error";
			}
		}
	}
?>
<!DOCTYPE html>
<html>
    <head>
        <title>SecurBank - Register</title>
		<meta charset="utf-8">
		<meta name="author" content="Kacper Zielinski">
		<meta name="viewport" content = "width=device-width, initial-scale=1.0"/>
		
        <link rel="stylesheet" href="../static/css/form.css" />
		<script src='https://www.google.com/recaptcha/api.js'></script>
    </head>
    <body>
		<div class="formDiv">
			<a href="../index.php" class="rightTopLink">Return to login page</a>
			<br><br>
			<form method="post">
				Login: <?php printOnlyErrorWithMessage('errorLogin', '*') ?>
				<br> 
				<input type="text" name="login" required value="<?php saveValue('saveLogin') ?>" />
				<?php printError('errorLogin') ?>
				<br>
				
				Name: <?php printOnlyErrorWithMessage('errorName', '*') ?>
				<br> 
				<input type="text" name="name" required value="<?php saveValue('saveName') ?>" />
				<?php printError('errorName') ?>
				<br>
				
				E-mail: <?php printOnlyErrorWithMessage('errorEmail', '*') ?>
				<br> 
				<input type="text" name="email" required value="<?php saveValue('saveEmail') ?>" />
				<?php printError('errorEmail') ?>
				<br>
				
				Password: <?php printOnlyErrorWithMessage('errorPassword', '*') ?>
				<br> 
				<input type="password" required name="password1" />
				<?php printError('errorPassword') ?>
				<br>
				
				Repeat password: <?php printOnlyErrorWithMessage('errorPassword', '*') ?>
				<br> 
				<input type="password" required name="password2" />
				<br>

				<label><input type="checkbox" name="checkbox1" /> I accept the terms and conditions</label>
				<?php printErrorWithMessage('errorCheckbox', '<br>You must accept terms and conditions!') ?>
				
				<br>
				<br>
				<input type="submit" value="Register" />
			</form>
		</div>
    </body>
</html>



