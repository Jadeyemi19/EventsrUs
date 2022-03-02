package com.example.eventsrus;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SavedEventsDao {

    void insert (SavedEvents event);
    @Query("SELECT * FROM saved_events")
    List<SavedEvents> getSavedEvents();
    @Query("DELETE FROM saved_events WHERE eventId='eventId' AND userId='userid'")
    void deleteSavedEvent(int eventId,int userId);






}
