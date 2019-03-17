<?php
	session_start();

	require_once "../includes/dbConnector.php";
	if(!isset($_POST['login']) || !isset($_POST['password'])) {
		header('Location: ../index.php');
		exit();
	}
	
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
		header('Location: home.php');
	} else {
		$_SESSION['signedIn'] = false;
		$_SESSION['error'] = true;
		header('Location: ../index.php');
	}
	$connector->closeDb();
?>