package com.example.coincontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coincontrol.api.AdminApi;
import com.example.coincontrol.api.AdvisorApi;
import com.example.coincontrol.api.ApiClientFactory;
import com.example.coincontrol.api.UserApi;
import com.example.coincontrol.api.AdvisorApi;
import com.example.coincontrol.model.Admin;
import com.example.coincontrol.model.Advisor;
import com.example.coincontrol.model.Expense;
import com.example.coincontrol.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Login extends AppCompatActivity
{
     Button loginBtn;
     Button newUserBtn;
     EditText username;
     EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.loginBtnLP);
        newUserBtn = findViewById(R.id.newUserBtnLP);
        username = findViewById(R.id.usernameTxtLP);
        password = findViewById(R.id.pswdTxtLP);

        UserApi userApi = ApiClientFactory.GetApiClientSeed().create(UserApi.class);
        AdvisorApi advisorApi = ApiClientFactory.GetApiClientSeed().create(AdvisorApi.class);
        AdminApi adminApi = ApiClientFactory.GetApiClientSeed().create(AdminApi.class);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                User user = new User();
                Advisor advisor = new Advisor();
                Admin admin = new Admin();
                String usernameTxt = username.getText().toString();
                String pswdTxt = password.getText().toString();

                Log.d("USER INFO", pswdTxt);

                user.setUsername(usernameTxt);
                user.setPassword(pswdTxt);

                advisor.setUserName(usernameTxt);
                advisor.setPassword(pswdTxt);

                admin.setUsername(usernameTxt);
                admin.setPassword(pswdTxt);

                Call<Integer> userCall = userApi.PostLoginUser(user);
                Call<Integer> advisorCall = advisorApi.PostLoginAdvisor(advisor);
                Call<Integer> adminCall = adminApi.AdminLogin(admin);
                userCall.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response)
                    {
                        Log.d("DEBUG", "Recieved response: " + response.body().toString());

                        if (response.isSuccessful())
                        {
                            if (response.body() >= 1)
                            {
                                Intent intent = new Intent(Login.this, Main.class);
                                intent.putExtra("userID", Integer.toString(response.body()));
                                Toast.makeText(Login.this, user.getUserName() + " successfully logged in!",
                                        Toast.LENGTH_LONG).show();
                                startActivity(intent);
                            }
                            else if (response.body() == 0)
                            {
                                Toast.makeText(Login.this, "Incorrect username or password",
                                        Toast.LENGTH_LONG).show();
                            }
                            else if (response.body() == -1)
                            {
                                Toast.makeText(Login.this, "Account not recognized",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            // Handle unsuccessful response (e.g., non-200 HTTP status codes)
//                            Toast.makeText(Login.this, "Login failed. Server returned an error.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t)
                    {
                        Log.e("LOGIN ERROR", t.getMessage());
                        Toast.makeText(Login.this, "Error" + t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });

                advisorCall.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response)
                    {
                        Log.d("DEBUG", "Recieved response: " + response.body().toString());

                        if (response.isSuccessful())
                        {
                            if (response.body() == 1)
                            {
                                Intent intent = new Intent(Login.this, AdvisorHome.class);
                                intent.putExtra("advisorUsername", advisor.getUserName());
                                startActivity(intent);
                                Toast.makeText(Login.this, intent.getStringExtra("advisorUsername") + " successfully logged in!",
                                        Toast.LENGTH_LONG).show();
                            }
                            else if (response.body() == 0)
                            {
                                Toast.makeText(Login.this, "Incorrect username or password",
                                        Toast.LENGTH_LONG).show();
                            }
                            else if (response.body() == -1)
                            {
                                Toast.makeText(Login.this, "Account not recognized",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t)
                    {
                        Log.e("LOGIN ERROR", t.getMessage());
                        Toast.makeText(Login.this, "Error" + t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });

                adminCall.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response)
                    {
                     if (response.isSuccessful())
                     {
                         if(response.body() == 1)
                         {
                             Intent intent = new Intent(Login.this, AdminPage.class);
                             startActivity(intent);
                             Toast.makeText(Login.this, usernameTxt + " successfully logged in!",
                                     Toast.LENGTH_LONG).show();
                         }
                         else if(response.body() == 0 || response.body() == -1)
                         {
                             Toast.makeText(Login.this, "Incorrect username or password",
                                     Toast.LENGTH_LONG).show();
                         }
                         else{
                             Toast.makeText(Login.this, "admin page login fail",
                                     Toast.LENGTH_LONG).show();
                         }
                     }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t)
                    {
                        Log.e("admin page login fail", t.getMessage());
                        Toast.makeText(Login.this, "Error" + t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        newUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Login.this, CreateAccount.class);
                startActivity(intent);
            }
        });

    }
}