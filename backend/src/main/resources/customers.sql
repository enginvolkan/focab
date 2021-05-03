#Customer and roles
INSERT INTO `focab`.`customer` (`username`, `enabled`,`fullname`,`password`,`level`) VALUES ('envolkan@gmail.com', b'1','Engin Volkan','$2a$10$Sc0P7.njy1Pqn/U5dd9TWuE2.U0aHivhQkvpKWsD1mNaFmFoCzxau','3');
INSERT INTO `focab`.`authority` (`authority`) VALUES ('ROLE_USER');
INSERT INTO `focab`.`authority` (`authority`) VALUES ('ROLE_ADMIN');
INSERT INTO `focab`.`customer_authority` (`customer_username`, `authority_authority`) VALUES ('envolkan@gmail.com','ROLE_USER');
