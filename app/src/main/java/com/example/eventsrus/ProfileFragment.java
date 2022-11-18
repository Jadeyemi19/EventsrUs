package com.example.eventsrus;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class ProfileFragment extends Fragment {


    private SharedPreferences sharedPreferences;
    private int userid;
    private Button Logout;
    boolean loggedin;
    String name;
    String email;


    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = (SharedPreferences) requireActivity().getSharedPreferences("com.example.android.sharedprefs", Context.MODE_PRIVATE);
          loggedin=sharedPreferences.getBoolean("loggedin",false);
          name= sharedPreferences.getString("forename", null) + " " + sharedPreferences.getString("surname", null);
          email=sharedPreferences.getString("email",null);

        Log.d("logged in",""+ sharedPreferences.getBoolean("loggedin",false));
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
          if(!loggedin) {
            Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.profile_notLoggedin);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);


        return  view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
   Switch gesture=view.findViewById(R.id.switch1);
   if (gesture!=null){
       Log.d("exsists", ""+ "true");
   }
        gesture.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
               SharedPreferences.Editor gesture= sharedPreferences.edit();
               gesture.putBoolean("gestureOn",true);
               gesture.apply();
                    Toast.makeText(getActivity(),"Shake to like is on",Toast.LENGTH_SHORT).show();
            }else{
                SharedPreferences.Editor gesture= sharedPreferences.edit();
                gesture.putBoolean("gestureOn",false);
                gesture.apply();
                    Toast.makeText(getActivity(),"Shake to like is off",Toast.LENGTH_SHORT).show();
            }
        }
        });


       Button button= view.findViewById(R.id.Logout);
          button.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  SharedPreferences.Editor editor=sharedPreferences.edit();
                  editor.putBoolean("loggedin",false).commit();
                  Log.d("logged in",""+ sharedPreferences.getBoolean("loggedin",false));
                  NavController navController= Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
                  navController.navigate(R.id.profile_notLoggedin);
              }
          });

        TextView personName=view.findViewById(R.id.textView);
        TextView personEmail=view.findViewById(R.id.email);
         personName.setText(name);
         personEmail.setText(email);



    }


}