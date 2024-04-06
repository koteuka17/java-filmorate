package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InMemoryFilmService implements FilmService {
    private final FilmStorage filmStorage;
    private final UserService userService;

    @Override
    public Film addFilm(Film film) {
        log.info("Добавление фильма {}", film);
        if (filmStorage.getFilms().contains(film)) {
            log.warn("Такой фильм уже добавлен");
            throw new ValidationException("Такой фильм уже добавлен");
        }
        return filmStorage.addFilm(film);
    }

    @Override
    public void delFilm(Long id) {
        log.info("Удаление фильма с id: {}", id);
        if (filmStorage.findFilm(id) == null) {
            log.warn("Такого фильма не существует");
            throw new NotFoundException("Такого фильма не существует");
        }
        log.info("Фильм с id: {} удален", id);
        filmStorage.delFilm(id);
    }

    @Override
    public void delAllFilms() {
        log.info("Список фильмов очищен");
        filmStorage.delAllFilms();
    }

    @Override
    public Film updFilm(Film film) {
        log.info("Обновление фильма {}", film);
        if (filmStorage.findFilm(film.getId()) == null) {
            log.warn("Такого фильма не существует");
            throw new NotFoundException("Такого фильма не существует");
        }
        return filmStorage.updFilm(film);
    }

    @Override
    public List<Film> getFilms() {
        List<Film> films = filmStorage.getFilms();
        log.info("Текущее количествоо фильмов: {}", films.size());
        return films;
    }

    @Override
    public void addLike(long filmId, long userId) {
        if (filmStorage.findFilm(filmId) == null) {
            log.warn("Такого фильма не существует");
            throw new NotFoundException("Такого фильма не существует");
        }
        if (userService.getUser(userId) == null) {
            log.warn("Пользователь не найден");
            throw new NotFoundException("Пользователь не найден");
        }
        log.info("Фильму с id: {} поставили лайк", filmId);
        filmStorage.addLike(filmId, userId);
    }

    @Override
    public void delLike(long filmId, long userId) {
        if (filmStorage.findFilm(filmId) == null) {
            log.warn("Такого фильма не существует");
            throw new NotFoundException("Такого фильма не существует");
        }
        if (userService.getUser(userId) == null) {
            log.warn("Пользователь не найден");
            throw new NotFoundException("Пользователь не найден");
        }
        log.info("У фильма с id: {} убрали лайк", filmId);
        filmStorage.delLike(filmId, userId);
    }

    @Override
    public List<Film> getPopularFilms(long count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Запрошено отрицательное число");
        }
        log.info("Список {} популярных фильма(ов)", count);
        return filmStorage.getPopularFilms(count);
    }

    @Override
    public Film getFilm(long id) {
        return filmStorage.findFilm(id);
    }
}
