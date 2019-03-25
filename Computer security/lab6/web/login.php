<?php
	session_start();

	require_once "../includes/dbConnector.php";
	if(!isset($_POST['login']) || !isset($_POST['password'])) {
		header('Location: ../index.php');
		exit();
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
	echo("{$url}?secret={$secret}&response={$response}");
	echo($verify);
	$captcha_success = json_decode($verify);
	
	if($captcha_success->success == true) {
		$_SESSION['invalidCaptcha'] = false;
	} else {
		$_SESSION['invalidCaptcha'] = true;
		unset($_SESSION['error']);
	}

	if($_SESSION['invalidCaptcha'] == false) {
		$login = $_POST['login'];
		$password = $_POST['password'];
		$connector = DbConnector::getInstance();
		$userData = $connector->login($login, $password);
		
		if($userData) {
			$_SESSION['signedIn'] = true;
			$_SESSION['userId'] = $userData['id'];
			$_SESSION['userLogin'] = $userData['login'];
			$_SESSION['userAccount'] = $userData['account'];
			$_SESSION['userName'] = $userData['name'];
			$_SESSION['userBalance'] = $userData['balance'];
			
			unset($_SESSION['error']);
			unset($_SESSION['invalidCaptcha']);
			header('Location: home.php');
		} else {
			$_SESSION['signedIn'] = false;
			$_SESSION['error'] = true;
			unset($_SESSION['invalidCaptcha']);
			header('Location: ../index.php');
		}
		$connector->closeDb();
	} else {
		$_SESSION['signedIn'] = false;
		$_SESSION['invalidCaptcha'] = true;
		header('Location: ../index.php');
	}
?>