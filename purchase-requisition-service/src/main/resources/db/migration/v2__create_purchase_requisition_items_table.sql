CREATE TABLE purchase_requisition_items
(
    id BIGSERIAL PRIMARY KEY,

    purchase_requisition_id BIGINT NOT NULL,

    line_number INTEGER NOT NULL,

    product_id BIGINT,

    description VARCHAR(255) NOT NULL,

    vendor_part_number VARCHAR(255),

    quantity NUMERIC(18, 4) NOT NULL,

    unit_of_measure VARCHAR(25) NOT NULL DEFAULT 'EA',

    unit_price NUMERIC(18, 2) NOT NULL DEFAULT 0,

    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);
