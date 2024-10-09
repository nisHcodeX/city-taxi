-- Get the next value from the account sequence
DO $$
DECLARE
account_id BIGINT;
BEGIN
    -- Get the next value from the sequence
SELECT nextval('account_sequence') INTO account_id;

-- Insert into the account table
INSERT INTO account(id, username, password, status, account_type, created_at)
VALUES (account_id, 'admin@citytaxi.com', 'Admin@1234', 'ACTIVE', 'ADMIN', now());

-- Insert into the admin table using the generated account_id
INSERT INTO admin(id, name, email, phone_number, account_id, created_at)
VALUES (nextval('admin_sequence'), 'John Doe', 'admin@citytaxi.com', '+94775145763', account_id, now());
END $$;