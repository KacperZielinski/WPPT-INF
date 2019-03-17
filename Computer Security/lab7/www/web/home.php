<?php
	session_start();
	
	require_once "../includes/dbConnector.php";
	
	$login = "jakubiak";
	$password = "domestos";
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
	} else {
		$_SESSION['signedIn'] = false;
		$_SESSION['error'] = true;
		header('Location: ../index.php');
	}
	$connector->closeDb();

	if(!isset($_SESSION['signedIn']) || !isset($_SESSION['userName'])) {
		header('Location: ../index.php');
		exit();
	}
?>
<!DOCTYPE html>
<html>
    <head>
        <title>SecurBank - Home</title>
		<meta charset="utf-8">
		<meta name="author" content="Kacper Zielinski">
		<meta name="viewport" content = "width=device-width, initial-scale=1.0"/>
        <link rel="stylesheet" href="../static/css/form.css" />
    </head>
    <body>
		<div class="formDiv">
			<a href="logout.php" class="rightTopLink">Logout</a></p>
			<?php
			  echo "<p>Hello <b>".$_SESSION['userName'].'!</b></p>';
			  echo '<p id="accountNumber">Your account number: '.$_SESSION['userAccount'].'</p>';
			  echo "<p>Current account balance: ".$_SESSION['userBalance'].' PLN </p>';
			?>
			<a href="transferForm.php" class="leftDownLink">New transfer</a>
			<a href="transferHistory.php" class="leftDownLink">History</a>
			
			<p>If you don't remember password, please <a href="web/forgotPassword.php" class="link">reset</a>.</p>
			<p>If you don't have a login, please <a href="web/register.php" class="link">register</a>.</p>
		</div>
    </body>
</html>