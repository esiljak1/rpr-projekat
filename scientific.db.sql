BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "ScWorks-Users" (
	"sc_id"	INTEGER,
	"user_id"	INTEGER,
	FOREIGN KEY("sc_id") REFERENCES "ScWorks-Users",
	FOREIGN KEY("user_id") REFERENCES "Users"
);
CREATE TABLE IF NOT EXISTS "ScWorks" (
	"id"	INTEGER,
	"name"	TEXT,
	"tags"	TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "Users" (
	"id"	INTEGER,
	"firstname"	TEXT,
	"lastname"	TEXT,
	"age"	INTEGER,
	"gender"	TEXT,
	PRIMARY KEY("id")
);
COMMIT;
