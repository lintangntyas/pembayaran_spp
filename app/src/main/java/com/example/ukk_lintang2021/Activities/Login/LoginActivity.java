package com.example.ukk_lintang2021.Activities.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ukk_lintang2021.Activities.Register.RegisterActivity;
import com.example.ukk_lintang2021.Model.Login.LoginData;
import com.example.ukk_lintang2021.Model.Login.ResponseLogin;
import com.example.ukk_lintang2021.Network.ApiClient;
import com.example.ukk_lintang2021.Network.ApiInterface;
import com.example.ukk_lintang2021.R;
import com.example.ukk_lintang2021.SessionManager.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edEmail, edPassword, edRole;
    Button Login;
    String Email, Password, Role;
    TextView Register;
    ApiInterface apiInterface;
    SessionManager sessionManager;
    private Context c = LoginActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();


        edEmail = findViewById(R.id.lg_email);
        edPassword = findViewById(R.id.lg_password);
        edRole = findViewById(R.id.lg_role);

        Login = findViewById(R.id.btn_login);
        Login.setOnClickListener(this);

        Register = findViewById(R.id.tv_register);
        Register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                Email = edEmail.getText().toString();
                Password = edPassword.getText().toString();
                Role = edRole.getText().toString();
                login(Email, Password, Role);
                break;
            case R.id.tv_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.lg_role:
                this.showSelectedRoleInEditText();
                break;
        }
    }

    private void showSelectedRoleInEditText() {
        edRole.setOnClickListener(v -> ApiClient.selectrole(c, edRole));
    }

    private void login(String email, String password, String role) {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseLogin> callLogin = apiInterface.loginResponse(email, password, role);

        callLogin.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()) {

                    // Ini untuk menyimpan sesi
                    sessionManager = new SessionManager(LoginActivity.this);
                    LoginData loginData = response.body().getLoginData();
                    sessionManager.createLoginSession(loginData);

                    //Ini untuk pindah
                    Toast.makeText(LoginActivity.this, response.body().getLoginData().getEmail(), Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
//                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {

            }
        });
    }
}