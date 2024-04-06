package ru.yandex.practicum.filmorate.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InMemoryUserService implements UserService {
    private final UserStorage userStorage;

    @Override
    public User addUser(User user) {
        log.info("Создание пользователя {}", user);
        if (userStorage.getUsers().contains(user)) {
            log.warn("Такой пользователь уже существует");
            throw new ValidationException("Такой пользователь уже существует");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        return userStorage.addUser(user);
    }

    @Override
    public void delUser(long id) {
        log.info("Удаление пользователя с id: {}", id);
        if (userStorage.findUser(id) == null) {
            log.warn("Такого пользователя не существует");
            throw new NotFoundException("Такого пользователя не существует");
        }
        log.info("Пользователь с id: {} удален", id);
        userStorage.delUser(id);
    }

    @Override
    public void delAllUsers() {
        log.info("Список пользователей очищен");
        userStorage.delAllUsers();
    }

    @Override
    public User updUser(User user) {
        log.info("Обновление пользователя {}", user);
        if (userStorage.findUser(user.getId()) == null) {
            log.warn("Такого пользователя не существует");
            throw new NotFoundException("Такого пользователя не существует");
        }
        return userStorage.updUser(user);
    }

    @Override
    public List<User> getUsers() {
        log.info("Текущее количествоо пользователей: {}", userStorage.getUsers().size());
        return userStorage.getUsers();
    }

    @Override
    public void addFriend(long id, long friendId) {
        if (userStorage.findUser(id) == null || userStorage.findUser(friendId) == null) {
            log.warn("Такого пользователя не существует");
            throw new NotFoundException("Такого пользователя не существует");
        }
        if (id == friendId) {
            log.warn("Пользователь не может добавить сам себя в друзья");
            throw new ValidationException("Невозможно добавить себя в друзья");
        }
        log.info("Пользователь с id: {} добавил в друзья пользователя с id: {}", id, friendId);
        userStorage.addFriend(id, friendId);
    }

    @Override
    public void delFriend(long id, long friendId) {
        if (userStorage.findUser(id) == null || userStorage.findUser(friendId) == null) {
            log.warn("Такого пользователя не существует");
            throw new NotFoundException("Такого пользователя не существует");
        }
        if (userStorage.findUser(id).getFriendsId().contains(friendId)) {
            log.info("Пользователь с id: {} удалил из друзей пользователя с id: {}", id, friendId);
            userStorage.delFriend(id, friendId);
        }
    }

    @Override
    public List<User> getFriends(long id) {
        if (userStorage.findUser(id) == null) {
            log.warn("Такого пользователя не существует");
            throw new NotFoundException("Такого пользователя не существует");
        }
        log.info("Получен список друзей пользователя с id: {}", id);
        return userStorage.getFriends(id);
    }

    @Override
    public List<User> getCommonFriends(long id, long friendId) {
        if (userStorage.findUser(id) == null || userStorage.findUser(friendId) == null) {
            log.warn("Такого пользователя не существует");
            throw new NotFoundException("Такого пользователя не существует");
        }
        log.info("Получен список общих друзей пользователей с id: {} и {}", id, friendId);
        return userStorage.getCommonFriends(id, friendId);
    }

    @Override
    public User getUser(long id) {
        return userStorage.findUser(id);
    }
}
