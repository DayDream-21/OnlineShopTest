SET search_path = "online_shop_schema";

INSERT INTO roles (name)
VALUES
    ('ROLE_USER'),
    ('ROLE_ADMIN');

INSERT INTO users (balance, email, enabled, password, username)
VALUES
    (1000000.0, 'admin@gmail.com', true, '$2a$12$5fimGcb99VNBnFlubqHuwOYc8nRAcKfx0E9v0PThRx.WPwpv0ediC', 'admin');

INSERT INTO user_role (user_id, role_id)
VALUES
    (1, 2);

INSERT INTO products (name, description, price, quantity)
VALUES
    ('Футбольный мяч', 'Мяч для игры в футбол', 1000.0, 20),
    ('Баскетбольный мяч', 'Мяч для игры в баскетбол', 1500.0, 15),
    ('Теннисная ракетка', 'Ракетка для игры в теннис', 3000.0, 10),
    ('Беговые кроссовки', 'Кроссовки для бега по асфальту', 5000.0, 3),
    ('Шорты для баскетбола', 'Шорты для игры в баскетбол', 2000.0, 12),
    ('Набор для йоги', 'Коврик и блоки для занятий йогой', 4500.0, 7),
    ('Клюшка для хоккея', 'Клюшка для игры в хоккей', 4000.0, 8),
    ('Мяч для волейбола', 'Мяч для игры в волейбол', 1200.0, 18),
    ('Комплект гантелей', 'Гантели разных весов для тренировок', 8000.0, 3),
    ('Ласты для плавания', 'Ласты для плавания в бассейне', 3500.0, 9);