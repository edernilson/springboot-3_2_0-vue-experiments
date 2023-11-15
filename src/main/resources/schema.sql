CREATE TABLE IF NOT EXISTS post (
    id INT NOT NULL,
    user_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    body text NOT NULL,
    version INT,
    primary key (id)
);