INSERT
INTO
    account(id, username, password, status, account_type, created_at)
VALUES
    (1, 'admin@citytaxi.com', 'Admin@1234', 'ACTIVE', 'ADMIN', now());

INSERT
INTO
    admin(id, name, email, phone_number, account_id, created_at)
VALUES
    (1, 'John Doe', 'admin@citytaxi.com', '+94775145763', 1, now());