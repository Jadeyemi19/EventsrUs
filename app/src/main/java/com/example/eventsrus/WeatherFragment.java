package com.example.eventsrus;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.eventsrus.Adapter.ViewPagerAdapter;
import com.example.eventsrus.Common.Common;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class WeatherFragment extends Fragment {

  private Toolbar toolbar;
  private TabLayout tabLayout;
  private ViewPager viewPager;

  private CoordinatorLayout coordinatorLayout;
  private FusedLocationProviderClient fusedLocationProviderClient;
  private LocationCallback locationCallback;
  private LocationRequest locationRequest;




    public WeatherFragment() {
        // Required empty public constructor
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION,
                                  Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if(report.areAllPermissionsGranted()){
                                    buildLocationRequest();
                                    buildLocationCallBack();
                                    if(ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                                    fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper());
                                }
                                }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                         Snackbar.make(coordinatorLayout,"Permission Denied",Snackbar.LENGTH_LONG)
                                 .show();
                    }
                }).check();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_weather, container, false);
        coordinatorLayout=(CoordinatorLayout) view.findViewById(R.id.root_view);
        toolbar=(Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);


        return view;
    }

    private void buildLocationRequest(){
        locationRequest=LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10.0f);
    }

    private void buildLocationCallBack(){
        locationCallback= new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Common.current_location=locationResult.getLastLocation();
                viewPager=(ViewPager)  getActivity().findViewById(R.id.view_pager);
                setupViewpager(viewPager);
                tabLayout= getActivity().findViewById(R.id.tabs);
                 tabLayout.setupWithViewPager(viewPager);
            }
        };
    }

    private void setupViewpager(ViewPager viewPager) {
        ViewPagerAdapter adapter=new ViewPagerAdapter(getParentFragmentManager()) ;
        adapter.addFragment(TodayWeatherFragment.getInstance(),"Today");
        viewPager.setAdapter(adapter);

    }
}