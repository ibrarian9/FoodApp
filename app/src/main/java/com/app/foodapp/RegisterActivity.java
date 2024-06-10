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
import com.app.foodapp.databinding.ActivityRegisterBinding;
import com.app.foodapp.models.RequestRegister;
import com.app.foodapp.models.ResponseData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRegisterBinding binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ApiCore apiCore = new ApiCore();

        binding.loginRegister.setOnClickListener(v ->
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));

        binding.register.setOnClickListener(v -> {
            String email = binding.email.getText().toString();
            String nama = binding.nama.getText().toString();
            String password = binding.password.getText().toString();
            String c_password = binding.cPassword.getText().toString();

            if (nama.isEmpty()){
                pesanToast("Nama belum diisi");
            } else if (email.isEmpty()) {
                pesanToast("Email belum diisi");
            } else if (password.isEmpty()) {
                pesanToast("Password belum diisi");
            } else if (c_password.isEmpty()) {
                pesanToast("Confirm Password belum diisi");
            } else {
                RequestRegister requestRegister = new RequestRegister(nama, email, password, c_password);
                Call<ResponseData> call = apiCore.getApiService().postRegister(requestRegister);
                call.enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseData> call, @NonNull Response<ResponseData> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().isSuccess()){
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            pesanToast("Register failed: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseData> call, @NonNull Throwable t) {
                        pesanToast("Register failed: " + t.getMessage());
                    }
                });

            }
        });
    }

    private void pesanToast(String pesan) {
        Toast.makeText(RegisterActivity.this, pesan, Toast.LENGTH_SHORT).show();
    }
}