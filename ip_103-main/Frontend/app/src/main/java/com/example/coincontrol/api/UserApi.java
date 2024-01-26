package com.example.coincontrol.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import com.example.coincontrol.model.User;


public interface UserApi
{
    @GET("/currentUser/infoFrom/{username}")
    Call<User> GetUserByUsername(@Path("username") String username);

    @GET("/finduser/{userID}")
    Call<User> GetUserByID(@Path("userID") int userID);
    // use this call in accountInfo^ called onCreate

    @GET("/user/{userID}")
    Call<String> GetUsernameByID(@Path("userID") int userID);

    @POST("/user/register")
    Call<Integer> PostRegisterUser(@Body User newUser);

    @POST("/user/login")
    Call<Integer> PostLoginUser(@Body User newUser);

    @PUT("/new/{newUsername}/username/{oldUsername}")
    Call<Integer> ChangeUsername(@Path("newUsername") String newUsername, @Path("oldUsername") String oldUsername); //modify the body
    // either make a get call for id, or add a parameter for oldUserName and newUserName
    // choose whether our code usually needs to know id or userName

    @PUT("/new/password/{password}/{username}")
    Call<Integer> ChangePassword(@Path("username")String username, @Path("password")String password);

    @PUT("/new/email/{email}/{username}")
    Call<Integer> ChangeEmail(@Path("username")String username, @Path("email")String email);

//    @POST("/user/logout/{username}")
//    Call<Integer> LogOut(@Body String username);

    //    @GET("/currentUser/infoFrom/{username}")
    //    Call<Integer> GetUserByUsername(@Body String username);





}
