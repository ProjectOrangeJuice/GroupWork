BEGIN TRANSACTION;
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
	`username`	TEXT NOT NULL UNIQUE,
	`firstName`	TEXT,
	`lastName`	TEXT,
	`telephone`	TEXT,
	`address`	TEXT,
	`postcode`	TEXT,
	`avatarPath`	TEXT,
	`accountBalance`	TEXT,
	PRIMARY KEY(`username`)
);

INSERT INTO `users` VALUES ('Alexandru','Alex','Dascalu','079999999','3 Maple Street','SA2 8PP','/SavedAvatars/Avatar1.png','0');
INSERT INTO `users` VALUES ('Manny','Jake','Manford','0108097352','6 Main Street','SA2 GFY','/SavedAvatars/Avatar1.png','0');
INSERT INTO `users` VALUES ('Steveo','Steve','Jamerson','0108035473','17 Lil Line','SA2 HRU','/SavedAvatars/Avatar1.png','0');
INSERT INTO `users` VALUES ('Queeny','McNiel','Nelson','0108037642','45 Avenue Drive','SA2 HFY','/SavedAvatars/Avatar1.png','0');
INSERT INTO `users` VALUES ('Jackie','Janet','Smith','0108034627','16 Cramford Way','SA2 I9L','/SavedAvatars/Avatar1.png','0');

INSERT INTO `users` VALUES ('Helper1','Carl','Walker','0108098743','7 Low Street','SA2 HFS','/SavedAvatars/Avatar1.png','0');
INSERT INTO `users` VALUES ('Manager1','Ben','Dover','0108034738','9 High street','SA2 IFA','/SavedAvatars/Avatar1.png','0');

DROP TABLE IF EXISTS `staff`;
CREATE TABLE IF NOT EXISTS `staff` (
	`username`	TEXT,
	`staffId`	INTEGER NOT NULL UNIQUE,
	`employmentDate`	TEXT,
	FOREIGN KEY(`username`) REFERENCES `users`(`username`)
);

INSERT INTO `staff` VALUES ('Helper1','1','03/11/2018');
INSERT INTO `staff` VALUES ('Manager1','2','21/02/2016');

DROP TABLE IF EXISTS `transactions`;
CREATE TABLE IF NOT EXISTS `transactions` (
	`transactionId`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`username`	TEXT,
	`paid`	REAL,
	`dateTime`	TEXT,
	FOREIGN KEY(`username`) REFERENCES `users`(`username`)
);
INSERT INTO `transactions` VALUES (1,'test',5.0,"sometime");
INSERT INTO `transactions` VALUES (2,'test',10.0,"othertime");

DROP TABLE IF EXISTS `system`;
CREATE TABLE IF NOT EXISTS `system` (
	`ver`	INTEGER
);
INSERT INTO `system` VALUES (13);

DROP TABLE IF EXISTS `resource`;
CREATE TABLE IF NOT EXISTS `resource` (
	`rID`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`title`	TEXT,
	`year`  INTEGER,
	`thumbnail`	TEXT
);
INSERT INTO `resource` VALUES (1,'Homo Deus',2017,'/graphics/homoDeus.png');
INSERT INTO `resource` VALUES (2,'Iron Man',2008,'/graphics/ironMan.jpg');
INSERT INTO `resource` VALUES (3,'The Winter Soldier',2014,'/graphics/winterSoldier.jpg');
INSERT INTO `resource` VALUES (4,'Thor',2011,'/graphics/thor.jpg');
INSERT INTO `resource` VALUES (5,'Creed 2',2018,'/graphics/creed2.jpg');
INSERT INTO `resource` VALUES (6,'Creed',2015,'/graphics/creed.jpg');
INSERT INTO `resource` VALUES (7,'The Martian',2015,'/graphics/theMartian.jpg');
INSERT INTO `resource` VALUES (8,'Sapiens',2015,'/graphics/sapiens.jpg');
INSERT INTO `resource` VALUES (9,'Waking Gods',2015,'/graphics/wakingGods.jpg');
INSERT INTO `resource` VALUES (10,'American Gods',2015,'/graphics/americanGods.jpg');
INSERT INTO `resource` VALUES (11,'Iron Man 3',2013,'/graphics/ironMan3.jpg');


DROP TABLE IF EXISTS `fines`;
CREATE TABLE IF NOT EXISTS `fines` (
	`fineID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`username`	TEXT,
	`rID`	INTEGER,
	`daysOver`	INTEGER,
	`amount`	REAL,
	`dateTime`	TEXT,
	`paid`	INTEGER,
	FOREIGN KEY(`username`) REFERENCES `users`(`username`),
	FOREIGN KEY(`rID`) REFERENCES `resource`(`rID`)
);
INSERT INTO `fines` VALUES (1,"test",1,3,5.0,'hi',1);
INSERT INTO `fines` VALUES (2,"test",2,100,10.0,'always',1);

DROP TABLE IF EXISTS `copies`;
CREATE TABLE IF NOT EXISTS `copies` (
	`copyID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`rID`	INTEGER NOT NULL,
	`keeper`	TEXT,
	loanDuration INTEGER,
	borrowDate TEXT,
	lastRenewal TEXT,
	dueDate TEXT,
	FOREIGN KEY(`keeper`) REFERENCES `users`(`username`),
	FOREIGN KEY(`rID`) REFERENCES `resource`(`rID`)
);

