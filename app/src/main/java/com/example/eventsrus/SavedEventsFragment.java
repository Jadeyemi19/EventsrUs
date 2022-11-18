package com.example.eventsrus;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

//import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.logging.Log;


public class SavedEventsFragment extends Fragment implements RecyclerViewInterface {

    private LiveData<List<Event>> savedEvents;
    private SavedEventListAdapter listAdapter;
    private RecyclerView eventRV;
    private EventViewModel viewmodel;
    List<Event> nonLiveDataevents;
    SharedPreferences sharedPreferences;
    EventsRUsRepository eventsRUsRepository;
    ProgressBar progressBar;
    boolean loggedIn;
    int userID;
    TextView empty;
    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

    public SavedEventsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity mainActivity = (MainActivity) getActivity();
        viewmodel = ViewModelProviders.of(this).get(EventViewModel.class);
        sharedPreferences = (SharedPreferences) requireActivity().getSharedPreferences("com.example.android.sharedprefs", Context.MODE_PRIVATE);
        userID = sharedPreferences.getInt("userId", 0);
        savedEvents = viewmodel.getUserLikedEventby(userID);
        listAdapter = new SavedEventListAdapter(getContext(), this);
        loggedIn = sharedPreferences.getBoolean("loggedin", false);
        nonLiveDataevents = new ArrayList<Event>();

        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        if (!loggedIn) {
            navController.navigate(R.id.notLoggedin);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_saved_events, container, false);
        Context context = view.getContext();
        progressBar = view.findViewById(R.id.progressBar);
        eventRV = view.findViewById(R.id.SavedeventCards);
        eventRV.setAdapter(listAdapter);
        empty = view.findViewById(R.id.NoSavedEvents);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        eventRV.setLayoutManager(linearLayoutManager);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(eventRV);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        if (!loggedIn) {
            navController.navigate(R.id.notLoggedin);
        }

        getSavedEvents();


                    }





    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottomNavbar);
        if (bottomNav.getVisibility() == View.GONE) {
            bottomNav.setVisibility(View.VISIBLE);
        }

    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            viewmodel.deleteEvent((listAdapter.getEventAt(viewHolder.getAdapterPosition()).getId()), userID);
            listAdapter.notifyDataSetChanged();

        }
    };

    @Override
    public void onitemClick(int Position) {
        Bundle args = new Bundle();
        args.putInt("eventID", nonLiveDataevents.get(Position).getId());
        args.putString("description", nonLiveDataevents.get(Position).getDescription());
        args.putString("name", nonLiveDataevents.get(Position).getName());
        args.putString("place", nonLiveDataevents.get(Position).getPlace());
        args.putString("city", nonLiveDataevents.get(Position).getCity());
        args.putString("time", nonLiveDataevents.get(Position).getTime());
        args.putString("address", nonLiveDataevents.get(Position).getAddress());
        args.putString("postcode", nonLiveDataevents.get(Position).getPostCode());
        args.putString("image", nonLiveDataevents.get(Position).getImgDesc());
        args.putString("type", nonLiveDataevents.get(Position).getType());
        NavController navHost = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navHost.navigate(R.id.action_savedEventsFragment_to_expandedEventCard, args);


    }

    public void getSavedEvents() {
        if (savedEvents != null) {
            savedEvents.observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
                @Override
                public void onChanged(final List<Event> events) {
                    nonLiveDataevents.clear();
                    if (events.size() != 0) {
                        nonLiveDataevents.addAll(events);
                        listAdapter.setEvents(events);

                        eventRV.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);

                    } else {
                        empty.setVisibility(View.VISIBLE);
                    }
                }
            });
        }


    }
}
