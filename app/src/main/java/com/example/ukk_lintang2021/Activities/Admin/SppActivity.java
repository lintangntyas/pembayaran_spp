package com.example.ukk_lintang2021.Activities.Admin;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.example.ukk_lintang2021.Network.ApiClient;
import com.example.ukk_lintang2021.Network.ApiInterface;
import com.example.ukk_lintang2021.Model.Spp.ResponseSpp;
import com.example.ukk_lintang2021.Model.Spp.SppResultItem;
import com.example.ukk_lintang2021.R;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;

import java.text.NumberFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SppActivity extends AppCompatActivity {

    private MaterialAutoCompleteTextView tahunTxt, nominalTxt;
    private TextView headerTxt;
    private ProgressBar mProgressBar;
    private String id_spp = null;
    private SppResultItem receivedSppResultItem;
    private Context c = SppActivity.this;
    ActionBar actionBar;


    private String formatRupiah(Double number){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        return formatRupiah.format(number);
    }

    private void initializeWidgets() {
        mProgressBar = findViewById(R.id.mProgressBarSave);
        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.GONE);

        headerTxt = findViewById(R.id.headerTxt);
        tahunTxt = findViewById(R.id.id_tahun);
        nominalTxt = findViewById(R.id.id_nominal);

        nominalTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String harga = nominalTxt.getText().toString();
                if (TextUtils.isEmpty(harga)){
                    Toast.makeText(SppActivity.this, "Form tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {
                    String resultRupiah = "Nominal : " + formatRupiah(Double.parseDouble(harga));
                    nominalTxt.setText(resultRupiah);
                }
            }
        });

    }

    private void insertData() {
        String tahun, nominal;
        if (ApiClient.validatespp(tahunTxt, nominalTxt)) {
            tahun = tahunTxt.getText().toString();
            nominal = nominalTxt.getText().toString();

            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponseSpp> insertDataspp = api.insertDataSpp("INSERT", tahun, nominal);

            ApiClient.showProgressBar(mProgressBar);

            insertDataspp.enqueue(new Callback<ResponseSpp>() {
                @Override
                public void onResponse(Call<ResponseSpp> call,
                                       Response<ResponseSpp> response) {

                    Log.d("RETROFIT", "response : " + response.body().toString());
                    String myResponseCode = response.body().getCode();

                    if (myResponseCode.equals("1")) {
                        ApiClient.show(c, "SUCCESS: \n 1. Data Inserted Successfully. \n 2. ResponseCode: "
                                + myResponseCode);
                        ApiClient.openActivity(c, ListsppActivity.class);
                    } else if (myResponseCode.equalsIgnoreCase("2")) {
                        ApiClient.showInfoDialog(SppActivity.this, "UNSUCCESSFUL",
                                "However Good Response. \n 1. CONNECTION TO SERVER WAS SUCCESSFUL \n 2. WE"+
                                        " ATTEMPTED POSTING DATA BUT ENCOUNTERED ResponseCode: "+myResponseCode+
                                        " \n 3. Most probably the problem is with your PHP Code.");
                    }else if (myResponseCode.equalsIgnoreCase("3")) {
                        ApiClient.showInfoDialog(SppActivity.this, "NO MYSQL CONNECTION","Your PHP Code is unable to connect to mysql database. Make sure you have supplied correct database credentials.");
                    }
                    ApiClient.hideProgressBar(mProgressBar);
                }
                @Override
                public void onFailure(Call<ResponseSpp> call, Throwable t) {
                    Log.d("RETROFIT", "ERROR: " + t.getMessage());
                    ApiClient.hideProgressBar(mProgressBar);
                    ApiClient.showInfoDialog(SppActivity.this, "FAILURE",
                            "FAILURE THROWN DURING INSERT."+
                                    " ERROR Message: " + t.getMessage());
                }
            });
        }
    }

    private void updateData() {
        String tahun, nominal;
        if (ApiClient.validatespp(tahunTxt, nominalTxt)) {
            tahun = tahunTxt.getText().toString();
            nominal = nominalTxt.getText().toString();

            ApiClient.showProgressBar(mProgressBar);
            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponseSpp> updateDataSpp = api.updateDataSpp("UPDATE", id_spp, tahun, nominal);
            updateDataSpp.enqueue(new Callback<ResponseSpp>() {
                @Override
                public void onResponse(Call<ResponseSpp> call, Response<ResponseSpp> response) {
                    Log.d("RETROFIT", "Response: " + response.body().getResult());

                    ApiClient.hideProgressBar(mProgressBar);
                    String myResponseCode = response.body().getCode();

                    if (myResponseCode.equalsIgnoreCase("1")) {
                        ApiClient.show(c, response.body().getMessage());
                        ApiClient.openActivity(c, ListsppActivity.class);
                        finish();
                    } else if (myResponseCode.equalsIgnoreCase("2")) {
                        ApiClient.showInfoDialog(SppActivity.this, "UNSUCCESSFUL",
                                "Good Response From PHP,"+
                                        "WE ATTEMPTED UPDATING DATA BUT ENCOUNTERED ResponseCode: "+myResponseCode+
                                        " \n 3. Most probably the problem is with your PHP Code.");
                    } else if (myResponseCode.equalsIgnoreCase("3")) {
                        ApiClient.showInfoDialog(SppActivity.this, "NO MYSQL CONNECTION",
                                " Your PHP Code"+
                                        " is unable to connect to mysql database. Make sure you have supplied correct"+
                                        " database credentials.");
                    }
                }
                @Override
                public void onFailure(Call<ResponseSpp> call, Throwable t) {
                    Log.d("RETROFIT", "ERROR THROWN DURING UPDATE: " + t.getMessage());
                    ApiClient.hideProgressBar(mProgressBar);
                    ApiClient.showInfoDialog(SppActivity.this, "FAILURE THROWN", "ERROR DURING UPDATE.Here"+
                            " is the Error: " + t.getMessage());
                }
            });
        }
    }

    private void deleteData() {
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseSpp> removeSpp = api.removeSpp("DELETE", id_spp);

        ApiClient.showProgressBar(mProgressBar);
        removeSpp.enqueue(new Callback<ResponseSpp>() {
            @Override
            public void onResponse(Call<ResponseSpp> call, Response<ResponseSpp> response) {
                Log.d("RETROFIT", "DELETE RESPONSE: " + response.body());
                ApiClient.hideProgressBar(mProgressBar);
                String myResponseCode = response.body().getCode();

                if (myResponseCode.equalsIgnoreCase("1")) {
                    ApiClient.show(c, response.body().getMessage());
                    ApiClient.openActivity(c, ListsppActivity.class);
                    finish();
                } else if (myResponseCode.equalsIgnoreCase("2")) {
                    ApiClient.showInfoDialog(SppActivity.this, "UNSUCCESSFUL",
                            "However Good Response. \n 1. CONNECTION TO SERVER WAS SUCCESSFUL"+
                                    " \n 2. WE ATTEMPTED POSTING DATA BUT ENCOUNTERED ResponseCode: "+
                                    myResponseCode+ " \n 3. Most probably the problem is with your PHP Code.");
                }else if (myResponseCode.equalsIgnoreCase("3")) {
                    ApiClient.showInfoDialog(SppActivity.this, "NO MYSQL CONNECTION",
                            " Your PHP Code is unable to connect to mysql database. Make sure you have supplied correct database credentials.");
                }
            }
            @Override
            public void onFailure(Call<ResponseSpp> call, Throwable t) {
                ApiClient.hideProgressBar(mProgressBar);
                Log.d("RETROFIT", "ERROR: " + t.getMessage());
                ApiClient.showInfoDialog(SppActivity.this, "FAILURE THROWN", "ERROR during DELETE attempt. Message: " + t.getMessage());
            }
        });
    }

    private void showSelectedStarInEditText() {
        tahunTxt.setOnClickListener(v -> ApiClient.select_tahun(c, tahunTxt));
    }

    @Override
    public void onBackPressed() {
        ApiClient.showInfoDialog(this, "Warning", "Are you sure you want to exit?");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (receivedSppResultItem == null) {
            getMenuInflater().inflate(R.menu.newspp_item_menu, menu);
//            headerTxt.setText("Add New Scientist");
        } else {
            getMenuInflater().inflate(R.menu.editspp_item_menu, menu);
//            headerTxt.setText("Edit Existing Scientist");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insertMenuItem:
                insertData();
                return true;
            case R.id.editMenuItem:
                if (receivedSppResultItem != null) {
                    updateData();
                } else {
                    ApiClient.show(this, "EDIT ONLY WORKS IN EDITING MODE");
                }
                return true;
            case R.id.deleteMenuItem:
                if (receivedSppResultItem != null) {
                    deleteData();
                } else {
                    ApiClient.show(this, "DELETE ONLY WORKS IN EDITING MODE");
                }
                return true;
            case R.id.viewAllMenuItem:
                ApiClient.openActivity(this, ListsppActivity.class);
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
        Object o = ApiClient.receiveSpp(getIntent(), c);
        if (o != null) {
            receivedSppResultItem = (SppResultItem) o;
            id_spp = receivedSppResultItem.getIdSpp();
            tahunTxt.setText(receivedSppResultItem.getTahun());
            nominalTxt.setText(receivedSppResultItem.getNominal());
        } else {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spp);

        actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Data Spp");

        this.initializeWidgets();
        this.showSelectedStarInEditText();
    }

}
