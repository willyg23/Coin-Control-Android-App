package com.example.coincontrol.api;

import com.example.coincontrol.model.Expense;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ExpenseApi {

//    @POST("/addExpense")
//    Call<Integer> PostNumber(@Body Expense PostNumber);

    @PUT("/addExpense/{userID}")
    Call<ArrayList> UpdateNumber(@Path("userID") int userID, @Body Expense expense);

    @GET("/calculateGoals/{userID}")
    Call<Expense> GetUserExpensesByUsername(@Path("userID") int userID);
}
