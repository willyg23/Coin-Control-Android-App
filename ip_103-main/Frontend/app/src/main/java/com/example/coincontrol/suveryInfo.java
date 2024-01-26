package com.example.coincontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coincontrol.api.ApiClientFactory;
import com.example.coincontrol.api.GoalsApi;
import com.example.coincontrol.model.Goals;
import com.example.coincontrol.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class suveryInfo extends AppCompatActivity {

    String[] item = {"26 Weeks", "52 Weeks"};
    AutoCompleteTextView autoCompleteTextView;
    Button sixMonthsButton;
    Button oneYearButton;
    Button submitButton;
    int id;

    User user;
    int timeEstimate;
    EditText monthlyIncome;
    EditText currentBankBalance;
    EditText desiredBankBalance;
    int numberOfMonths = 0;
    private int userID;
    ArrayAdapter<String> adapterItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_info);
        Goals submitGoal = new Goals();
        // where to get all of the values to set the attributes of submitGoal?

        //firstName = findViewById(R.id.firstNameTxtCAP);
        //sets firstName to the value inside the editTextBox firstNameTxtCAP

        monthlyIncome = findViewById(R.id.monthlyIncomeInput);
        currentBankBalance = findViewById(R.id.currentBalanceInput);
        desiredBankBalance = findViewById(R.id.desiredBalanceInput);

        sixMonthsButton = findViewById(R.id.sixMonthsButton);
        oneYearButton = findViewById(R.id.oneYearButton);
        submitButton = findViewById(R.id.submitSurveyInfoButton);


        sixMonthsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfMonths = 26;
                Toast.makeText(suveryInfo.this,"Selected 6 months",Toast.LENGTH_LONG).show();
            }

        });

        oneYearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfMonths = 52;
                Toast.makeText(suveryInfo.this,"Selected 1 year",Toast.LENGTH_LONG).show();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call<Integer> call = GoalsApi.P

                //sets a string equal to to the value inside firstName, which is the value inisde the firstName editTextBox
                //String firstNameTxt = firstName.getText().toString();
                double monthlyIncomeInput = Double.parseDouble(monthlyIncome.getText().toString());
                double currentBankBalanceInput = Double.parseDouble(currentBankBalance.getText().toString());
                double desiredBankBalanceInput = Double.parseDouble(desiredBankBalance.getText().toString());

                Goals goalForCall = new Goals();
                goalForCall.setTimeEstimate(numberOfMonths);
                goalForCall.setMonthlyIncome(monthlyIncomeInput);
                goalForCall.setCurrentBankBalance(currentBankBalanceInput);
                goalForCall.setDesiredBankBalance(desiredBankBalanceInput);

                //Call<Integer> call = ApiClientFactory.GetUserApi().PostRegisterUser(user);
                GoalsApi goalsApi = ApiClientFactory.GetApiClientSeed().create(GoalsApi.class);
                Call<Integer> call = goalsApi.PostGoals(goalForCall);
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if(response.isSuccessful()){
                            if(response.body() >= 1)
                            {
                                userID = response.body();
                                Toast.makeText(suveryInfo.this,"Submission Successful!",Toast.LENGTH_LONG).show();

                                Intent intent2 = new Intent(suveryInfo.this, Main.class);
                                intent2.putExtra("userID2", Integer.toString(userID));
                                startActivity(intent2);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t)
                    {
                        Toast.makeText(suveryInfo.this,"Error in submitting",Toast.LENGTH_LONG).show();
                        Log.e("SURVEY INFO", t.getMessage());
                    }
                });
            }
        });
    }


}