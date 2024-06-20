package ru.yandex.practicum.filmorate.storage.friendship;

import java.util.List;

public interface FriendshipStorage {
    void add(long fromUserId, long toUserId);

    void delete(long fromUserId, long toUserId);

    List<Long> getFromUserIDs(long toUserId);
}

