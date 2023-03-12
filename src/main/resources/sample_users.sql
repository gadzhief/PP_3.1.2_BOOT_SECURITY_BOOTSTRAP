INSERT INTO users (first_name, last_name, email, password, age)
VALUES ('John', 'Doe', 'johndoe@example.com', '$2a$10$o8xJHunhSlZ1w7JtdnDcQO5/VUZqiG5wPGTMvd3JFFRyr/rkEOOBi', 30),
       ('Jane', 'Doe', 'janedoe@example.com', '$2a$10$o8xJHunhSlZ1w7JtdnDcQO5/VUZqiG5wPGTMvd3JFFRyr/rkEOOBi', 20),
       ('Alice', 'Smith', 'alicesmith@example.com', '$2a$10$o8xJHunhSlZ1w7JtdnDcQO5/VUZqiG5wPGTMvd3JFFRyr/rkEOOBi', 19);

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1), (2, 1), (3, 2);

INSERT INTO test_db.roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO test_db.roles (name) VALUES ('ROLE_USER');

# password = 123