USE demo;

CREATE TABLE IF NOT EXISTS books (
    id               CHAR(36)     NOT NULL,
    title            VARCHAR(255) NOT NULL,
    title_updated_at TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (id)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_unicode_ci;
