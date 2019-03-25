<?php
require_once "passwordUtils.php";

class DbConnector {

	private static $instance;
	private $dbHandler;

	public static function getInstance() {
		if(self::$instance == null) {
			self::$instance = new DbConnector();
		}
		return self::$instance;
	}

	private function __construct() {
		$hostDB = "localhost:3306";
		$userDB = "root";
		$passwordDB = "";
		$nameDB = "SEC_BANK";
		$this->dbHandler = new mysqli($hostDB, $userDB, $passwordDB, $nameDB);
	}

	public function closeDb() {
		$this->dbHandler->close();
		self::$instance = null;
	}

	public function login($login, $password) {
		$loginSafe = htmlentities($login, ENT_QUOTES, "UTF-8");
		$loginSafe = mysqli_real_escape_string($this->dbHandler, $loginSafe);

		$sql = "SELECT * FROM users u INNER JOIN salts s ON u.id = s.user_id WHERE u.login='$loginSafe'";

		if($result = $this->dbHandler->query($sql)) {
			$numberUsers = $result->num_rows;
			
			if($numberUsers > 0) {
				$row = $result->fetch_assoc();
				$salt = $row['salt'];
				$dbPassword = $row['password'];

				$isProperPassword = HashWithSalt::checkPassword($password, $salt, $dbPassword);
				
				if($isProperPassword) {
					$userData['id'] = $row['id'];
					$userData['login'] = $row['login'];
					$userData['name'] = $row['name'];
					$userData['account'] = $row['account_number'];
					$userData['balance'] = $row['balance'];
					return $userData;
				}
				$result->free_result();
			}
		}
		return false;
	}

	public function register($login, $password, $name, $email) {
		$salt = HashWithSalt::getSalt(32);
		$hashedPasswordWithSalt = HashWithSalt::hashPassword($password, $salt);
		
		$accountNumber;
		for ($i = 0; $i < 8; $i++) {
			$accountNumber .= rand(1000,10000);
		}

		$sql = "INSERT INTO users VALUES(NULL, '$login', '$hashedPasswordWithSalt', '$name', '$email', '$accountNumber', 0.0)";

		if($this->dbHandler->query($sql)) {
			$id = $this->getId($login);
			$sql2 = "INSERT INTO salts VALUES(NULL, '$id', '$salt')";
			
			if($this->dbHandler->query($sql2)) {
				return true;
			}
		}
		return false;
	}

	public function getId($login):int {
		$loginSafe= mysqli_real_escape_string($this->dbHandler, $login);
		$sql = "SELECT * FROM users WHERE login='$loginSafe'";

		if($result = $this->dbHandler->query($sql)) {
			$numberUsers = $result->num_rows;
			if($numberUsers > 0) {
				$row = $result->fetch_assoc();
				$userId = $row['id'];
				$result->free_result();

				return $userId;
			}
		}
		return false;
	}

	public function hasEmail($email):bool {
		$emailSafe = mysqli_real_escape_string($this->dbHandler, $email);
		$sql = "SELECT * FROM users WHERE email='$emailSafe'";

		if($result = $this->dbHandler->query($sql)) {
			$numberUsers = $result->num_rows;
			return boolval($numberUsers);
		}
		return false;
	}

	public function hasLogin($login):bool {
		$loginSafe= mysqli_real_escape_string($this->dbHandler, $login);
		$sql = "SELECT * FROM users WHERE login='$loginSafe'";

		if($result = $this->dbHandler->query($sql)) {
			$numberUsers = $result->num_rows;
			return boolval($numberUsers);
		}
		return false;
	}

	public function addTransfer($userId, $account, $receiver, $amount, $title) {
		$safeTitle =  mysqli_real_escape_string($this->dbHandler, $title);
		$sql = "INSERT INTO transfer VALUES(NULL, '$userId', '$receiver', '$account', '$amount', '$title', NOW())";

		if($this->dbHandler->query($sql)) {
			return true;
		}
		return false;
	}

	public function updateAccount($userId, $amount, $cost) {
		$newAmount = $amount - $cost;
		$sql = "UPDATE users SET balance='$newAmount' WHERE id='$userId'";

		if($this->dbHandler->query($sql)) {
			return $newAmount;
		}
		return false;
	}

	public function getTransfersByUserId($userId) {
		$sql = "SELECT * FROM transfer WHERE user_id='$userId' ORDER BY date DESC";
		$arrayRow;
		$i = 0;
		if($result = $this->dbHandler->query($sql)) {
			$numberUsers = $result->num_rows;
			if($numberUsers > 0) {
				while($row = $result->fetch_assoc()) {
					$arrayRow[$i][0] = $row['receiver'];
					$arrayRow[$i][1] = $row['to_account_number'];
					$arrayRow[$i][2] = $row['amount'];
					$arrayRow[$i][3] = $row['title'];
					$arrayRow[$i][4] = $row['date'];
					$i++;
				}
				$result->free_result();
				return $arrayRow;
			}
		}
		return false;
	}
	
	public function sendResetToken($email) {
		$emailSafe = mysqli_real_escape_string($this->dbHandler, $email);
		$sql = "SELECT * FROM users WHERE email='$emailSafe'";
		
		if($result = $this->dbHandler->query($sql)) {
			$numberUsers = $result->num_rows;
			if($numberUsers == 1) {
				$row = $result->fetch_assoc();
				if($email == $row['email']) {
					$token = bin2hex(random_bytes(64));
					$userId = $row['id'];
					$login = $row['login'];
					$name = $row['name'];
					$sql = "INSERT INTO password_reset VALUES(NULL, '$userId', '$token', NOW())";
					
					$subject = 'SecurBank Reset Token';
					$headers = 'From: reset@securbank.pl' . "\r\n";
					$headers .= "Reply-To: reset@securbank.pl" . "\r\n";
					$headers .= "MIME-Version: 1.0\r\n";
					$headers .= "Content-Type: text/html; charset=ISO-8859-1\r\n";
					$message = '<html><body>Hello <b>'.$name.'</b>, please reset within 15 minutes.<br>Your login is <b>'.$login.'</b><br><br>Reset token<p>'.$token.'</p><br></body></html>';

					mail($email, $subject, $message, $headers);
					
					return $this->dbHandler->query($sql);
				}
			}
			$result->free_result();
		}
		return false;
	}

	public function changePassword($email, $newPassword, $token) {
		$emailSafe = mysqli_real_escape_string($this->dbHandler, $email);
		$sql = "SELECT * FROM users JOIN salts ON users.id = salts.user_id WHERE users.email='$emailSafe'";
		
		if($result = $this->dbHandler->query($sql)) {
			$numberUsers = $result->num_rows;
			if($numberUsers > 0) {
				$row = $result->fetch_assoc();
				$userId = $row['id'];
				$tokenSql = "SELECT * FROM password_reset WHERE user_id='$userId' and created > DATE_SUB(NOW(), INTERVAL 15 MINUTE) ORDER BY created DESC LIMIT 1";
				
				if($result2 = $this->dbHandler->query($tokenSql)) {
					$numberOfTokens = $result2->num_rows;
					if($numberOfTokens > 0) {
						$tokenRow = $result2->fetch_assoc();
						$dbToken = $tokenRow['token'];
						
						if($dbToken == $token && $userId == $tokenRow['user_id']) {
							$newSafePassword = HashWithSalt::hashPassword($newPassword, $row['salt']);
							$sql = "UPDATE users SET password='$newSafePassword' WHERE id='$userId'";
							return $this->dbHandler->query($sql);
						}
					}
				}
			}
			$result->free_result();
			$result2->free_result();
		}
		return false;
	}
}

?>
