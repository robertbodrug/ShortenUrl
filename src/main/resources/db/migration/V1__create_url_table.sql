CREATE TABLE  IF NOT EXISTS users (
        id BIGINT AUTO_INCREMENT,
        username VARCHAR(50) NOT NULL,
        password TEXT NOT NULL,
        role VARCHAR NOT NULL,
        PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS urls (
        id BIGINT AUTO_INCREMENT ,
        short_url VARCHAR(2048) NOT NULL,
        long_url VARCHAR(2048) NOT NULL,
        score INT NOT NULL,
        is_active BOOLEAN NOT NULL,
        user_id BIGINT NOT NULL,
        PRIMARY KEY(id)
);
