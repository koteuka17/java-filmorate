package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    Film addFilm(Film film);

    void delFilm(Long id);

    void delAllFilms();

    Film updFilm(Film film);

    List<Film> getFilms();

    void addLike(long filmId, long userId);

    void delLike(long filmId, long userId);

    List<Film> getPopularFilms(long count);

    Film findFilm(Long id);
}
