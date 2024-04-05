package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import java.util.List;

public interface UserStorage {
    User addUser(User user);

    void delUser(long id);

    void delAllUsers();

    User updUser(User user);

    List<User> getUsers();

    void addFriend(long id, long friendId);

    void delFriend(long id, long friendId);

    List<User> getFriends(long id);

    List<User> getCommonFriends(long id, long friendId);

    User findUser(Long id);
}
