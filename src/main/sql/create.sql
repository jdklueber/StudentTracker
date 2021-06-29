drop schema if exists StudentTracker;
create schema StudentTracker;
use StudentTracker;

create table Student(
    id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255),
    last_name VARCHAR(255)
);

create table Klass(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    start_date DATE,
    end_date DATE
);

create table Roster(
    id INT PRIMARY KEY AUTO_INCREMENT,
    klass_id INT NOT NULL,
    student_id INT NOT NULL,
    is_active BOOLEAN,
    CONSTRAINT FK_ROSTER_CLASS
                   FOREIGN KEY (klass_id)
                   REFERENCES  Klass(id),
    CONSTRAINT FK_ROSTER_STUDENT
        FOREIGN KEY (student_id)
            REFERENCES  Student(id)
);

create table Tag(
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    tag VARCHAR(255) NOT NULL,
                    INDEX (tag)
);

create table Log(
    id INT PRIMARY KEY AUTO_INCREMENT,
    klass_id INT NOT NULL,
    student_id INT NOT NULL,
    time_stamp DATETIME,
    tag_id int NOT NULL,
    body TEXT NOT NULL,
    CONSTRAINT FK_LOG_CLASS
        FOREIGN KEY (klass_id)
            REFERENCES  Klass(id),
    CONSTRAINT FK_LOG_STUDENT
        FOREIGN KEY (student_id)
            REFERENCES  Student(id),
    CONSTRAINT FK_LOG_TAG
        FOREIGN KEY (tag_id)
            REFERENCES  Tag(id),
    FULLTEXT (body),
    INDEX (klass_id, time_stamp),
    INDEX (student_id, time_stamp)
);



insert into Tag (tag) VALUES ('Bio');
insert into Tag (tag) VALUES ('Interests');
insert into Tag (tag) VALUES ('Interaction');
insert into Tag (tag) VALUES ('Strengths');
insert into Tag (tag) VALUES ('Weaknesses');
insert into Tag (tag) VALUES ('Concern');
insert into Tag (tag) VALUES ('Praise');
insert into Tag (tag) VALUES ('Status');


