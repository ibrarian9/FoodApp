package com.app.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.foodapp.api.ApiCore;
import com.app.foodapp.databinding.ActivityLoginBinding;
import com.app.foodapp.models.RequestLogin;
import com.app.foodapp.models.ResponseData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ApiCore apiCore = new ApiCore();

        binding.registerLogin.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

        binding.home.setOnClickListener( v -> startActivity(new Intent(LoginActivity.this, MainActivity.class)));

        binding.login.setOnClickListener(v -> {
            String email = binding.email.getText().toString();
            String password = binding.password.getText().toString();

            if (email.isEmpty()){
                pesanToast("Email masih kosong");
            } else if (password.isEmpty()){
                pesanToast("Password masih kosong");
            } else {
                RequestLogin requestLogin = new RequestLogin(email, password);

                Call<ResponseData> call = apiCore.getApiService().postLogin(requestLogin);
                call.enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseData> call, @NonNull Response<ResponseData> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().isSuccess()){
                            Intent intent = new Intent(LoginActivity.this, ListFoodActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            pesanToast("Login failed: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseData> call, @NonNull Throwable t) {
                        pesanToast("Login failed: " + t.getMessage());
                    }
                });
            }
        });
    }

    private void pesanToast(String pesan) {
        Toast.makeText(LoginActivity.this, pesan, Toast.LENGTH_SHORT).show();
    }
}