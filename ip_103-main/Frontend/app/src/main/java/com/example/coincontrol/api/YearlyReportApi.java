package com.example.coincontrol.api;

import com.example.coincontrol.model.YearlyReport;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface YearlyReportApi
{
    @GET("/report/{userID}")
    Call<YearlyReport> GetUserReportByID(@Path("userID") int userID);
}
