/******************** Création des tables de la base de donnée **************/
USE erp;
INSERT INTO TVA (ID,LIBELLE,TAUX) VALUES
(1,"Exonéré de TVA",0),
(2,"TVA normale 20%",20),
(3,"TVA intermédiaire 10%",10),
(4,"TVA réduite 5.5%",5.5)
