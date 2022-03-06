package com.example.eventsrus;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SavedEventsDao {
    @Insert
    void insert (SavedEvents event);
    @Query("SELECT * FROM saved_events")
    List<SavedEvents> getSavedEvents();
    @Query("DELETE FROM saved_events WHERE eventId=:eventId AND userId=:userId")
    void deleteSavedEvent(int eventId,int userId);






}
