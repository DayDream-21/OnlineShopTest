SET search_path = "online_shop_schema";

CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       email VARCHAR(45) NOT NULL UNIQUE,
                       full_name VARCHAR(45) NOT NULL,
                       password VARCHAR(64) NOT NULL,
                       enabled SMALLINT
);

CREATE TABLE roles (
                       role_id SERIAL PRIMARY KEY,
                       name VARCHAR(45) NOT NULL
);

CREATE TABLE users_roles (
                             user_id INT NOT NULL REFERENCES users(user_id),
                             role_id INT NOT NULL REFERENCES roles(role_id),
                             PRIMARY KEY (user_id, role_id)
);