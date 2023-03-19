SET search_path = "online_shop_schema";

CREATE TABLE online_shop_schema.sales
(
    sale_id     BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    sale_amount DOUBLE PRECISION                        NOT NULL,
    "from"      TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    "to"        TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    CONSTRAINT pk_sales PRIMARY KEY (sale_id)
);