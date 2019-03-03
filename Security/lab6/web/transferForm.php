<?php
	session_start();
	require_once "webUtils.php";

	if(!isset($_SESSION['signedIn'])){
		header('Location: ../index.php');
		exit();
	}

	if(isset($_POST['name'])) {
		$name = $_POST['name'];
		$account = $_POST['account'];
		$amountZl = $_POST['zl'];
		$amountGr = $_POST['gr'];
		$title = $_POST['title'];

		$error = false;

		if(!preg_match('/^[a-zA-Z0-9ąćęłńóśźżł. ]+$/' , $name)) {
			$error = true;
			$_SESSION['errorName'] = "Incorrect receiver name.";
		} else {
			$_SESSION['saveName'] = $name;
		}

		if(!preg_match('/^[0-9]+$/' , $account)) {
			$error = true;
			$_SESSION['errorAccount'] = "Account number contains only numbers.";
		} else if(!preg_match('/^[0-9]{15,32}$/' , $account)) {
			$error = true;
			$_SESSION['errorAccount'] = "Account number has to between 15 and 32 numbers.";
			$_SESSION['saveAccount'] = $account;
		} else {
			$_SESSION['saveAccount'] = $account;
		}

		$_SESSION['saveZl'] = $amountZl;
		$_SESSION['saveGr'] = $amountGr;
		$_SESSION['saveTitle'] = $title;;

		if(!$error) {
			$amount = $amountZl + $amountGr / 100;
			$_SESSION['transfer'] = true;
			$_SESSION['payName'] = $name;
			$_SESSION['payAccount'] = $account;
			$_SESSION['payAmount'] = $amount;
			$_SESSION['payTitle'] = htmlentities($title, ENT_QUOTES, "UTF-8");
			header('Location: confirmTransfer.php');
			exit();
		}

		if(isset($_SESSION['transfer'])) {
			unset($_SESSION['transfer']);
		}
	}
?>
<html>
    <head>
        <title>SecurBank Transfer Form</title>
		<meta charset="utf-8">
		<meta name="author" content="Kacper Zielinski">
		<meta name="viewport" content = "width=device-width, initial-scale=1.0"/>
		
        <link rel="stylesheet" href="../static/css/form.css" />
    </head>
    <body>
		<div class="formDiv">
			<a href="home.php" class="rightTopLink">Return</a>
			<br><br>
			<form method="post">
				Receiver: 
				<br>
				<input type="text" name="name" size="30" required value="<?php saveValue('saveName') ?>" />
				<br>
				<?php printError('errorName') ?>
				
				Account number: 
				<br>
				<input type="text" name="account" size="30" required value="<?php saveValue('saveAccount') ?>" />
				<br>
				<?php printError('errorAccount') ?>
				
				Amount: 
				<br>
				<input type="number" name="zl" min="0" required value="<?php saveValue('saveZl') ?>" /> zł
				<input type="number" name="gr" min="0" max="99" required value="<?php saveValue('saveGr') ?>" /> gr 
				<br>
				
				Title: 
				<br>
				<input type="text" name="title" size="30" required value="<?php saveValue('saveTitle')?>" />
				<br>
				<input type="submit" value="Transfer" />
			</form>
		</div>
    </body>
</html>
