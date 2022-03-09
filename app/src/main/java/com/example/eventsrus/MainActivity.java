package com.example.eventsrus;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;


public class MainActivity extends AppCompatActivity  {

    private EventViewModel eventViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.nav_host_fragment, new DiscoveryFragment())
                .addToBackStack(null)
                .commit();

    }




    public EventViewModel getEventViewModel(){
        return eventViewModel;
    }
}