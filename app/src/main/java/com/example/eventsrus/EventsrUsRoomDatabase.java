package com.example.eventsrus;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;

@Database(entities = {Event.class,SavedEvents.class,User.class},version=1,exportSchema = false)

public abstract class EventsrUsRoomDatabase extends RoomDatabase {

        public abstract EventDao eventDao();
        public abstract UsersDao userDao();
        public abstract SavedEventsDao savedEventsDao();
        private static EventsrUsRoomDatabase INSTANCE;


        static final Migration Migration=new Migration(1,2) {
                @Override
                public void migrate(@NonNull SupportSQLiteDatabase database) {
                }
        };

static EventsrUsRoomDatabase getDatabase(final Context context) {
                if (INSTANCE == null) {
                        synchronized (EventsrUsRoomDatabase.class) {
                                if (INSTANCE == null) {
                                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                               EventsrUsRoomDatabase.class, "EventsRus_Database")
                                                // Wipes and rebuilds instead of migrating
                                                // if no Migration object.
                                                // Migration is not part of this practical.
                                                .addMigrations(Migration)
                                                .build();
                                }
                        }
                }
                return INSTANCE;
        }
        private static RoomDatabase.Callback sRoomDatabaseCallback =
                new RoomDatabase.Callback(){

                        @Override
                        public void onOpen (@NonNull SupportSQLiteDatabase db){
                                super.onOpen(db);
                                new PopulateDbAsync(INSTANCE).execute();
                        }
                };
        private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

                private final EventDao dao;
                ArrayList<Event> events;


                PopulateDbAsync(EventsrUsRoomDatabase db) {
                       dao=db.eventDao();
                }

                @Override
                protected Void doInBackground(final Void... params) {
                      events.add(new Event("Football Funday","Aston St,Birmingham","B4 7ET","footballpitch","30/03/2022","19:00-21:00","Birmingham","Come on down to our football fundays. A great way to get active whilst making friends along the way. Don't miss out","Aston University","Sports"));
                      events.add(new Event ("ACS meetup","Aston St,Birmingham","B4 7ET","acsimage","27/03/2022","18:00-22:00","Birmingham","It's finally that time again, it's time for the African Carrabian Society meetup. Tell a friend to tell a friend to tell a friend beause you don't want to miss out on this one. From games to quizes, we have it all.","Aston University","Cultural"));
                    events.add(new Event ("United Stadium Tour","Sir Matt Busby Way, Old Trafford","M16 8RA","oldtrafford","27/03/2022","12:00-19:00","Manchester","There's no team in the English Premier Lea that have a richer history than us, we've got the trophes to prove it. Not convinced? Come on down to the great city of manchester to experience what a gargantuan football club looks like.","Old Trafford","Sports"));
                    events.add(new Event ("United Stadium Tour","Sir Matt Busby Way, Old Trafford","M16 8RA","oldtrafford","27/03/2022","12:00-19:00","Manchester","There's no team in the English Premier Lea that have a richer history than us, we've got the trophes to prove it. Not convinced? Come on down to the great city of manchester to experience what a gargantuan football club looks like.","Old Trafford","Sports"));




                        for (int i = 0; i <= words.length - 1; i++) {
                                Word word = new Word(words[i]);
                                mDao.insert(word);
                        }
                        return null;
                }
        }

}




