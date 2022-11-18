package com.example.eventsrus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


public class Profile_notLoggedin extends Fragment {




    public Profile_notLoggedin() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile_not_loggedin, container, false);
        TextView message = view.findViewById(R.id.profileNotLoggedinMessage);
        Button login = view.findViewById(R.id.profileLogIn);
        Button register = view.findViewById(R.id.profileRegister);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
                navController.navigate(R.id.action_profile_notLoggedin_to_profile_login);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
                navController.navigate(R.id.action_profile_notLoggedin_to_profile_register2);
            }
        });
        return  view;
    }

}