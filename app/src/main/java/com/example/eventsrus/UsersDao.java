package com.example.eventsrus;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UsersDao {
    @Insert
    void insert(User user);
    @Query("SELECT * FROM users WHERE email=:email AND password=:password")
    User getUser(String email,String password);
    @Query("DELETE  FROM users WHERE email=:email")
    void deleteUser(String email);
    @Query("SELECT * FROM event_table e join saved_events s on s.userId=:id and s.eventId=e.id")
    LiveData<List<Event>> getAllEventsForUser(int id);



}
