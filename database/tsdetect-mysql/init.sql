-- Create test_runs, test_smells, and test_run_smells
CREATE TABLE `tsdetect`.`test_runs` (
  `uid` CHAR(50) NOT NULL,
  `timestamp` DATETIME NOT NULL DEFAULT NOW(),
  `run_id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`uid`, `timestamp`),
  UNIQUE INDEX `run_id_UNIQUE` (`run_id` ASC) VISIBLE);

CREATE TABLE `tsdetect`.`test_smells` (
  `test_smell_id` INT NOT NULL AUTO_INCREMENT,
  `name` TINYTEXT NOT NULL,
  PRIMARY KEY (`test_smell_id`),
  UNIQUE INDEX `test_smell_id_UNIQUE` (`test_smell_id` ASC) VISIBLE);

CREATE TABLE `tsdetect`.`test_run_smells` (
  `run_id` INT NOT NULL,
  `test_smell_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`run_id`, `test_smell_id`),
  INDEX `test_smell_id_idx` (`test_smell_id` ASC) VISIBLE,
  CONSTRAINT `run_id`
    FOREIGN KEY (`run_id`)
    REFERENCES `tsdetect`.`test_runs` (`run_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `test_smell_id`
    FOREIGN KEY (`test_smell_id`)
    REFERENCES `tsdetect`.`test_smells` (`test_smell_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- Create user
CREATE USER 'plugin'@'localhost' IDENTIFIED WITH mysql_native_password BY 'password';
GRANT INSERT ON tsdetect.test_runs TO 'plugin'@'localhost';
GRANT INSERT ON tsdetect.test_run_smells TO 'plugin'@'localhost';

CREATE USER 'dashboard'@'localhost' IDENTIFIED WITH mysql_native_password BY 'password';
GRANT SELECT ON tsdetect.* TO 'dashboard'@'localhost';

-- Update test smells table with data
USE tsdetect;
INSERT INTO test_smells (name)
VALUES ('Assertion Roulette'),
('Conditional Test Logic'),
('Constructor Initialization'),
('Default Test'),
('Duplicate Assert'),
('Eager Test'),
('Empty Test'),
('Exception Handling'),
('General Fixture'),
('Ignored Test'),
('Lazy Test'),
('Magic Number Test'),
('Mystery Guest'),
('Redundant Print'),
('Redundant Assertion'),
('Resource Optimism'),
('Sensitive Equality'),
('Sleepy Test'),
('Unknown Test');

-- Insert random data into the test_runs and test_run smells table
DELIMITER $$
CREATE PROCEDURE randomData(IN NumRows INT)
BEGIN
	DECLARE i INT;
    SET i = 1;
	START TRANSACTION;
		WHILE i <= NumRows DO
			INSERT INTO test_runs(uid, timestamp) 
            -- uid = random number 1-10 | timestamp = random time
            VALUES (1 + CEIL(RAND() * (10-1)), FROM_UNIXTIME(UNIX_TIMESTAMP('2020-04-30 14:53:27') + FLOOR(0 + (RAND() * 63072000))));
            
            INSERT INTO test_run_smells(run_id, test_smell_id, quantity) 
            -- run_id = i | test_smell_id = random number 1-19 | quantity = random number 1-50
            VALUES (i, (1 + CEIL(RAND() * (19-1))), (1 + CEIL(RAND() * (50-1))));
            SET i = i + 1;
		END WHILE;
        COMMIT;
END$$
DELIMITER ;

CALL randomData(50);