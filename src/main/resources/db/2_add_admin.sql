SET search_path = "online_shop_schema";

INSERT INTO users (balance, email, enabled, password, username)
VALUES
    (1000000.0, 'admin@gmail.com', true, '$2a$12$5fimGcb99VNBnFlubqHuwOYc8nRAcKfx0E9v0PThRx.WPwpv0ediC', 'admin');

INSERT INTO users_roles (user_id, role_id)
VALUES
    (1, 2);