DROP TABLE IF EXISTS `book`;
CREATE TABLE IF NOT EXISTS `book` (
	`author`	TEXT,
	`publisher`	TEXT,
	`genre`		TEXT,
	`ISBN`		TEXT,
	`language`	TEXT,
	`rID`		INTEGER,
	PRIMARY KEY (rID),
	FOREIGN KEY (rID) REFERENCES `resource`(`rID`) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO `book` VALUES ('Yuval Noah Harari','Harvill Secker','Non-Fiction','978-191-070-187-4','English',1);
INSERT INTO `book` VALUES ('Andy Weir','Harvill Secker','Fiction','978-191-070-187-5','English',7);
INSERT INTO `book` VALUES ('Yuval Noah Harari','Harvill Secker','Non-Fiction','978-191-070-187-5','English',8);
INSERT INTO `book` VALUES ('Unknown Author','Harvill Secker','Fiction','978-191-070-187-6','English',9);
INSERT INTO `book` VALUES ('Neil Gaimen','Harvill Secker','Fiction','978-191-070-187-7','English',10);

DROP TABLE IF EXISTS `dvd`;
CREATE TABLE IF NOT EXISTS `dvd` (
	`director`	TEXT,
	`runtime`	INTEGER,
	`language`	TEXT,
	`rID`		INTEGER,
	PRIMARY KEY (rID),
	FOREIGN KEY (rID) REFERENCES `resource`(`rID`) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO `dvd` VALUES ('Jon Favreau',126,'english',2);
INSERT INTO `dvd` VALUES ('Jon Favreau',126,'english',3);
INSERT INTO `dvd` VALUES ('Jon Favreau',126,'english',4);
INSERT INTO `dvd` VALUES ('Jon Favreau',126,'english',5);
INSERT INTO `dvd` VALUES ('Jon Favreau',126,'english',6);
INSERT INTO `dvd` VALUES ('Jon Favreau',126,'english',11);

drop table if exists `subtitles`;
create table if not exists `subtitles` (
	`dvdID` integer,
	`subtitleLanguage` TEXT,
	primary key (dvdID,subtitleLanguage),
	foreign key (dvdID) references `dvd`(`rID`) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO `subtitles` VALUES (1,'romanian');
INSERT INTO `subtitles` VALUES (1,'greek');
INSERT INTO `subtitles` VALUES (2,'welsh');

DROP TABLE IF EXISTS `laptop`;
CREATE TABLE IF NOT EXISTS `laptop` (
	`manufacturer`	TEXT,
	`model`			INTEGER,
	`os`			TEXT,
	`rID`			INTEGER,
	PRIMARY KEY (rID),
	FOREIGN KEY (rID) REFERENCES `resource`(`rID`) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO `copies` VALUES (1,3,'Manny',8,'12/03/2018',NULL,NULL);
INSERT INTO `copies` VALUES (2,1,NULL,7,NULL,NULL,NULL);
INSERT INTO `copies` VALUES (3,2,NULL,7,NULL,NULL,NULL);
INSERT INTO `copies` VALUES (4,2,NULL,7,NULL,NULL,NULL);

DROP TABLE IF EXISTS `borrowRecords`;
CREATE TABLE IF NOT EXISTS `borrowRecords` (
	`borrowId`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`copyId`	INTEGER NOT NULL,
	`username`	TEXT NOT NULL,
	`description`	TEXT,
	FOREIGN KEY (copyId) REFERENCES `copies`(`copyID`) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (username) REFERENCES `users`(`username`) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO `borrowRecords` VALUES (1,2,'Manny','Returned on 08/12/2018');

DROP TABLE IF EXISTS `requestsToApprove`;
CREATE TABLE IF NOT EXISTS `requestsToApprove` (
	`rID` INTEGER,
	`userName` TEXT,
	PRIMARY KEY (userName,rID),
	FOREIGN KEY (userName) REFERENCES `users` (`username`) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (rID) REFERENCES `resource` (`rID`) ON UPDATE CASCADE ON DELETE CASCADE
);

DROP TABLE IF EXISTS `userRequests`;
CREATE TABLE IF NOT EXISTS `userRequests` (
	`rID` INTEGER,
	`userName` TEXT,
	`orderNumber` INTEGER,
	PRIMARY KEY (userName,rID),
	FOREIGN KEY (userName) REFERENCES `users` (`username`) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (rID) REFERENCES `resource` (`rID`) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO `userRequests` (rID,userName,orderNumber) VALUES (1,"Alexandru",1);
INSERT INTO `userRequests` (rID,userName,orderNumber) VALUES (2,"Manny",2);
COMMIT;
