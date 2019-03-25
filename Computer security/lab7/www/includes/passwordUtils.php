<?php
class HashWithSalt {
	public static function getSalt(int $numberBit) {
		return openssl_random_pseudo_bytes($numberBit);
	}

	public static function hashPassword($password, $salt) {
		$passwordWithSalt = $password.$salt;
		// PASSWORD_DEFAULT - bcrypt algorithm (default as of PHP 5.5.0)
		return password_hash($passwordWithSalt, PASSWORD_DEFAULT);
	}

	public static function checkPassword($password, $salt, $hashPassword) {
		$passwordWithSalt = $password.$salt;
		return password_verify($passwordWithSalt, $hashPassword);
	}
}
?>
