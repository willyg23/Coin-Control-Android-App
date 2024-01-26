package com.example.coincontrol;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coincontrol.api.AdvisorApi;
import com.example.coincontrol.api.ApiClientFactory;
import com.example.coincontrol.api.ExpenseApi;
import com.example.coincontrol.model.Expense;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdvisorHome extends AppCompatActivity {

    private TextView allowanceAmount;
    private TextView savingsAmount;
    private ProgressBar allowanceBar;
    private ProgressBar savingsBar;
    private String username;

    private Expense expense = new Expense();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advisor_home);

        allowanceAmount = findViewById(R.id.allowanceAmountTxtHP);
        savingsAmount = findViewById(R.id.savingsAmountTxtHP);
        allowanceBar = findViewById(R.id.allowanceBarHP);
        savingsBar = findViewById(R.id.savingsBarHP);

        Intent intent = getIntent();
        if (intent.hasExtra("advisorUsername"))
        {
            username = intent.getStringExtra("advisorUsername");
        }

        AdvisorApi advisorApi = ApiClientFactory.GetApiClientSeed().create(AdvisorApi.class);

        Call<Expense> getcall = advisorApi.GetClientWeeklyExpensesByAdvisorUsername(username);

        getcall.enqueue(new Callback<Expense>() {
            @Override
            public void onResponse(Call<Expense> call, Response<Expense> response) {
                if (response.isSuccessful()) {
                    expense = response.body();
//                    Toast.makeText(getActivity(), "Welcome back!", Toast.LENGTH_LONG).show();
                    allowanceBar.setMax((int) expense.getWeeklyAllowanceGoal());
                    allowanceBar.setProgress((int)expense.getTotalWeeklyExpenses());
                    savingsBar.setMax((int)expense.getWeeklySavingsGoal());
                    savingsBar.setProgress((int)expense.getTotalWeeklySavings());

                    allowanceAmount.setText("$" + (Math.round(expense.getTotalWeeklyExpenses() * 100.0) / 100.0) + "/"
                            + (Math.round(expense.getWeeklyAllowanceGoal() * 100.0) / 100.0));
                    savingsAmount.setText("$" + (Math.round(expense.getTotalWeeklySavings() * 100.0) / 100.0) + "/"
                            + (Math.round(expense.getWeeklySavingsGoal() * 100.0) / 100.0));
                }
            }

            @Override
            public void onFailure(Call<Expense> call, Throwable t) {
                Log.e("GET CALL ERROR", t.getMessage());
                Toast.makeText(AdvisorHome.this, "Error Submitting Expense", Toast.LENGTH_LONG).show();
            }
        });
    }
}