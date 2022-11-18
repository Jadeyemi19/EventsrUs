package com.example.eventsrus;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity  {

    private EventViewModel eventViewModel;
    private static final String TAG="MainActivity";
    private static final int ERROR_DIALOG_REQUEST=9001;
    SharedPreferences sharedPreferences;
    String sharedPrefFile;

    private AppBarConfiguration appBarConfiguration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavbar);
        setupBottomNav(bottomNavigationView);
        sharedPrefFile= "com.example.android.sharedprefs";
        sharedPreferences= getSharedPreferences(sharedPrefFile, MODE_PRIVATE);



    }



    protected void setupBottomNav(BottomNavigationView view){
      appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.discoveryFragment, R.id.savedEventsFragment, R.id.profileFragment)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(view, navController);



    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment), appBarConfiguration)
                || super.onSupportNavigateUp();

    }


    public EventViewModel getEventViewModel(){
        return eventViewModel;
    }


    public boolean isServicesOk(){
       Log.d(TAG,"isServicesOK:checking google services version");
       int available= GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
       if(available== ConnectionResult.SUCCESS){
           Log.d(TAG,"isServicesOk:Google Play Services is working");
           return true;
       }
       else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
           Log.d(TAG,"isServicesOK:an error occured but we can fix it");
           Dialog dialog =GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this,available,ERROR_DIALOG_REQUEST);
                   dialog.show();

       }else{
           Toast.makeText(this,"You can't make map requests",Toast.LENGTH_SHORT).show();
       }
       return false;
    }
}