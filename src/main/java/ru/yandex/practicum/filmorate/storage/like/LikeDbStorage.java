package ru.yandex.practicum.filmorate.storage.like;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LikeDbStorage implements LikeStorage {
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addLikeToFilm(long filmId, long userId) {
        final String sql = "insert into film_likes (user_id, film_id) values (?, ?)";

        jdbcTemplate.update(sql, userId, filmId);
    }

    @Override
    public void deleteLikeFromFilm(long filmId, long userId) {
        final String sql = "delete from film_likes where user_id = ? and film_id = ?";

        jdbcTemplate.update(sql, userId, filmId);
    }
}
