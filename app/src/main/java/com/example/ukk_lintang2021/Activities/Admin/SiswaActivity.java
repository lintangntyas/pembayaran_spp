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
import com.example.ukk_lintang2021.Model.Siswa.ResponseSiswa;
import com.example.ukk_lintang2021.Model.Siswa.SiswaResultItem;
import com.example.ukk_lintang2021.Network.ApiClient;
import com.example.ukk_lintang2021.Network.ApiInterface;
import com.example.ukk_lintang2021.R;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SiswaActivity extends AppCompatActivity {
    //we'll have several instance fields
    private MaterialAutoCompleteTextView nisnTxt, nisTxt, namaTxt, idkelasTxt, alamatTxt, notelpTxt, idsppTxt;
    private TextView headerTxt;
    private ProgressBar mProgressBar;
    private String nisn = null;
    private SiswaResultItem receivedSiswaResultItem;
    private Context c = SiswaActivity.this;

    /**
     * Let's reference our widgets
     */
    private void initializeWidgets() {
        mProgressBar = findViewById(R.id.mProgressBarSave);
        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.GONE);

        headerTxt = findViewById(R.id.headerTxt);
        nisnTxt = findViewById(R.id.ids_nisn);
        nisTxt = findViewById(R.id.ids_nis);
        namaTxt = findViewById(R.id.ids_nama);
        idkelasTxt = findViewById(R.id.ids_kelas);
        alamatTxt = findViewById(R.id.ids_alamat);
        notelpTxt = findViewById(R.id.ids_notelp);
        idsppTxt = findViewById(R.id.ids_spp);
    }

    private void insertData() {
        String nis, nama, idkelas, alamat, notelp, idspp;
        if (ApiClient.validatesiswa(nisTxt, namaTxt, idkelasTxt, alamatTxt, notelpTxt, idsppTxt)) {
            nis = nisTxt.getText().toString();
            nama = namaTxt.getText().toString();
            idkelas = idkelasTxt.getText().toString();
            alamat = alamatTxt.getText().toString();
            notelp = notelpTxt.getText().toString();
            idspp = idsppTxt.getText().toString();

            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponseSiswa> insertDataSiswa = api.insertDataSiswa("INSERT", nis, nama, idkelas, alamat, notelp, idspp);

            ApiClient.showProgressBar(mProgressBar);

            insertDataSiswa.enqueue(new Callback<ResponseSiswa>() {
                @Override
                public void onResponse(Call<ResponseSiswa> call, Response<ResponseSiswa> response) {

                    Log.d("RETROFIT", "response : " + response.body().toString());
                    String myResponseCode = response.body().getCode();

                    if (myResponseCode.equals("1")) {
                        ApiClient.show(c, "SUCCESS: \n 1. Data Inserted Successfully. \n 2. ResponseCode: "
                                + myResponseCode);
                        ApiClient.openActivity(c, ListsiswaActivity.class);
                    } else if (myResponseCode.equalsIgnoreCase("2")) {
                        ApiClient.showInfoDialog(SiswaActivity.this, "UNSUCCESSFUL",
                                "However Good Response. \n 1. CONNECTION TO SERVER WAS SUCCESSFUL \n 2. WE"+
                                        " ATTEMPTED POSTING DATA BUT ENCOUNTERED ResponseCode: "+myResponseCode+
                                        " \n 3. Most probably the problem is with your PHP Code.");
                    }else if (myResponseCode.equalsIgnoreCase("3")) {
                        ApiClient.showInfoDialog(SiswaActivity.this, "NO MYSQL CONNECTION","Your PHP Code is unable to connect to mysql database. Make sure you have supplied correct database credentials.");
                    }
                    ApiClient.hideProgressBar(mProgressBar);
                }
                @Override
                public void onFailure(Call<ResponseSiswa> call, Throwable t) {
                    Log.d("RETROFIT", "ERROR: " + t.getMessage());
                    ApiClient.hideProgressBar(mProgressBar);
                    ApiClient.showInfoDialog(SiswaActivity.this, "FAILURE",
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
        String nis, nama, idkelas, alamat, notelp, idspp;
        if (ApiClient.validatesiswa(nisTxt, namaTxt, idkelasTxt, alamatTxt, notelpTxt, idsppTxt)) {
            nis = nisTxt.getText().toString();
            nama = namaTxt.getText().toString();
            idkelas = idkelasTxt.getText().toString();
            alamat = alamatTxt.getText().toString();
            notelp = notelpTxt.getText().toString();
            idspp = idsppTxt.getText().toString();

            ApiClient.showProgressBar(mProgressBar);
            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponseSiswa> updateDatasiswa = api.updateDataSiswa("UPDATE", nisn,  nis, nama, idkelas, alamat, notelp, idspp);
            updateDatasiswa.enqueue(new Callback<ResponseSiswa>() {
                @Override
                public void onResponse(Call<ResponseSiswa> call, Response<ResponseSiswa> response) {
                    Log.d("RETROFIT", "Response: " + response.body().getResult());

                    ApiClient.hideProgressBar(mProgressBar);
                    String myResponseCode = response.body().getCode();

                    if (myResponseCode.equalsIgnoreCase("1")) {
                        ApiClient.show(c, response.body().getMessage());
                        ApiClient.openActivity(c, ListsiswaActivity.class);
                        finish();
                    } else if (myResponseCode.equalsIgnoreCase("2")) {
                        ApiClient.showInfoDialog(SiswaActivity.this, "UNSUCCESSFUL",
                                "Good Response From PHP,"+
                                        "WE ATTEMPTED UPDATING DATA BUT ENCOUNTERED ResponseCode: "+myResponseCode+
                                        " \n 3. Most probably the problem is with your PHP Code.");
                    } else if (myResponseCode.equalsIgnoreCase("3")) {
                        ApiClient.showInfoDialog(SiswaActivity.this, "NO MYSQL CONNECTION",
                                " Your PHP Code"+
                                        " is unable to connect to mysql database. Make sure you have supplied correct"+
                                        " database credentials.");
                    }
                }
                @Override
                public void onFailure(Call<ResponseSiswa> call, Throwable t) {
                    Log.d("RETROFIT", "ERROR THROWN DURING UPDATE: " + t.getMessage());
                    ApiClient.hideProgressBar(mProgressBar);
                    ApiClient.showInfoDialog(SiswaActivity.this, "FAILURE THROWN", "ERROR DURING UPDATE.Here"+
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
        Call<ResponseSiswa> removesiswa = api.removeSiswa("DELETE", nisn);

        ApiClient.showProgressBar(mProgressBar);
        removesiswa.enqueue(new Callback<ResponseSiswa>() {
            @Override
            public void onResponse(Call<ResponseSiswa> call, Response<ResponseSiswa> response) {
                Log.d("RETROFIT", "DELETE RESPONSE: " + response.body());
                ApiClient.hideProgressBar(mProgressBar);
                String myResponseCode = response.body().getCode();

                if (myResponseCode.equalsIgnoreCase("1")) {
                    ApiClient.show(c, response.body().getMessage());
                    ApiClient.openActivity(c, ListsiswaActivity.class);
                    finish();
                } else if (myResponseCode.equalsIgnoreCase("2")) {
                    ApiClient.showInfoDialog(SiswaActivity.this, "UNSUCCESSFUL",
                            "However Good Response. \n 1. CONNECTION TO SERVER WAS SUCCESSFUL"+
                                    " \n 2. WE ATTEMPTED POSTING DATA BUT ENCOUNTERED ResponseCode: "+
                                    myResponseCode+ " \n 3. Most probably the problem is with your PHP Code.");
                }else if (myResponseCode.equalsIgnoreCase("3")) {
                    ApiClient.showInfoDialog(SiswaActivity.this, "NO MYSQL CONNECTION",
                            " Your PHP Code is unable to connect to mysql database. Make sure you have supplied correct database credentials.");
                }
            }
            @Override
            public void onFailure(Call<ResponseSiswa> call, Throwable t) {
                ApiClient.hideProgressBar(mProgressBar);
                Log.d("RETROFIT", "ERROR: " + t.getMessage());
                ApiClient.showInfoDialog(SiswaActivity.this, "FAILURE THROWN", "ERROR during DELETE attempt. Message: " + t.getMessage());
            }
        });
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
        if (receivedSiswaResultItem == null) {
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
                if (receivedSiswaResultItem != null) {
                    updateData();
                } else {
                    ApiClient.show(this, "EDIT ONLY WORKS IN EDITING MODE");
                }
                return true;
            case R.id.deleteMenuItem:
                if (receivedSiswaResultItem != null) {
                    deleteData();
                } else {
                    ApiClient.show(this, "DELETE ONLY WORKS IN EDITING MODE");
                }
                return true;
            case R.id.viewAllMenuItem:
                ApiClient.openActivity(this, ListsiswaActivity.class);
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
        Object o = ApiClient.receiveSiswa(getIntent(), c);
        if (o != null) {
            receivedSiswaResultItem = (SiswaResultItem) o;
            nisn = receivedSiswaResultItem.getNisn();
            nisTxt.setText(receivedSiswaResultItem.getNis());
            namaTxt.setText(receivedSiswaResultItem.getNama());
            idkelasTxt.setText(receivedSiswaResultItem.getIdKelas());
            alamatTxt.setText(receivedSiswaResultItem.getAlamat());
            notelpTxt.setText(receivedSiswaResultItem.getNoTelp());
            idsppTxt.setText(receivedSiswaResultItem.getIdSpp());

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
        setContentView(R.layout.activity_siswa);

        this.initializeWidgets();
    }
}
//end