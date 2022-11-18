package com.example.eventsrus;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;


public class expandedcard_login extends Fragment {
    String emailInput;
    String passwordInput;
    EventViewModel eventViewModel;
    boolean islLoginSuccessful;


    public expandedcard_login() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_expandedcard_login, container, false);
        EditText email=view.findViewById(R.id.exEmailAddress);
        EditText password=view.findViewById(R.id.exPasswordInput);
        Button button=view.findViewById(R.id.exSignInButton);
        TextView emailErrormessage=view.findViewById(R.id.exEmailErrorMessage);
        TextView userNotRecognised=view.findViewById(R.id.exUsernotrecognised);
        TextView fillInallfields=view.findViewById(R.id.exLoginErrorStatement);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailInput= email.getText().toString().trim();
                passwordInput=password.getText().toString().trim();
                if(areAllFieldsFilled()){
                    if(isEmailValid(emailInput)){
                        User user= eventViewModel.logInUser(emailInput,passwordInput);
                        if(user==null){
                            userNotRecognised.setVisibility(View.VISIBLE);
                        }else{
                            islLoginSuccessful=true;
                            SharedPreferences sharedPreferences=requireActivity().getSharedPreferences( "com.example.android.sharedprefs",MODE_PRIVATE);
                            SharedPreferences.Editor preferencesEditor=sharedPreferences.edit();
                            preferencesEditor.putInt("userId", user.getUserid());
                            preferencesEditor.putString("forename",user.getForename());
                            preferencesEditor.putString("surname", user.getEmail());
                            preferencesEditor.putBoolean("loggedin",true);
                            preferencesEditor.apply();
                            NavHostFragment navHostFragment= (NavHostFragment) getParentFragmentManager().findFragmentById(R.id.nav_host_fragment);
                            assert navHostFragment != null;
                            NavController navController=navHostFragment.getNavController();
                            navController.navigate(R.id.action_expandedcard_login_to_expandedEventCard);



                        }





                    }else{
                        emailErrormessage.setVisibility(View.VISIBLE);
                    }




                } else{
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

