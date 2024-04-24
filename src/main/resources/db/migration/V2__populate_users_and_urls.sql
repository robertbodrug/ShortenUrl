INSERT INTO users (username, password, role) VALUES
        ('user1', '$2a$10$elToPQ8V884EnSrMGUKppuzO19LUwFASYSbCs0f3P.M8Bm0yi0HsO', 'USER'),
        ('admin1', '$2a$10$qXdVJNGaMQPjcNIryf4lh.xtyPeyvVGOdW9v3jTwzHbLMZb7uMF4y', 'ADMIN');

INSERT INTO urls ( short_url, long_url, score, is_active, user_id) VALUES
        ( 'abc', 'https://github.com', 100, TRUE, 1),
        ( 'def', 'https://google.com', 50, TRUE, 2),
        ( 'ghi', 'http://yet-another-long-url.com/this-is-yet-another-long-url', 75, TRUE, 2),
        ( 'jkl', 'http://short-url.com/short-url', 25, FALSE, 1);
