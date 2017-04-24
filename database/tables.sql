drop table IF EXISTS cardItem;
drop table IF EXISTS card;
drop table IF EXISTS productInventory;
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
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) not null,
  retailPrice float NOT NULL,
  vendor int NOT NULL,
  brand int NOT NULL,
  category int NOT NULL,
  description VARCHAR(100) not null,
  imgSrc VARCHAR(100) not null,
  total int DEFAULT 0,
  stockLimit int DEFAULT 0,
  unitCost float DEFAULT 0,
  boxCost float DEFAULT 0,
  boxSize int DEFAULT 0,
  FOREIGN KEY (vendor) REFERENCES vendor(id),
  FOREIGN KEY (brand) REFERENCES brand(id),
  FOREIGN KEY (category) REFERENCES category(id),
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create table productInventory (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  quantity int not null,
  cost float NOT NULL,
  vendor int NOT NULL,
  product int NOT NULL,
  FOREIGN KEY (vendor) REFERENCES vendor(id),
  FOREIGN KEY (product) REFERENCES product(id),
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create table card (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  customer int NOT NULL,
  totalPrice float,
  observation VARCHAR(100),
  FOREIGN KEY (customer) REFERENCES customer(id),
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create table cardItem (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  card int NOT NULL,
  product int NOT NULL,
  quantity int not null,
  price float NOT NULL,
  totalPrice float NOT NULL,
  FOREIGN KEY (card) REFERENCES card(id),
  FOREIGN KEY (product) REFERENCES product(id),
  createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO brand(id, name, description)
  VALUES(1, "Brand Name", "Description of brand");

INSERT INTO vendor(id, name, address, contact, contact2)
  VALUES(1, "Vendor Name", "Republica Av,Cochabamba City, BO", "Contact 1", "Contact 2");

INSERT INTO category(id, name, description, imgSrc )
  VALUES(1, "CATEGORY1", "Republica Av, Cochabamba City, BO", "/scalafx/ecstock/products/category.png");

INSERT INTO customer(id, name, address)
  VALUES(1, "Customer Name", "Republica Av,Cochabamba City, BO");

INSERT INTO product(name, retailPrice, vendor, brand, category, description, imgSrc, total, stockLimit, unitCost, boxCost, boxSize)
  VALUES('Product 0', '100', '1', '1', '1', 'description', '/scalafx/ecstock/products/product.png', '100', '5', '10', 100, 10),
  ('Product 1', '50', '1', '1', '1', 'description', '/scalafx/ecstock/products/product.png', '50', '5', '10', 100, 10),
  ('Product 2', '200', '1', '1', '1', 'description', '/scalafx/ecstock/products/product.png', '200', '5', '10', 100, 10),
  ('Product 3', '50', '1', '1', '1', 'description', '/scalafx/ecstock/products/product.png', '50', '5', '10', 100, 10),
  ('Product 4', '200', '1', '1', '1', 'description', '/scalafx/ecstock/products/product.png', '200', '5', '10', 100, 10),
  ('Product 5', '50', '1', '1', '1', 'description', '/scalafx/ecstock/products/product.png', '50', '5', '10', 100, 10),
  ('Product 6', '200', '1', '1', '1', 'description', '/scalafx/ecstock/products/product.png', '200', '5', '10', 100, 10),
  ('Product 7', '50', '1', '1', '1', 'description', '/scalafx/ecstock/products/product.png', '50', '5', '10', 100, 10),
  ('Product 8', '200', '1', '1', '1', 'description', '/scalafx/ecstock/products/product.png', '200', '5', '10', 100, 10),
  ('Product 9', '50', '1', '1', '1', 'description', '/scalafx/ecstock/products/product.png', '50', '5', '10', 100, 10),
  ('Product 10', '200', '1', '1', '1', 'description', '/scalafx/ecstock/products/product.png', '200', '5', '10', 100, 10),
  ('Product 11', '20', '1', '1', '1', 'description', '/scalafx/ecstock/products/product.png', '20', '5', '10', 100, 10);

