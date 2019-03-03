<?php
	session_start();

	if(!isset($_SESSION['reset'])) {
		header('Location: resetPassword.php');
		unset($_SESSION['reset']);
		exit();
	}
?>
<html>
    <head>
		<meta charset="utf-8">
		<meta name="author" content="Kacper Zielinski">
		<meta name="viewport" content = "width=device-width, initial-scale=1.0"/>
		
        <title>SecurBank - Reset successful</title>
		
        <link rel="stylesheet" href="../static/css/form.css" />
    </head>
    <body>
		<div class="formDiv">
			<?php echo $_SESSION['reset']; ?>
			<p><a href="logout.php" class="leftDownLink">Logout</a></p>
		</div>
    </body>
</html>