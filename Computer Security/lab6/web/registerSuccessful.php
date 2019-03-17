<?php
	session_start();

	if(!isset($_SESSION['name'])) {
		header('Location: register.php');
		exit();
	}
?>
<!DOCTYPE html>
<html>
    <head>
		<meta charset="utf-8">
		<meta name="author" content="Kacper Zielinski">
		<meta name="viewport" content = "width=device-width, initial-scale=1.0"/>
		
        <title>SecurBank - Register Successful</title>
		
        <link rel="stylesheet" href="../static/css/form.css" />
    </head>
    <body>
		<div class="formDiv">
			<?php 
				echo "<p>Welcome <b>".$_SESSION['name'].'</b></p>';
			?>
			<p> Account has been created. </p>
			<p> You can login now.</p>
			<p><a href="login.php" class="leftDownLink">Login</a></p>
		</div>
    </body>
</html>