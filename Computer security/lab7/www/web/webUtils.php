<?php
function printError($sessionValue) {
	if(isset($_SESSION[$sessionValue])) {
		echo '<div class="error">'.$_SESSION[$sessionValue].'</div><br>';
		unset($_SESSION[$sessionValue]);
	}
}

function printErrorWithMessage($sessionValue, $message) {
	if(isset($_SESSION[$sessionValue])) {
		echo '<div class="error">'.$message.'</div>';
		unset($_SESSION[$sessionValue]);
	}
}

function printOnlyError($sessionValue) {
	if(isset($_SESSION[$sessionValue])) {
		echo '<div class="error">'.$_SESSION[$sessionValue].'</div>';
	}
}

function printOnlyErrorWithMessage($sessionValue, $message) {
	if(isset($_SESSION[$sessionValue])) {
		echo '<div class="error">'.$message.'</div>';
	}
}

function saveValue($sessionValue) {
	if(isset($_SESSION[$sessionValue])) {
		echo $_SESSION[$sessionValue];
		unset($_SESSION[$sessionValue]);
	}
}
?>
