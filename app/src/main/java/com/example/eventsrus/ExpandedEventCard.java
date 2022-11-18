package com.example.eventsrus;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;


public class ExpandedEventCard extends Fragment implements OnMapReadyCallback {
    private MapView mapview;
    private GoogleMap map;
    private int eventId;
    private String name;
    private String imgDesc;
    private String location;
    private String date;
    private String city;
    private String place;
    private String time;
    private String address;
    private String postcode;
    private String description;
    private String type;
    private LatLng Cood;
    boolean liked=false;
    boolean loggedIn;
    EventViewModel viewModel;
    SharedPreferences sharedPreferences;
    int userId;
    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    Boolean gesture;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BottomNavigationView bottomNavigationView=getActivity().findViewById(R.id.bottomNavbar);
        bottomNavigationView.setVisibility(View.GONE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        name = getArguments().getString("name");
        imgDesc = getArguments().getString("image");
        location = getArguments().getString("location");
        date = getArguments().getString("date");
        city = getArguments().getString("city");
        place = getArguments().getString("place");
        time = getArguments().getString("time");
        address = getArguments().getString("address");
        postcode = getArguments().getString("postcode");
        description = getArguments().getString("description");
        type = getArguments().getString("type");
        setHasOptionsMenu(true);
        viewModel= ViewModelProviders.of(this).get(EventViewModel.class);
        sharedPreferences= requireActivity().getSharedPreferences("com.example.android.sharedprefs",Context.MODE_PRIVATE);
        userId=sharedPreferences.getInt("userId",0);
        loggedIn=sharedPreferences.getBoolean("loggedin",false);
        gesture=sharedPreferences.getBoolean("gestureOn",false);
        int eventID=getArguments().getInt("eventID");
        Log.d("EventID","Event id is:"+ eventID);
        mSensorManager = (SensorManager)requireActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor sensorshake=mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorEventListener sensorEventListener= new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if(event!=null){
                    float x_accl=event.values[0];
                    float y_accl=event.values[1];
                    float z_accl=event.values[2];

                    float floatsum=Math.abs(x_accl)+Math.abs(y_accl)+Math.abs(z_accl);


                    if(floatsum>14) {
                        if (gesture) {
                            assert getArguments() != null;
                            SavedEvents events=new SavedEvents(getArguments().getInt("eventID"),userId);
                            viewModel.likeEvent(events);
                            Toast.makeText(getActivity(), "Event Saved", Toast.LENGTH_SHORT).show();
                        }
                    }
                    }
                }



            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        mSensorManager.registerListener(sensorEventListener,sensorshake,mSensorManager.SENSOR_DELAY_NORMAL);


    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_expanded__event__card, container, false);




    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView Vname = view.findViewById(R.id.eventName);
        Vname.setText(name);
        ImageView imageView = view.findViewById(R.id.expandedimgEvent);
        int drawableResourceId = getActivity().getResources().getIdentifier(imgDesc, "drawable", getActivity().getPackageName());
        imageView.setImageResource(drawableResourceId);
        TextView eventTime = view.findViewById(R.id.eventTime);
        eventTime.setText(time);
        TextView eventdate=view.findViewById(R.id.eventDate);
        eventdate.setText(date);
        TextView eventLocation = view.findViewById(R.id.eventLocation);
        eventLocation.setText(new StringBuilder().append(place).append(" ,").append(city).toString());
        TextView eventDesc = view.findViewById(R.id.expandable_text);
        eventDesc.setText(getArguments().getString("description"));
        mapview=view.findViewById(R.id.map);
        mapview.onCreate(savedInstanceState);
        if(mapview!=null) {
            mapview.getMapAsync(this);
        }
        ImageView food=view.findViewById(R.id.food);
        ImageView hotel=view.findViewById(R.id.hotel);


        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args=new Bundle();
                args.putString("type","resturants");
                args.putDouble("lat",Cood.latitude);
                args.putDouble("lon",Cood.longitude);
                NavController navController= Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
                navController.navigate(R.id.action_expandedEventCard_to_nearByPlacesFragment,args);

            }
        });

        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args=new Bundle();
                args.putString("type","hotel");
                args.putDouble("lat",Cood.latitude);
                args.putDouble("lon",Cood.longitude);
                NavController navController= Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
                navController.navigate(R.id.action_expandedEventCard_to_nearByPlacesFragment,args);

            }
        });

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        String eventAddress = address + "," + postcode;
        Cood = getLocationFromAddress(getActivity(), eventAddress);
        if (Cood != null) {
            float zoom = 17;
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(Cood, zoom));
            map.addMarker(new MarkerOptions()
                    .position(Cood)
                    .icon(BitmapDescriptorFactory.defaultMarker
                            (BitmapDescriptorFactory.HUE_RED)));


        }
    }


    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 1);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }


   @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater){
        super.onCreateOptionsMenu(menu,menuInflater);
        menuInflater.inflate(R.menu.action_buttons,menu);
        MenuItem item=menu.findItem(R.id.weatherFragment);
        MenuItem like=menu.findItem(R.id.LikedEvents);
        like.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
       public boolean onMenuItemClick(MenuItem item){
                Log.d("isloggedin",""+loggedIn);
                if(loggedIn) {
                    if (!liked ) {
                        item.setIcon(R.drawable.ic_savedevents);
                        liked = true;
                        assert getArguments() != null;
                        SavedEvents event=new SavedEvents(getArguments().getInt("eventID"),userId);
                        viewModel.likeEvent(event);
                        Toast.makeText(getActivity(), "Event Saved", Toast.LENGTH_SHORT).show();

                    } else {
                        item.setIcon(R.drawable.ic_baseline_favorite_border_24);
                        liked = false;
                        assert getArguments() != null;
                        Log.d("Delete", "delete"+ "" + userId +getArguments().getInt("eventID"));
                        viewModel.deleteEvent( getArguments().getInt("eventID"),userId);

                    }
                }else{
                    Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_expandedEventCard_to_expandedcard_notloggedin);
                }

                return true;
                }



       });


        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                                            @Override
                                            public boolean onMenuItemClick(MenuItem item) {
                                                NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                                                NavController navHost = navHostFragment.getNavController();
                                                Bundle latlng=new Bundle();
                                                latlng.putDouble("lat",Cood.latitude);
                                                latlng.putDouble("long",Cood.longitude);
                                                navHost.navigate(R.id.action_expandedEventCard_to_weatherFragment,latlng);
                                                return true;
                                            }
                                        }
        );

    }



    @Override
    public void onPause() {
       mapview.onPause();

        super.onPause();
    }

    @Override
    public void onDestroy() {
        mapview.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapview.onLowMemory();
    }

    @Override
    public void onResume() {
        super.onResume();
       mapview.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();
       mapview.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
       mapview.onStop();
    }
}






