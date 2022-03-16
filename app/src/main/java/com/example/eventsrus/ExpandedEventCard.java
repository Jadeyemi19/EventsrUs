package com.example.eventsrus;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
    private static String test = "expandedeventcard";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BottomNavigationView bottomNavigationView=getActivity().findViewById(R.id.bottomNavbar);
        bottomNavigationView.setVisibility(View.GONE);
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
        TextView eventLocation = view.findViewById(R.id.eventLocation);
        eventLocation.setText(new StringBuilder().append(place).append(" ,").append(city).toString());
        TextView eventDesc = view.findViewById(R.id.expandable_text);
        eventDesc.setText(getArguments().getString("description"));
        mapview=view.findViewById(R.id.map);
        mapview.onCreate(savedInstanceState);
        if(mapview!=null) {
            mapview.getMapAsync(this);
        }

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        String eventAddress = address + "," + postcode;
        LatLng address = getLocationFromAddress(getActivity(), eventAddress);
        if (address != null) {
            Log.d(test, "we lit");
        }
        float zoom= 17;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(address,zoom));
        map.addMarker(new MarkerOptions()
                .position(address)
                .icon(BitmapDescriptorFactory.defaultMarker
                        (BitmapDescriptorFactory.HUE_RED)));


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






