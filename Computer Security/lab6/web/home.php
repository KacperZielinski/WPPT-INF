<?php
	session_start();

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
		</div>
    </body>
</html>