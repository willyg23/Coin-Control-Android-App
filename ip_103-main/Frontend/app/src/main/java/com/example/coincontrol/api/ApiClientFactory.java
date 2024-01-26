package com.example.coincontrol.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.ZonedDateTime;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientFactory {

    private static Retrofit apiClientSeed;

    public static Gson gson = new GsonBuilder()
            .serializeNulls()
            .registerTypeAdapter(ZonedDateTime.class, new TypeAdapter<ZonedDateTime>() {
                @Override
                public void write(JsonWriter out, ZonedDateTime value) throws IOException {
                    out.value(value.toString());
                }

                @Override
                public ZonedDateTime read(JsonReader in) throws IOException {
                    return ZonedDateTime.parse(in.nextString());
                }
            })
            .setLenient()
            .create();

    public static Retrofit GetApiClientSeed() {

        if (apiClientSeed == null) {
            apiClientSeed = new Retrofit.Builder()
                    .baseUrl("http://coms-309-009.class.las.iastate.edu:8080")
//                    .baseUrl("https://b2d2972d-ea47-466e-b111-29ce2fbf178b.mock.pstmn.io")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return apiClientSeed;
    }

    public static UserApi GetUserApi(){
        return GetApiClientSeed().create(UserApi.class);
    }
    public static GoalsApi GetGoalsApi(){
        return GetApiClientSeed().create(GoalsApi.class);
    }

    public static AdminApi GetAdminApi(){
        return GetApiClientSeed().create(AdminApi.class);
    }
    public static AdvisorApi GetAdvisorApi(){return GetApiClientSeed().create(AdvisorApi.class);}
}

