package com.example.eventsrus;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;


public class EventsRUsRepository {

    private final EventDao eventDao;
    private SavedEventsDao savedEventsDao;
    private final UsersDao usersDao;
    private LiveData allEvents;
    private LiveData usersLikedEvents;
    private LiveData<List<SavedEvents>> savedEvents;
    private User credentials;
    private Event Userevent;


    public EventsRUsRepository(Application application) {
        EventsrUsRoomDatabase db = EventsrUsRoomDatabase.getDatabase(application);
        eventDao = db.eventDao();
        usersDao = db.userDao();
        savedEventsDao = db.savedEventsDao();
        allEvents = eventDao.getAllEvents();
        savedEvents=savedEventsDao.getSavedEventsAll();




    }

    LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }
    LiveData<List<SavedEvents>> getAllSavedEvents(){return savedEvents;}

    public void insert(Event event) {
        new insertAsyncTask(eventDao).execute(event);
    }

    public void registerUser(User user) {
        new registerUserAsyncTask(usersDao).execute(user);
    }

    public void loginUser(String email, String password) {
        new logInUserAsyncTask(usersDao).execute(email, password);
    }

    public void getUserSavedEvents(int userID) {
        new getSavedEventsAsyncTask(savedEventsDao).execute(userID);

    }


    public void deleteSavedEvent(int userID, int eventiD) {
        new DeleteSavedEventsAsyncTask(savedEventsDao).execute(userID, eventiD);
    }

    public void likeEvent(SavedEvents event) {
        new LikeEventAsyncTask(savedEventsDao).execute(event);
    }

    public void getUserLikeEvents(int userid) {
       new getEventsAsyncTask(usersDao).execute(userid);
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

   public class registerUserAsyncTask extends AsyncTask<User, Void, Void> {

        private UsersDao Udoa;

        registerUserAsyncTask(UsersDao dao) {
            Udoa = dao;
        }

        @Override
        protected Void doInBackground(final User... users) {
            Udoa.insert(users[0]);
            credentials=users[0];
            return null;

        }


    }


    public class getEventsAsyncTask extends AsyncTask<Integer,Void,LiveData<List<Event>>>{
         UsersDao uDao;
         public getEventsAsyncTask(UsersDao uDao){
             this.uDao=uDao;
         }

        @Override
        protected LiveData<List<Event>> doInBackground(Integer... integers) {
             Log.d("Problem", "This is the event id"+integers[0]);
            return uDao.getAllEventsForUser(integers[0]);
        }

        @Override
        protected void onPostExecute(LiveData<List<Event>> event){
            usersLikedEvents=event;
        }
    }



    public class logInUserAsyncTask extends AsyncTask<String, Void, Void> {

        private UsersDao udoa;


        logInUserAsyncTask(UsersDao udoa) {
            this.udoa = udoa;

        }

        @Override
        protected Void doInBackground(String... strings) {
            User user = udoa.getUser(strings[0], strings[1]);
            credentials = user;
             return null;
        }


    }


    public class getSavedEventsAsyncTask extends AsyncTask<Integer, Void, LiveData<List<SavedEvents>>>{
        SavedEventsDao sDao;

        public getSavedEventsAsyncTask(SavedEventsDao savedevents) {
            this.sDao = savedEventsDao;


        }
        @Override
        protected LiveData<List<SavedEvents>> doInBackground(Integer... integers) {
            LiveData<List<SavedEvents>> usersavedEvents = sDao.getSavedEvents(integers[0]);
           // LiveData<List<SavedEvents>> usersavedEvents = (LiveData<List<SavedEvents>>) sDao.getSavedEventsAll();
            Log.d("MYTAG", "We got some saved events " + usersavedEvents.getValue() + " for " +
                    integers[0]);

            return usersavedEvents;
        }
        @Override
        public void onPostExecute(LiveData<List<SavedEvents>> events){
            Log.d("MYTAG", "We got some events " + events.getValue());
            savedEvents=events;

        }


    }

    class DeleteSavedEventsAsyncTask extends AsyncTask<Integer, Void, Void> {
        SavedEventsDao eDao;
        int eventID;
        int useriD;

        DeleteSavedEventsAsyncTask(SavedEventsDao eDao) {
            this.eDao = eDao;



        }

        @Override
        protected Void doInBackground(Integer... integers) {

            eDao.deleteSavedEvent(integers[0], integers[1]);
            Log.d("params","parameters"+" " +integers[0]+integers[1]);
            Log.d("Deleted?","item has been deleted");
            return null;
        }
    }

    class LikeEventAsyncTask extends AsyncTask<SavedEvents, Void, Void> {

        SavedEventsDao sDao;

        LikeEventAsyncTask(SavedEventsDao sDao) {
            this.sDao = sDao;
        }

        @Override
        protected Void doInBackground(SavedEvents...savedEvents) {
            sDao.insert(savedEvents[0]);
            return null;

        }
    }

    public User getCredentials() {
        return credentials;
    }

    public LiveData<List<Event>> getUserLikedEvent() {
        return usersLikedEvents;
    }

    public LiveData<List<SavedEvents>> getSavedEvents(){
        return  savedEvents;
    }

    public SavedEventsDao getSavedEventsDao() {
        return savedEventsDao;
    }
}











