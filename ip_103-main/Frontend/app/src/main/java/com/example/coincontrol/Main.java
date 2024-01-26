package com.example.coincontrol;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.coincontrol.api.ApiClientFactory;
import com.example.coincontrol.api.UserApi;
import com.example.coincontrol.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main extends AppCompatActivity
        implements BottomNavigationView
        .OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    private int userID;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView
                = findViewById(R.id.bottomNavigationView);
        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.homeFragment);
        Intent intent = getIntent();
        if (intent.hasExtra("userID"))
        {
            userID = Integer.parseInt(intent.getStringExtra("userID"));
        }
        if (intent.hasExtra("userID2"))
        {
            userID = Integer.parseInt(intent.getStringExtra("userID2"));
        }

    }
    YearlyReport yearlyReport = new YearlyReport();
    Chat chat = new Chat();
    Questions questions = new Questions();
    AccountInfo accountInfo = new AccountInfo();
    Home home = new Home();

    @Override
    public boolean
    onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.yearlyReportFragment) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, yearlyReport)
                    .commit();
        } else if (itemId == R.id.chatFragment) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, chat)
                    .commit();
        } else if (itemId == R.id.homeFragment) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, home)
                    .commit();
        } else if (itemId == R.id.accountInfoFragment) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, accountInfo)
                    .commit();
        }
        return false;
    }

    public String getDataFromIntent()
    {
        UserApi userApi = ApiClientFactory.GetApiClientSeed().create(UserApi.class);
        Call<String> call = userApi.GetUsernameByID(userID);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                if (response.isSuccessful())
                {
                    username = response.body();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
                Log.e("USERNAME FAIL", t.getMessage());
            }
        });
        return username;
    }

    public int getUserIDFromIntent()
    {
        return userID;
    }
}

