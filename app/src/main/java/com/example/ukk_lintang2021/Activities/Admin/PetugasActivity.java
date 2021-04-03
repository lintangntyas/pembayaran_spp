package com.example.ukk_lintang2021.Activities.Admin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ukk_lintang2021.Network.ApiClient;
import com.example.ukk_lintang2021.Network.ApiInterface;
import com.example.ukk_lintang2021.Model.Petugas.PetugasResultItem;
import com.example.ukk_lintang2021.Model.Petugas.ResponsePetugas;
import com.example.ukk_lintang2021.R;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;

//import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetugasActivity extends AppCompatActivity {
    //we'll have several instance fields
    private MaterialAutoCompleteTextView usernameTxt, passwordTxt, nama_petugasTxt, levelTxt;
    private TextView headerTxt;
    private ProgressBar mProgressBar;
    private String id_petugas = null;
    private PetugasResultItem receivedPetugasResultItem;
    private Context c = PetugasActivity.this;
    ActionBar actionBar;

    /**
     * Let's reference our widgets
     */
    private void initializeWidgets() {
        mProgressBar = findViewById(R.id.mProgressBarSave);
        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.GONE);

        headerTxt = findViewById(R.id.headerTxt);
        usernameTxt = findViewById(R.id.id_username);
        passwordTxt = findViewById(R.id.id_password);
        nama_petugasTxt = findViewById(R.id.id_namapetugas);
        levelTxt = findViewById(R.id.id_level);
    }

    /**
     * The following method will allow us insert data typed in this page into th
     * e database
     */
    private void insertData() {
        String username, password, nama_petugas, level;
        if (ApiClient.validate(usernameTxt, passwordTxt, nama_petugasTxt)) {
            username = usernameTxt.getText().toString();
            password = passwordTxt.getText().toString();
            nama_petugas = nama_petugasTxt.getText().toString();
            level = levelTxt.getText().toString();

            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponsePetugas> insertData = api.insertData("INSERT", username, password, nama_petugas, level);

            ApiClient.showProgressBar(mProgressBar);

            insertData.enqueue(new Callback<ResponsePetugas>() {
                @Override
                public void onResponse(Call<ResponsePetugas> call,
                                       Response<ResponsePetugas> response) {

                    Log.d("RETROFIT", "response : " + response.body().toString());
                    String myResponseCode = response.body().getCode();

                    if (myResponseCode.equals("1")) {
                        ApiClient.show(c, "SUCCESS: \n 1. Data Inserted Successfully. \n 2. ResponseCode: "
                                + myResponseCode);
                        ApiClient.openActivity(c, ListpetugasActivity.class);
                    } else if (myResponseCode.equalsIgnoreCase("2")) {
                        ApiClient.showInfoDialog(PetugasActivity.this, "UNSUCCESSFUL",
                                "However Good Response. \n 1. CONNECTION TO SERVER WAS SUCCESSFUL \n 2. WE" +
                                        " ATTEMPTED POSTING DATA BUT ENCOUNTERED ResponseCode: " + myResponseCode +
                                        " \n 3. Most probably the problem is with your PHP Code.");
                    } else if (myResponseCode.equalsIgnoreCase("3")) {
                        ApiClient.showInfoDialog(PetugasActivity.this, "NO MYSQL CONNECTION", "Your PHP Code is unable to connect to mysql database. Make sure you have supplied correct database credentials.");
                    }
                    ApiClient.hideProgressBar(mProgressBar);
                }

                @Override
                public void onFailure(Call<ResponsePetugas> call, Throwable t) {
                    Log.d("RETROFIT", "ERROR: " + t.getMessage());
                    ApiClient.hideProgressBar(mProgressBar);
                    ApiClient.showInfoDialog(PetugasActivity.this, "FAILURE",
                            "FAILURE THROWN DURING INSERT." +
                                    " ERROR Message: " + t.getMessage());
                }
            });
        }
    }

    /**
     * The following method will allow us update the current scientist's data in the database
     */
    private void updateData() {
        String username, password, nama_petugas, level;
        if (ApiClient.validate(usernameTxt, passwordTxt, nama_petugasTxt)) {
            username = usernameTxt.getText().toString();
            password = passwordTxt.getText().toString();
            nama_petugas = nama_petugasTxt.getText().toString();
            level = levelTxt.getText().toString();

            ApiClient.showProgressBar(mProgressBar);
            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponsePetugas> update = api.updateData("UPDATE", id_petugas, username, password, nama_petugas,
                    level);
            update.enqueue(new Callback<ResponsePetugas>() {
                @Override
                public void onResponse(Call<ResponsePetugas> call, Response<ResponsePetugas> response) {
                    Log.d("RETROFIT", "Response: " + response.body().getResult());

                    ApiClient.hideProgressBar(mProgressBar);
                    String myResponseCode = response.body().getCode();

                    if (myResponseCode.equalsIgnoreCase("1")) {
                        ApiClient.show(c, response.body().getMessage());
                        ApiClient.openActivity(c, ListpetugasActivity.class);
                        finish();
                    } else if (myResponseCode.equalsIgnoreCase("2")) {
                        ApiClient.showInfoDialog(PetugasActivity.this, "UNSUCCESSFUL",
                                "Good Response From PHP," +
                                        "WE ATTEMPTED UPDATING DATA BUT ENCOUNTERED ResponseCode: " + myResponseCode +
                                        " \n 3. Most probably the problem is with your PHP Code.");
                    } else if (myResponseCode.equalsIgnoreCase("3")) {
                        ApiClient.showInfoDialog(PetugasActivity.this, "NO MYSQL CONNECTION",
                                " Your PHP Code" +
                                        " is unable to connect to mysql database. Make sure you have supplied correct" +
                                        " database credentials.");
                    }
                }

                @Override
                public void onFailure(Call<ResponsePetugas> call, Throwable t) {
                    Log.d("RETROFIT", "ERROR THROWN DURING UPDATE: " + t.getMessage());
                    ApiClient.hideProgressBar(mProgressBar);
                    ApiClient.showInfoDialog(PetugasActivity.this, "FAILURE THROWN", "ERROR DURING UPDATE.Here" +
                            " is the Error: " + t.getMessage());
                }
            });
        }
    }

    /**
     * The following method will allow us delete data from database
     */
    private void deleteData() {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponsePetugas> del = api.remove("DELETE", id_petugas);

        ApiClient.showProgressBar(mProgressBar);
        del.enqueue(new Callback<ResponsePetugas>() {
            @Override
            public void onResponse(Call<ResponsePetugas> call, Response<ResponsePetugas> response) {
                Log.d("RETROFIT", "DELETE RESPONSE: " + response.body());
                ApiClient.hideProgressBar(mProgressBar);
                String myResponseCode = response.body().getCode();

                if (myResponseCode.equalsIgnoreCase("1")) {
                    ApiClient.show(c, response.body().getMessage());
                    ApiClient.openActivity(c, ListpetugasActivity.class);
                    finish();
                } else if (myResponseCode.equalsIgnoreCase("2")) {
                    ApiClient.showInfoDialog(PetugasActivity.this, "UNSUCCESSFUL",
                            "However Good Response. \n 1. CONNECTION TO SERVER WAS SUCCESSFUL" +
                                    " \n 2. WE ATTEMPTED POSTING DATA BUT ENCOUNTERED ResponseCode: " +
                                    myResponseCode + " \n 3. Most probably the problem is with your PHP Code.");
                } else if (myResponseCode.equalsIgnoreCase("3")) {
                    ApiClient.showInfoDialog(PetugasActivity.this, "NO MYSQL CONNECTION",
                            " Your PHP Code is unable to connect to mysql database. Make sure you have supplied correct database credentials.");
                }
            }

            @Override
            public void onFailure(Call<ResponsePetugas> call, Throwable t) {
                ApiClient.hideProgressBar(mProgressBar);
                Log.d("RETROFIT", "ERROR: " + t.getMessage());
                ApiClient.showInfoDialog(PetugasActivity.this, "FAILURE THROWN", "ERROR during DELETE attempt. Message: " + t.getMessage());
            }
        });
    }

    /**
     * Show selected star in our edittext
     */
    private void showSelectedStarInEditText() {
        levelTxt.setOnClickListener(v -> ApiClient.selectlevel(c, levelTxt));
    }

    /**
     * When our back button is pressed
     */
    @Override
    public void onBackPressed() {
        ApiClient.showInfoDialog(this, "Warning", "Are you sure you want to exit?");
    }

    /**
     * Let's inflate our menu based on the role this page has been opened for.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (receivedPetugasResultItem == null) {
            getMenuInflater().inflate(R.menu.newpetugas_item_menu, menu);
        } else {
            getMenuInflater().inflate(R.menu.editpetugas_item_menu, menu);
//            headerTxt.setText("Edit Existing Data petugas");
        }
        return true;
    }

    /**
     * Let's listen to menu action events and perform appropriate function
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insertMenuItem:
                insertData();
                return true;
            case R.id.editMenuItem:
                if (receivedPetugasResultItem != null) {
                    updateData();
                } else {
                    ApiClient.show(this, "EDIT ONLY WORKS IN EDITING MODE");
                }
                return true;
            case R.id.deleteMenuItem:
                if (receivedPetugasResultItem != null) {
                    deleteData();
                } else {
                    ApiClient.show(this, "DELETE ONLY WORKS IN EDITING MODE");
                }
                return true;
            case R.id.viewAllMenuItem:
                ApiClient.openActivity(this, ListpetugasActivity.class);
                finish();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Object o = ApiClient.receivePetugas(getIntent(), c);
        if (o != null) {
            receivedPetugasResultItem = (PetugasResultItem) o;
            id_petugas = receivedPetugasResultItem.getIdPetugas();
            usernameTxt.setText(receivedPetugasResultItem.getUsername());
            passwordTxt.setText(receivedPetugasResultItem.getPassword());
            nama_petugasTxt.setText(receivedPetugasResultItem.getNamaPetugas());
            levelTxt.setText(receivedPetugasResultItem.getLevel());
        }
    }

    /**
     * Let's override our onCreate() method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petugas);

        actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Data Petugas");

        this.initializeWidgets();
        this.showSelectedStarInEditText();
    }

}
//end