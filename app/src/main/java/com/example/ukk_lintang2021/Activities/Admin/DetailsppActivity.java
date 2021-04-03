package com.example.ukk_lintang2021.Activities.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ukk_lintang2021.Network.ApiClient;
import com.example.ukk_lintang2021.Model.Spp.SppResultItem;
import com.example.ukk_lintang2021.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailsppActivity extends AppCompatActivity implements View.OnClickListener {

    //Let's define our instance fields
    private TextView tahunTV, nominalTV;
    private FloatingActionButton editFAB;
    private SppResultItem receivedSppData;
//    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    private void initializeWidgets(){
        tahunTV= findViewById(R.id.tahunTV);
        nominalTV= findViewById(R.id.nominalTV);

        editFAB=findViewById(R.id.editFAB);
        editFAB.setOnClickListener(this);
//        mCollapsingToolbarLayout=findViewById(R.id.mCollapsingToolbarLayout);
    }

    private void receiveAndShowData(){
        receivedSppData = ApiClient.receiveSpp(getIntent(),DetailsppActivity.this);

        if(receivedSppData != null){
            tahunTV.setText(receivedSppData.getTahun());
            nominalTV.setText(receivedSppData.getNominal());

//            mCollapsingToolbarLayout.setTitle(receivedSppData.getNominal());
//            mCollapsingToolbarLayout.setExpandedTitleColor(getResources().
//                    getColor(R.color.white));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detailspp_page_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                ApiClient.sendSppToActivity(this, receivedSppData, SppActivity.class);
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
    public void onClick(View v) {
        int id =v.getId();
        if(id == R.id.editFAB){
            ApiClient.sendSppToActivity(this, receivedSppData, SppActivity.class);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailspp);

        initializeWidgets();
        receiveAndShowData();
    }
}