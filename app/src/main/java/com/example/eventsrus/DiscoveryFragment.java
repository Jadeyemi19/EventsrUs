package com.example.eventsrus;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;


public class DiscoveryFragment extends Fragment implements RecyclerViewInterface {
    private LiveData<List<Event>> allevents;
    private EventListAdapter listAdapter;
    private RecyclerView eventRV;
    private EventViewModel viewmodel;
    List<Event> nonLiveDataevents;
    private static int REQUEST_LOCATION_PERMISSION=3;


    public DiscoveryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity mainActivity = (MainActivity) getActivity();
        viewmodel = ViewModelProviders.of(this).get(EventViewModel.class);
        allevents = viewmodel.getAllEvents();
        setHasOptionsMenu(true);
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
                nonLiveDataevents = events;
                listAdapter = new EventListAdapter(getActivity(), DiscoveryFragment.this);
                listAdapter.setEvents(events);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                eventRV.setLayoutManager(linearLayoutManager);
                eventRV.setAdapter(listAdapter);

            }


        });
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater ){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.options_menu,menu);
        MenuItem menuItem= menu.findItem(R.id.search);
        SearchView searchView= (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }
    @Override
    public void onResume(){
        super.onResume();
        BottomNavigationView bottomNav= getActivity().findViewById(R.id.bottomNavbar);
        if(bottomNav.getVisibility()==View.GONE){
            bottomNav.setVisibility(View.VISIBLE);
        }
       // ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }


    @Override
    public void onitemClick(int Position) {
        Bundle args = new Bundle();
        args.putInt("eventID", nonLiveDataevents.get(Position).getId());
        args.putString("description", nonLiveDataevents.get(Position).getDescription());
        args.putString("name", nonLiveDataevents.get(Position).getName());
        args.putString("place", nonLiveDataevents.get(Position).getPlace());
        args.putString("city", nonLiveDataevents.get(Position).getCity());
        args.putString("time", nonLiveDataevents.get(Position).getTime());
        args.putString("date",nonLiveDataevents.get(Position).getDate());
        args.putString("address", nonLiveDataevents.get(Position).getAddress());
        args.putString("postcode", nonLiveDataevents.get(Position).getPostCode());
        args.putString("image", nonLiveDataevents.get(Position).getImgDesc());
        args.putString("type", nonLiveDataevents.get(Position).getType());
        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
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
