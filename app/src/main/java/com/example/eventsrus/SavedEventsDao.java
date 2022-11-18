package com.example.eventsrus;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SavedEventsDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert (SavedEvents event);

    @Query("SELECT * FROM saved_events WHERE userId=:userID")
    LiveData<List<SavedEvents>>getSavedEvents(int userID);

    @Query("SELECT * FROM saved_events")
    LiveData<List<SavedEvents>> getSavedEventsAll();

    @Query("DELETE  FROM saved_events WHERE eventId=:eventId AND userId=:userId")
    void deleteSavedEvent(Integer eventId,Integer userId);









}
