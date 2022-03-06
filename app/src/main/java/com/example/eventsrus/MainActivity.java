package com.example.eventsrus;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EventViewModel eventViewModel;
    private RecyclerView eventRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eventRV=findViewById(R.id.eventCards);


        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        eventViewModel.getAllEvents().observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged( final List<Event> events) {
                // Update the cached copy of the words in the adapter.
                EventListAdapter adapter=new EventListAdapter(MainActivity.this);
                adapter.setEvents(events);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
                eventRV.setLayoutManager(linearLayoutManager);
                eventRV.setAdapter(adapter);
            }
        });
    }
}