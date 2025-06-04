--liquibase formatted sql

--changeset thakshila:1

ALTER TABLE survey ADD COLUMN description VARCHAR(255) DEFAULT NULL;