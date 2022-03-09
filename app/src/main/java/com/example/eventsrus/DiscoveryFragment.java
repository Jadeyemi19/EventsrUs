package com.example.eventsrus;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class DiscoveryFragment extends Fragment implements RecyclerViewInterface {
    private LiveData<List<Event>> allevents;
    private EventListAdapter listAdapter;
    private RecyclerView eventRV;
    private EventViewModel viewmodel;
    List<Event> nonLiveDataevents;


    public DiscoveryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity mainActivity = (MainActivity) getActivity();
        viewmodel = mainActivity.getEventViewModel();
        allevents = viewmodel.getAllEvents();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_discovery, container, false);
        Context context = view.getContext();
        eventRV = view.findViewById(R.id.eventCards);

        allevents.observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(final List<Event> events) {
                // Update the cached copy of the words in the adapter.
                EventListAdapter adapter = new EventListAdapter(getActivity(), DiscoveryFragment.this);
                adapter.setEvents(events);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                eventRV.setLayoutManager(linearLayoutManager);
                eventRV.setAdapter(adapter);
                nonLiveDataevents = events;
            }


        });
        return view;
    }


    @Override
    public void onitemClick(int Position) {
        Bundle args = new Bundle();
        args.putInt("Event ID", nonLiveDataevents.get(Position).getId());
        args.putString("Event Description", nonLiveDataevents.get(Position).getDescription());
        args.putString("Event Name", nonLiveDataevents.get(Position).getName());
        args.putString("Event Place", nonLiveDataevents.get(Position).getPlace());
        args.putString("Event City", nonLiveDataevents.get(Position).getCity());
        args.putString("Event Time", nonLiveDataevents.get(Position).getTime());
        args.putString("Event Address", nonLiveDataevents.get(Position).getAddress());
        args.putString("Event Postcode", nonLiveDataevents.get(Position).getPostCode());
        args.putString("Event Image", nonLiveDataevents.get(Position).getImgDesc());
        args.putString("Event Type", nonLiveDataevents.get(Position).getType());
        NavHostFragment navHostFragment = (NavHostFragment) getParentFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navHost = navHostFragment.getNavController();
        navHost.navigate(R.id.action_discoveryFragment_to_expandedEventCard,args);


    }
}