package com.example.eventsrus;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EventViewModel extends AndroidViewModel {

    private EventsRUsRepository eRepository;
    private LiveData<List<Event>> allEvents;
    private LiveData<List<SavedEvents>> SavedEvents;
    private User credentials;


    public EventViewModel(Application application) {
        super(application);
        eRepository = new EventsRUsRepository(application);
        allEvents = eRepository.getAllEvents();
        SavedEvents = eRepository.getSavedEvents();

    }

    LiveData<List<Event>> getAllEvents() {
        return allEvents;
    }

    public void insert(Event event) {
        eRepository.insert(event);
    }

    public void insert(User user) {
        eRepository.registerUser(user);
    }

    public void likeEvent(SavedEvents event) {
        eRepository.likeEvent(event);
    }

    public LiveData<List<Event>> getUserLikedEventby(int userId) {
        eRepository.getUserLikeEvents(userId);
        return eRepository.getUserLikedEvent();
    }

    public User logInUser(String email, String password) {
        eRepository.loginUser(email, password);
        credentials = eRepository.getCredentials();
        if (credentials == null) {
            Log.d("credentials", "it is null");
        } else {
            return credentials;
        }
        return null;
    }

    public User registerUser(User user) {
        eRepository.registerUser(user);
        return credentials;

    }

    public void deleteEvent(int userid, int eventid) {
        eRepository.deleteSavedEvent(userid, eventid);
    }

    public LiveData<List<SavedEvents>> getSavedEvents(int userId) {
        eRepository.getUserSavedEvents(userId);

        return eRepository.getSavedEvents();

    }
}
