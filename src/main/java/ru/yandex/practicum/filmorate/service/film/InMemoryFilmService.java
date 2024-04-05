package ru.yandex.practicum.filmorate.service.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InMemoryFilmService implements FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

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
        log.info("Текущее количествоо фильмов: {}", filmStorage.getFilms().size());
        return filmStorage.getFilms();
    }

    @Override
    public void addLike(long filmId, long userId) {
        if (filmStorage.findFilm(filmId) == null) {
            log.warn("Такого фильма не существует");
            throw new NotFoundException("Такого фильма не существует");
        }
        if (userStorage.findUser(userId) == null) {
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
        if (userStorage.findUser(userId) == null) {
            log.warn("Пользователь не найден");
            throw new NotFoundException("Пользователь не найден");
        }
        log.info("У фильма с id: {} убрали лайк", filmId);
        filmStorage.delLike(filmId, userId);
    }

    @Override
    public List<Film> getPopularFilms(long count) {
        if (count <= 0 || count > 10) {
            count = 10;
        }
        log.info("Список {} популярных фильма(ов)", count);
        return filmStorage.getPopularFilms(count);
    }

    @Override
    public Film getFilm(long id) {
        return filmStorage.findFilm(id);
    }
}
