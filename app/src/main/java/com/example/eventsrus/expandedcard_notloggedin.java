package com.example.eventsrus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;


public class expandedcard_notloggedin extends Fragment {



    public expandedcard_notloggedin() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_expandedcard_notloggedin, container, false);

        TextView message = view.findViewById(R.id.exNotLoggedInMessage);
        Button login = view.findViewById(R.id.exLogIn);
        Button register = view.findViewById(R.id.exRegister);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(),R.id.nav_host_fragment);


                navController.navigate(R.id.action_expandedcard_notloggedin_to_expandedcard_login);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment navHostFragment = (NavHostFragment) getParentFragmentManager().findFragmentById(R.id.nav_host_fragment);
                assert navHostFragment != null;
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.action_expandedcard_notloggedin_to_expandedcard_register);
            }
        });
        return  view;
    }
}