CREATE TABLE IF NOT EXISTS history (ID int(11),
batchNumber VARCHAR(255),
expirationDate DATETIME,
productID  int(11),
medicamentID int(11));

DELIMITER $$
CREATE TRIGGER withdraw 
before DELETE ON medicamentProduct
FOR EACH ROW
BEGIN
	insert into history value (OLD.ID, OLD.batchNumber, OLD.expirationDate, OLD.productID, OLD.medicament);
END$$
DELIMITER ;



CREATE TABLE IF NOT EXISTS history_medicament (medicamentID int(11),
medicamentName VARCHAR(255),
content VARCHAR(255));

DELIMITER $$
CREATE TRIGGER save_medicament
before DELETE ON medicament
FOR EACH ROW
BEGIN
	insert into history_medicament value (OLD.medicamentID, OLD.name, OLD.content);
END$$
DELIMITER ;

ALTER DATABASE drugstore CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci