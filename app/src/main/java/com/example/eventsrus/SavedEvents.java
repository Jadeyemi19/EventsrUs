package com.example.eventsrus;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "saved_events")
public class SavedEvents {
    @NonNull
    @ColumnInfo(name="eventId")
     private int eventId;
    @ColumnInfo(name="userId")
     private int userId;

     public SavedEvents(@NonNull int eventId, int userId){
         this.eventId=eventId;
         this.userId=userId;
     }



}
