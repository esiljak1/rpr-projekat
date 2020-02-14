BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Author" (
	"id"	INTEGER,
	"university"	TEXT,
	PRIMARY KEY("id"),
	FOREIGN KEY("id") REFERENCES "Person"("id")
);
CREATE TABLE IF NOT EXISTS "Users" (
	"id"	INTEGER,
	"username"	TEXT,
	"password"	TEXT,
	"mail"	TEXT,
	"image"	TEXT,
	PRIMARY KEY("id"),
	FOREIGN KEY("id") REFERENCES "Person"("id")
);
CREATE TABLE IF NOT EXISTS "Person" (
	"id"	INTEGER,
	"firstname"	TEXT,
	"lastname"	TEXT,
	"age"	INTEGER,
	"gender"	TEXT CHECK(gender='m' or gender='f'),
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "ScWorks" (
	"id"	INTEGER,
	"name"	TEXT,
	"tags"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "ScWorks-Users" (
	"sc_id"	INTEGER,
	"user_id"	INTEGER,
	FOREIGN KEY("sc_id") REFERENCES "ScWorks-Users",
	FOREIGN KEY("user_id") REFERENCES "Users"
);
INSERT INTO "Users" VALUES (1,'esiljak1','test','','default.jpg');
INSERT INTO "Users" VALUES (2,'anesimi1','12345678','anesimi1@etf.unsa.ba','file:/C:/Users/Emin%20Šiljak/IdeaProjects/Projekat-bazaPodatakaZaNaucneRadove/default.jpg');
INSERT INTO "Users" VALUES (3,'esiljak1','test1234','esiljak1@etf.unsa.ba','file:/C:/Users/Emin%20Šiljak/IdeaProjects/Projekat-bazaPodatakaZaNaucneRadove/default.jpg');
COMMIT;
