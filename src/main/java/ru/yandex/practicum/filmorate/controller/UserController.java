package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final HashMap<Integer, User> users = new HashMap();
    private int idCounter = 1;

    //создание пользователя;
    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("Создание пользователя {}", user);
        if (users.containsValue(user)) {
            log.warn("Такой пользователь уже существует");
            throw new ValidationException("Такой пользователь уже существует");
        }
        user.setId(idCounter++);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        return user;
    }

    //обновление пользователя;
    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("Обновление пользователя {}", user);
        if (!users.containsKey(user.getId())) {
            log.warn("Такого пользователя не существует");
            throw new ValidationException("Такого пользователя не существует");
        }
        users.put(user.getId(), user);
        return user;
    }

    //получение списка всех пользователей.
    @GetMapping
    public ArrayList<User> findAll() {
        log.info("Текущее количествоо пользователей: {}", users.size());
        return new ArrayList<>(users.values());
    }
}
