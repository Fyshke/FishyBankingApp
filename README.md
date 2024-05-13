Simple banking app with:
- Login & Register - Register your user and access the banking app through the login page
- Deposit - Deposit money to your account
- Withdraw - Withdraw money from your account
- Transfer - Transfer money to another registered user
- Past Transactions - Shows all transactions made since the making of your account

![image](https://github.com/Fyshke/FishyBankingApp/assets/147095784/a7e49d4a-8e39-4f3d-9a4c-4d1c607e5ff3)
![image](https://github.com/Fyshke/FishyBankingApp/assets/147095784/e7312dd8-613a-4357-82f0-6ffb2d085682)
![image](https://github.com/Fyshke/FishyBankingApp/assets/147095784/f674a1b3-4e43-4bd8-9893-c24982181904)



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
