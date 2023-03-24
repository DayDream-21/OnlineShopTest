package com.slavamashkov.onlineshoptest;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {
    private final JdbcTemplate jdbcTemplate;

    // По непонятным пока причинам, таблица sales не создается автоматически,
    // поэтому был написан вот такой костыль, с ним по-прежнему вылетает ошибка создания,
    // но после запуска приложения все таблицы на месте
    @Override
    public void run(String... args) {
        jdbcTemplate.execute("""
                INSERT INTO roles (name)
                VALUES
                    ('ROLE_USER'),
                    ('ROLE_ADMIN');

                INSERT INTO users (balance, email, enabled, password, username)
                VALUES
                    (1000000.0, 'admin@gmail.com', true, '$2a$12$5fimGcb99VNBnFlubqHuwOYc8nRAcKfx0E9v0PThRx.WPwpv0ediC', 'admin'),
                    (1000000.0, 'user@gmail.com', true, '$2a$12$/OCiBOPsE/JTvHQRkFaeL.967eM6YR8J8g2i.Ay6CJixrOUzlcEXO', 'user'),
                    (1000000.0, 'user1@gmail.com', true, '$2a$12$6eBGExqdKhA32jiJysQTju3euLL8Qm/LZpQzO0bCsTL/HUDDEExkK', 'user1');

                INSERT INTO user_role (user_id, role_id)
                VALUES
                    (1, 2),
                    (2, 1),
                    (3, 1);

                INSERT INTO products (name, description, price, quantity, active)
                VALUES
                    ('Футбольный мяч', 'Мяч для игры в футбол', 1000.0, 20, true),
                    ('Баскетбольный мяч', 'Мяч для игры в баскетбол', 1500.0, 15, true),
                    ('Теннисная ракетка', 'Ракетка для игры в теннис', 3000.0, 10, true),
                    ('Беговые кроссовки', 'Кроссовки для бега по асфальту', 5000.0, 3, false),
                    ('Шорты для баскетбола', 'Шорты для игры в баскетбол', 2000.0, 12, true),
                    ('Набор для йоги', 'Коврик и блоки для занятий йогой', 4500.0, 7, false),
                    ('Клюшка для хоккея', 'Клюшка для игры в хоккей', 4000.0, 8, true),
                    ('Мяч для волейбола', 'Мяч для игры в волейбол', 1200.0, 18, true),
                    ('Комплект гантелей', 'Гантели разных весов для тренировок', 8000.0, 3, true),
                    ('Ласты для плавания', 'Ласты для плавания в бассейне', 3500.0, 9, true);
                
                INSERT INTO organization (active, description, name, user_id)
                VALUES
                    (true, 'user1_org1', 'user1_org1', 3),
                    (true, 'user1_org2', 'user1_org2', 3),
                    (false, 'user1_org3', 'user1_org3', 3);
                    
                INSERT INTO products (name, description, price, quantity, active, organization_id)
                VALUES
                    ('Гиря 12 кг', 'Гиря 12 кг', 5000.0, 20, true, 2);""");
    }
}
