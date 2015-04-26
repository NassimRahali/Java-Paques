USE BD_CLIENTS;

INSERT INTO CLIENTS VALUES(null,"client1@gmail.com","Client1","Louis","yjhykl",null,null,null);
INSERT INTO CLIENTS VALUES(null,"client2@gmail.com","Client2","Marc","jtktt",null,null,null);
INSERT INTO CLIENTS VALUES(null,"client3@gmail.com","Client3","Laurent","azaede",null,null,null);
INSERT INTO CLIENTS VALUES(null,"client4@gmail.com","Client4","Louis","yjhykl",null,null,null);
INSERT INTO CLIENTS VALUES(null,"client5@gmail.com","Client5","Marc","jtktt",null,null,null);
INSERT INTO CLIENTS VALUES(null,"client6@gmail.com","Client6","Laurent","azaede",null,null,null);
INSERT INTO CLIENTS VALUES(null,"client7@gmail.com","Client7","Louis","yjhykl",null,null,null);
INSERT INTO CLIENTS VALUES(null,"client8@gmail.com","Client8","Marc","jtktt",null,null,null);
INSERT INTO CLIENTS VALUES(null,"client9@gmail.com","Client9","Laurent","azaede",null,null,null);

INSERT INTO COMPTES VALUES(null,"222-6655-6655", 1,TRUE, 30000);
INSERT INTO COMPTES VALUES(null,"255-8885-7896", 1,TRUE, 500);
INSERT INTO COMPTES VALUES(null,"289-1114-6589", 2,TRUE, 20000);
INSERT INTO COMPTES VALUES(null,"879-6555-7894", 3,FALSE, 100000000 );
INSERT INTO COMPTES VALUES(null,"222-6655-6651", 1,TRUE, 30000);
INSERT INTO COMPTES VALUES(null,"255-8885-7892", 1,TRUE, 500);
INSERT INTO COMPTES VALUES(null,"289-1114-6583", 2,TRUE, 20000);
INSERT INTO COMPTES VALUES(null,"879-6555-7897", 3,FALSE, 100000000 );
INSERT INTO COMPTES VALUES(null,"222-6655-6689", 1,TRUE, 30000);
INSERT INTO COMPTES VALUES(null,"255-8885-7890", 1,TRUE, 500);
INSERT INTO COMPTES VALUES(null,"289-1114-6594", 2,TRUE, 20000);
INSERT INTO COMPTES VALUES(null,"879-6555-7804", 3,FALSE, 100000000 );

INSERT INTO OPERATIONS VALUES(null,'2015-01-06',1,1,"débit", TRUE, 1000);
INSERT INTO OPERATIONS VALUES(null,'2015-02-25',1,2, "débit",FALSE, 2500);
INSERT INTO OPERATIONS VALUES(null,'2015-03-19',2,3,"crédit", TRUE, 3000);
INSERT INTO OPERATIONS VALUES(null,'2014-04-20',3,4,"crédit",FALSE, 4000);
INSERT INTO OPERATIONS VALUES(null,'2015-01-06',1,1,"débit", TRUE, 1000);
INSERT INTO OPERATIONS VALUES(null,'2015-02-25',1,2, "débit",FALSE, 2500);
INSERT INTO OPERATIONS VALUES(null,'2015-03-19',2,3,"crédit", TRUE, 3000);
INSERT INTO OPERATIONS VALUES(null,'2014-04-20',3,4,"crédit",FALSE, 4000);
INSERT INTO OPERATIONS VALUES(null,'2015-01-06',1,1,"crédit", TRUE, 1000);
INSERT INTO OPERATIONS VALUES(null,'2015-02-25',1,2, "crédit",FALSE, 2500);
INSERT INTO OPERATIONS VALUES(null,'2015-03-19',2,3,"crédit", TRUE, 3000);
INSERT INTO OPERATIONS VALUES(null,'2014-04-20',3,4,"crédit",FALSE, 4000);



CREATE TABLE OPERATIONS
(
    IdOp INTEGER AUTO_INCREMENT,
    DateOp DATE,
    ClientID INTEGER NOT NULL,
	CompteID INTEGER NOT NULL,
	TypeOp VARCHAR(100),
	Traitee BOOLEAN,
	Montant FLOAT,
    CONSTRAINT OPERATIONS$pk PRIMARY KEY(IdOp),
	CONSTRAINT OPERATIONS$DateOp CHECK (DateOp IS NOT NULL),
	CONSTRAINT OPERATIONS$TypeOp CHECK (TypeOp IS NOT NULL),
	CONSTRAINT OPERATIONS$Montant CHECK (Montant IS NOT NULL),
	CONSTRAINT FOREIGN KEY(CompteID)REFERENCES COMPTES(IdCompte),
	CONSTRAINT FOREIGN KEY(ClientID) REFERENCES CLIENTS(IdClient)
);
