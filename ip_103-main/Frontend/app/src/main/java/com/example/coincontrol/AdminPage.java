package com.example.coincontrol;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coincontrol.api.AdminApi;
import com.example.coincontrol.api.ApiClientFactory;



import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminPage extends AppCompatActivity
{
    //private Spinner listOfUsersSpinner;
    //private List<String> listOfUsers = new ArrayList<>();
    //private Response<ArrayList<String>> listOfUsers = new Response<ArrayList<>()>;
    //private RecyclerView reportRecyclerView;


    /*
    //private ReportAdapter reportAdapter;
     private List<Report> reports;
     reportRecyclerView.setAdapter(reportAdapter);
     reportRecyclerView.setLayoutManager(new LinearLayoutManager(this));
     reportAdapter.setReports(reports);
     reportAdapter.notifyDataSetChanged();
     */
    private ArrayList<String> listOfUsers;
    //private Response<String> listOfUsers = new ArrayList<>();
    String displayListOfUsers;
    private ArrayList<String> listOfAdvisors;
    String displayListOfAdvisors;
    TextView listOfUsersTextView;
    TextView listOfAdivsorsTextView;
    TextView drewIdeaDisplay;
    Button deleteUserButton;
    Button showUsersButton;
    Button showAdvisorsButton;
    EditText usernameOfUserToDelete;
    EditText usernameOfAdvisorToDelete;
    Button deleteAdvisorButton;
    Button kareemLogOutButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        //

        displayListOfUsers = "";
        listOfAdvisors = new ArrayList<>();
        displayListOfAdvisors = "";
        listOfUsersTextView = findViewById(R.id.listOfUserstextView);
        listOfAdivsorsTextView = findViewById(R.id.listOfAdvisorsTextView);
        deleteUserButton = findViewById(R.id.deleteUserButton);
        showUsersButton = findViewById(R.id.showUsersButton);
        showAdvisorsButton = findViewById(R.id.showAdvisorsButton);
        usernameOfUserToDelete = findViewById(R.id.usernameOfUserToDelete);
        usernameOfAdvisorToDelete = findViewById(R.id.usernameOfAdvisorToDelete);
        deleteAdvisorButton = findViewById(R.id.deleteAdvisorButton);
        kareemLogOutButton = findViewById(R.id.kareemLogOutButton);
        drewIdeaDisplay = findViewById(R.id.drewIdeaDisplay);


        //
        AdminApi adminApi = ApiClientFactory.GetApiClientSeed().create(AdminApi.class);

        Call<ArrayList<String>> getAllUserNamesCall = adminApi.GetAllUserNames();
        getAllUserNamesCall.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                if(response.isSuccessful()) {
                    listOfUsers = response.body(); // set listOfUsers = the response, which should be a list
                    // what is in the response.body?
                    Toast.makeText(AdminPage.this,"First User: " + listOfUsers.get(0),Toast.LENGTH_LONG).show();
                    for(int i = 0; i < listOfUsers.size() - 1; i++){
//                        displayListOfUsers += listOfUsers.get(i);
//                        displayListOfUsers += "/n";

                        //displayListOfUsers = displayListOfUsers + listOfUsers.get(i);
                        //displayListOfUsers = displayListOfUsers + "/n";
                        System.out.println(listOfUsers.get(i));
                    }
//                    for (String str : listOfUsers) {
//                        displayListOfUsers.append(str).append(", ");
//                    }
                    listOfUsersTextView.setText(displayListOfUsers);
                    drewIdeaDisplay.setText(listOfUsers.get(0));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                Toast.makeText(AdminPage.this,"users recieved not successful!",Toast.LENGTH_LONG).show();
            }
        });

        // Here I would make a call to get a list of all the advisors. Same as before but with advisors instead of users.
        // We didn't get that call done, but here is where it'd be.

        Call<ArrayList<String>> getAllAdvisorUserNamesCall = adminApi.GetAllAdvisorUserNames();
        getAllAdvisorUserNamesCall.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                listOfAdvisors = response.body(); // set listOfUsers = the response, which should be a list
                // what is in the response.body?
                for(int i = 0; i < listOfAdvisors.size() - 1; i++){
                    displayListOfAdvisors += listOfAdvisors.get(i);
                    displayListOfAdvisors += "/n";
                }
                listOfAdivsorsTextView.setText(displayListOfAdvisors);
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                Toast.makeText(AdminPage.this,"advisors recieved not successful!",Toast.LENGTH_LONG).show();
            }
        });

        /*
        String listOfUsers
        for loop that adds each String element from listOfUsers to the string, and then \n
        put that hoe in a textView

         */

        showUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listOfUsersTextView.getVisibility() == View.VISIBLE) {
                    listOfUsersTextView.setVisibility(View.GONE); // Hide the TextView
                    deleteUserButton.setVisibility(View.GONE);
                    usernameOfUserToDelete.setVisibility(View.GONE);
                } else {
                    listOfUsersTextView.setVisibility(View.VISIBLE); // Show the TextView

                    deleteUserButton.setVisibility(View.VISIBLE);
                    usernameOfUserToDelete.setVisibility(View.VISIBLE);

                    //hide advisor stuff if it's currently up
                    listOfAdivsorsTextView.setVisibility(View.GONE);
                    usernameOfAdvisorToDelete.setVisibility(View.GONE);
                    deleteAdvisorButton.setVisibility(View.GONE);

                }
            }
        });

        showAdvisorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listOfAdivsorsTextView.getVisibility() == View.VISIBLE) {
                    listOfAdivsorsTextView.setVisibility(View.GONE); // Hide the TextView
                    deleteAdvisorButton.setVisibility(View.GONE);
                    usernameOfAdvisorToDelete.setVisibility(View.GONE);

                } else {
                    listOfAdivsorsTextView.setVisibility(View.VISIBLE); // Show the TextView

                    deleteAdvisorButton.setVisibility(View.VISIBLE);
                    usernameOfAdvisorToDelete.setVisibility(View.VISIBLE);

                    //hide user stuff if it's currently up
                    listOfUsersTextView.setVisibility(View.GONE);
                    deleteUserButton.setVisibility(View.GONE);
                    usernameOfUserToDelete.setVisibility(View.GONE);
                }

            }
        });

        deleteAdvisorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Integer> deleteAdminCall = adminApi.DeleteAdvisor(usernameOfAdvisorToDelete.getText().toString());
                deleteAdminCall.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.isSuccessful()) {
                            //if (response.body() == 100) {
                                Toast.makeText(AdminPage.this, "Deletion of user successful!", Toast.LENGTH_LONG).show();
                            //}
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable throwable) {
                        Toast.makeText(AdminPage.this, "Deletion of user not successful!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        deleteUserButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                        Call<Integer> deleteUserCall = adminApi.DeleteUser(usernameOfUserToDelete.getText().toString());
                        deleteUserCall.enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                if (response.isSuccessful()) {
                                    if (response.body() == 100) {
                                        Toast.makeText(AdminPage.this, "Deletion of user successful!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                Toast.makeText(AdminPage.this, "Deletion of user not successful!", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
        });

        kareemLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPage.this, Login.class);
                startActivity(intent);

            }
        });

    }

}