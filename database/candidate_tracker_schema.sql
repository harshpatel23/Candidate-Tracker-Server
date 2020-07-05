-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema candidate_tracker
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema candidate_tracker
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `candidate_tracker` DEFAULT CHARACTER SET utf8 ;
USE `candidate_tracker` ;

-- -----------------------------------------------------
-- Table `candidate_tracker`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `candidate_tracker`.`roles` (
  `role` VARCHAR(20) NOT NULL,
  `role_string` VARCHAR(30) NOT NULL,
  UNIQUE INDEX `role_UNIQUE` (`role` ASC) VISIBLE,
  PRIMARY KEY (`role`),
  UNIQUE INDEX `role_string_UNIQUE` (`role_string` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `candidate_tracker`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `candidate_tracker`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `role` VARCHAR(20) NOT NULL,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `contact` VARCHAR(10) NULL,
  `is_active` TINYINT NULL DEFAULT 1,
  `manager_id` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `fk_role_idx` (`role` ASC) VISIBLE,
  INDEX `fk_manager_id_idx` (`manager_id` ASC) VISIBLE,
  CONSTRAINT `fk_role`
    FOREIGN KEY (`role`)
    REFERENCES `candidate_tracker`.`roles` (`role`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_manager_id`
    FOREIGN KEY (`manager_id`)
    REFERENCES `candidate_tracker`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `candidate_tracker`.`candidate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `candidate_tracker`.`candidate` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(45) NOT NULL,
  `recruiter_id` INT NOT NULL,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `contact` VARCHAR(10) NULL,
  `address` TEXT(150) NULL,
  `preferred_loc` VARCHAR(45) NULL,
  `ectc` INT NULL,
  `ctct` INT NULL,
  `source` VARCHAR(45) NULL,
  `cv` MEDIUMBLOB NULL,
  `current_round` INT NULL,
  `status` ENUM('ready', 'hold', 'hired', 'rejected') NULL,
  `last_updated` DATETIME NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  INDEX `fk_recruiter_id_idx` (`recruiter_id` ASC) VISIBLE,
  CONSTRAINT `fk_candidate_recruiter_id`
    FOREIGN KEY (`recruiter_id`)
    REFERENCES `candidate_tracker`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `candidate_tracker`.`skills`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `candidate_tracker`.`skills` (
  `skill_id` INT NOT NULL AUTO_INCREMENT,
  `skill_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`skill_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `candidate_tracker`.`candidate_skills`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `candidate_tracker`.`candidate_skills` (
  `candidate_id` INT NOT NULL,
  `skill_id` INT NOT NULL,
  PRIMARY KEY (`candidate_id`, `skill_id`),
  INDEX `fk_skill_id_idx` (`skill_id` ASC) VISIBLE,
  CONSTRAINT `fk_candidate_id`
    FOREIGN KEY (`candidate_id`)
    REFERENCES `candidate_tracker`.`candidate` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_skill_id`
    FOREIGN KEY (`skill_id`)
    REFERENCES `candidate_tracker`.`skills` (`skill_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `candidate_tracker`.`interviewer_skills`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `candidate_tracker`.`interviewer_skills` (
  `interviewer_id` INT NOT NULL,
  `skill_id` INT NOT NULL,
  PRIMARY KEY (`interviewer_id`, `skill_id`),
  INDEX `fk_skill_id_idx` (`skill_id` ASC) VISIBLE,
  CONSTRAINT `fk_interviewer_id`
    FOREIGN KEY (`interviewer_id`)
    REFERENCES `candidate_tracker`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_skill_interviewer_id`
    FOREIGN KEY (`skill_id`)
    REFERENCES `candidate_tracker`.`skills` (`skill_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `candidate_tracker`.`interview`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `candidate_tracker`.`interview` (
  `interview_id` INT NOT NULL AUTO_INCREMENT,
  `candidate_id` INT NOT NULL,
  `interviewer_id` INT NOT NULL,
  `start_time` DATETIME NOT NULL,
  `end_time` DATETIME NOT NULL,
  `feedback` TEXT NULL,
  `updated_by` INT NULL,
  `round_no` INT NOT NULL,
  `rescheduled_start` DATETIME NULL,
  `rescheduled_end` DATETIME NULL,
  `isComplete` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`interview_id`),
  INDEX `fk_interviewer_id_idx` (`interviewer_id` ASC) VISIBLE,
  INDEX `fk_candidate_id_idx` (`candidate_id` ASC) VISIBLE,
  INDEX `fk_updated_by_idx` (`updated_by` ASC) VISIBLE,
  CONSTRAINT `fk_interview_interviewer_id`
    FOREIGN KEY (`interviewer_id`)
    REFERENCES `candidate_tracker`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_interview_candidate_id`
    FOREIGN KEY (`candidate_id`)
    REFERENCES `candidate_tracker`.`candidate` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_updated_by`
    FOREIGN KEY (`updated_by`)
    REFERENCES `candidate_tracker`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `candidate_tracker`.`user_closure`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `candidate_tracker`.`user_closure` (
  `mapping_id` INT NOT NULL,
  `parent_id` INT NULL,
  `child_id` INT NULL,
  `depth` INT NULL,
  PRIMARY KEY (`mapping_id`),
  INDEX `fk_user_parent_idx` (`parent_id` ASC) VISIBLE,
  INDEX `fk_user_child_idx` (`child_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_parent`
    FOREIGN KEY (`parent_id`)
    REFERENCES `candidate_tracker`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_child`
    FOREIGN KEY (`child_id`)
    REFERENCES `candidate_tracker`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;