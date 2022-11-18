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
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


public class RegisterFragment extends Fragment {
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








    public RegisterFragment() {

    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventViewModel=ViewModelProviders.of(this).get(EventViewModel.class);





        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        email = view.findViewById(R.id.EmailAddress);
        password = view.findViewById(R.id.PasswordInput);
        surname = view.findViewById(R.id.SurnameInput);
        forename = view.findViewById(R.id.ForenameInput);
        button = view.findViewById(R.id.signInButton);
        TextView error = view.findViewById(R.id.RegisterErrorStatement);
        TextView emailError = view.findViewById(R.id.ErrorStatement);

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
                        User registered=eventViewModel.registerUser(user);
                        isRegistrationSuccessful=true;
                        SharedPreferences sharedPreferences=getActivity().getSharedPreferences( "com.example.android.sharedprefs",MODE_PRIVATE);
                        SharedPreferences.Editor preferencesEditor=sharedPreferences.edit();
                        preferencesEditor.putString("forename", user.getForename());
                        preferencesEditor.putString("surname", user.getSurname());
                        preferencesEditor.putString("email",user.getEmail());
                        preferencesEditor.putBoolean("loggedin",true);
                        preferencesEditor.putInt("userId", registered.getUserid());
                        preferencesEditor.apply();
                        NavController navController= Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
                        navController.navigate(R.id.action_registerFragment_to_savedEventsFragment);
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
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
    }




