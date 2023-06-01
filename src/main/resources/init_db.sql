CREATE TABLE manufacturers
(
    id         BIGINT AUTO_INCREMENT
        PRIMARY KEY,
    name       VARCHAR(45) NOT NULL,
    country    VARCHAR(45) NOT NULL,
    is_deleted TINYINT     NOT NULL DEFAULT 0
)
    COMMENT 'List of car makers.';

/*
CREATE TABLE drivers
(
    id         BIGINT AUTO_INCREMENT
        PRIMARY KEY,
    name       VARCHAR(45) NOT NULL,
    licenseNumber    VARCHAR(45) NOT NULL,
    is_deleted TINYINT     NOT NULL DEFAULT 0
)
    COMMENT 'List of drivers.';
*/