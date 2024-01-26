package com.example.coincontrol.api;

import com.example.coincontrol.model.Goals;
import com.example.coincontrol.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GoalsApi {

    @POST("/goals/post") //drew's server reads this url, and gives the corresponding post call
    Call<Integer> PostGoals(@Body Goals newGoals);
    // have the call type be integer because the call returns a 1 or a 2?? question for drew.

}
