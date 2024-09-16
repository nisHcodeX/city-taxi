CREATE SEQUENCE IF NOT EXISTS account_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE account
(
    id           BIGINT NOT NULL,
    username     VARCHAR(255),
    password     VARCHAR(255),
    status       VARCHAR(255),
    account_type VARCHAR(255),
    created_at   TIMESTAMP WITHOUT TIME ZONE,
    updated_at   TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_account PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS booking_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE booking
(
    id                BIGINT NOT NULL,
    estimated_cost    DOUBLE PRECISION,
    start_destination VARCHAR(255),
    end_destination   VARCHAR(255),
    status            VARCHAR(255),
    driver_id         BIGINT,
    customer_id       BIGINT,
    rating_id         BIGINT,
    created_at        TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_booking PRIMARY KEY (id)
);

ALTER TABLE booking
    ADD CONSTRAINT uc_booking_rating UNIQUE (rating_id);

ALTER TABLE booking
    ADD CONSTRAINT FK_BOOKING_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE booking
    ADD CONSTRAINT FK_BOOKING_ON_DRIVER FOREIGN KEY (driver_id) REFERENCES driver (id);

ALTER TABLE booking
    ADD CONSTRAINT FK_BOOKING_ON_RATING FOREIGN KEY (rating_id) REFERENCES rating (id);

CREATE SEQUENCE IF NOT EXISTS customer_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE customer
(
    id           BIGINT NOT NULL,
    name         VARCHAR(255),
    email        VARCHAR(255),
    phone_number VARCHAR(255),
    account_id   BIGINT,
    longitude    DOUBLE PRECISION,
    latitude     DOUBLE PRECISION,
    created_at   TIMESTAMP WITHOUT TIME ZONE,
    updated_at   TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_customer PRIMARY KEY (id)
);

ALTER TABLE customer
    ADD CONSTRAINT uc_customer_account UNIQUE (account_id);

ALTER TABLE customer
    ADD CONSTRAINT uc_customer_email UNIQUE (email);

ALTER TABLE customer
    ADD CONSTRAINT uc_customer_phone_number UNIQUE (phone_number);

ALTER TABLE customer
    ADD CONSTRAINT FK_CUSTOMER_ON_ACCOUNT FOREIGN KEY (account_id) REFERENCES account (id);

CREATE SEQUENCE IF NOT EXISTS rating_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE rating
(
    id          BIGINT NOT NULL,
    rating      INTEGER,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    customer_id BIGINT,
    driver_id   BIGINT,
    CONSTRAINT pk_rating PRIMARY KEY (id)
);

ALTER TABLE rating
    ADD CONSTRAINT FK_RATING_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE rating
    ADD CONSTRAINT FK_RATING_ON_DRIVER FOREIGN KEY (driver_id) REFERENCES driver (id);

CREATE SEQUENCE IF NOT EXISTS vehicle_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE vehicle
(
    id              BIGINT NOT NULL,
    manufacturer    VARCHAR(255),
    model           VARCHAR(255),
    colour          VARCHAR(255),
    license_plate   VARCHAR(255),
    seat_count      INTEGER,
    vehicle_type_id BIGINT,
    created_at      TIMESTAMP WITHOUT TIME ZONE,
    updated_at      TIMESTAMP WITHOUT TIME ZONE,
    driver_id       BIGINT,
    CONSTRAINT pk_vehicle PRIMARY KEY (id)
);

ALTER TABLE vehicle
    ADD CONSTRAINT uc_vehicle_manufacturer UNIQUE (manufacturer);

ALTER TABLE vehicle
    ADD CONSTRAINT uc_vehicle_vehicle_type UNIQUE (vehicle_type_id);

ALTER TABLE vehicle
    ADD CONSTRAINT FK_VEHICLE_ON_DRIVER FOREIGN KEY (driver_id) REFERENCES driver (id);

ALTER TABLE vehicle
    ADD CONSTRAINT FK_VEHICLE_ON_VEHICLE_TYPE FOREIGN KEY (vehicle_type_id) REFERENCES vehicle_type (id);

CREATE SEQUENCE IF NOT EXISTS vehicle_type_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE vehicle_type
(
    id              BIGINT NOT NULL,
    name            VARCHAR(255),
    price_per_meter DOUBLE PRECISION,
    created_at      TIMESTAMP WITHOUT TIME ZONE,
    updated_at      TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_vehicle_type PRIMARY KEY (id)
);

ALTER TABLE vehicle_type
    ADD CONSTRAINT uc_vehicle_type_name UNIQUE (name);

CREATE SEQUENCE IF NOT EXISTS driver_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE driver
(
    id             BIGINT NOT NULL,
    nic            VARCHAR(255),
    name           VARCHAR(255),
    email          VARCHAR(255),
    phone_number   VARCHAR(255),
    driver_license VARCHAR(255),
    availability   VARCHAR(255),
    account_id     BIGINT,
    longitude      DOUBLE PRECISION,
    latitude       DOUBLE PRECISION,
    created_at     TIMESTAMP WITHOUT TIME ZONE,
    updated_at     TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_driver PRIMARY KEY (id)
);

ALTER TABLE driver
    ADD CONSTRAINT uc_driver_account UNIQUE (account_id);

ALTER TABLE driver
    ADD CONSTRAINT uc_driver_driver_license UNIQUE (driver_license);

ALTER TABLE driver
    ADD CONSTRAINT uc_driver_email UNIQUE (email);

ALTER TABLE driver
    ADD CONSTRAINT uc_driver_nic UNIQUE (nic);

ALTER TABLE driver
    ADD CONSTRAINT uc_driver_phone_number UNIQUE (phone_number);

ALTER TABLE driver
    ADD CONSTRAINT FK_DRIVER_ON_ACCOUNT FOREIGN KEY (account_id) REFERENCES account (id);