package ru.yandex.practicum.filmorate.storage.friendship;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Friendship;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class FriendshipDbStorage implements FriendshipStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void add(long fromUserId, long toUserId) {
        final String sql = "insert into friends (user_id_1, user_id_2) values (?, ?)";
        jdbcTemplate.update(sql, fromUserId, toUserId);
    }

    @Override
    public void delete(long fromUserId, long toUserId) {
        final String sql = "delete from friends where user_id_1 = ? and user_id_2 = ?";

        jdbcTemplate.update(sql, fromUserId, toUserId);
    }

    //получение id пользователей, которым отправлен запрос на дружбу
    @Override
    public List<Long> getFromUserIDs(long toUserId) {
        final String sql = "select * from friends where user_id_1 = ?";

        return jdbcTemplate.query(sql, friendshipRowMapper(), toUserId)
                .stream()
                .map(Friendship::getFromUserId)
                .collect(Collectors.toList());
    }

    private RowMapper<Friendship> friendshipRowMapper() {
        return ((rs, rowNum) -> new Friendship(
                rs.getLong("user_id_1"),
                rs.getLong("user_id_2")
        ));
    }

    private Friendship get(long fromUserId, long toUserId) {
        final String sql = "select * from friends where user_id_1 = ? and user_id_2 = ?";

        return jdbcTemplate.queryForObject(sql, friendshipRowMapper(), fromUserId, toUserId);
    }
}
