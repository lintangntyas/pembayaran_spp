package com.example.ukk_lintang2021.Activities.Admin;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.example.ukk_lintang2021.Model.Kelas.KelasResultItem;
import com.example.ukk_lintang2021.Network.ApiClient;
import com.example.ukk_lintang2021.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailkelasActivity  extends AppCompatActivity implements View.OnClickListener {

    //Let's define our instance fields
    private TextView nama_kelasTV, kompetensi_keahlianTV;
    private FloatingActionButton editFAB;
    private KelasResultItem receivedKelasResultItem;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    /**
     * Let's initialize our widgets
     */
    private void initializeWidgets(){
        nama_kelasTV= findViewById(R.id.namakelasTV);
        kompetensi_keahlianTV= findViewById(R.id.kompetensiTV);

        editFAB=findViewById(R.id.editFAB);
        editFAB.setOnClickListener(this);
        mCollapsingToolbarLayout=findViewById(R.id.mCollapsingToolbarLayout);
    }

    /**
     * We will now receive and show our data to their appropriate views.
     */
    private void receiveAndShowData(){
        receivedKelasResultItem = ApiClient.receiveKelas(getIntent(),DetailkelasActivity.this);

        if(receivedKelasResultItem != null){
            nama_kelasTV.setText(receivedKelasResultItem.getNamaKelas());
            kompetensi_keahlianTV.setText(receivedKelasResultItem.getKompetensiKeahlian());

            mCollapsingToolbarLayout.setTitle(receivedKelasResultItem.getNamaKelas());
            mCollapsingToolbarLayout.setExpandedTitleColor(getResources().
                    getColor(R.color.white));
        }
    }
    /**
     * Let's inflate our menu for the detail page
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detailkelas_page_menu, menu);
        return true;
    }

    /**
     * When a menu item is selected we want to navigate to the appropriate page
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                ApiClient.sendKelasToActivity(this, receivedKelasResultItem, KelasActivity.class);
                finish();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * When FAB button is clicked we want to go to the editing page
     */
    @Override
    public void onClick(View v) {
        int id =v.getId();
        if(id == R.id.editFAB){
            ApiClient.sendKelasToActivity(this, receivedKelasResultItem, KelasActivity.class);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
    /**
     * Our onCreate method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailkelas);

        initializeWidgets();
        receiveAndShowData();
    }

}
//end