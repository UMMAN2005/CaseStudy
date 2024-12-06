drop database if exists CaseStudy;
create database CaseStudy;
use CaseStudy;

create table users
(
    id              int primary key auto_increment,
    username        varchar(255) not null,
    password_hash   varchar(255) not null,
    profile_picture longblob
);

create table messages
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(255) NOT NULL,
    content    TEXT         NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
create table failed_login_attempts
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(255),
    ip_address VARCHAR(255),
    timestamp  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    reason     VARCHAR(255)
);


select * from users;
