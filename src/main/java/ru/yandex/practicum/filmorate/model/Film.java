package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.validation.annotation.AfterCinemaBirthday;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Film.
 */
@Data
public class Film {
    private int id;
    @NotBlank(message = "Название не может быть пустым")
    private String name;

    @NotBlank(message = "У фильма должно быть описание")
    @Size(max = 200, message = "Описание не должно быть больше 200 символов")
    private String description;

    @NotNull(message = "У фильма должна быть указана дата релиза")
    @AfterCinemaBirthday
    private LocalDate releaseDate;

    @NotNull(message = "У фильма должна быть указана продолжительность")
    @Positive(message = "Продолжительность фильма не может быть отрицательной")
    private Integer duration;
}
