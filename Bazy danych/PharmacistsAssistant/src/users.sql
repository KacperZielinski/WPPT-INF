USE `drugstore`;

-- NEW SUPPLIER
CREATE USER IF NOT EXISTS 'supplier'@'localhost';
SET PASSWORD FOR 'supplier'@'localhost' = PASSWORD('secretpassword');
GRANT SELECT, INSERT, UPDATE, DELETE ON `drugstore`.* TO 'supplier'@'localhost';
FLUSH PRIVILEGES;
	
-- NEW PHARMACIST
CREATE USER IF NOT EXISTS 'farmaceuta'@'localhost';
SET PASSWORD FOR 'farmaceuta'@'localhost' = PASSWORD('secretpassword');  
GRANT SELECT, INSERT, UPDATE, DELETE ON `drugstore`.* TO 'farmaceuta'@'localhost';
FLUSH PRIVILEGES;
	
	
	
	
GRANT SELECT, INSERT, UPDATE, DELETE ON `drugstore`.`medicament_activeingredient` TO 'supplier'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE ON `drugstore`.`medicament` TO 'supplier'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE ON `drugstore`.`medicamentreplacement` TO 'supplier'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE ON `drugstore`.`medicamentproduct` TO 'supplier'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE ON `drugstore`.`activeingredient` TO 'supplier'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE ON `drugstore`.`medicamentproduct` TO 'supplier'@'localhost';
	