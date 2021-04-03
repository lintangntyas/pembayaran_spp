package com.example.ukk_lintang2021.Activities.Admin;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.example.ukk_lintang2021.Model.Siswa.SiswaResultItem;
import com.example.ukk_lintang2021.Network.ApiClient;
import com.example.ukk_lintang2021.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailsiswaActivity  extends AppCompatActivity implements View.OnClickListener {

    //Let's define our instance fields
    private TextView tv_nisn, tv_nis, tv_nama, tv_idkelas, tv_alamat, tv_notelp, tv_idspp;
    private FloatingActionButton editFAB;
    private SiswaResultItem receivedSiswaResultItem;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    /**
     * Let's initialize our widgets
     */
    private void initializeWidgets(){
        tv_nisn= findViewById(R.id.nisnTV);
        tv_nis= findViewById(R.id.nisTV);
        tv_nama= findViewById(R.id.namaTV);
        tv_idkelas= findViewById(R.id.idkelasTV);
        tv_alamat= findViewById(R.id.alamatTV);
        tv_notelp= findViewById(R.id.notelpTV);
        tv_idspp= findViewById(R.id.idsppTV);

        editFAB=findViewById(R.id.editFAB);
        editFAB.setOnClickListener(this);
        mCollapsingToolbarLayout=findViewById(R.id.mCollapsingToolbarLayout);
    }

    /**
     * We will now receive and show our data to their appropriate views.
     */
    private void receiveAndShowData(){
        receivedSiswaResultItem = ApiClient.receiveSiswa(getIntent(),DetailsiswaActivity.this);

        if(receivedSiswaResultItem != null){
            tv_nisn.setText(receivedSiswaResultItem.getNisn());
            tv_nis.setText(receivedSiswaResultItem.getNis());
            tv_nama.setText(receivedSiswaResultItem.getNama());
            tv_idkelas.setText(receivedSiswaResultItem.getIdKelas());
            tv_alamat.setText(receivedSiswaResultItem.getAlamat());
            tv_notelp.setText(receivedSiswaResultItem.getNoTelp());
            tv_idspp.setText(receivedSiswaResultItem.getIdSpp());

            mCollapsingToolbarLayout.setTitle(receivedSiswaResultItem.getNama());
            mCollapsingToolbarLayout.setExpandedTitleColor(getResources().
                    getColor(R.color.darkGreen));
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
                ApiClient.sendSiswaToActivity(this, receivedSiswaResultItem, SiswaActivity.class);
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
            ApiClient.sendSiswaToActivity(this, receivedSiswaResultItem, SiswaActivity.class);
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
        setContentView(R.layout.activity_detailsiswa);

        initializeWidgets();
        receiveAndShowData();
    }

}
//end