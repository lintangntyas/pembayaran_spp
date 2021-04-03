package com.example.ukk_lintang2021.Activities.Petugas;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.example.ukk_lintang2021.Model.Pembayaran.PembayaranResultItem;
import com.example.ukk_lintang2021.Network.ApiClient;
import com.example.ukk_lintang2021.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailpembayaranActivity extends
        AppCompatActivity implements View.OnClickListener {

    private TextView tv_petugas, tv_nisn, tv_tanggal, tv_bulan, tv_tahun, tv_idspp, tv_jumlah;
    private FloatingActionButton editFAB;
    private PembayaranResultItem receivedPembayaranResultItem;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    private void initializeWidgets(){
        tv_petugas= findViewById(R.id.IdpetugasTV);
        tv_nisn= findViewById(R.id.IdnisnTV);
        tv_tanggal= findViewById(R.id.TanggalTV);
        tv_bulan= findViewById(R.id.BulanTV);
        tv_tahun= findViewById(R.id.TahunTV);
        tv_idspp= findViewById(R.id.IdsppTV);
        tv_jumlah= findViewById(R.id.JumlahTV);

        editFAB=findViewById(R.id.editFAB);
        editFAB.setOnClickListener(this);
        mCollapsingToolbarLayout=findViewById(R.id.mCollapsingToolbarLayout);
    }

    private void receiveAndShowData(){
        receivedPembayaranResultItem = ApiClient.receivePembayaran(getIntent(),
                DetailpembayaranActivity.this);

        if(receivedPembayaranResultItem != null){
            tv_petugas.setText(receivedPembayaranResultItem.getIdPetugas());
            tv_nisn.setText(receivedPembayaranResultItem.getNisn());
            tv_tanggal.setText(receivedPembayaranResultItem.getTglBayar());
            tv_bulan.setText(receivedPembayaranResultItem.getBulanDibayar());
            tv_tahun.setText(receivedPembayaranResultItem.getTahunDibayar());
            tv_idspp.setText(receivedPembayaranResultItem.getIdSpp());
            tv_jumlah.setText(receivedPembayaranResultItem.getJumlahBayar());

            mCollapsingToolbarLayout.setTitle(receivedPembayaranResultItem.getNisn());
            mCollapsingToolbarLayout.setExpandedTitleColor(getResources().
                    getColor(R.color.white));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detailpembayaran_page_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                ApiClient.sendPembayaranToActivity(this, receivedPembayaranResultItem, PembayaranActivity.class);
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
            ApiClient.sendPembayaranToActivity(this, receivedPembayaranResultItem, PembayaranActivity.class);
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
        setContentView(R.layout.activity_detailpembayaran);

        initializeWidgets();
        receiveAndShowData();
    }
}