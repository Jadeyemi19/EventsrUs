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


public class NotLoggedin extends Fragment {

    public NotLoggedin() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notlogged_in, container, false);
        TextView message = view.findViewById(R.id.notLoggedinMessage);
        Button login = view.findViewById(R.id.LogIn);
        Button register = view.findViewById(R.id.Register);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
                navController.navigate(R.id.action_notLoggedin_to_login_page);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
                navController.navigate(R.id.action_notLoggedin_to_registerFragment);
            }
        });
        return  view;
    }

}