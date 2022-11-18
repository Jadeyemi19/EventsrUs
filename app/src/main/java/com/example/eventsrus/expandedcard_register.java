package com.example.eventsrus;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;


public class expandedcard_register extends DialogFragment {
    EventViewModel eventViewModel;
    TextView email;
    TextView password;
    TextView forename;
    TextView surname;
    Button button;
    String emailInput;
    String passwordInput;
    String forenameInput;
    String surnameInput;
    boolean isRegistrationSuccessful;


    public expandedcard_register() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventViewModel= ViewModelProviders.of(this).get(EventViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_expandedcard_register, container, false);
        email = view.findViewById(R.id.exEmailAddressInput);
        password = view.findViewById(R.id.exPasswordInput);
        surname = view.findViewById(R.id.exSurnameInput);
        forename = view.findViewById(R.id.exForenameInput);
        button = view.findViewById(R.id.exSignInButton);
        TextView error = view.findViewById(R.id.exRegisterErrorStatement);
        TextView emailError = view.findViewById(R.id.exErrorStatement);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forenameInput = forename.getText().toString().trim();
                surnameInput = surname.getText().toString().trim();
                emailInput = email.getText().toString().trim();
                passwordInput = password.getText().toString().trim();

                if (areAllFieldsFilled()) {
                    if (isEmailValid(emailInput)) {
                        User user = new User(forenameInput, surnameInput, emailInput, passwordInput);
                        eventViewModel.registerUser(user);
                        isRegistrationSuccessful=true;
                        SharedPreferences sharedPreferences=getActivity().getSharedPreferences( "com.example.android.sharedprefs",MODE_PRIVATE);
                        SharedPreferences.Editor preferencesEditor=sharedPreferences.edit();
                        preferencesEditor.putInt("userId", user.getUserid());
                        preferencesEditor.putString("forename",user.getForename());
                        preferencesEditor.putString("surname", user.getSurname());
                        preferencesEditor.putBoolean("loggedin",true);
                        preferencesEditor.apply();
                        NavHostFragment navHostFragment= (NavHostFragment) getParentFragmentManager().findFragmentById(R.id.nav_host_fragment);
                        NavController navController=navHostFragment.getNavController();
                        navController.navigate(R.id.action_expandedcard_register_to_expandedEventCard);
                    } else {
                        emailError.setVisibility(View.VISIBLE);
                    }
                } else {
                    error.setVisibility(View.VISIBLE);

                }
            }


        });
        return  view;
    }
    public boolean areAllFieldsFilled() {
        if (emailInput.isEmpty() || passwordInput.isEmpty() || forenameInput.isEmpty() || surnameInput.isEmpty()) {
            return false;

        } else {
            return true;
        }
    }

    public boolean isEmailValid(String email){
        String checkEmail=email;
        String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(email.matches(emailPattern)){
            return true;
        }else{
            return false;
        }
    }
}



