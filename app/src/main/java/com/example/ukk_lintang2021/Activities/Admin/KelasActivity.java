package com.example.ukk_lintang2021.Activities.Admin;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.example.ukk_lintang2021.Model.Kelas.KelasResultItem;
import com.example.ukk_lintang2021.Model.Kelas.ResponseKelas;
import com.example.ukk_lintang2021.Network.ApiClient;
import com.example.ukk_lintang2021.Network.ApiInterface;
import com.example.ukk_lintang2021.R;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelasActivity extends AppCompatActivity {
    //we'll have several instance fields
    private MaterialAutoCompleteTextView nama_kelasTxt, kompetensi_keahlianTxt;
    private TextView headerTxt;
    private ProgressBar mProgressBar;
    private String id_kelas = null;
    private KelasResultItem receivedKelasResultItem;
    private Context c = KelasActivity.this;

    /**
     * Let's reference our widgets
     */
    private void initializeWidgets() {
        mProgressBar = findViewById(R.id.mProgressBarSave);
        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.GONE);

        headerTxt = findViewById(R.id.headerTxt);
        nama_kelasTxt = findViewById(R.id.id_kelas);
        kompetensi_keahlianTxt = findViewById(R.id.id_kompetensi);
    }

    private void insertData() {
        String nama_kelas, kompetensi_keahlian;
        if (ApiClient.validatekelas(nama_kelasTxt, kompetensi_keahlianTxt)) {
            nama_kelas = nama_kelasTxt.getText().toString();
            kompetensi_keahlian = kompetensi_keahlianTxt.getText().toString();

            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponseKelas> insertDatakelas = api.insertDataKelas("INSERT", nama_kelas, kompetensi_keahlian);

            ApiClient.showProgressBar(mProgressBar);

            insertDatakelas.enqueue(new Callback<ResponseKelas>() {
                @Override
                public void onResponse(Call<ResponseKelas> call, Response<ResponseKelas> response) {

                    Log.d("RETROFIT", "response : " + response.body().toString());
                    String myResponseCode = response.body().getCode();

                    if (myResponseCode.equals("1")) {
                        ApiClient.show(c, "SUCCESS: \n 1. Data Inserted Successfully. \n 2. ResponseCode: "
                                + myResponseCode);
                        ApiClient.openActivity(c, ListkelasActivity.class);
                    } else if (myResponseCode.equalsIgnoreCase("2")) {
                        ApiClient.showInfoDialog(KelasActivity.this, "UNSUCCESSFUL",
                                "However Good Response. \n 1. CONNECTION TO SERVER WAS SUCCESSFUL \n 2. WE"+
                                        " ATTEMPTED POSTING DATA BUT ENCOUNTERED ResponseCode: "+myResponseCode+
                                        " \n 3. Most probably the problem is with your PHP Code.");
                    }else if (myResponseCode.equalsIgnoreCase("3")) {
                        ApiClient.showInfoDialog(KelasActivity.this, "NO MYSQL CONNECTION","Your PHP Code is unable to connect to mysql database. Make sure you have supplied correct database credentials.");
                    }
                    ApiClient.hideProgressBar(mProgressBar);
                }
                @Override
                public void onFailure(Call<ResponseKelas> call, Throwable t) {
                    Log.d("RETROFIT", "ERROR: " + t.getMessage());
                    ApiClient.hideProgressBar(mProgressBar);
                    ApiClient.showInfoDialog(KelasActivity.this, "FAILURE",
                            "FAILURE THROWN DURING INSERT."+
                                    " ERROR Message: " + t.getMessage());
                }
            });
        }
    }
    /**
     * The following method will allow us update the current scientist's data in the database
     */
    private void updateData() {
        String nama_kelas, kompetensi_keahlian;
        if (ApiClient.validatekelas(nama_kelasTxt, kompetensi_keahlianTxt)) {
            nama_kelas = nama_kelasTxt.getText().toString();
            kompetensi_keahlian = kompetensi_keahlianTxt.getText().toString();

            ApiClient.showProgressBar(mProgressBar);
            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponseKelas> updateDatakelas = api.updateDataKelas("UPDATE", id_kelas, nama_kelas, kompetensi_keahlian);
            updateDatakelas.enqueue(new Callback<ResponseKelas>() {
                @Override
                public void onResponse(Call<ResponseKelas> call, Response<ResponseKelas> response) {
                    Log.d("RETROFIT", "Response: " + response.body().getResult());

                    ApiClient.hideProgressBar(mProgressBar);
                    String myResponseCode = response.body().getCode();

                    if (myResponseCode.equalsIgnoreCase("1")) {
                        ApiClient.show(c, response.body().getMessage());
                        ApiClient.openActivity(c, ListkelasActivity.class);
                        finish();
                    } else if (myResponseCode.equalsIgnoreCase("2")) {
                        ApiClient.showInfoDialog(KelasActivity.this, "UNSUCCESSFUL",
                                "Good Response From PHP,"+
                                        "WE ATTEMPTED UPDATING DATA BUT ENCOUNTERED ResponseCode: "+myResponseCode+
                                        " \n 3. Most probably the problem is with your PHP Code.");
                    } else if (myResponseCode.equalsIgnoreCase("3")) {
                        ApiClient.showInfoDialog(KelasActivity.this, "NO MYSQL CONNECTION",
                                " Your PHP Code"+
                                        " is unable to connect to mysql database. Make sure you have supplied correct"+
                                        " database credentials.");
                    }
                }
                @Override
                public void onFailure(Call<ResponseKelas> call, Throwable t) {
                    Log.d("RETROFIT", "ERROR THROWN DURING UPDATE: " + t.getMessage());
                    ApiClient.hideProgressBar(mProgressBar);
                    ApiClient.showInfoDialog(KelasActivity.this, "FAILURE THROWN", "ERROR DURING UPDATE.Here"+
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
        Call<ResponseKelas> removekelas = api.removekelas("DELETE", id_kelas);

        ApiClient.showProgressBar(mProgressBar);
        removekelas.enqueue(new Callback<ResponseKelas>() {
            @Override
            public void onResponse(Call<ResponseKelas> call, Response<ResponseKelas> response) {
                Log.d("RETROFIT", "DELETE RESPONSE: " + response.body());
                ApiClient.hideProgressBar(mProgressBar);
                String myResponseCode = response.body().getCode();

                if (myResponseCode.equalsIgnoreCase("1")) {
                    ApiClient.show(c, response.body().getMessage());
                    ApiClient.openActivity(c, ListkelasActivity.class);
                    finish();
                } else if (myResponseCode.equalsIgnoreCase("2")) {
                    ApiClient.showInfoDialog(KelasActivity.this, "UNSUCCESSFUL",
                            "However Good Response. \n 1. CONNECTION TO SERVER WAS SUCCESSFUL"+
                                    " \n 2. WE ATTEMPTED POSTING DATA BUT ENCOUNTERED ResponseCode: "+
                                    myResponseCode+ " \n 3. Most probably the problem is with your PHP Code.");
                }else if (myResponseCode.equalsIgnoreCase("3")) {
                    ApiClient.showInfoDialog(KelasActivity.this, "NO MYSQL CONNECTION",
                            " Your PHP Code is unable to connect to mysql database. Make sure you have supplied correct database credentials.");
                }
            }
            @Override
            public void onFailure(Call<ResponseKelas> call, Throwable t) {
                ApiClient.hideProgressBar(mProgressBar);
                Log.d("RETROFIT", "ERROR: " + t.getMessage());
                ApiClient.showInfoDialog(KelasActivity.this, "FAILURE THROWN", "ERROR during DELETE attempt. Message: " + t.getMessage());
            }
        });
    }
    /**
     * Show selected star in our edittext
     */
    private void showSelectedStarInEditText() {
        kompetensi_keahlianTxt.setOnClickListener(v -> ApiClient.selectkelas(c, kompetensi_keahlianTxt));
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
        if (receivedKelasResultItem == null) {
            getMenuInflater().inflate(R.menu.newkelas_item_menu, menu);
//            headerTxt.setText("Add New Scientist");
        } else {
            getMenuInflater().inflate(R.menu.editkelas_item_menu, menu);
//            headerTxt.setText("Edit Existing Scientist");
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
                if (receivedKelasResultItem != null) {
                    updateData();
                } else {
                    ApiClient.show(this, "EDIT ONLY WORKS IN EDITING MODE");
                }
                return true;
            case R.id.deleteMenuItem:
                if (receivedKelasResultItem != null) {
                    deleteData();
                } else {
                    ApiClient.show(this, "DELETE ONLY WORKS IN EDITING MODE");
                }
                return true;
            case R.id.viewAllMenuItem:
                ApiClient.openActivity(this, ListkelasActivity.class);
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
        Object o = ApiClient.receiveKelas(getIntent(), c);
        if (o != null) {
            receivedKelasResultItem = (KelasResultItem) o;
            id_kelas = receivedKelasResultItem.getIdKelas();
            nama_kelasTxt.setText(receivedKelasResultItem.getNamaKelas());
            kompetensi_keahlianTxt.setText(receivedKelasResultItem.getKompetensiKeahlian());

        } else {
            //Utils.show(c,"Received Scientist is Null");
        }
    }
    /**
     * Let's override our onCreate() method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelas);

        this.initializeWidgets();
        this.showSelectedStarInEditText();
    }
}
//end