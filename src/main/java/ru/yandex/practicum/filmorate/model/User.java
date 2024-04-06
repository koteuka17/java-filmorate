package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private Long id;
    private Set<Long> friendsId = new HashSet<>();

    @NotNull(message = "Электронная почта не может быть пустой")
    @Email(message = "Некорректная электронная почта")
    private String email;

    @NotBlank(message = "Логин не может быть пустым")
    @Pattern(regexp = "\\S+", message = "В логине не могут находиться пробелы")
    private String login;
    private String name;

    @NotNull(message = "Не указанна дата рождения")
    @Past(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;

    public void addFriend(Long friendId) {
        friendsId.add(friendId);
    }

    public void delFriend(Long friendId) {
        friendsId.remove(friendId);
    }
}
