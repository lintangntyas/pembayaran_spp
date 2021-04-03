package com.example.ukk_lintang2021.Activities.Admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.helper.DateTimePickerEditText;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.example.ukk_lintang2021.Network.ApiClient;
import com.example.ukk_lintang2021.Network.ApiInterface;
import com.example.ukk_lintang2021.Model.Pembayaran.PembayaranResultItem;
import com.example.ukk_lintang2021.Model.Pembayaran.ResponsePembayaran;
import com.example.ukk_lintang2021.R;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PembayaranActivity extends AppCompatActivity {
    //we'll have several instance fields
    private MaterialAutoCompleteTextView id_petugasTxt, id_nisnTxt, bulanTxt, tahunTxt, id_sppTxt, jumlahTxt;
    private TextView headerTxt;
    private DateTimePickerEditText tanggalTxt;
    private ProgressBar mProgressBar;
    private String id_pembayaran = null;
    private PembayaranResultItem receivedPembayaranResultItem;
    private Context c = PembayaranActivity.this;
    private ActionBar actionBar;

    /**
     * Let's reference our widgets
     */
    private void initializeWidgets() {
        mProgressBar = findViewById(R.id.mProgressBarSave);
        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.GONE);

        headerTxt = findViewById(R.id.headerTxt);

        id_petugasTxt = findViewById(R.id.id_petugas);
        id_nisnTxt = findViewById(R.id.id_nisn);
        bulanTxt = findViewById(R.id.id_bulan);
        tahunTxt = findViewById(R.id.id_tahun);
        id_sppTxt = findViewById(R.id.id_spp);
        jumlahTxt = findViewById(R.id.id_jumlah);

        tanggalTxt = findViewById(R.id.id_tanggal);
        tanggalTxt.setFormat(ApiClient.DATE_FORMAT);
    }
    /**
     * The following method will allow us insert data typed in this page into th
     * e database
     */
    private void insertData() {
        String id_petugas, nisn, tanggal, bulan, tahun, id_spp, jumlah;
        if (ApiClient.validatepembayaran(id_petugasTxt, id_nisnTxt, bulanTxt, tahunTxt, id_sppTxt, jumlahTxt)) {
            id_petugas = id_petugasTxt.getText().toString();
            nisn = id_nisnTxt.getText().toString();
            bulan = bulanTxt.getText().toString();
            tahun = tahunTxt.getText().toString();
            id_spp = id_sppTxt.getText().toString();
            jumlah = jumlahTxt.getText().toString();

            if (tanggalTxt.getDate() != null) {
                tanggal = tanggalTxt.getDate().toString();
            } else {
                tanggalTxt.setError("Invalid Date");
                tanggalTxt.requestFocus();
                return;
            }

            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponsePembayaran> insertDataPembayaran = api.insertDataPembayaran(
                    "INSERT", id_petugas, nisn, tanggal, bulan, tahun, id_spp, jumlah);

            ApiClient.showProgressBar(mProgressBar);

            insertDataPembayaran.enqueue(new Callback<ResponsePembayaran>() {
                @Override
                public void onResponse(Call<ResponsePembayaran> call,
                                       Response<ResponsePembayaran> response) {

                    Log.d("RETROFIT", "response : " + response.body().toString());
                    String myResponseCode = response.body().getCode();

                    if (myResponseCode.equals("1")) {
                        ApiClient.show(c, "SUCCESS: \n 1. Data Inserted Successfully. \n 2. ResponseCode: "
                                + myResponseCode);
                        ApiClient.openActivity(c, ListpembayaranActivity.class);
                    } else if (myResponseCode.equalsIgnoreCase("2")) {
                        ApiClient.showInfoDialog(PembayaranActivity.this, "UNSUCCESSFUL",
                                "However Good Response. \n 1. CONNECTION TO SERVER WAS SUCCESSFUL \n 2. WE"+
                                        " ATTEMPTED POSTING DATA BUT ENCOUNTERED ResponseCode: "+myResponseCode+
                                        " \n 3. Most probably the problem is with your PHP Code.");
                    }else if (myResponseCode.equalsIgnoreCase("3")) {
                        ApiClient.showInfoDialog(PembayaranActivity.this, "NO MYSQL CONNECTION","Your PHP Code is unable to connect to mysql database. Make sure you have supplied correct database credentials.");
                    }
                    ApiClient.hideProgressBar(mProgressBar);
                }
                @Override
                public void onFailure(Call<ResponsePembayaran> call, Throwable t) {
                    Log.d("RETROFIT", "ERROR: " + t.getMessage());
                    ApiClient.hideProgressBar(mProgressBar);
                    ApiClient.showInfoDialog(PembayaranActivity.this, "FAILURE",
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
        String id_petugas, nisn, tanggal, bulan, tahun, id_spp, jumlah;
        if (ApiClient.validatepembayaran(id_petugasTxt, id_nisnTxt, bulanTxt, tahunTxt, id_sppTxt, jumlahTxt)) {
            id_petugas = id_petugasTxt.getText().toString();
            nisn = id_nisnTxt.getText().toString();
            bulan = bulanTxt.getText().toString();
            tahun = tahunTxt.getText().toString();
            id_spp = id_sppTxt.getText().toString();
            jumlah = jumlahTxt.getText().toString();

            if (tanggalTxt.getDate() != null) {
                tanggal = tanggalTxt.getDate().toString();
            } else {
                tanggalTxt.setError("Invalid Date");
                tanggalTxt.requestFocus();
                return;
            }

            ApiClient.showProgressBar(mProgressBar);
            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponsePembayaran> updateDataPembayaran = api.updateDataPembayaran(
                    "UPDATE", id_pembayaran, id_petugas, nisn, tanggal, bulan, tahun, id_spp, jumlah);
            updateDataPembayaran.enqueue(new Callback<ResponsePembayaran>() {
                @Override
                public void onResponse(Call<ResponsePembayaran> call, Response<ResponsePembayaran> response) {
                    Log.d("RETROFIT", "Response: " + response.body().getResult());

                    ApiClient.hideProgressBar(mProgressBar);
                    String myResponseCode = response.body().getCode();

                    if (myResponseCode.equalsIgnoreCase("1")) {
                        ApiClient.show(c, response.body().getMessage());
                        ApiClient.openActivity(c, ListpembayaranActivity.class);
                        finish();
                    } else if (myResponseCode.equalsIgnoreCase("2")) {
                        ApiClient.showInfoDialog(PembayaranActivity.this, "UNSUCCESSFUL",
                                "Good Response From PHP,"+
                                        "WE ATTEMPTED UPDATING DATA BUT ENCOUNTERED ResponseCode: "+myResponseCode+
                                        " \n 3. Most probably the problem is with your PHP Code.");
                    } else if (myResponseCode.equalsIgnoreCase("3")) {
                        ApiClient.showInfoDialog(PembayaranActivity.this, "NO MYSQL CONNECTION",
                                " Your PHP Code"+
                                        " is unable to connect to mysql database. Make sure you have supplied correct"+
                                        " database credentials.");
                    }
                }
                @Override
                public void onFailure(Call<ResponsePembayaran> call, Throwable t) {
                    Log.d("RETROFIT", "ERROR THROWN DURING UPDATE: " + t.getMessage());
                    ApiClient.hideProgressBar(mProgressBar);
                    ApiClient.showInfoDialog(PembayaranActivity.this, "FAILURE THROWN", "ERROR DURING UPDATE.Here"+
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
        Call<ResponsePembayaran> removepembayaran = api.removepembayaran("DELETE", id_pembayaran);

        ApiClient.showProgressBar(mProgressBar);
        removepembayaran.enqueue(new Callback<ResponsePembayaran>() {
            @Override
            public void onResponse(Call<ResponsePembayaran> call, Response<ResponsePembayaran> response) {
                Log.d("RETROFIT", "DELETE RESPONSE: " + response.body());
                ApiClient.hideProgressBar(mProgressBar);
                String myResponseCode = response.body().getCode();

                if (myResponseCode.equalsIgnoreCase("1")) {
                    ApiClient.show(c, response.body().getMessage());
                    ApiClient.openActivity(c, ListpembayaranActivity.class);
                    finish();
                } else if (myResponseCode.equalsIgnoreCase("2")) {
                    ApiClient.showInfoDialog(PembayaranActivity.this, "UNSUCCESSFUL",
                            "However Good Response. \n 1. CONNECTION TO SERVER WAS SUCCESSFUL"+
                                    " \n 2. WE ATTEMPTED POSTING DATA BUT ENCOUNTERED ResponseCode: "+
                                    myResponseCode+ " \n 3. Most probably the problem is with your PHP Code.");
                }else if (myResponseCode.equalsIgnoreCase("3")) {
                    ApiClient.showInfoDialog(PembayaranActivity.this, "NO MYSQL CONNECTION",
                            " Your PHP Code is unable to connect to mysql database. Make sure you have supplied correct database credentials.");
                }
            }
            @Override
            public void onFailure(Call<ResponsePembayaran> call, Throwable t) {
                ApiClient.hideProgressBar(mProgressBar);
                Log.d("RETROFIT", "ERROR: " + t.getMessage());
                ApiClient.showInfoDialog(PembayaranActivity.this, "FAILURE THROWN", "ERROR during DELETE attempt. Message: " + t.getMessage());
            }
        });
    }
    /**
     * Show selected star in our edittext
     */
    private void showSelectedStarInEditText() {
        bulanTxt.setOnClickListener(v -> ApiClient.select_bulan(c, bulanTxt));
        tahunTxt.setOnClickListener(v -> ApiClient.select_tahun(c, tahunTxt));
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
        if (receivedPembayaranResultItem == null) {
            getMenuInflater().inflate(R.menu.newpembayaran_item_menu, menu);
//            headerTxt.setText("Add New Scientist");
        } else {
            getMenuInflater().inflate(R.menu.editpembayaran_item_menu, menu);
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
                if (receivedPembayaranResultItem != null) {
                    updateData();
                } else {
                    ApiClient.show(this, "EDIT ONLY WORKS IN EDITING MODE");
                }
                return true;
            case R.id.deleteMenuItem:
                if (receivedPembayaranResultItem != null) {
                    deleteData();
                } else {
                    ApiClient.show(this, "DELETE ONLY WORKS IN EDITING MODE");
                }
                return true;
            case R.id.viewAllMenuItem:
                ApiClient.openActivity(this, ListpembayaranActivity.class);
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
        Object o = ApiClient.receivePembayaran(getIntent(), c);
        if (o != null) {
            receivedPembayaranResultItem = (PembayaranResultItem) o;
            id_pembayaran = receivedPembayaranResultItem.getIdPembayaran();
            id_petugasTxt.setText(receivedPembayaranResultItem.getIdPetugas());
            id_nisnTxt.setText(receivedPembayaranResultItem.getNisn());
            bulanTxt.setText(receivedPembayaranResultItem.getBulanDibayar());
            tahunTxt.setText(receivedPembayaranResultItem.getTahunDibayar());
            id_sppTxt.setText(receivedPembayaranResultItem.getIdSpp());
            jumlahTxt.setText(receivedPembayaranResultItem.getJumlahBayar());
            Object tanggal = receivedPembayaranResultItem.getTglBayar();
            if (tanggal != null) {
                String t = tanggal.toString();
                tanggalTxt.setDate(ApiClient.giveMeDate(t));
            }
        } else {
            //Utils.show(c,"Received Scientist is Null");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);

        actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Data Pembayaran");

        this.initializeWidgets();
        this.showSelectedStarInEditText();
    }

}
