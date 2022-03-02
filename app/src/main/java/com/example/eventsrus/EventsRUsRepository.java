package com.example.eventsrus;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class EventsRUsRepository  {
    private EventDao eventDao;
    private SavedEventsDao savedEventsDao;
    private UsersDao usersDao;
    private LiveData<List<Event>> allEvents;
    private LiveData<List<Event>> fiterEvents;
    private LiveData<List<Event>> allsavedEvents;
    private LiveData<List<SavedEvents>> savedEvents;
    private User credentials;


public EventsRUsRepository(Application application){
    EventsrUsRoomDatabase db= EventsrUsRoomDatabase.getDatabase(application);
    eventDao= db.eventDao();
    allEvents=eventDao.getAllEvents();
}
   LiveData<List<Event>> getAllEvents(){
    return allEvents;
}

public void insert(Event event) {
    new insertAsyncTask(eventDao).execute(event);
}

    private static class insertAsyncTask extends AsyncTask<Event, Void, Void> {

        private EventDao mAsyncTaskDao;

        insertAsyncTask(EventDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Event... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }



}
