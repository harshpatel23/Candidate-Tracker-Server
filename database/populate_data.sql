INSERT INTO `candidate_tracker`.`roles` (`role`,`role_string`,`h_level`) VALUES ('root','Root','1.0'),('admin','Admin','2'),('ops','OPS','3'),('recruiter','Recruiter','4'),('interviewer','Interviewer','5');

INSERT  INTO `candidate_tracker`.`user` (`email`,`password`,`r_id`,`first_name`,`last_name`,`contact`) VALUES ('wissen@wissen.com','$2y$10$CCzs6K2oepmDJO1SReJaVume1BKX6RtqiX8/KztUmEK.nzqWm0TnG
','root','Wissen','Technology','123456789');
INSERT INTO `candidate_tracker`.`user` (`email`,`password`,`r_id`,`first_name`,`last_name`,`contact`,`manager_id`) VALUES ('harsh.patel4@somaiya.edu','$2y$10$yzbedCS5132euijy6P6GTuQJi0pUz4MbrHhnRTXQINylS170KRXd2
','admin','Harsh','Patel','1864513287',1);
INSERT INTO `candidate_tracker`.`user` (`email`,`password`,`r_id`,`first_name`,`last_name`,`contact`,`manager_id`) VALUES ('shivani@gmail.com','$2y$10$vkJUNXBnI14.OAtxNyWlPOIhkiGNHGb/LOC9asNmD0TdBNTfD7Euy
','admin','Shivani','Jaiswal','8745852147',1);
INSERT INTO `candidate_tracker`.`user` (`email`,`password`,`r_id`,`first_name`,`last_name`,`contact`,`manager_id`) VALUES ('hitanshu@somaiya.edu','$2y$10$RH0mB2IC0Bmr6/A1vTUsNuauiXBgaSoKvquSlbtdEeC6oiVA/.PAO
','ops','Hitanshu','Shah','8451284511',2);
INSERT INTO `candidate_tracker`.`user` (`email`,`password`,`r_id`,`first_name`,`last_name`,`contact`,`manager_id`) VALUES ('murtaza@gmail.com','$2y$10$FffiDgxWetjAylLrmUlrBO3SmYmKk0TvkXYe7UN3RhMAJw99.u/tC
','ops','Murtaza','Patrawala','8541484511',2);
INSERT INTO `candidate_tracker`.`user` (`email`,`password`,`r_id`,`first_name`,`last_name`,`contact`,`manager_id`) VALUES ('ojas@somaiya.edu','$2y$10$zFtsTy4ILqfHIIJiq19SrOKDa9TyRFjAwb1iomGqQXzhwS5Yv5srq
','recruiter','Ojas','Kapre','8451247511',4);
INSERT INTO `candidate_tracker`.`user` (`email`,`password`,`r_id`,`first_name`,`last_name`,`contact`,`manager_id`) VALUES ('tanay@somaiya.edu','$2y$10$6V8F1Wyu84k8x3ULhopwgOAWl.gmErMVhgPxQ0GbunkbJhR48AvQm
','interviewer','Tanay','Raul','7141284511',5);

select * from candidate_tracker.roles;
select * from candidate_tracker.user;
select * from candidate_tracker.user_closure;

# retreiving all children of an admin (Harsh -> id = 1)
use candidate_Tracker;
select first_name, last_name, email from user inner join user_closure on user.id = user_closure.child_id where user_closure.parent_id = 5;
