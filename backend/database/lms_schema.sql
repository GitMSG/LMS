


DROP TABLE IF EXISTS users                      cascade;
DROP TABLE IF EXISTS employee_profile           cascade;
DROP TABLE IF EXISTS training                   cascade;
DROP TABLE IF EXISTS cert_period                cascade;
DROP TABLE IF EXISTS training_cert_period       cascade;
DROP TABLE IF EXISTS campus                     cascade;

CREATE TABLE users(
    id serial ,
    email varchar(64) NOT NULL UNIQUE,
    firstname varchar(32),
    lastname varchar(32),
    profile_pic varchar(255),
    password varchar(32) NOT NULL,
    salt varchar(256) NOT NULL,
    permission varchar(32),
    CONSTRAINT pk_users_id PRIMARY KEY (id)
);

CREATE TABLE campus(
    short_code varchar(64) NOT NULL,
    city varchar(64) NOT NULL,
    state varchar(64) NOT NULL,
    cert_length integer NOT NULL,
    CONSTRAINT pk_campus_short_code PRIMARY KEY (short_code)
);

CREATE TABLE employee_profile(
    emp_id serial NOT NULL,
    user_id integer NOT NULL,
    role varchar(255) NOT NULL,
    start_date date NOT NULL,
    end_date date,
    campus_short varchar(64) NOT NULL,
    CONSTRAINT pk_employee_profile_emp_id PRIMARY KEY (emp_id)
);

CREATE TABLE training(
    train_id serial ,
    train_name varchar(255) NOT NULL,
    train_provider varchar(255) NOT NULL,
    train_topic varchar(800) NOT NULL,
    train_date date NOT NULL,
    is_compliance boolean NOT NULL,
    train_proof varchar(255),
    minutes integer NOT NULL,
    approved boolean NOT NULL,
    CONSTRAINT pk_training_train_id PRIMARY KEY (train_id)
);

CREATE TABLE cert_period(
    cert_id serial,
    emp_id integer NOT NULL,
    cert_start_date date NOT NULL,
    CONSTRAINT pk_cert_period_cert_id PRIMARY KEY (cert_id)
);

CREATE TABLE training_cert_period (
    train_id integer NOT NULL,
    cert_period_id integer NOT NULL,
    CONSTRAINT pk_training_cert_period_train_id_cert_period_id PRIMARY KEY (train_id, cert_period_id)
);

-- Password is 'greatwall'

INSERT INTO users (email, firstname, lastname, profile_pic, password, salt, permission) 
        VALUES ('matt@gmail.com','Matt','Goshorn','https://res.cloudinary.com/goshorn/image/upload/v1593971293/lms_test/yskdldqgqnvu7jieywyr.jpg','FjZDm+sndmsdEDwNtfr6NA==',
                'kidcasB0te7i0jK0fmRIGHSm0mYhdLTaiGkEAiEvLp7dAEHWnuT8n/5bd2V/mqjstQ198iImm1xCmEFu+BHyOz1Mf7vm4LILcrr17y7Ws40Xyx4FOCt8jD03G+jEafpuVJnPiDmaZQXJEpEfekGOvhKGOCtBnT5uatjKEuVWuDA=','admin');

INSERT INTO training (train_name, train_provider, train_topic, train_date, is_compliance, train_proof, minutes, approved) 
        VALUES('LEARNING HOW TO TEACH', 'YMCA', 'We teach people how to teach, its very educational.', '2020-02-13',true, null, 90, true);
        
INSERT INTO campus (short_code, city, state, cert_length)
        --VALUES('CLE', 'Cleveland', 'Ohio', 2);
        --VALUES('CBUS', 'Columbus','Ohio', 2);
        --VALUES('CINCY','Cincinnati','Ohio', 2);
        --VALUES('PGH','Pittsburgh','Pennsylvania', 2);
        VALUES('PHL','Philadelphia','Pennsylvania', 2);

INSERT INTO cert_period (emp_id, cert_start_date) 
        VALUES ((SELECT id FROM users WHERE users.email ='matt@gmail.com'), '2018-10-01');

INSERT INTO employee_profile (user_id,  role, start_date, end_date, campus_short) 
        VALUES ((SELECT id FROM users WHERE email = 'matt@gmail.com' ), 'Instructor', '2020-01-13', null,'CLE' );

INSERT INTO training_cert_period (train_id, cert_period_id)
        VALUES ((SELECT train_id FROM training WHERE training.train_name='LEARNING HOW TO TEACH'), 1);
  
ALTER TABLE employee_profile
ADD FOREIGN KEY(user_id)
REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE employee_profile
ADD FOREIGN KEY(campus_short)
REFERENCES campus(short_code)ON DELETE CASCADE;

ALTER TABLE cert_period
ADD FOREIGN KEY(emp_id)
REFERENCES employee_profile(emp_id)ON DELETE CASCADE;

ALTER TABLE training_cert_period 
ADD FOREIGN KEY(train_id)
REFERENCES training(train_id)ON DELETE CASCADE;

ALTER TABLE training_cert_period 
ADD FOREIGN KEY(cert_period_id)
REFERENCES cert_period(cert_id)ON DELETE CASCADE;

            --         'https://res.cloudinary.com/goshorn/image/upload/v1596050794/lms_test/wgc3a23t3fia1wjcska9.jpg'           Wierd Guy
            --         'https://res.cloudinary.com/goshorn/image/upload/v1593971293/lms_test/yskdldqgqnvu7jieywyr.jpg'           Matt Photo
            --         'https://res.cloudinary.com/goshorn/image/upload/v1596286167/lms_test/TE_bur_z3zvc4.png'                  TE logo
            --         'https://res.cloudinary.com/goshorn/image/upload/v1594733918/lms_test/m5dk9epbzckoohjh4zgv.jpg'           Lady model
         
update users
set profile_pic = 'https://res.cloudinary.com/goshorn/image/upload/v1596050794/lms_test/wgc3a23t3fia1wjcska9.jpg'
where users.id = 2
--------------------------------------------------------------------------------------------------------------------- 
----------------------------------Testing sql queries before using in DAO---------------------------------------------     
        
   SELECT training.*, user_profile.firstname, user_profile.lastname, user_profile.profile_pic
   FROM training
        JOIN training_cert_period ON training.train_id = training_cert_period.train_id
        JOIN cert_period ON training_cert_period.cert_period_id = cert_period.cert_id
        JOIN user_profile ON cert_period.profile_id = user_profile.profile_id
        JOIN campus ON user_profile.campus_id = campus.campus_id
    WHERE training.approved = false AND campus.campus_id = 1;
    
    SELECT employee_profile.*, users.email, users.firstname, users.lastname, users.profile_pic
    FROM employee_profile
        JOIN users ON employee_profile.user_id = users.id
    WHERE users.firstname NOT LIKE 'TE Firstname' AND employee_profile.end_date is NULL
        
        SELECT EXISTS(SELECT 1 FROM employee_profile WHERE user_id = ?);
        "SELECT count(*) FROM employee_profile WHERE user_id = ?"
        
          
    



