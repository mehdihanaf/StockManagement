INSERT INTO stock_user (active,deleted,id,"logged",email,first_name,last_name,"password",phone,roles,"token",username) VALUES
    (true,false,1,false,'admin@gmail.com','admin','lastname','$2a$12$zejkoE5.FWWTZObedy4f9ePgtya4u5ejJH8HswW.PImWXlY0nnVsK','0603041583','admin',NULL,'admin');


INSERT INTO CATEGORY (ID, NAME)
VALUES
    (1001, 'Telephones'),
    (1002, 'Smartwatch'),
    (1003, 'Chargeurs'),
    (1004, 'Pochette'),
    (1005, 'Laptops'),
    (1006, 'Tablets'),
    (1007, 'Headphones'),
    (1008, 'Monitors'),
    (1009, 'Gaming Mice'),
    (1010, 'Keyboards'),
    (1011, 'Printers'),
    (1012, 'Routers'),
    (1013, 'Graphics Cards'),
    (1014, 'Webcams'),
    (1015, 'Bluetooth Speakers'),
    (1016, 'Laptop Stands'),
    (1017, 'Projectors'),
    (1018, 'Gaming Chairs'),
    (1019, 'VR Headsets'),
    (1020, 'Microphones'),
    (1021, 'Smart TVs'),
    (1022, 'Desktop PCs');


INSERT INTO PRODUCT (ID, PRODUCT_CODE, PRODUCT_NAME, DESCRIPTION, QUANTITY, BUY_DATE, UNIT_BUY_PRICE, UNIT_SELL_PRICE, CATEGORY_ID)
VALUES
    (1001, 'P12345', 'Laptop Dell', 'High-performance laptop', 10, '2024-09-20', 800.00, 1200.00, 1001),
    (1002, 'P54321', 'Samsung A51', 'Latest model smartphone', 50, '2024-09-15', 400.00, 700.00, 1001),
    (1003, 'P98765', 'Headphones Sony', 'Noise-cancelling headphones', 100, '2024-09-18', 50.00, 100.00, 1002),
    (1004, 'P65432', 'Monitor', '4K Ultra HD Monitor', 25, '2024-09-10', 250.00, 400.00, 1003),
    (1005, 'P87654', 'Gaming Mouse', 'Wireless gaming mouse', 75, '2024-09-19', 30.00, 60.00, 1002),
    (1006, 'P11111', 'Mechanical Keyboard', 'Ergonomic mechanical keyboard', 60, '2024-09-21', 45.00, 80.00, 1004),
    (1007, 'P22222', 'Android Tablet', '10-inch Android tablet', 20, '2024-09-22', 200.00, 300.00, 1005),
    (1008, 'P33333', 'Smartwatch', 'Fitness tracking smartwatch', 35, '2024-09-23', 150.00, 250.00, 1006),
    (1009, 'P44444', 'External Hard Drive', '1TB USB 3.0 Hard Drive', 40, '2024-09-24', 70.00, 120.00, 1007),
    (1010, 'P55555', 'Wireless Charger', 'Fast charging wireless pad', 55, '2024-09-25', 25.00, 50.00, 1008),
    (1011, 'P66666', 'Laser Printer', 'High-speed laser printer', 10, '2024-09-26', 150.00, 250.00, 1009),
    (1012, 'P77777', 'Wi-Fi Router', 'High-performance Wi-Fi 6 router', 30, '2024-09-27', 100.00, 180.00, 1010),
    (1013, 'P88888', 'Graphics Card', 'High-end gaming graphics card', 5, '2024-09-28', 600.00, 900.00, 1011),
    (1014, 'P99999', 'HD Webcam', '1080p HD webcam', 80, '2024-09-29', 40.00, 70.00, 1012),
    (1015, 'P12121', 'Bluetooth Speaker', 'Portable Bluetooth speaker', 90, '2024-09-30', 60.00, 100.00, 1013),
    (1016, 'P23232', 'Laptop Stand', 'Adjustable laptop stand', 20, '2024-10-01', 30.00, 60.00, 1014),
    (1017, 'P34343', 'HD Projector', 'Full HD home projector', 15, '2024-10-02', 300.00, 500.00, 1015),
    (1018, 'P45454', 'Flagship Smartphone', 'High-end smartphone', 25, '2024-10-03', 700.00, 1000.00, 1016),
    (1019, 'P56565', 'Gaming Chair', 'Ergonomic gaming chair', 12, '2024-10-04', 200.00, 350.00, 1017),
    (1020, 'P67676', 'VR Headset', 'Virtual reality headset', 10, '2024-10-05', 400.00, 600.00, 1018),
    (1021, 'P78787', 'USB Microphone', 'Studio-quality USB microphone', 45, '2024-10-06', 80.00, 150.00, 1019),
    (1022, 'P89898', 'Smart TV', '50-inch 4K Smart TV', 8, '2024-10-07', 500.00, 800.00, 1020),
    (1023, 'P90909', 'Desktop PC', 'Powerful gaming desktop PC', 7, '2024-10-08', 1000.00, 1500.00, 1021),
    (1024, 'P10101', 'Portable SSD', '500GB Portable SSD', 60, '2024-10-09', 70.00, 120.00, 1001),
    (1025, 'P20202', 'Drawing Tablet', 'Professional drawing tablet', 25, '2024-10-10', 150.00, 250.00, 1002),
    (1026, 'P30303', 'Smart Air Purifier', 'Air purifier with smart controls', 20, '2024-10-11', 120.00, 200.00, 1003),
    (1027, 'P40404', 'Electric Kettle', 'Smart electric kettle with temperature control', 50, '2024-10-12', 40.00, 80.00, 1004),
    (1028, 'P50505', 'Gaming Keyboard', 'RGB mechanical gaming keyboard', 65, '2024-10-13', 60.00, 100.00, 1005),
    (1029, 'P60606', 'Fitness Tracker', 'Waterproof fitness tracker', 30, '2024-10-14', 30.00, 60.00, 1006),
    (1030, 'P70707', 'Wireless Earbuds', 'Bluetooth wireless earbuds', 100, '2024-10-15', 50.00, 100.00, 1007);


   insert into sale(id,description,discount,sale_date,sale_quantity,product_id) values
   	(1000, 'sale-1',500,'2024-09-20', 5, 1001),
       (1001, 'sale-2',500, '2024-09-21', 3, 1002),
       (1002, 'sale-3',500, '2024-09-22', 8, 1003),
       (1003, 'sale-4',500, '2024-09-23', 2, 1004),
       (1004, 'sale-5',500, '2024-09-24', 7, 1005);