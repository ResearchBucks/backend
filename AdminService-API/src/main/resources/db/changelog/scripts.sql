--liquibase formatted sql

--changeset thakshila:1
create table admin
(
    id            bigint auto_increment
        primary key,
    created_admin bigint                                  not null,
    created_date  datetime(6)                             not null,
    email         varchar(255)                            not null,
    first_name    varchar(255)                            not null,
    last_name     varchar(255)                            null,
    is_deleted    bit                                     null,
    last_login    datetime(6)                             null,
    password      varchar(255)                            not null,
    reset_token   varchar(255)                            null,
    role          enum ('ROLE_ADMIN', 'ROLE_SUPER_ADMIN') not null
);

--changeset thakshila:2
create table researcher
(
    id            bigint auto_increment
        primary key,
    address       varchar(255)             not null,
    created_date  datetime(6)              null,
    email         varchar(255)             not null,
    first_name    varchar(255)             not null,
    last_name     varchar(255)             null,
    is_deleted    bit                      null,
    is_locked     bit                      null,
    is_verified   bit                      null,
    mobile_number varchar(255)             not null,
    nic           varchar(255)             null,
    occupation    varchar(255)             null,
    password      varchar(255)             null,
    reset_token   varchar(255)             null,
    role          enum ('ROLE_RESEARCHER') not null,
    verify_token  varchar(255)             null
);

--changeset thakshila:3
create table respondent
(
    id             bigint auto_increment
        primary key,
    address        varchar(255)             not null,
    created_date   datetime(6)              null,
    email          varchar(255)             not null,
    first_name     varchar(255)             not null,
    last_name      varchar(255)             null,
    is_deleted     bit                      null,
    is_locked      bit                      null,
    is_verified    bit                      null,
    mobile_number  varchar(255)             not null,
    nic            varchar(255)             null,
    password       varchar(255)             null,
    reset_token    varchar(255)             null,
    role           enum ('ROLE_RESPONDENT') not null,
    total_earnings int                      null,
    verify_token   varchar(255)             null
);

--changeset thakshila:4
create table survey
(
    id               bigint auto_increment
        primary key,
    approved_date    datetime(6)                                         null,
    created_date     datetime(6)                                         null,
    expiring_date    datetime(6)                                         null,
    is_deleted       bit                                                 not null,
    is_verified      bit                                                 not null,
    last_edited      datetime(6)                                         null,
    paid_date        datetime(6)                                         null,
    payment_per_user int                                                 not null,
    payment_status   enum ('COMPLETED', 'FAILED', 'PENDING', 'REFUNDED') not null,
    remaining_amount int                                                 not null,
    survey_price     int                                                 null,
    title            varchar(255)                                        not null,
    created_by       bigint                                              null,
    constraint FK88xx8eipd46wf9up96v665klu
        foreign key (created_by) references researcher (id)
);

--changeset thakshila:5
create table user_survey
(
    id                  bigint      not null
        primary key,
    expired_at          datetime(6) not null,
    is_deleted          bit         null,
    paid_to_respondents bit         not null
);

--changeset thakshila:6
create table survey_respondents
(
    survey_id      bigint not null,
    respondents_id bigint not null,
    constraint FKbksismo6shigtma7hn30on712
        foreign key (respondents_id) references respondent (id),
    constraint FKk3gvq75p9e7l4sjjv8e8te39r
        foreign key (survey_id) references user_survey (id)
);


