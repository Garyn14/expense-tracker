-- create ENUM for Expense type
CREATE TYPE expense_type AS ENUM ('food', 'travel', 'entertainment', 'other');

-- create table for Expenses
CREATE TABLE expenses(
    id           BIGSERIAL PRIMARY KEY,
    description  VARCHAR(255) NOT NULL,
    type         expense_type NOT NULL,
    amount       NUMERIC(10, 2) NOT NULL
);