drop procedure  set_known_good_state;
delimiter //
create procedure set_known_good_state()
begin
    delete from Student where id > 0;
    delete from Klass where id > 0;
    delete from Roster where id > 0;
    delete from Log where id > 0;
    delete from Tag where id > 0;

    alter table Student auto_increment = 1;
    alter table Klass auto_increment = 1;
    alter table Roster auto_increment = 1;
    alter table Log auto_increment = 1;
    alter table Tag auto_increment = 1;

    insert into Tag (tag) VALUES ('Bio');
    insert into Tag (tag) VALUES ('Interests');
    insert into Tag (tag) VALUES ('Interaction');
    insert into Tag (tag) VALUES ('Strengths');
    insert into Tag (tag) VALUES ('Weaknesses');
    insert into Tag (tag) VALUES ('Concern');
    insert into Tag (tag) VALUES ('Praise');
    insert into Tag (tag) VALUES ('Status');
end //
-- 4. Change the statement terminator back to the original.
delimiter ;