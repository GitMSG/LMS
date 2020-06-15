
BEGIN TRANSACTION;

DROP TABLE IF EXISTS users                      cascade;
DROP TABLE IF EXISTS user_profile               cascade;
DROP TABLE IF EXISTS training                   cascade;
DROP TABLE IF EXISTS certification_status       cascade;

CREATE TABLE users(
    id serial PRIMARY KEY,
    email varchar(64) NOT NULL UNIQUE,
    password varchar(32) NOT NULL,
    salt varchar(256) NOT NULL,
    permission varchar(32)
    --CONSTRAINT pk_user_user_id PRIMARY KEY (user_id)
);

CREATE TABLE user_profile(
    profile_id integer NOT NULL,
    profile_firstname varchar(32) NOT NULL,
    profile_lastname varchar(32) NOT NULL,
    role varchar(255) NOT NULL,
    start_date date NOT NULL,
    profile_pic varchar(255),
    cert_id integer NOT NULL
    /*CONSTRAINT pk_user_profile_profile_id PRIMARY KEY (profile_id),
    CONSTRAINT fk_user_profile_email FOREIGN KEY (email) REFERENCES user(email),
    CONSTRAINT fk_user_profile_cert_id FOREIGN KEY (cert_id) REFERENCES certification_status(cert_id)*/
);

CREATE TABLE training(
    training_id serial PRIMARY KEY,
    training_name varchar(255) NOT NULL,
    training_provider varchar(255) NOT NULL,
    training_topic varchar(800) NOT NULL,
    training_date date NOT NULL,
    compliance boolean NOT NULL,
    elective boolean NOT NULL,
    training_proof varchar(255),
    hours double precision NOT NULL
   -- CONSTRAINT pk_training_training_id PRIMARY KEY (training_id)
);

CREATE TABLE certification_status(
    cert_id serial PRIMARY KEY,
    train_id integer NOT NULL,
    compliance_hours double precision,
    elective_hours double precision,
    up_to_date boolean
    /*CONSTRAINT pk_certification_status_cert_id PRIMARY KEY (cert_id),
    CONSTRAINT fk_certification_status_train_id FOREIGN KEY (train_id) REFERENCES training(training_id)*/
);

ALTER TABLE user_profile
ADD FOREIGN KEY(profile_id)
REFERENCES users(id);

ALTER TABLE user_profile
ADD FOREIGN KEY(cert_id)
REFERENCES certification_status(cert_id);

ALTER TABLE certification_status
ADD FOREIGN KEY(train_id)
REFERENCES training(training_id);

-- Password for this user is 'greatwall'
INSERT INTO users (email, password, salt, permission) 
VALUES ('matt.goshorn@techelevator.com','FjZDm+sndmsdEDwNtfr6NA==',
'kidcasB0te7i0jK0fmRIGHSm0mYhdLTaiGkEAiEvLp7dAEHWnuT8n/5bd2V/mqjstQ198iImm1xCmEFu+BHyOz1Mf7vm4LILcrr17y7Ws40Xyx4FOCt8jD03G+jEafpuVJnPiDmaZQXJEpEfekGOvhKGOCtBnT5uatjKEuVWuDA=','admin');

INSERT INTO training (training_name, training_provider, training_topic, training_date, compliance, elective, training_proof, hours) 
VALUES('LEARNING HOW TO TEACH', 'YMCA', 'We teach people how to teach, its very educational.', '2020-02-13',true, false,null, 2.5);

INSERT INTO certification_status (train_id, compliance_hours, elective_hours, up_to_date) 
VALUES ((SELECT training_id FROM training WHERE training_name = 'LEARNING HOW TO TEACH'), 2.5, 0.0, false);

INSERT INTO user_profile (profile_id, profile_firstname, profile_lastname, role, start_date, profile_pic, cert_id) 
VALUES ((SELECT id FROM users WHERE email = 'matt.goshorn@techelevator.com' ),'Matt','Goshorn', 'Instructor', '2020-01-13', null, 1 );

commit;







