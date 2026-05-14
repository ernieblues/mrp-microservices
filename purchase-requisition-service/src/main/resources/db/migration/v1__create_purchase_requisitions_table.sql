CREATE SEQUENCE purchase_requisitions_num_seq START WITH 1000;

CREATE TABLE purchase_requisitions
(
    id BIGSERIAL PRIMARY KEY,

    purchase_requisition_number BIGINT NOT NULL DEFAULT nextval('purchase_requisitions_num_seq'),

    date_requested DATE NOT NULL,
    date_required DATE NOT NULL,

    requested_by_id BIGINT NOT NULL,

    cost_center_id BIGINT NOT NULL,
    vendor_id BIGINT NOT NULL,

    comments TEXT,

    status VARCHAR(25) NOT NULL DEFAULT 'PENDING',

    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER SEQUENCE purchase_requisitions_num_seq
    OWNED BY purchase_requisitions.purchase_requisition_number;
