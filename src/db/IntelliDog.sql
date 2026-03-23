SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema intellidog
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema intellidog
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `intellidog` DEFAULT CHARACTER SET utf8 ;
USE `intellidog` ;

-- -----------------------------------------------------
-- Table `intellidog`.`cargo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `intellidog`.`cargo` (
  `ID_cargo` INT NOT NULL AUTO_INCREMENT,
  `tipo` VARCHAR(30) NOT NULL,
  `adm` TINYINT NOT NULL,
  PRIMARY KEY (`ID_cargo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `intellidog`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `intellidog`.`usuario` (
  `ID_email` VARCHAR(60) NOT NULL,
  `Nome` VARCHAR(60) NOT NULL,
  `Senha` VARCHAR(45) NOT NULL,
  `COD_cargo` INT NOT NULL,
  PRIMARY KEY (`ID_email`),
  INDEX `fk_usuario_cargo_idx` (`COD_cargo` ASC) VISIBLE,
  CONSTRAINT `fk_usuario_cargo`
    FOREIGN KEY (`COD_cargo`)
    REFERENCES `intellidog`.`cargo` (`ID_cargo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `intellidog`.`pedido`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `intellidog`.`pedido` (
  `ID_pedido` INT NOT NULL AUTO_INCREMENT,
  `criado` DATETIME NOT NULL,
  `status` VARCHAR(12) NOT NULL,
  `data_aprovação` DATE NULL,
  `preço_total` DECIMAL(2) NOT NULL,
  `forma_de_pagamento` VARCHAR(10) NULL,
  `motivo` LONGTEXT NULL,
  `COD_email` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`ID_pedido`),
  INDEX `fk_pedido_usuario1_idx` (`COD_email` ASC) VISIBLE,
  CONSTRAINT `fk_pedido_usuario1`
    FOREIGN KEY (`COD_email`)
    REFERENCES `intellidog`.`usuario` (`ID_email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `intellidog`.`compra`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `intellidog`.`compra` (
  `ID_compra` INT NOT NULL AUTO_INCREMENT,
  `valor_da_compra` DECIMAL(2) NOT NULL,
  `anexo_fiscal` VARCHAR(100) NULL,
  `COD_pedido` INT NOT NULL,
  PRIMARY KEY (`ID_compra`),
  INDEX `fk_compra_pedido1_idx` (`COD_pedido` ASC) VISIBLE,
  CONSTRAINT `fk_compra_pedido1`
    FOREIGN KEY (`COD_pedido`)
    REFERENCES `intellidog`.`pedido` (`ID_pedido`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `intellidog`.`categoria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `intellidog`.`categoria` (
  `ID_categoria` INT NOT NULL AUTO_INCREMENT,
  `categoria` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`ID_categoria`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `intellidog`.`fornecedor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `intellidog`.`fornecedor` (
  `ID_fornecedor` INT NOT NULL AUTO_INCREMENT,
  `nome_fornecedor` VARCHAR(45) NOT NULL,
  `descrição` LONGTEXT NULL,
  PRIMARY KEY (`ID_fornecedor`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `intellidog`.`produto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `intellidog`.`produto` (
  `ID_produto` INT NOT NULL AUTO_INCREMENT,
  `nome_produto` VARCHAR(45) NOT NULL,
  `preço` DECIMAL(2) NOT NULL,
  `quantidade` INT NOT NULL,
  `minimo` INT NULL,
  `COD_categoria` INT NOT NULL,
  `COD_fornecedor` INT NOT NULL,
  PRIMARY KEY (`ID_produto`),
  INDEX `fk_produto_categoria1_idx` (`COD_categoria` ASC) VISIBLE,
  INDEX `fk_produto_fornecedor1_idx` (`COD_fornecedor` ASC) VISIBLE,
  CONSTRAINT `fk_produto_categoria1`
    FOREIGN KEY (`COD_categoria`)
    REFERENCES `intellidog`.`categoria` (`ID_categoria`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_produto_fornecedor1`
    FOREIGN KEY (`COD_fornecedor`)
    REFERENCES `intellidog`.`fornecedor` (`ID_fornecedor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `intellidog`.`produto_has_pedido`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `intellidog`.`produto_has_pedido` (
  `ID_pedpro` INT NOT NULL AUTO_INCREMENT,
  `COD_produto` INT NOT NULL,
  `COD_pedido` INT NOT NULL,
  `quantidade` VARCHAR(45) NOT NULL,
  `preço_unitario` DECIMAL(2) NOT NULL,
  PRIMARY KEY (`ID_pedpro`),
  INDEX `fk_produto_has_pedido_pedido1_idx` (`COD_pedido` ASC) VISIBLE,
  INDEX `fk_produto_has_pedido_produto1_idx` (`COD_produto` ASC) VISIBLE,
  CONSTRAINT `fk_produto_has_pedido_produto1`
    FOREIGN KEY (`COD_produto`)
    REFERENCES `intellidog`.`produto` (`ID_produto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_produto_has_pedido_pedido1`
    FOREIGN KEY (`COD_pedido`)
    REFERENCES `intellidog`.`pedido` (`ID_pedido`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `intellidog`.`entrada_saida`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `intellidog`.`entrada_saida` (
  `ID_entrada_saida` INT NOT NULL AUTO_INCREMENT,
  `tipo` TINYINT NOT NULL,
  `quantidade` INT NOT NULL,
  `COD_produto` INT NOT NULL,
  PRIMARY KEY (`ID_entrada_saida`),
  INDEX `fk_entrada_saida_produto1_idx` (`COD_produto` ASC) VISIBLE,
  CONSTRAINT `fk_entrada_saida_produto1`
    FOREIGN KEY (`COD_produto`)
    REFERENCES `intellidog`.`produto` (`ID_produto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;