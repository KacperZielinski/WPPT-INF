<?php
	session_start();
	require_once "webUtils.php";

	if(isset($_POST['email'])){
		$error = false;
		$email = $_POST['email'];

		$error = false;

		$emailBool = filter_var($email, FILTER_VALIDATE_EMAIL);
		if(!$emailBool) {
			$error = true;
			$_SESSION['errorEmail'] = "Invalid email address.";
		} else {
			$_SESSION['saveEmail'] = $email;
		}
		
		$secret = '6LdNGX4UAAAAAHooCrQd43aYCPzys2o_TxCFye9A';
		$response = $_POST["g-recaptcha-response"];
		$url = 'https://www.google.com/recaptcha/api/siteverify';
		
		$data = array(
			$secret = '6LdNGX4UAAAAAHooCrQd43aYCPzys2o_TxCFye9A',
			$response = $_POST["g-recaptcha-response"]
		);
		
		$options = array(
			'http' => array(
				'method' => 'POST',
				'content' => http_build_query($data)
			)
		);
		
		$context = stream_context_create($options);
		$verify = file_get_contents("{$url}?secret={$secret}&response={$response}");
		$captcha_success = json_decode($verify);
		
		if($captcha_success->success == true) {
			unset($_SESSION['invalidCaptcha']);
		} else {
			$_SESSION['invalidCaptcha'] = true;
			$error = true;
		}

		if(!$error) {
			require_once "../includes/dbConnector.php";
			$connector = DbConnector::getInstance();
			$result = $connector->sendResetToken($email);

			if($result) {
				$_SESSION['reset'] = "Reset token has been sent.";
				header('Location: resetPassword.php');
			} else {
				$_SESSION['errorEmail'] = "Invalid email address.";
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
		<script src='https://www.google.com/recaptcha/api.js'></script>
    </head>
    <body>
		<div class="formDiv">
			<a href="../index.php" class="rightTopLink">Sign in</a>
			<br>
			<br>
			<form method="post">				
				E-mail: 
				<br> 
				<input type="text" name="email" required value="<?php saveValue('saveEmail') ?>" />
				<br>
				<?php printError('errorEmail') ?>
				<div class="g-recaptcha" data-sitekey="6LdNGX4UAAAAAO5nKjKGC8UT6O6_u3WLCZytdY3n"></div>
				<?php printErrorWithMessage('invalidCaptcha', 'Invalid captcha!') ?>
				<input type="submit" value="Reset password" />
			</form>
		</div>
    </body>
</html>
