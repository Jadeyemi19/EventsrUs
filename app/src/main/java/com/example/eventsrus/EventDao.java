package com.example.eventsrus;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EventDao {
    void insert(Event event);

    @Query("DELETE FROM event_table")
    void deleteAll();
     @Query("SELECT * FROM event_table")
    LiveData<List<Event>> getAllEvents();
    @Query("SELECT * FROM event_table WHERE city='location'")
     LiveData<List<Event>>getAllEventsbyCity(String city);
    @Query("SELECT * FROM event_table WHERE date='date'")
    List<Event> getAllEventsbyDate(String date);
    @Query("SELECT * FROM event_table WHERE type='type'")
    List<Event> getAllEventsbyType(String type);
    @Query("SELECT * FROM event_table WHERE date='date' AND city='city'")
    LiveData<List<Event>> getAllEventsbyDateandLocation(String date,String city);
    @Query("SELECT * FROM event_table WHERE id ='id'")
    LiveData<Event> getEventbyID(int id);




}
