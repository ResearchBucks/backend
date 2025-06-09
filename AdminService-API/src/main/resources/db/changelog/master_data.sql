--liquibase formatted sql

--changeset thakshila:1
INSERT INTO `admin` (id, created_admin, created_date, email, first_name, is_deleted, last_login, last_name, password, role, reset_token) VALUES
(1, 1, '2025-05-27 23:10:45.163000', 'admin@researchbucks.com', 'Admin', false, null, 'Admin', '$2a$10$PU6SkfeRQewsJbqqH1VIQOquZKrnRSUgvxgl.3afXXFj5YWkmxoXS', 'ROLE_SUPER_ADMIN', null);