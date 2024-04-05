package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryUserStorage implements UserStorage {
    private static final HashMap<Long, User> users = new HashMap<>();
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
        List<User> friendsList = new ArrayList<>();
        for (Long friendId : users.get(id).getFriendsId()) {
            friendsList.add(users.get(friendId));
        }
        return friendsList;
    }

    @Override
    public List<User> getCommonFriends(long id, long friendId) {
        List<User> commonFriendsList = new ArrayList<>();
        for (Long userId : users.get(id).getFriendsId()) {
            if (users.get(friendId).getFriendsId().contains(userId)) {
                commonFriendsList.add(users.get(userId));
            }
        }
        return commonFriendsList;
    }

    @Override
    public User findUser(Long id) {
        return users.getOrDefault(id, null);
    }
}
