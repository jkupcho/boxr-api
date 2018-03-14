INSERT INTO PRODUCT (id, description, name) VALUES(1, 'Simple Widget', 'Widget');

INSERT INTO INVENTORY (id, on_hand, on_order, low_threshold, product_id) VALUES (1, 10, 0, 100, 1);
INSERT INTO INVENTORY (id, on_hand, on_order, low_threshold, product_id) VALUES (2, 0, 10, 200, 1);
INSERT INTO INVENTORY (id, on_hand, on_order, low_threshold, product_id) VALUES (3, 0, 10, 200, 1);
