Simple banking app with a Login & Register, Deposit, Withdraw, Transfer & Past Transactions function.
Made with IntelliJ & MySQL.


Here is the SQL code needed for it to work:

CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `current_balance` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
)

CREATE TABLE `transactions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `transaction_amount` decimal(10,2) NOT NULL,
  `transaction_date` datetime NOT NULL,
  `transaction_type` varchar(45) NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id_idx` (`user_id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
)
