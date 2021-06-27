drop schema if exists StudentTracker;
create schema StudentTracker;
use StudentTrackerTest;

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

create table Log(
    id INT PRIMARY KEY AUTO_INCREMENT,
    klass_id INT NOT NULL,
    student_id INT NOT NULL,
    time_stamp DATETIME,
    tag VARCHAR(255) NOT NULL,
    body TEXT NOT NULL,
    CONSTRAINT FK_LOG_CLASS
        FOREIGN KEY (klass_id)
            REFERENCES  Klass(id),
    CONSTRAINT FK_LOG_STUDENT
        FOREIGN KEY (student_id)
            REFERENCES  Student(id),
    FULLTEXT (body),
    INDEX (tag),
    INDEX (klass_id, time_stamp),
    INDEX (student_id, time_stamp)
);

create table Tag(
    id INT PRIMARY KEY AUTO_INCREMENT,
    tag VARCHAR(255) NOT NULL,
    INDEX (tag)
);

insert into Tag (tag) VALUES ('Bio');
insert into Tag (tag) VALUES ('Interests');
insert into Tag (tag) VALUES ('Interaction');
insert into Tag (tag) VALUES ('Strengths');
insert into Tag (tag) VALUES ('Weaknesses');
insert into Tag (tag) VALUES ('Concern');
insert into Tag (tag) VALUES ('Praise');
insert into Tag (tag) VALUES ('Status');


