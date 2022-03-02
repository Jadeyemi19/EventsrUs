package com.example.eventsrus;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface UsersDao {

    void insert(User user);
    @Query("SELECT * FROM users WHERE username='username'")
    User getUser(String username);
    @Query("DELETE  FROM users WHERE username='username'")
    void deleteUser(String username);



}
