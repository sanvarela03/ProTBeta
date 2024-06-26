INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
INSERT INTO roles(name) VALUES('ROLE_PRODUCER');
INSERT INTO roles(name) VALUES('ROLE_TRANSPORTER');
INSERT INTO roles(name) VALUES('ROLE_CUSTOMER');


INSERT INTO status(name) VALUES('CREATED');
INSERT INTO status(name) VALUES('ACCEPTED');
INSERT INTO status(name) VALUES('TRANSPORTER_ASSIGNED');
INSERT INTO status(name) VALUES('ON_WAY_TO_PICKUP');
INSERT INTO status(name) VALUES('PICKED_UP');
INSERT INTO status(name) VALUES('SHIPPED');
INSERT INTO status(name) VALUES('ON_WAY_TO_DELIVERY');
INSERT INTO status(name) VALUES('PROCESSING_PAYMENT');
INSERT INTO status(name) VALUES('DELIVERED');
INSERT INTO status(name) VALUES('CANCELED');


INSERT INTO payment_methods(name) VALUES ('CREDIT_CARD');
INSERT INTO payment_methods(name) VALUES ('DEBIT_CARD');
INSERT INTO payment_methods(name) VALUES ('CASH_ON_DELIVERY');
INSERT INTO payment_methods(name) VALUES ('BANK_TRANSFER');
INSERT INTO payment_methods(name) VALUES ('OTHER');

INSERT INTO rates(name, value, currency, unit_description) VALUES ('GAS', 4068.25152, 'COP', 'COP / Liter');
INSERT INTO rates(name, value, currency, unit_description) VALUES ('WEIGHT', 1000.0, 'COP', 'COP/Kg');
