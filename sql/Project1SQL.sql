-- MySQL Script generated by MySQL Workbench
-- Sun May 17 18:49:15 2020
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema drug_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema drug_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `drug_db` DEFAULT CHARACTER SET latin1 ;
USE `drug_db` ;

-- -----------------------------------------------------
-- Table `drug_db`.`pharma_company`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `drug_db`.`pharma_company` (
  `pharma_company_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `phone` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`pharma_company_id`),
  UNIQUE INDEX `uq_phone` (`phone` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `drug_db`.`pharmacy`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `drug_db`.`pharmacy` (
  `pharmacy_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`pharmacy_id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `drug_db`.`contracts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `drug_db`.`contracts` (
  `pharma_company_id` INT(11) NOT NULL,
  `pharmacy_id` INT(11) NOT NULL,
  `start_date` DATE NOT NULL,
  `end_date` DATE NULL DEFAULT NULL,
  `supervisor` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`pharma_company_id`, `pharmacy_id`),
  INDEX `fk_contracts_pharmacy` (`pharmacy_id` ASC) ,
  CONSTRAINT `fk_contracts_pharma_company`
    FOREIGN KEY (`pharma_company_id`)
    REFERENCES `drug_db`.`pharma_company` (`pharma_company_id`),
  CONSTRAINT `fk_contracts_pharmacy`
    FOREIGN KEY (`pharmacy_id`)
    REFERENCES `drug_db`.`pharmacy` (`pharmacy_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `drug_db`.`contract_text`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `drug_db`.`contract_text` (
  `pharma_company_id` INT(11) NOT NULL,
  `pharmacy_id` INT(11) NOT NULL,
  `contract_text` LONGTEXT NULL DEFAULT NULL,
  PRIMARY KEY (`pharma_company_id`, `pharmacy_id`),
  CONSTRAINT `fk_contract_text_contracts`
    FOREIGN KEY (`pharma_company_id` , `pharmacy_id`)
    REFERENCES `drug_db`.`contracts` (`pharma_company_id` , `pharmacy_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `drug_db`.`doctors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `drug_db`.`doctors` (
  `doctor_id` INT(11) NOT NULL AUTO_INCREMENT,
  `doctor_ssn` VARCHAR(25) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `specialty` VARCHAR(45) NULL DEFAULT NULL,
  `xp` INT(11) NOT NULL,
  PRIMARY KEY (`doctor_id`),
  UNIQUE INDEX `doctor_ssn_UNIQUE` (`doctor_ssn` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `drug_db`.`drugs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `drug_db`.`drugs` (
  `drug_id` INT(11) NOT NULL AUTO_INCREMENT,
  `trade_name` VARCHAR(45) NOT NULL,
  `generic_name` VARCHAR(45) NOT NULL,
  `pharma_company_id` INT(11) NOT NULL,
  PRIMARY KEY (`drug_id`),
  INDEX `idx_pharma_company_id` (`pharma_company_id` ASC) ,
  UNIQUE INDEX `trade_name_UNIQUE` (`trade_name` ASC) ,
  CONSTRAINT `fk_drugs_pharma_company`
    FOREIGN KEY (`pharma_company_id`)
    REFERENCES `drug_db`.`pharma_company` (`pharma_company_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- -----------------------------------------------------
-- Table `drug_db`.`drug_price`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `drug_db`.`drug_price` (
  `drug_id` INT(11) NOT NULL,
  `pharmacy_id` INT(11) NOT NULL,
  `price` FLOAT NOT NULL,
  PRIMARY KEY (`drug_id`, `pharmacy_id`),
  INDEX `fk_drug_price_pharmacy` (`pharmacy_id` ASC) ,
  CONSTRAINT `fk_drug_price_pharmacy`
    FOREIGN KEY (`pharmacy_id`)
    REFERENCES `drug_db`.`pharmacy` (`pharmacy_id`),
  CONSTRAINT `fk_drug_price_drugs`
    FOREIGN KEY (`drug_id`)
    REFERENCES `drug_db`.`drugs` (`drug_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `drug_db`.`patients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `drug_db`.`patients` (
  `patient_id` INT(11) NOT NULL AUTO_INCREMENT,
  `patient_ssn` VARCHAR(25) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `age` INT(11) NOT NULL,
  `address1` VARCHAR(45) NULL DEFAULT NULL,
  `address2` VARCHAR(45) NULL DEFAULT NULL,
  `city` VARCHAR(45) NOT NULL,
  `state` VARCHAR(45) NOT NULL,
  `zipcode` VARCHAR(45) NOT NULL,
  `primary_dc_id` INT(11) NOT NULL,
  `preferred_pharmacy_id` INT(11) NOT NULL,
  PRIMARY KEY (`patient_id`),
  INDEX `idx_primary_dc_id` (`primary_dc_id` ASC) ,
  UNIQUE INDEX `patient_ssn_UNIQUE` (`patient_ssn` ASC) ,
  CONSTRAINT `fk_patients_doctors`
    FOREIGN KEY (`primary_dc_id`)
    REFERENCES `drug_db`.`doctors` (`doctor_id`),
  CONSTRAINT `fk_patients_pharmacy`
    FOREIGN KEY (`preferred_pharmacy_id`)
    REFERENCES `drug_db`.`pharmacy` (`pharmacy_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `drug_db`.`prescriptions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `drug_db`.`prescriptions` (
  `patient_id` INT(11) NOT NULL,
  `doctor_id` INT(11) NOT NULL,
  `drug_id` INT(11) NOT NULL,
  `pharmacy_id` INT(11),
  `prescription_date` DATETIME NOT NULL,
  `quantity` INT(11) NOT NULL,
  `refills_used` INT(11) NOT NULL DEFAULT '0',
  `refills_authorized` INT(11) NOT NULL DEFAULT '6',
  PRIMARY KEY (`patient_id`, `doctor_id`, `drug_id`),
  INDEX `fk_prescriptions_doctors` (`doctor_id` ASC) ,
  INDEX `fk_prescriptions_drugs` (`drug_id` ASC) ,
  CONSTRAINT `fk_perscriptions_patients`
    FOREIGN KEY (`patient_id`)
    REFERENCES `drug_db`.`patients` (`patient_id`),
  CONSTRAINT `fk_perscriptions_doctors`
    FOREIGN KEY (`doctor_id`)
    REFERENCES `drug_db`.`doctors` (`doctor_id`),
  CONSTRAINT `fk_perscriptions_drugs`
    FOREIGN KEY (`drug_id`)
    REFERENCES `drug_db`.`drugs` (`drug_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
