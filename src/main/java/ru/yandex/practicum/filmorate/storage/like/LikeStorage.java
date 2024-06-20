package ru.yandex.practicum.filmorate.storage.like;

public interface LikeStorage {
    void addLikeToFilm(long filmId, long userId);

    void deleteLikeFromFilm(long filmId, long userId);
}
