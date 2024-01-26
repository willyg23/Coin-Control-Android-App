package com.example.coincontrol;

import static java.lang.Math.round;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.coincontrol.api.ApiClientFactory;
import com.example.coincontrol.api.ExpenseApi;
import com.example.coincontrol.model.Expense;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment {

    private Button submitExpenseBtn;
    private EditText insertExpenseTxt;
    private TextView allowanceTxt;
    private TextView savingsTxt;
    private TextView allowanceAmount;
    private TextView savingsAmount;
    private ProgressBar allowanceBar;
    private ProgressBar savingsBar;
//    private String username;
    private int userID;

    private Expense expense = new Expense();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        submitExpenseBtn = view.findViewById(R.id.submitExpenseBtnHP);
        insertExpenseTxt = view.findViewById(R.id.insertExpenseTxtHP);
        allowanceTxt = view.findViewById(R.id.allowanceTxtHP);
        savingsTxt = view.findViewById(R.id.savingsTxtHP);
        allowanceAmount = view.findViewById(R.id.allowanceAmountTxtHP);
        savingsAmount = view.findViewById(R.id.savingsAmountTxtHP);
        allowanceBar = view.findViewById(R.id.allowanceBarHP);
        savingsBar = view.findViewById(R.id.savingsBarHP);

        // Access the data from the main activity
        Main main = (Main) getActivity();
        userID = main.getUserIDFromIntent();

        ExpenseApi expenseApi = ApiClientFactory.GetApiClientSeed().create(ExpenseApi.class);

        Call<Expense> getcall = expenseApi.GetUserExpensesByUsername(userID);

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

                    allowanceAmount.setText("$" + (Math.round(expense.getTotalWeeklyExpenses() * 100.0) / 100.0) + " / $"
                                                + (Math.round(expense.getWeeklyAllowanceGoal() * 100.0) / 100.0));
                    savingsAmount.setText("$" + (Math.round(expense.getTotalWeeklySavings() * 100.0) / 100.0) + " / $"
                            + (Math.round(expense.getWeeklySavingsGoal() * 100.0) / 100.0));
                }
            }

            @Override
            public void onFailure(Call<Expense> call, Throwable t) {
                Log.e("GET CALL ERROR", t.getMessage());
                Toast.makeText(getActivity(), "Error Getting Expense Info", Toast.LENGTH_LONG).show();
            }
        });

        submitExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (insertExpenseTxt.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter a valid number", Toast.LENGTH_LONG).show();
                } else
                {
                    double expenseAmount = Double.parseDouble(insertExpenseTxt.getText().toString());
                    expense.setExpenseAmount(expenseAmount);
                    Call<ArrayList> putcall = expenseApi.UpdateNumber(userID, expense);

                    putcall.enqueue(new Callback<ArrayList>() {
                        @Override
                        public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {
                            if (response.isSuccessful()) {
                                ArrayList<Double> expenseInfo = response.body();
                                if (expenseInfo.get(0) >= expenseAmount) {
                                    Toast.makeText(getActivity(), "Expense submitted!", Toast.LENGTH_LONG).show();
                                    allowanceBar.setProgress((int)Math.round(expenseInfo.get(0)));
                                    allowanceAmount.setText("$" + (Math.round(expenseInfo.get(0) * 100.0) / 100.0) + "/"
                                            + (Math.round(expense.getWeeklyAllowanceGoal() * 100.0) / 100.0));
                                    savingsBar.setProgress((int)Math.round(expenseInfo.get(1)));
                                    savingsAmount.setText("$" + (Math.round(expenseInfo.get(1) * 100.0) / 100.0) + "/"
                                            + (Math.round(expense.getWeeklySavingsGoal() * 100.0) / 100.0));
                                } else {
                                    Toast.makeText(getActivity(), "Error submitting expense", Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList> call, Throwable t) {
                            Log.e("Expense FAIL", t.getMessage());
                            Toast.makeText(getActivity(), "Response failed", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        return view;
    }
}
