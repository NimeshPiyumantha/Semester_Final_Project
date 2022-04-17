DROP DATABASE IF EXISTS SunriseBakery;
CREATE DATABASE IF NOT EXISTS SunriseBakery;
SHOW DATABASES;
USE SunriseBakery;

#------------Customer---------------------------------------------------------------------------------------------------

DROP TABLE IF EXISTS Customer;
CREATE TABLE Customer
(
    CustomerID       VARCHAR(6),
    CustomerTitle    VARCHAR(5),
    CustomerName     VARCHAR(30),
    CustomerAddress  VARCHAR(30),
    CustomerCity     VARCHAR(20),
    CustomerProvince VARCHAR(20),
    CustomerPostCode VARCHAR(9),
    CustomerAddDate DATE,
    CustomerAddTime TIME,
    CONSTRAINT PRIMARY KEY (CustomerID)
);

SHOW TABLES;
DESCRIBE Customer;

INSERT INTO  Customer VALUES ('C-001','Mr','Nimesh','Hapugala','Galle','Southern ',80000,'2022-03-26','09:10:25');
INSERT INTO  Customer VALUES ('C-002','Mrs','Anuri','Ukwatta','Galle','Southern ',80000,'2022-03-26','09:15:15');
INSERT INTO  Customer VALUES ('C-003','Mr','Pasan','Mampitiya','Galle','Southern ',80000,'2022-03-26','09:20:05');
INSERT INTO  Customer VALUES ('C-004','Mr','Akila','Niladeniya','Galle','Southern ',80000,'2022-03-26','09:20:25');

#--------Cashier--------------------------------------------------------------------------------------------------------

DROP TABLE IF EXISTS Cashier;
CREATE TABLE Cashier
(
    CashierID          VARCHAR(6),
    CashierName        VARCHAR(30),
    CashierPsw         VARCHAR(20),
    CashierAddress     VARCHAR(20),
    CashierCNumber     VARCHAR(15),
    CashierNIC         VARCHAR(15),
    CashierSalary      DOUBLE,
    CashierDescription VARCHAR(15),
    CashierAddDate DATE,
    CashierAddTime TIME,
    CONSTRAINT PRIMARY KEY (CashierID)
);

SHOW TABLES;
DESCRIBE Cashier;

INSERT INTO  Cashier VALUES ('CH-001','Nimesh','Nimma12','Galle','0712345671','200131132302',60000,'Cashier','2022-03-25','08:10:15');

#------------Item-------------------------------------------------------------------------------------------------------

DROP TABLE IF EXISTS Item;
CREATE TABLE IF NOT EXISTS Item
(
    ItemCode        VARCHAR(6),
    ItemName        VARCHAR(50),
    ItemDescription VARCHAR(50),
    ItemPackSize    VARCHAR(20),
    ItemUnitPrice   DOUBLE DEFAULT 0.00,
    ItemQtyOnHand   INT    DEFAULT 0,
    ItemAddDate     DATE,
    ItemAddTime     TIME,
    CONSTRAINT PRIMARY KEY (ItemCode)
);

SHOW TABLES;
DESCRIBE Item;

INSERT INTO  ITEM VALUES ('I-001','Rose Cupcake','Glomark','70 g',100.00,300,'2022-03-26','09:24:24');
INSERT INTO  ITEM VALUES ('I-002','Blueberry Muffins','Glomark','70 g',70.00,300,'2022-03-26','09:25:24');
INSERT INTO  ITEM VALUES ('I-003','Fish Patty','Glomark','50 g',110.00,300,'2022-03-26','09:28:24');
INSERT INTO  ITEM VALUES ('I-004','Seeni Sambol Bun','Glomark','70 g',100.00,400,'2022-03-26','09:29:24');
INSERT INTO  ITEM VALUES ('I-005','Fish Bun','Glomark','70 g',100.00,400,'2022-03-26','09:30:24');


#----------------`Order`-----------------------------------------------------------------------------------------
DROP TABLE IF EXISTS `Order`;
CREATE TABLE IF NOT EXISTS `Order`
(
    OrderID    VARCHAR(6),
    CustomerID VARCHAR(6),
    OrderDate  DATE,
    OrderTime  TIME,
    CONSTRAINT PRIMARY KEY (OrderID),
    CONSTRAINT FOREIGN KEY (CustomerID) REFERENCES Customer (CustomerID) ON DELETE CASCADE ON UPDATE CASCADE
);

SHOW TABLES;
DESCRIBE `Order`;

#--------------------`OrderDetails`--------------------------------------------------------------------------------------------

DROP TABLE IF EXISTS `Order Details`;
CREATE TABLE IF NOT EXISTS `Order Details`
(
    OrderID   VARCHAR(6),
    CustomerID VARCHAR(6),
    ItemCode  VARCHAR(6),
    ItemName  VARCHAR(50),
    OrderQty  INT    DEFAULT 0,
    unitPrice DOUBLE DEFAULT 0.00,
    Discount  DOUBLE,
    Total     DOUBLE,
    CONSTRAINT PRIMARY KEY (OrderID,CustomerID,ItemCode),
    CONSTRAINT FOREIGN KEY (OrderID) REFERENCES `Order` (OrderID) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (CustomerID) REFERENCES Customer (CustomerID) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (ItemCode) REFERENCES Item (ItemCode) ON DELETE CASCADE ON UPDATE CASCADE
);

SHOW TABLES;
DESCRIBE `Order Details`;

#-------------------------------------Admin-Login-----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS Login
(
    UserName VARCHAR(10),
    Password VARCHAR(10),
    CONSTRAINT PRIMARY KEY (UserName)
);
SHOW TABLES;
DESCRIBE Login;
INSERT INTO Login (UserName, PassWord)
VALUES ('Admin', 1234);


#---------------------------Insert data---------------------------------------------------------------------------------
SHOW TABLES;

Use SunriseBakery;
SELECT sum(OrderQty),ItemCode,ItemName,unitPrice FROM `Order Details` GROUP BY  ItemCode ORDER BY sum(OrderQty) DESC  ;
SELECT sum(OrderQty),ItemCode,ItemName,unitPrice,OrderQty FROM `Order Details` GROUP BY  ItemCode ORDER BY sum(OrderQty) DESC limit 1 ;

USE SunriseBakery;
