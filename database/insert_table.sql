use sql6642024;

insert into role(code,name) values('ROLE_ADMIN','Quan tri');
insert into role(code,name) values('ROLE_USER','Nguoi dung');

insert into users(user_name,password,name, avatar) 
values('admin','$2a$10$/RUbuT9KIqk6f8enaTQiLOXzhnUkiwEJRdtzdrMXXwU7dgnLKTCYG','admin', 'http://localhost:8080/user/tom.png');

INSERT INTO user_role(user_id,role_id) VALUES (1,1);


DELIMITER $$  
  
CREATE TRIGGER before_delete_user 
BEFORE DELETE  
ON users FOR EACH ROW  
BEGIN  
    delete from user_role where user_role.user_id = OLD.`id`;
    delete from videos where videos.createdby = OLD.`id`;
    delete from `comments` where `comments`.createdby = OLD.`id`;
    delete from emotions where emotions.user_id = OLD.`id`;
END$$   
  
DELIMITER ; 

DELIMITER $$  
  
CREATE TRIGGER before_delete_video
BEFORE DELETE  
ON videos FOR EACH ROW  
BEGIN  
    delete from `comments` where `comments`.video_id = OLD.`id`;
     delete from emotions where emotions.video_id = OLD.`id`;
END$$   
  
DELIMITER ; 
