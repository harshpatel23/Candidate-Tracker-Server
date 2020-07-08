INSERT INTO `candidate_tracker`.`roles` (`role`,`role_string`) VALUES ('root','Root'),('admin','Admin'),('ops','OPS'),('recruiter','Recruiter'),('interviewer','Interviewer');

INSERT INTO `candidate_tracker`.`user` (`email`,`password`,`r_id`,`first_name`,`last_name`,`contact`) VALUES ('wissen@wissen.com','Wissen123','root','Wissen','Technology','123456789');
INSERT INTO `candidate_tracker`.`user` (`email`,`password`,`r_id`,`first_name`,`last_name`,`contact`,`manager_id`) VALUES ('harsh.patel4@somaiya.edu','Harsh123','admin','Harsh','Patel','1864513287',1);
INSERT INTO `candidate_tracker`.`user` (`email`,`password`,`r_id`,`first_name`,`last_name`,`contact`,`manager_id`) VALUES ('shivani@gmail.com','Shivani123','admin','Shivani','Jaiswal','8745852147',1);
INSERT INTO `candidate_tracker`.`user` (`email`,`password`,`r_id`,`first_name`,`last_name`,`contact`,`manager_id`) VALUES ('hitanshu@somaiya.edu','Hitanshu123','ops','Hitanshu','Shah','8451284511',2);
INSERT INTO `candidate_tracker`.`user` (`email`,`password`,`r_id`,`first_name`,`last_name`,`contact`,`manager_id`) VALUES ('murtaza@gmail.com','Murtaza123','ops','Murtaza','Patrawala','8541484511',2);
INSERT INTO `candidate_tracker`.`user` (`email`,`password`,`r_id`,`first_name`,`last_name`,`contact`,`manager_id`) VALUES ('ojas@somaiya.edu','Ojas123','recruiter','Ojas','Kapre','8451247511',4);
INSERT INTO `candidate_tracker`.`user` (`email`,`password`,`r_id`,`first_name`,`last_name`,`contact`,`manager_id`) VALUES ('tanay@somaiya.edu','Tanay123','interviewer','Tanay','Raul','7141284511',5);

select * from candidate_tracker.roles;
select * from candidate_tracker.user;
select * from candidate_tracker.user_closure;

# retreiving all children of an admin (Harsh -> id = 1)
use candidate_Tracker;
select first_name, last_name, email from user inner join user_closure on user.id = user_closure.child_id where user_closure.parent_id = 5;
