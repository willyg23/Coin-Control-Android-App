package com.example.coincontrol;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coincontrol.api.ApiClientFactory;
import com.example.coincontrol.api.YearlyReportApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YearlyReport extends Fragment {

    private TextView initialBalanceTxt;
    private TextView desiredBalanceTxt;
    private TextView totalExpenses;
    private TextView totalSavings;
    private int userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_yearly_report, container, false);

        Main main = (Main) getActivity();
        userID = main.getUserIDFromIntent();

        initialBalanceTxt = myView.findViewById(R.id.initialBalanceTxtRP);
        desiredBalanceTxt = myView.findViewById(R.id.desiredBalanceTxtRP);
        totalExpenses = myView.findViewById(R.id.totalExpensesTxtRP);
        totalSavings = myView.findViewById(R.id.totalSavingsTxtRP);

        YearlyReportApi yearlyReportApi = ApiClientFactory.GetApiClientSeed().create(YearlyReportApi.class);

        Call<com.example.coincontrol.model.YearlyReport> call = yearlyReportApi.GetUserReportByID(userID);

        call.enqueue(new Callback<com.example.coincontrol.model.YearlyReport>() {
            @Override
            public void onResponse(Call<com.example.coincontrol.model.YearlyReport> call, Response<com.example.coincontrol.model.YearlyReport> response)
            {
                if (response.isSuccessful())
                {
                    com.example.coincontrol.model.YearlyReport yearlyReport = new com.example.coincontrol.model.YearlyReport();
                    yearlyReport = response.body();
                    initialBalanceTxt.setText("Initial Balance: " + yearlyReport.getInitialBalance());
                    desiredBalanceTxt.setText("Desired Balance: " + yearlyReport.getDesiredBalance());
                    totalExpenses.setText("Total Expenses: " + (Math.round(yearlyReport.getTotalExpenses() * 100.0) / 100.0));
                    totalSavings.setText("Total Savings: " + (Math.round(yearlyReport.getTotalSavings() * 100.0) / 100.0));
                }
                else
                {
                    Toast.makeText(getActivity(), "Response failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<com.example.coincontrol.model.YearlyReport> call, Throwable t)
            {
                Log.e("REPORT GET CALL ERROR", t.getMessage());
                Toast.makeText(getActivity(), "Error Getting Information", Toast.LENGTH_LONG).show();
            }
        });
        return myView;
    }
}
