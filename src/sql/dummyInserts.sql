-- 📋 Insert into lookup tables first

INSERT INTO thelodge.designation (name) VALUES
('Manager'),
('Receptionist'),
('Housekeeping');

INSERT INTO thelodge.payment_method (name) VALUES
('Credit Card'),
('Cash'),
('UPI');

INSERT INTO thelodge.travel_mode (name) VALUES
('Car'),
('Train'),
('Airplane');

INSERT INTO thelodge.room_type (name, description, cost, smoke_friendly, pet_friendly) VALUES
('Single', 'Single bed room', 100.00, false, false),
('Double', 'Double bed room', 150.00, false, true),
('Suite', 'Luxury suite', 300.00, true, true);

-- 📋 Addresses

INSERT INTO thelodge.address (line1, city, state, pincode) VALUES
('123 Main St', 'New York', 'NY', '10001'),
('456 Oak St', 'Chicago', 'IL', '60611'),
('789 Pine St', 'San Francisco', 'CA', '94107');

-- 📋 Hotels

INSERT INTO thelodge.hotel (name, room_capacity, address_id) VALUES
('Grand Hotel', 50, 1),
('Ocean View', 30, 2),
('Mountain Inn', 20, 3);

-- 📋 Guests

INSERT INTO thelodge.guest (first_name, last_name, phone, email, age, address_id) VALUES
('John', 'Doe', '5551234', 'john@example.com', 30, 1),
('Jane', 'Smith', '5555678', 'jane@example.com', 25, 2),
('Alice', 'Johnson', '5558765', 'alice@example.com', 28, 3);

-- 📋 Employees

INSERT INTO thelodge.employee (first_name, last_name, designation_id, phone, email, hotel_id, address_id) VALUES
('Michael', 'Scott', 1, '5550001', 'michael@hotel.com', 1, 1),
('Pam', 'Beesly', 2, '5550002', 'pam@hotel.com', 2, 2),
('Dwight', 'Schrute', 3, '5550003', 'dwight@hotel.com', 3, 3);

-- 📋 Rooms

INSERT INTO thelodge.room (room_number, room_type_id, hotel_id) VALUES
(101, 1, 1),
(102, 2, 1),
(201, 3, 2),
(202, 1, 2),
(301, 2, 3),
(302, 3, 3);

-- 📋 Bookings

INSERT INTO thelodge.booking (guest_id, employee_id, hotel_id, travel_mode_id, booking_date, check_in, check_out, total_rooms, total_amount) VALUES
(1, 1, 1, 1, '2025-07-01', '2025-07-10', '2025-07-12', 1, 200.00),
(2, 2, 2, 2, '2025-07-02', '2025-07-15', '2025-07-18', 2, 400.00),
(3, 3, 3, 3, '2025-07-03', '2025-07-20', '2025-07-22', 1, 300.00);

-- 📋 Booking Rooms

INSERT INTO thelodge.booking_room (booking_id, room_id) VALUES
(1, 1),
(2, 3),
(2, 4),
(3, 6);

-- 📋 Payments

INSERT INTO thelodge.payment (booking_id, method_id, amount, paid_at, transaction_ref, status) VALUES
(1, 1, 200.00, '2025-07-01 12:00', 'TXN123', 'SUCCESS'),
(2, 2, 200.00, '2025-07-02 15:00', NULL, 'SUCCESS'),
(2, 3, 200.00, '2025-07-02 16:00', 'UPI456', 'SUCCESS'),
(3, 1, 300.00, '2025-07-03 18:00', 'TXN789', 'SUCCESS');
