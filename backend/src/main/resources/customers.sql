#Customer and roles
INSERT INTO `focab`.`customer` (`id`, `email`, `enabled`,`fullname`,`password`,`level`) VALUES ('1', 'envolkan@gmail.com', b'1','Engin Volkan','$2a$10$Sc0P7.njy1Pqn/U5dd9TWuE2.U0aHivhQkvpKWsD1mNaFmFoCzxau','1000');
INSERT INTO `focab`.`roles` (`role_id`) VALUES ('ADMIN');
INSERT INTO `focab`.`customer_roles` (`customer_id`, `roles_role_id`) VALUES ('1', 'ADMIN');