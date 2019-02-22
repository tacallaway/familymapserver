CREATE TABLE `User` (
	`UserName`	TEXT NOT NULL UNIQUE,
	`Password`	TEXT NOT NULL,
	`Email`	TEXT NOT NULL,
	`PersonID`	TEXT NOT NULL UNIQUE,
	FOREIGN KEY(`PersonID`) REFERENCES `Person`(`PersonID`),
	PRIMARY KEY(`UserName`)
);

CREATE TABLE `Person` (
	`PersonID`	TEXT NOT NULL UNIQUE,
	`Descendant`	TEXT,
	`FirstName`	TEXT NOT NULL,
	`LastName`	TEXT NOT NULL,
	`Gender`	TEXT NOT NULL CHECK(Gender IN ( "f" , "m" )),
	`Father`	TEXT,
	`Mother`	TEXT,
	`Spouse`	TEXT,
	FOREIGN KEY(`Descendant`) REFERENCES `User`(`UserName`),
	PRIMARY KEY(`PersonID`)
);

CREATE TABLE `Event` (
	`EventID`	TEXT NOT NULL UNIQUE,
	`Descendant`	TEXT NOT NULL,
	`PersonID`	TEXT NOT NULL,
	`Latitude`	REAL NOT NULL,
	`Longitude`	REAL NOT NULL,
	`Country`	TEXT NOT NULL,
	`City`	TEXT NOT NULL,
	`EventType`	TEXT NOT NULL,
	`Year`	INTEGER NOT NULL,
	PRIMARY KEY(`EventID`),
	FOREIGN KEY(`Descendant`) REFERENCES `User`(`UserName`),
	FOREIGN KEY(`PersonID`) REFERENCES `Person`(`PersonID`)
);

CREATE TABLE `AuthToken` (
	`Token`	TEXT NOT NULL UNIQUE,
	`Username`	TEXT NOT NULL,
	PRIMARY KEY(`Token`),
	FOREIGN KEY(`Username`) REFERENCES `User`(`UserName`)
);