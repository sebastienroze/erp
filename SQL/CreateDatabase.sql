/******************** Cr�ation de la base de donn�e et de l'utilisateur erp **************/
CREATE SCHEMA `erp` ;
CREATE USER 'erpuser'@'localhost' IDENTIFIED BY 'erppass';
GRANT SELECT, INSERT, UPDATE ON erp.* TO 'erpuser'@'localhost';  
