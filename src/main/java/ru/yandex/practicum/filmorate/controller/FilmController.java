package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap();
    private int idCounter = 1;

    //добавление фильма;
    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info(film.toString());
        if (films.containsValue(film)) {
            log.warn("Такой фильм уже существует");
            throw new ValidationException("Такой фильм уже существует");
        }
        film.setId(idCounter++);
        films.put(film.getId(), film);
        return film;
    }

    //обновление фильма;
    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info(film.toString());
        if (!films.containsKey(film.getId())) {
            log.warn("Такого фильма не существует");
            throw new ValidationException("Такого фильма не существует");
        }
        films.put(film.getId(), film);
        return film;
    }

    //получение всех фильмов;
    @GetMapping
    public ArrayList<Film> findAll() {
        log.info("Текущее количествоо фильмов: {}", films.size());
        return new ArrayList<>(films.values());
    }
}
