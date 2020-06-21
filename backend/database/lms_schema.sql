
BEGIN TRANSACTION;

DROP TABLE IF EXISTS users                      cascade;
DROP TABLE IF EXISTS user_profile               cascade;
DROP TABLE IF EXISTS training                   cascade;
DROP TABLE IF EXISTS cert_period                cascade;
DROP TABLE IF EXISTS training_cert_period       cascade;

CREATE TABLE users(
    id serial ,
    email varchar(64) NOT NULL UNIQUE,
    password varchar(32) NOT NULL,
    salt varchar(256) NOT NULL,
    permission varchar(32)
    CONSTRAINT pk_users_id PRIMARY KEY (id)
);

CREATE TABLE user_profile(
    profile_id integer NOT NULL,
    profile_firstname varchar(32) NOT NULL,
    profile_lastname varchar(32) NOT NULL,
    role varchar(255) NOT NULL,
    start_date date NOT NULL,
    profile_pic varchar(255),
    CONSTRAINT uq_user_profile_profile_id UNIQUE (profile_id)
);

CREATE TABLE training(
    train_id serial ,
    train_name varchar(255) NOT NULL,
    train_provider varchar(255) NOT NULL,
    train_topic varchar(800) NOT NULL,
    train_date date NOT NULL,
    is_compliance boolean NOT NULL,
    train_proof varchar(255),
    minutes integer NOT NULL
    CONSTRAINT pk_training_train_id PRIMARY KEY (train_id)
);

CREATE TABLE cert_period(
    cert_id serial PRIMARY KEY,
    profile_id integer NOT NULL,
    up_to_date boolean
);

CREATE TABLE training_cert_period (
    train_id integer NOT NULL,
    cert_period_id integer NOT NULL
);

-- Password is 'greatwall'
BEGIN TRANSACTION;
INSERT INTO users (email, password, salt, permission) 
        VALUES ('matt.goshorn@techelevator.com','FjZDm+sndmsdEDwNtfr6NA==',
                'kidcasB0te7i0jK0fmRIGHSm0mYhdLTaiGkEAiEvLp7dAEHWnuT8n/5bd2V/mqjstQ198iImm1xCmEFu+BHyOz1Mf7vm4LILcrr17y7Ws40Xyx4FOCt8jD03G+jEafpuVJnPiDmaZQXJEpEfekGOvhKGOCtBnT5uatjKEuVWuDA=','admin');

INSERT INTO training (train_name, train_provider, train_topic, train_date, is_compliance, train_proof, minutes) 
        VALUES('LEARNING HOW TO TEACH', 'YMCA', 'We teach people how to teach, its very educational.', '2020-02-13',true, null, 90);

INSERT INTO cert_period (profile_id, up_to_date) 
        VALUES ((SELECT id FROM users WHERE users.email ='matt.goshorn@techelevator.com'), false);

INSERT INTO user_profile (profile_id, profile_firstname, profile_lastname, role, start_date, profile_pic,) 
        VALUES ((SELECT id FROM users WHERE email = 'matt.goshorn@techelevator.com' ),'Matt','Goshorn', 'Instructor', '2020-01-13', null);

INSERT INTO training_cert_period (train_id, cert_period_id)
        VALUES ((SELECT train_id FROM training WHERE training.train_name='LEARNING HOW TO TEACH'), 1);


--commit;
ALTER TABLE user_profile
ADD FOREIGN KEY(profile_id)
REFERENCES users(id);

ALTER TABLE cert_period
ADD FOREIGN KEY(profile_id)
REFERENCES user_profile(profile_id);

ALTER TABLE training_cert_period 
ADD FOREIGN KEY(train_id)
REFERENCES training(train_id);

ALTER TABLE training_cert_period 
ADD FOREIGN KEY(cert_period_id)
REFERENCES cert_period(cert_id);







