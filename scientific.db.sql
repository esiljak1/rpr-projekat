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
COMMIT;
