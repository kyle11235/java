CREATE SCHEMA `test` DEFAULT CHARACTER SET utf8 ;


CREATE TABLE `test`.`test` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(200) NULL,
  `address` VARCHAR(200) NULL,
  `email` VARCHAR(200) NULL,
  `phone` VARCHAR(200) NULL,
  PRIMARY KEY (`id`));

INSERT INTO test (name, address, email, phone) VALUES('kyle', 'z', 'x@x.com', '123');
