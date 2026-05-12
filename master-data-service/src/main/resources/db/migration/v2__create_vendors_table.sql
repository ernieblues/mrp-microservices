CREATE TABLE vendors (
    id BIGSERIAL PRIMARY KEY,

    code VARCHAR(25) NOT NULL UNIQUE,

    name VARCHAR(100) NOT NULL,

    contact_name VARCHAR(100),

    email VARCHAR(100),

    phone VARCHAR(25),

    address_line1 VARCHAR(200),
    address_line2 VARCHAR(200),

    city VARCHAR(100),
    state VARCHAR(50),
    postal_code VARCHAR(20),
    country VARCHAR(100),

    active BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);
