INSERT
INTO
    vehicle_type
    (id, name, price_per_meter, seat_count)
VALUES
    (nextval('vehicle_type_sequence'), 'Bike', 0.5, 1),
    (nextval('vehicle_type_sequence'), 'Car', 2.5, 4);