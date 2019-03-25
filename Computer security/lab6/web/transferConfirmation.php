<?php
	session_start();

	if(!isset($_SESSION['transferConfirmation']) || $_SESSION['transferConfirmation'] != true) {
		header('Location: home.php');
		exit();
	}

	require_once "../includes/dbConnector.php";
	$connector = DbConnector::getInstance();

	$result = $connector->addTransfer($_SESSION['userId'], $_SESSION['payAccount'], $_SESSION['payName'], $_SESSION['payAmount'], $_SESSION['payTitle']);
	$balance = $_SESSION['userBalance'];
	$_SESSION['userBalance'] = $connector->updateAccount($_SESSION['userId'], $balance, $_SESSION['payAmount']);

	unset($_SESSION['transferConfirmation']);
	unset($_SESSION['saveName']);
	unset($_SESSION['saveAccount']);
	unset($_SESSION['saveZl']);
	unset($_SESSION['saveGr']);
	unset($_SESSION['saveTitle']);
?>
<html>
    <head>
        <title>SecurBank - Transfer Confirmation</title>
		<meta charset="utf-8">
		<meta name="author" content="Kacper Zielinski">
		<meta name="viewport" content = "width=device-width, initial-scale=1.0"/>
        <link rel="stylesheet" href="../static/css/form.css" />
    </head>
    <body>
		<div class="formDiv">
			<?php
				if($result) {
					echo "<h1>Money have been transfered.</h1>";
				} else {
					echo "<h1>An error occured during transfer.</h1>";
				}
			?>
			<div>
				<p> <b>Receiver:</b> <?php echo $_SESSION['payName'] ?> </p>
				<p> <b>Account number:</b>  <?php echo $_SESSION['payAccount'] ?> </p>
				<p> <b>Amount:</b>  <?php echo $_SESSION['payAmount'] ?> zł </p>
				<p> <b>Title:</b>  <?php echo $_SESSION['payTitle'] ?> </p>
			</div>

			<a href="home.php" class="leftDownLink">Return to homepage</a>
		</div>
    </body>
</html>