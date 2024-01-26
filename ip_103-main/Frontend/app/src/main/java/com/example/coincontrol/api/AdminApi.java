package com.example.coincontrol.api;

import com.example.coincontrol.model.Admin;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
//import com.example.coincontrol.model.User;

public interface AdminApi {

    @GET("/admin/allUsers")
    Call<ArrayList<String>> GetAllUserNames();

    @DELETE("/admin/delete/user/{userName}")
    Call<Integer> DeleteUser(@Path("userName") String userName);

    @GET("/admin/all/Advisor")
    Call<ArrayList<String>> GetAllAdvisorUserNames();

    @DELETE("/admin/delete/advisor/{userName}")
    Call<Integer> DeleteAdvisor(@Path("userName") String userName);

    @POST("/admin/login")
    Call<Integer> AdminLogin(@Body Admin admin);
}
