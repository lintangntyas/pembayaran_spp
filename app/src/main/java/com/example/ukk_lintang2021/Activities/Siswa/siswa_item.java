package com.example.ukk_lintang2021.Activities.Siswa;

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
import com.example.ukk_lintang2021.Model.Siswa.SiswaResultItem;
import com.example.ukk_lintang2021.R;
import com.example.ukk_lintang2021.SessionManager.SessionManager;

public class siswa_item extends AppCompatActivity {

    SessionManager sessionManager;
    CardView cv_pembayaran;
    TextView tvEmail, tvUsername, lihat_semua;
    String username, email;
    ImageView browser_btn;
    LinearLayout browser_linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_item);

        sessionManager = new SessionManager(siswa_item.this);
        if(!sessionManager.isLoggedIn()){
            moveToLogin();
        }

        cv_pembayaran = findViewById(R.id.adm_pembayaran);

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

        cv_pembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent datapembayaranIntent = new Intent(siswa_item.this, SiswaListpembayaran.class);
                startActivity(datapembayaranIntent);
            }
        });
    }
    private void moveToLogin() {
        Intent intent = new Intent(siswa_item.this, LoginActivity.class);
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