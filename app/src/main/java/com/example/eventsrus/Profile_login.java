package com.example.eventsrus;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


public class Profile_login extends Fragment {
    String emailInput;
    String passwordInput;
    EventViewModel eventViewModel;
    boolean islLoginSuccessful;



    public Profile_login() {
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
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile_login, container, false);
        EditText email = view.findViewById(R.id.profileEmailAddress);
        EditText password = view.findViewById(R.id.profilePasswordInput);
        Button button = view.findViewById(R.id.profileSignInButton);
        TextView emailErrormessage = view.findViewById(R.id.profileEmailErrorMessage);
        TextView userNotRecognised = view.findViewById(R.id.profileUsernotrecognised);
        TextView fillInallfields = view.findViewById(R.id.profileLoginErrorStatement);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailInput = email.getText().toString().trim();
                passwordInput = password.getText().toString().trim();
                if (areAllFieldsFilled()) {
                    if (isEmailValid(emailInput)) {
                        User user = eventViewModel.logInUser(emailInput, passwordInput);
                        if (user == null) {
                            userNotRecognised.setVisibility(View.VISIBLE);
                        } else {
                            islLoginSuccessful = true;
                            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("com.example.android.sharedprefs", MODE_PRIVATE);
                            SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
                            preferencesEditor.putInt("userId", user.getUserid());
                            preferencesEditor.putString("forename", user.getForename());
                            preferencesEditor.putString("surname",user.getSurname());
                            preferencesEditor.putString("email", user.getEmail());
                            preferencesEditor.putBoolean("loggedin", true);
                            preferencesEditor.apply();

                            NavController navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
                            navController.navigate(R.id.action_profile_login_to_profileFragment);


                        }


                    } else {
                        emailErrormessage.setVisibility(View.VISIBLE);
                    }


                } else {
                    fillInallfields.setVisibility(View.VISIBLE);
                }


            }
        });

        return view;
    }

    public boolean areAllFieldsFilled() {
        if (emailInput.isEmpty() || passwordInput.isEmpty()) {
            return false;

        } else {
            return true;
        }
    }

    public boolean isEmailValid(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());


    }
}