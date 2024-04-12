CREATE DATABASE `modc_project` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE USER root@localhost IDENTIFIED BY admin;

-- modc_project.Messages definition
CREATE TABLE `Messages` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `NameUser` varchar(100) NOT NULL,
  `Content` varchar(1000) NOT NULL,
  `Receiver` varchar(100) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `Messages_Users_FK` (`NameUser`),
  KEY `Messages_Users_FK_1` (`Receiver`),
  CONSTRAINT `Messages_Users_FK` FOREIGN KEY (`NameUser`) REFERENCES `Users` (`Name`),
  CONSTRAINT `Messages_Users_FK_1` FOREIGN KEY (`Receiver`) REFERENCES `Users` (`Name`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- modc_project.Users definition

CREATE TABLE `Users` (
  `Name` varchar(100) NOT NULL,
  `Password` varchar(100) NOT NULL,
  PRIMARY KEY (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO modc_project.Users (Name,Password) VALUES
	 ('Diogo','12345'),
	 ('Diogo M','123'),
	 ('Enzo','Italia'),
	 ('Grande Pedro','a'),
	 ('Teste','testepass'),
	 ('Teste1','123'),
	 ('Teste2','12345');

INSERT INTO modc_project.Messages (NameUser,Content,Receiver) VALUES
	 ('Diogo M','Segunda msg','Grande Pedro'),
	 ('Diogo M','Terceira Msg','Grande Pedro'),
	 ('Diogo M','Olá','Enzo'),
	 ('Diogo M','Isto é um teste','Enzo'),
	 ('Enzo','Olá','Diogo M'),
	 ('Enzo','Obrigado','Diogo M'),
	 ('Enzo','ty','Diogo M'),
	 ('Diogo M','De nada','Enzo'),
	 ('Diogo','Teste','Grande Pedro'),
	 ('Diogo','Hello','Enzo');
INSERT INTO modc_project.Messages (NameUser,Content,Receiver) VALUES
	 ('DIogo M','hm','Grande Pedro'),
	 ('Diogo M','Estás por ai?','Enzo'),
	 ('Diogo M','Estás por ai?','Enzo'),
	 ('Diogo M','Estás por ai?','Enzo'),
	 ('Diogo M','Estás por ai?','Enzo'),
	 ('Diogo M','Grande Pedro','Grande Pedro'),
	 ('Diogo M','olá','Grande Pedro'),
	 ('Diogo M','é isto','Grande Pedro'),
	 ('Diogo M','Ola','Enzo'),
	 ('Enzo','teste','Diogo M');
INSERT INTO modc_project.Messages (NameUser,Content,Receiver) VALUES
	 ('Diogo M','Teste','Enzo'),
	 ('Enzo','Olá','Diogo M'),
	 ('Diogo M','Grande Enzo','Enzo'),
	 ('Diogo M','b','Enzo'),
	 ('Diogo M','hey','Enzo'),
	 ('Diogo M','teste','Enzo'),
	 ('Diogo M','adsad','Grande Pedro'),
	 ('Diogo M',' as a','Grande Pedro');