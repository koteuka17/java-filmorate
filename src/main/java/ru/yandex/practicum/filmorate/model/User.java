package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {
    private int id;

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
}
