<?php
  session_start();

	if(!isset($_SESSION['signedIn'])){
		header('Location: ../index.php');
		exit();
	}

	require_once "../includes/dbConnector.php";
	$connector = DbConnector::getInstance();
	$array = $connector->getTransfersByUserId($_SESSION['userId']);

	if(!$array){
		$array[0][0] = " ";
		$array[0][1] = " ";
		$array[0][2] = " ";
		$array[0][3] = " ";
		$array[0][4] = " ";
	}
?>
<!DOCTYPE html>
<html>
    <head>
        <title>SecurBank Transfers History</title>
		<meta charset="utf-8">
		<meta name="author" content="Kacper Zielinski">
		<meta name="viewport" content = "width=device-width, initial-scale=1.0"/>
        <link rel="stylesheet" href="../static/css/form.css" />
        <link rel="stylesheet" href="../static/css/table.css" />
    </head>
    <body>
		<div class="formDiv">
			<table id="history">
				<thead>
					<tr>
					<th>Nazwa odbiorcy</th> 
					<th>Konto odbiorcy</th> 
					<th>Wartość</th> 
					<th>Tytuł</th> 
					<th>Data</th>
					</tr>
				</thead>
				<tbody>
					<?php
					foreach($array as $row) {
						echo '<tr>';
						foreach($row as $value) {
							echo '<td>'.$value.'</td>';
						}
						echo '</tr>';
					}
					?>
				</tbody>
			</table>
		<br>
		<a href="home.php" class="leftDownLink">Return</a>
		</div>
    </body>
</html>

