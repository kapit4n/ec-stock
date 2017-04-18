drop table IF EXISTS customer;

create table customer (
  id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) not null,
  address VARCHAR(100),
  contact VARCHAR(100),
  contact2 VARCHAR(100),
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
