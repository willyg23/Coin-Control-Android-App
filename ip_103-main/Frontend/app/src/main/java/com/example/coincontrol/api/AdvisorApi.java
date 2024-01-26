package com.example.coincontrol.api;

import com.example.coincontrol.model.Advisor;
import com.example.coincontrol.model.Expense;
import com.example.coincontrol.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AdvisorApi
{
    @GET("/advisor/user/{username}")
    Call<Expense> GetClientWeeklyExpensesByAdvisorUsername(@Path("username") String username);

    @GET("/findAdvisorName/{username}")
    Call<User> GetAdvisorByUsername(@Path("username") String username);

    @POST("/advisor/register")
    Call<Integer> PostAdvisor(@Body Advisor newAdvisor);

    @POST("/advisor/login")
    Call<Integer> PostLoginAdvisor(@Body Advisor newAdvisor);

}
