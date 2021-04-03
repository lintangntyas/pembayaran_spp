package com.example.ukk_lintang2021.Activities.Admin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.ukk_lintang2021.Activities.Login.LoginActivity;
import com.example.ukk_lintang2021.R;
import com.example.ukk_lintang2021.SessionManager.SessionManager;

public class admin_item extends AppCompatActivity {

    SessionManager sessionManager;
    CardView cv_petugas, cv_kelas, cv_spp, cv_pembayaran, cv_siswa, cv_laporan;
    TextView tvEmail, tvUsername, lihat_semua;
    String username, email;
    ImageView browser_btn;
    LinearLayout browser_linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_item);

        sessionManager = new SessionManager(admin_item.this);
        if(!sessionManager.isLoggedIn()){
            moveToLogin();
        }

        cv_petugas = findViewById(R.id.adm_petugas);
        cv_kelas = findViewById(R.id.adm_kelas);
        cv_spp = findViewById(R.id.adm_spp);
        cv_pembayaran = findViewById(R.id.adm_pembayaran);
        cv_siswa = findViewById(R.id.adm_siswa);
        cv_laporan = findViewById(R.id.adm_laporan);

        lihat_semua = findViewById(R.id.lihatsemua);

        tvEmail = findViewById(R.id.etMainEmail);
        tvUsername = findViewById(R.id.etMainUsername);

        browser_btn = findViewById(R.id.browser_button);
        browser_linear = findViewById(R.id.browser_linear);

        //username ambil dari string, USERNAME ambil dari session string
        username = sessionManager.getUserDetail().get(SessionManager.USERNAME);
        email= sessionManager.getUserDetail().get(SessionManager.EMAIL);

        //etUsername di ambil dari textview, username diambil dari string diatas
        tvUsername.setText(username);
        tvEmail.setText(email);

        browser_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.smkti.net/"));
                startActivity(intent);
            }
        });

        browser_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.smkti.net/"));
                startActivity(intent);
            }
        });

        cv_petugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent datapetugasIntent = new Intent(admin_item.this, ListpetugasActivity.class);
                startActivity(datapetugasIntent);
            }
        });

        cv_kelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent datakelasIntent = new Intent(admin_item.this, ListkelasActivity.class);
                startActivity(datakelasIntent);
            }
        });

        cv_spp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent datasppIntent = new Intent(admin_item.this, ListsppActivity.class);
                startActivity(datasppIntent);
            }
        });

        cv_pembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent datapembayaranIntent = new Intent(admin_item.this, ListpembayaranActivity.class);
                startActivity(datapembayaranIntent);
            }
        });

        cv_siswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent datasiswaIntent = new Intent(admin_item.this, ListsiswaActivity.class);
                startActivity(datasiswaIntent);
            }
        });

        cv_laporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent datalaporanIntent = new Intent(admin_item.this, ListpetugasActivity.class);
                startActivity(datalaporanIntent);
            }
        });
    }
    private void moveToLogin() {
        Intent intent = new Intent(admin_item.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.actionLogout:
                sessionManager.logoutSession();
                moveToLogin();
        }
        return super.onOptionsItemSelected(item);
    }
}