drop table IF EXISTS customer;
drop table IF EXISTS category;

create table customer (
  id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) not null,
  address VARCHAR(100),
  contact VARCHAR(100),
  contact2 VARCHAR(100),
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create table category (
  id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) not null,
  description VARCHAR(100),
  imgSrc VARCHAR(100),
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
