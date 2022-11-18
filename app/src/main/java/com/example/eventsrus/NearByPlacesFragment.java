package com.example.eventsrus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class NearByPlacesFragment extends Fragment {

    private LatLng cood;
    GoogleMap mMap;
    private double lat,lng;








    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        @Override
        public void onMapReady(GoogleMap googleMap) {
            lat=getArguments().getDouble("lat");
            lng=getArguments().getDouble("lon");
            mMap=googleMap;
            cood=new LatLng(lat,lng);
            mMap.addMarker(new MarkerOptions().position(cood).title("Event location").icon(BitmapDescriptorFactory.defaultMarker
                    (BitmapDescriptorFactory.HUE_RED)));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cood,15));
            StringBuilder stringBuilder= new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
            stringBuilder.append("location="+ String.valueOf(lat) + "," + String.valueOf( lng));
            stringBuilder.append("&radius=1000");
            stringBuilder.append("&type=").append(getArguments().getString("type"));
            stringBuilder.append("&sensor=true");
            stringBuilder.append("&key=").append(getResources().getString(R.string.google_maps_key));

            String url=stringBuilder.toString();
            Object dataFetch[]=new Object[2];
            dataFetch[0]=mMap;
            dataFetch[1]=url;
            FetchData fetchData= new FetchData();
            fetchData.execute(dataFetch);


        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_near_by_places, container, false);

    }

    private void setUpGLClient(){

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}