DROP DATABASE IF EXISTS sec_bank;
CREATE DATABASE IF NOT EXISTS sec_bank CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE `sec_bank`.`users` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `login` VARCHAR(30) NOT NULL,
    `password` CHAR(255) NOT NULL,
    `name` VARCHAR(30) NOT NULL,
    `email` VARCHAR(50) NOT NULL,
	`account_number` VARCHAR(32) NOT NULL,
	`balance` DECIMAL(15, 2) DEFAULT 0.00 NOT NULL,
	UNIQUE (login),
	UNIQUE (email),
	UNIQUE (account_number)
) ENGINE = InnoDB;

CREATE TABLE `sec_bank`.`salts` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT NOT NULL,
    `salt` CHAR(32) NOT NULL,
	FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE = InnoDB;

DROP TABLE IF EXISTS `sec_bank`.`transfer`;
CREATE TABLE `sec_bank`.`transfer` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT NOT NULL,
	`receiver` VARCHAR(30) NOT NULL,
	`to_account_number` VARCHAR(32) NOT NULL,
	`amount` DECIMAL(15, 2) NOT NULL,
    `title` VARCHAR(32) NOT NULL,
	`date` DATETIME,
	FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE = InnoDB;

DROP TABLE IF EXISTS `sec_bank`.`password_reset`;
CREATE TABLE `sec_bank`.`password_reset` (
    `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT NOT NULL,
    `token` VARCHAR(255) NOT NULL,
	`created` DATETIME,
	FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE = InnoDB;

CREATE USER 'sec_user'@'localhost' IDENTIFIED BY 'CEcDpp2hy2QM2Bs4ScyktKCs';
GRANT SELECT, INSERT, UPDATE ON `secure_login`.* TO 'sec_user'@'localhost';
