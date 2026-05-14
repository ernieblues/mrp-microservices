CREATE TABLE purchase_requisition_approvals
(
    id BIGSERIAL PRIMARY KEY,

    purchase_requisition_id BIGINT NOT NULL,

    reviewer_id BIGINT NOT NULL,

    date_reviewed TIMESTAMP WITH TIME ZONE,

    approval_status VARCHAR(25) NOT NULL DEFAULT 'PENDING',

    comments TEXT,

    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);
