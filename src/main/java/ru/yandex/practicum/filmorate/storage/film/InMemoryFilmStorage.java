package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Long, Film> films = new HashMap<>();
    private long idCounter = 1;

    @Override
    public Film addFilm(Film film) {
        film.setId(idCounter++);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public void delFilm(Long id) {
        films.remove(id);
    }

    @Override
    public void delAllFilms() {
        films.clear();
    }

    @Override
    public Film updFilm(Film film) {
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public void addLike(long filmId, long userId) {
        films.get(filmId).addLike(userId);
    }

    @Override
    public void delLike(long filmId, long userId) {
        films.get(filmId).delLike(userId);
    }

    @Override
    public List<Film> getPopularFilms(long count) {
        return films.values().stream()
                .filter(film -> !film.getLikes().isEmpty())
                .sorted(Comparator.comparingLong(Film::getNumOfLikes).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    @Override
    public Film findFilm(Long id) {
        return films.getOrDefault(id, null);
    }
}
