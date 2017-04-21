drop table IF EXISTS product;
drop table IF EXISTS category;
drop table IF EXISTS brand;
drop table IF EXISTS vendor;
drop table IF EXISTS customer;

create table customer (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  address VARCHAR(100),
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create table category (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  description VARCHAR(100),
  imgSrc VARCHAR(100),
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create table brand (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  description VARCHAR(100),
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create table vendor (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  address VARCHAR(100),
  contact VARCHAR(100),
  contact2 VARCHAR(100),
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create table product (
  id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) not null,
  retailPrice int NOT NULL,
  vendor int NOT NULL,
  brand int NOT NULL,
  category int NOT NULL,
  description VARCHAR(100) not null,
  imgSrc VARCHAR(100) not null,
  FOREIGN KEY (vendor) REFERENCES vendor(id),
  FOREIGN KEY (brand) REFERENCES brand(id),
  FOREIGN KEY (category) REFERENCES category(id),
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

