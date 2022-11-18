package com.example.eventsrus;

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
                                                .allowMainThreadQueries()
                                                .fallbackToDestructiveMigration()
                                                .addCallback(sRoomDatabaseCallback)
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
                    events=new ArrayList<>();
                      events.add(new Event("Football Funday","Aston Street,Birmingham","B4 7ET","footballpitch","30/03/2022",".19:00-21:00","Birmingham","Come on down to our football fundays. A great way to get active whilst making friends along the way. Don't miss out","Aston University","Sports"));
                      events.add(new Event ("ACS meetup","Aston Street,Birmingham","B4 7ET","acsimage","27/03/2022",".18:00-22:00","Birmingham","It's finally that time again, it's time for the African Carrabian Society meetup. Tell a friend to tell a friend to tell a friend beause you don't want to miss out on this one. From games to quizes, we have it all.","Aston University","Cultural"));
                      events.add(new Event ("United Stadium Tour","Sir Matt Busby Way, Old Trafford","M16 8RA","oldtrafford","27/03/2022",".12:00-19:00","Manchester","There's no team in the English Premier Lea that have a richer history than us, we've got the trophies to prove it. Not convinced? Come on down to the great city of manchester to experience what a gargantuan football club looks like.","Old Trafford","Sports"));
                      events.add(new Event ("Chelsea Stadium Tour","Fulham Road","SW6 1HS","stamfordbridge","23/03/2022",".12:00-19:00","London","Chelsea FC is not just a football club it's the Pride of London. No other team in London epitomises a winning mentally more than Chelsea which is reflected by the amount of trophies won in the last 20 years. An opportunity that you'd regret missing,come along and bring the family","Stamford Bridge","Sports"));
                      events.add(new Event ("R&B/Hip Hop Nights","240 Broad Street","B1 2HG","playersbar","05/04/2022",".00:00-05:00","Birmingham","Our R&B/Hip Hop  nights are back and better. We've got the latest tunes and the best speakers in the game. Gey your dancing shows on and join the vibe ","Players bar","Music"));
                      events.add(new Event ("Aston Chess Society ","Aston Street,Birmingham","B4 7ET","chesssociety","10/04/2022",".18:00-20:00","Birmingham","Your day way to all things chess, It doesn't matter what level you are, we accommodate all skill levels. We also take part in tournaments across the country. No better way to tap into critical thinking than the classic game of chess ","Aston University","Sports"));
                      events.add(new Event ("Drake at the 02 ","Peninsula Square","B4 7ET","theo2","10/04/2022",".19:00-20:00","London","Coming at you live from the 02, we have the biggest muiscian in the world coming to you live. For the first time in 3 years,Drake returns to London and you need to be here.Head over to ticketmaster to sign up for pre release tickets","The O2","Music"));
                      events.add(new Event ("Japanese Painting Workshop ","Chatsworth House","M1 1BY","cafe","15/04/2022",".19:00-20:00","Manchester"," Sometimes your spare time is best spent relaxing.We always seem to busy zipping around for work and running errands so join us for an afternoon of arts and crafts ","Chapter One Books","Hobbies"));
                      events.add(new Event ("Bottemless Brunch ","19 Lever Street","M1 1BY","manchesterbrunch","10/04/2022",".12:00-15:00","Manchester","What's better than breakfast and lunch, brunch of course. Stop by before you venture into the city centre to eat and refuel to your hearts delight","19 Cafe Bar","Food and Drink"));

                      for (int i = 0; i < events.size() ; i++) {
                              Event event= events.get(i);
                                dao.insert(event); }
                        return null;
                }
        }

}




