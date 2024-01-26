package com.example.coincontrol;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coincontrol.api.ApiClientFactory;
import com.example.coincontrol.api.UserApi;
import com.example.coincontrol.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountInfo extends Fragment {

    private String username;
    private int userID;

    private Button editUsernameButton;
    private Button editPasswordButton;
    private Button editEmailButton;
    private Button logOutButton;


    private EditText editTextUsername;
    private EditText password;
    private EditText email;

    private TextView currentUsername;
    private TextView currentPassword;
    private TextView currentEmail;
    private TextView yourNameTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment's layout XML file
        View view = inflater.inflate(R.layout.fragment_account_info, container, false);
        // code here
        Main main = (Main) getActivity();
//        username = main.getDataFromIntent();
        userID = main.getUserIDFromIntent();

        yourNameTextView = view.findViewById(R.id.yourNameTextView);
        editUsernameButton = view.findViewById(R.id.usernameButton);
        editEmailButton = view.findViewById(R.id.emailButton);
        editPasswordButton = view.findViewById(R.id.passwordButton);
        logOutButton = view.findViewById(R.id.logOutButton);

        editTextUsername = view.findViewById(R.id.usernameEditText);
        password = view.findViewById(R.id.passwordEditText);
        email = view.findViewById(R.id.emailEditText);

        currentUsername = view.findViewById(R.id.currentUsernameTextView);
        currentPassword = view.findViewById(R.id.currentPasswordTextView);
        currentEmail = view.findViewById(R.id.currentEmailTextView);

        UserApi userApi = ApiClientFactory.GetApiClientSeed().create(UserApi.class);
        Call<User> getUserCall= userApi.GetUserByID(userID);

        getUserCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    //set the currentUsername or currentUsername hint to the username
                    //same for password
                    //same for email
                    //currentUsername.setText(response.body());
                    User user = new User();
                    user = response.body();
                    username = user.getUsername();

                    yourNameTextView.setText(user.getFirstName() + " " + user.getLastName());
                    currentUsername.setText(user.getUserName());
                    currentPassword.setText(user.getPassword());
                    currentEmail.setText(user.getEmail());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(),"Current user not obtained",Toast.LENGTH_LONG).show();
            }
        });

        editUsernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameInput = editTextUsername.getText().toString();
                UserApi userApi = ApiClientFactory.GetApiClientSeed().create(UserApi.class);
                Call<Integer> usernameCall = userApi.ChangeUsername(usernameInput, username);
                // check drew's updated code to make sure these aren't flip flopped^
                usernameCall.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if(response.isSuccessful()){
                            if(response.body() == 5)
                            {
                                Toast.makeText(getActivity(),"Username changed successfully",Toast.LENGTH_LONG).show();
                                currentUsername.setText(usernameInput);
                                username = usernameInput;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Log.e("UPDATE USERNAME", t.getMessage());
                        Toast.makeText(getActivity(),"Response failed",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        editPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordInput = password.getText().toString();
                UserApi userApi = ApiClientFactory.GetApiClientSeed().create(UserApi.class);
                Call<Integer> passwordCall = userApi.ChangePassword(username, passwordInput);
                passwordCall.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if(response.isSuccessful()){
                            if(response.body() == 6)
                            {
                                Toast.makeText(getActivity(),"Password changed successfully",Toast.LENGTH_LONG).show();
                                currentPassword.setText(passwordInput);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Toast.makeText(getActivity(),"Response failed",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        editEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailInput = email.getText().toString();
                UserApi userApi = ApiClientFactory.GetApiClientSeed().create(UserApi.class);
                Call<Integer> emailCall = userApi.ChangeEmail(username, emailInput);
                emailCall.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if(response.isSuccessful()){
                            if(response.body() == 7)
                            {
                                Toast.makeText(getActivity(),"Successfully changed email.",Toast.LENGTH_LONG).show();
                                currentEmail.setText(emailInput);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Toast.makeText(getActivity(),"Response failed",Toast.LENGTH_LONG).show();
                        Log.e("ACCOUNT ERROR", t.getMessage());
                    }
                });
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });

        // code above
        return view;
        //return inflater.inflate(R.layout.fragment_account_info, container, false);
    }
}