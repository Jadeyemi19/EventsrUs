package com.example.eventsrus;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.List;


public class ExpandedEventCard extends Fragment implements OnMapReadyCallback {

    private MapView mapview;
    private GoogleMap map;
    private Context context;







    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       return inflater.inflate(R.layout.fragment_expanded__event__card, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView name=view.findViewById(R.id.eventName);
        ExpandableTextView description=view.findViewById(R.id.expand_text_view);
        MapView map=view.findViewById(R.id.map);
        TextView eventplaceandcity=view.findViewById(R.id.eventPlaceandCity);
        TextView time=view.findViewById(R.id.eventTime);
        ImageView image=view.findViewById(R.id.expandedimgEvent);
        String imgPath=getArguments().getString("EventImage");
        name.setText(getArguments().getString("EventName"));
        description.setText(getArguments().getString("EventDescription"));
        int drawableResourceId = context.getResources().getIdentifier(imgPath, "drawable", context.getPackageName());
        image.setImageResource(drawableResourceId);
        mapview.getMapAsync(this::onMapReady);


        }







    @Override
    public void onMapReady( GoogleMap googleMap) {
        map=googleMap;
        String address=getArguments().getString("EventAddress")+","+getArguments().getString("EventPostcode");
        LatLng addressOnMap= getLocationFromAddress(getActivity(),address);
        googleMap.addMarker(new MarkerOptions()
                               .position(addressOnMap)
        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
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

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    @Override
    public void onStart(){
        super.onStart();
        mapview.onStart();
    }
    @Override
    public void onResume(){
        super.onResume();
        mapview.onResume();
    }
    @Override
    public void onStop(){
        super.onStop();
        mapview.onStop();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mapview.onStart();
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outstate){
        super.onSaveInstanceState(outstate);
        mapview.onSaveInstanceState(outstate);
    }
    @Override
    public void onLowMemory(){
        super.onLowMemory();
        mapview.onLowMemory();
    }

}


