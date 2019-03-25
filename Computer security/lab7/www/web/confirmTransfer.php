<?php
	session_start();

	if(!isset($_SESSION['transfer']) || $_SESSION['transfer'] != true){
		header('Location: transferForm.php');
		exit();
	}

	unset($_SESSION['transfer']);
	$_SESSION['transferConfirmation'] = true;
?>
<!DOCTYPE html>
<html>
    <head>
        <title>SecurBank - Confirm transfer</title>
		<meta charset="utf-8">
		<meta name="author" content="Kacper Zielinski">
		<meta name="viewport" content = "width=device-width, initial-scale=1.0"/>
        <link rel="stylesheet" href="../static/css/form.css" />
    </head>
    <body>
		<div class="formDiv">
			<p> <b>Receiver:</b> <?php echo $_SESSION['payName'] ?> </p>
			<p> <b>Account number:</b>  <?php echo $_SESSION['payAccount'] ?> </p>
			<p> <b>Amount:</b>  <?php echo $_SESSION['payAmount'] ?> zł </p>
			<p> <b>Title:</b>  <?php echo $_SESSION['payTitle'] ?> </p>
			<br><br>
			<a href="transferForm.php" class="leftDownLink">Cancel</a>
			<a href="transferConfirmation.php" class="leftDownLink">Confirm</a>
		</div>
    </body>
</html>





