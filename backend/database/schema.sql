BEGIN TRANSACTION;

DROP TABLE IF EXISTS users                      cascade;



CREATE TABLE users (
  id serial PRIMARY KEY,
  username varchar(255) NOT NULL UNIQUE,     -- Username
  password varchar(32) NOT NULL,      -- Password
  salt varchar(256) NOT NULL,          -- Password Salt
  role varchar(255) NOT NULL default('user'),
  picture varchar(255)
);


	   
update users set role = 'admin' where username = '';


COMMIT TRANSACTION;
