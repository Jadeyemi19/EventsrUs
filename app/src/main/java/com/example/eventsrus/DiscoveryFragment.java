package com.example.eventsrus;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class DiscoveryFragment extends Fragment implements RecyclerViewInterface {
    private static int REQUEST_LOCATION_PERMISSION=3;
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
        enableMyLocation();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_discovery, container, false);
        Context context = view.getContext();
        eventRV = view.findViewById(R.id.eventCards);

        allevents.observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
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
        args.putString("EventDescription", nonLiveDataevents.get(Position).getDescription());
        args.putString("EventName", nonLiveDataevents.get(Position).getName());
        args.putString("EventPlace", nonLiveDataevents.get(Position).getPlace());
        args.putString("EventCity", nonLiveDataevents.get(Position).getCity());
        args.putString("EventTime", nonLiveDataevents.get(Position).getTime());
        args.putString("EventAddress", nonLiveDataevents.get(Position).getAddress());
        args.putString("EventPostcode", nonLiveDataevents.get(Position).getPostCode());
        args.putString("EventImage", nonLiveDataevents.get(Position).getImgDesc());
        args.putString("EventType", nonLiveDataevents.get(Position).getType());
        NavHostFragment navHostFragment = (NavHostFragment) getParentFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navHost = navHostFragment.getNavController();
        navHost.navigate(R.id.action_discoveryFragment_to_expandedEventCard,args);


    }


        private void enableMyLocation() {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]
                                {Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION_PERMISSION);
            }
        }
    }
