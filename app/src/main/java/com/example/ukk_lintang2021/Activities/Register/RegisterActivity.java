package com.example.ukk_lintang2021.Activities.Register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ukk_lintang2021.Activities.Login.LoginActivity;
import com.example.ukk_lintang2021.Model.Register.ResponseRegister;
import com.example.ukk_lintang2021.Network.ApiClient;
import com.example.ukk_lintang2021.Network.ApiInterface;
import com.example.ukk_lintang2021.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edEmail, edUsername, edPassword, edRole;
    Button Register;
    String Email, Username, Password, Role;
    TextView Login;
    ApiInterface apiInterface;
    private Context c = RegisterActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        edEmail = (EditText) findViewById(R.id.rg_email);
        edUsername = (EditText) findViewById(R.id.rg_username);
        edPassword = (EditText) findViewById(R.id.rg_password);
        edRole = (EditText) findViewById(R.id.rg_role);

        Register = (Button) findViewById(R.id.btn_regis);
        Register.setOnClickListener(this);

        Login = (TextView) findViewById(R.id.tv_login);
        Login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_regis:
                Email = edEmail.getText().toString();
                Username = edUsername.getText().toString();
                Password = edPassword.getText().toString();
                Role = edRole.getText().toString();
                registration(Email, Username, Password, Role);
                break;
            case R.id.tv_login:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.rg_role:
                this.showSelectedRoleInEditText();
                break;
        }
    }

    private void showSelectedRoleInEditText() {
        edRole.setOnClickListener(v -> ApiClient.selectrole(c, edRole));
    }

    private void registration(String Email, String Username, String Password, String Role) {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseRegister> call = apiInterface.registerResponse(Email, Username, Password, Role);
        call.enqueue(new Callback<ResponseRegister>() {

            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}