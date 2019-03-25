<?php
	session_start();
	require_once "webUtils.php";
	
	if(!isset($_SESSION['reset'])) {
		header('Location: forgotPassword.php');
		exit();
	}

	if(isset($_POST['email'])) {
		$error = false;
		$email = $_POST['email'];
		$token = $_POST['token'];
		$password1 = $_POST['password1'];
		$password2 = $_POST['password2'];

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

		require_once "../includes/dbConnector.php";
		$connector = DbConnector::getInstance();

		if(!$error) {
			$result = $connector->changePassword($email, $password1, $token);

			if($result) {
				$_SESSION['reset'] = "The password has been changed correctly.";
				header('Location: resetSuccessful.php');
			} else {
				$_SESSION['reset'] = "Password change failed";
				header('Location: ../index.php');
			}
		}
	}

?>
<!DOCTYPE html>
<html>
    <head>
        <title>SecurBank - Reset Password</title>
		<meta charset="utf-8">
		<meta name="author" content="Kacper Zielinski">
		<meta name="viewport" content = "width=device-width, initial-scale=1.0"/>
        <link rel="stylesheet" href="../static/css/form.css" />
    </head>
    <body>
		<div class="formDiv">
			<a href="../index.php" class="rightTopLink">Sign In</a>
			<br>
			<br>
			<form method="post">
				Token:<br>
				<input type="text" name="token" required /> 
				<br>
				<?php printError('errorToken') ?>
				
				E-mail: 
				<br> 
				<input type="text" name="email" required value="<?php saveValue('saveEmail') ?>" />
				<br>
				<?php printError('errorEmail') ?>
				
				New password: 
				<br>
				<input type="password" name="password1" required /> 
				<br>
				<?php printError('errorPassword') ?>
				
				Repeat password: 
				<br>
				<input type="password" name="password2" required /> 
				<br><br>
				<input type="submit" value="Reset password" />
			</form>
		</div>
    </body>
</html>
