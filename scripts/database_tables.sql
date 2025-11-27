CREATE TABLE `users`
(
    `user_id`    integer PRIMARY KEY,
    `email`      varchar(255) UNIQUE NOT NULL,
    `username`   varchar(100) UNIQUE,
    `password`   varchar(255)        NOT NULL,
    `created_at` timestamp DEFAULT (now()),
    `updated_at` timestamp
);

CREATE TABLE `contacts`
(
    `contact_id`  integer PRIMARY KEY,
    `user_id`     integer      NOT NULL,
    `first_name`  varchar(200) NOT NULL,
    `last_name`   varchar(200),
    `phone`       varchar(50),
    `email`       varchar(255),
    `address`     varchar(500),
    `company`     varchar(200),
    `notes`       text,
    `is_favorite` boolean   DEFAULT false,
    `created_at`  timestamp DEFAULT (now()),
    `updated_at`  timestamp
);

CREATE TABLE `contact_groups`
(
    `group_id`   integer PRIMARY KEY,
    `user_id`    integer      NOT NULL,
    `name`       varchar(100) NOT NULL,
    `created_at` timestamp DEFAULT (now())
);

CREATE TABLE `contact_group_members`
(
    `contact_id` integer,
    `group_id`   integer,
    PRIMARY KEY (`contact_id`, `group_id`)
);


CREATE UNIQUE INDEX `contacts_index_0` ON `contacts` (`user_id`, `email`);

CREATE INDEX `contacts_index_1` ON `contacts` (`user_id`);

ALTER TABLE `contacts`
    ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE;

ALTER TABLE `contact_groups`
    ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE;

ALTER TABLE `contact_group_members`
    ADD FOREIGN KEY (`contact_id`) REFERENCES `contacts` (`contact_id`) ON DELETE CASCADE;

ALTER TABLE `contact_group_members`
    ADD FOREIGN KEY (`group_id`) REFERENCES `contact_groups` (`group_id`) ON DELETE CASCADE;
