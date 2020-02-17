BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Rating" (
	"user_id"	INTEGER,
	"paper_id"	INTEGER,
	"grade"	INTEGER CHECK(grade>0 and grade<=5),
	FOREIGN KEY("user_id") REFERENCES "Users"("id"),
	FOREIGN KEY("paper_id") REFERENCES "ScWorks"("id")
);
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
CREATE TABLE IF NOT EXISTS "ScWorksAuthors" (
	"sc_id"	INTEGER,
	"author_id"	INTEGER,
	FOREIGN KEY("sc_id") REFERENCES "ScWorksAuthors",
	FOREIGN KEY("author_id") REFERENCES "Author"("id")
);
INSERT INTO "Author" VALUES (1,'UNSA');
INSERT INTO "Author" VALUES (2,'UNSA');
INSERT INTO "Author" VALUES (3,'UNSA');
INSERT INTO "Users" VALUES (0,'esiljak1','12345678','esiljak1@etf.unsa.ba','file:/C:/Users/Emin%20Šiljak/IdeaProjects/Projekat-bazaPodatakaZaNaucneRadove/@/../Resources/images/default.jpg');
INSERT INTO "Person" VALUES (0,'Emin','Siljak',20,'m');
INSERT INTO "Person" VALUES (1,'Nedim','Bektas',19,'m');
INSERT INTO "Person" VALUES (2,'Emin','Siljak',20,'m');
INSERT INTO "Person" VALUES (3,'Adin','Nesimi',19,'f');
INSERT INTO "ScWorks" VALUES (0,'5. zadatak Dijkstra.txt','dijkstra, dm, graphs,');
INSERT INTO "ScWorks" VALUES (1,'5. zadatak Belmann-Ford sa obrnutim tezinama.txt','dm, graphs,');
INSERT INTO "ScWorksAuthors" VALUES (0,1);
INSERT INTO "ScWorksAuthors" VALUES (1,2);
INSERT INTO "ScWorksAuthors" VALUES (1,3);
COMMIT;
