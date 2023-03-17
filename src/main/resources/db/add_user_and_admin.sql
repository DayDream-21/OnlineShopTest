SET search_path = "online_shop_schema";

INSERT INTO users (enabled, password, username)
VALUES
    (true, '$2a$12$SgdTkCoZ4UbMCwSiIWJcduy6g5VWw6eyXaC3b6rdWTH/1tK6LJeJa', 'user'),
    (true, '$2a$12$5fimGcb99VNBnFlubqHuwOYc8nRAcKfx0E9v0PThRx.WPwpv0ediC', 'admin');

INSERT INTO roles (name)
VALUES
    ('USER'),
    ('ADMIN');

INSERT INTO users_roles (user_id, role_id)
VALUES
    (1, 1),
    (2, 1),
    (2, 2);