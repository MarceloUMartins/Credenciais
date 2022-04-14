package com.example.credenciais;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbarLogin);
        setSupportActionBar(toolbar);

        Button buttonregistrar = findViewById(R.id.buttonRegistrar);
        buttonregistrar.setOnClickListener(v -> registrar());

        Button buttonLogin = findViewById(R.id.button);
        buttonLogin.setOnClickListener(v -> logIn());

    }

    public void registrar() {
        Intent intent = new Intent(this, Cadastro.class);
        startActivity(intent);
    }

    public void logIn() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}