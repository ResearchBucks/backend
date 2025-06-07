--liquibase formatted sql

--changeset thakshila:1

ALTER TABLE survey ADD COLUMN description VARCHAR(255) DEFAULT NULL;

--changeset thakshila:2

ALTER TABLE user_survey ADD COLUMN is_verified BIT DEFAULT NULL;
ALTER TABLE user_survey ADD COLUMN title VARCHAR(255) DEFAULT NULL;
ALTER TABLE user_survey ADD COLUMN description VARCHAR(255) DEFAULT NULL;
ALTER TABLE user_survey ADD COLUMN payment_per_user INT DEFAULT NULL;

--changeset thakshila:3
ALTER TABLE survey ADD COLUMN is_rejected BIT DEFAULT NULL;
ALTER TABLE user_survey ADD COLUMN is_rejected BIT DEFAULT NULL;