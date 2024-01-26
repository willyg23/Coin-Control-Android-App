package com.example.coincontrol;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.coincontrol.api.AdvisorApi;
import com.example.coincontrol.api.ApiClientFactory;
import com.example.coincontrol.model.Advisor;
import com.example.coincontrol.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAccount extends AppCompatActivity
{
    Button createBtn;
    EditText firstName;
    EditText lastName;
    EditText username;
    EditText email;
    EditText password;
    EditText confirmPswd;
    Switch advisorSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        createBtn = findViewById(R.id.createBtnCAP);
        firstName = findViewById(R.id.firstNameTxtCAP);
        lastName = findViewById(R.id.lastNameTxtCAP);
        username = findViewById(R.id.usernameTxtCAP);
        email = findViewById(R.id.emailTxtCAP);
        password = findViewById(R.id.pswdTxtCAP);
        confirmPswd = findViewById(R.id.confirmPswdTxtCAP);
        advisorSwitch = findViewById(R.id.advisorSwitchCAP);

        createBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                User user = new User();
                Advisor advisor = new Advisor();

                String firstNameTxt = firstName.getText().toString();
                String lastNameTxt = lastName.getText().toString();
                String usernameTxt = username.getText().toString();
                String emailTxt = email.getText().toString();
                String pswdTxt = password.getText().toString();
                String confirmPswdTxt = confirmPswd.getText().toString();


                if (firstNameTxt.isEmpty() || lastNameTxt.isEmpty() || emailTxt.isEmpty()
                        || pswdTxt.isEmpty() || confirmPswdTxt.isEmpty())
                {
                    Toast.makeText(CreateAccount.this, "Please fill in all text boxes",
                            Toast.LENGTH_LONG).show();
                }
                else if (!(android.util.Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()))
                {
                    Toast.makeText(CreateAccount.this, "Incorrect email format",
                            Toast.LENGTH_LONG).show();
                }
                else if (!pswdTxt.equals(confirmPswdTxt))
                {
                    Toast.makeText(CreateAccount.this, "Passwords do not match",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    if (advisorSwitch.isChecked())
                    {
                        advisor.setFirstName(firstNameTxt);
                        advisor.setLastName(lastNameTxt);
                        advisor.setEmail(emailTxt);
                        advisor.setUserName(usernameTxt);
                        advisor.setPassword(pswdTxt);

                        Call<Integer> call = ApiClientFactory.GetAdvisorApi().PostAdvisor(advisor);
                        call.enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                if (response.isSuccessful()) {
                                    if (response.body() == 2) {
                                        Toast.makeText(CreateAccount.this,
                                                "Email is already in use",
                                                Toast.LENGTH_LONG).show();
                                    } else if (response.body() == 3) {
                                        Toast.makeText(CreateAccount.this,
                                                "Username is taken",
                                                Toast.LENGTH_LONG).show();
                                    } else if (response.body() == 4) {
                                        Toast.makeText(CreateAccount.this,
                                                "Account successfully created!",
                                                Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(CreateAccount.this, AdvisorHome.class);
                                        intent.putExtra("advisorUsername", advisor.getUserName());
                                        startActivity(intent);
                                    }
                                } else {
                                    // Handle unsuccessful response (e.g., non-200 HTTP status codes)
                                    Toast.makeText(CreateAccount.this, "Server returned an error.",
                                            Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                System.out.print(t.getMessage());
                                Toast.makeText(CreateAccount.this, "Error" + t.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    else if (!advisorSwitch.isChecked())
                    {
                        user.setFirstName(firstNameTxt);
                        user.setLastName(lastNameTxt);
                        user.setEmail(emailTxt);
                        user.setUsername(usernameTxt);
                        user.setPassword(pswdTxt);
                        Call<Integer> call = ApiClientFactory.GetUserApi().PostRegisterUser(user);
                        call.enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                if (response.isSuccessful()) {
                                    if (response.body() == 2) {
                                        Toast.makeText(CreateAccount.this,
                                                "Email is already in use",
                                                Toast.LENGTH_LONG).show();
                                    } else if (response.body() == 3) {
                                        Toast.makeText(CreateAccount.this,
                                                "Username is taken",
                                                Toast.LENGTH_LONG).show();
                                    } else if (response.body() == 4) {
                                        Toast.makeText(CreateAccount.this,
                                                "Account successfully created!",
                                                Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(CreateAccount.this, suveryInfo.class);
                                        intent.putExtra("username2", user.getUsername());
                                        startActivity(intent);
                                    }
                                } else {
                                    // Handle unsuccessful response (e.g., non-200 HTTP status codes)
                                    Toast.makeText(CreateAccount.this, "Server returned an error.",
                                            Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                System.out.print(t.getMessage());
                                Toast.makeText(CreateAccount.this, "Error" + t.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }
        });
    }
}