-- Create test_runs, test_smells, and test_run_smells
CREATE TABLE `tsdetect`.`test_runs` (
  `uid` CHAR(50) NOT NULL CHECK (`uid` <> ''),
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

-- Create users
CREATE USER 'plugin'@'localhost' IDENTIFIED WITH mysql_native_password BY 'password';
GRANT INSERT ON tsdetect.test_runs TO 'plugin'@'localhost';
GRANT INSERT ON tsdetect.test_run_smells TO 'plugin'@'localhost';
GRANT SELECT ON tsdetect.* TO 'plugin'@'localhost';

CREATE USER 'plugin'@'%' IDENTIFIED WITH mysql_native_password BY 'password';
GRANT INSERT ON tsdetect.test_runs TO 'plugin'@'%';
GRANT INSERT ON tsdetect.test_run_smells TO 'plugin'@'%';
GRANT SELECT ON tsdetect.* TO 'plugin'@'%';

CREATE USER 'dashboard'@'localhost' IDENTIFIED WITH mysql_native_password BY 'password';
GRANT SELECT ON tsdetect.* TO 'dashboard'@'localhost';

CREATE USER 'dashboard'@'%' IDENTIFIED WITH mysql_native_password BY 'password';
GRANT SELECT ON tsdetect.* TO 'dashboard'@'%';

-- Update test smells table with data
USE tsdetect;
INSERT INTO test_smells (name)
VALUES ('Assertion Roulette'),
('Conditional Test'),
('Constructor Initialization'),
('Default Test'),
('Duplicate Assert'),
('Eager Test'),
('Empty Test'),
('Exception Handling'),
('General Fixture'),
('Ignored Test'),
('Lazy Test'),
('Magic Number'),
('Mystery Guest'),
('Redundant Print'),
('Redundant Assertion'),
('Resource Optimism'),
('Sensitive Equality'),
('Sleepy Test'),
('Unknown Test'),
('Verbose Test');
