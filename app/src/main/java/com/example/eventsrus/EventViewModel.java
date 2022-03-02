package com.example.eventsrus;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EventViewModel extends AndroidViewModel {

    private EventsRUsRepository eRepository;
    private LiveData<List<Event>> allEvents;

    public EventViewModel (Application application){
        super(application);
        eRepository= new EventsRUsRepository(application);
        allEvents=eRepository.getAllEvents();
    }

    LiveData<List<Event>> getAllEvents(){
       return allEvents;
    }
    public void insert(Event event) { eRepository.insert(event); }

}
