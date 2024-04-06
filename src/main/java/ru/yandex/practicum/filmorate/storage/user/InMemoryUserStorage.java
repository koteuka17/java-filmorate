package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Long, User> users = new HashMap<>();
    private long idCounter = 1;

    @Override
    public User addUser(User user) {
        user.setId(idCounter++);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void delUser(long id) {
        users.remove(id);
    }

    @Override
    public void delAllUsers() {
        users.clear();
    }

    @Override
    public User updUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void addFriend(long id, long friendId) {
        users.get(id).addFriend(friendId);
        users.get(friendId).addFriend(id);
    }

    @Override
    public void delFriend(long id, long friendId) {
        users.get(id).delFriend(friendId);
        users.get(friendId).delFriend(id);
    }

    @Override
    public List<User> getFriends(long id) {
        return users.get(id).getFriendsId().stream()
                .map(users::get)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getCommonFriends(long id, long friendId) {
        final Set<Long> friends = users.get(id).getFriendsId();
        final Set<Long> otherFriends = users.get(friendId).getFriendsId();
        return friends.stream()
                .filter(otherFriends::contains)
                .map(users::get)
                .collect(Collectors.toList());
    }

    @Override
    public User findUser(Long id) {
        return users.getOrDefault(id, null);
    }
}
