package com.example.credenciais;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.credenciais.entidades.Perfil;

import java.util.Objects;

public class Login extends AppCompatActivity {

    Perfil perfil = null;
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

        perfil = (Perfil) getIntent().getSerializableExtra("perfil");
    }

    public void registrar() {
        Intent intent = new Intent(this, Cadastro.class);
        startActivity(intent);
    }

    public void logIn() {
        EditText emailLogin = findViewById(R.id.editTextLoginEmail);
        EditText senhaLogin =findViewById(R.id.editTextLoginSenha);
        if (perfil != null && perfil.getEmail().equals(emailLogin.getText().toString()) && perfil.getSenha().equals(senhaLogin.getText().toString())) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("perfil", perfil);
            startActivity(intent);
        } else {
            Toast.makeText(Login.this, Html.fromHtml("<font color='red' ><b>Login inválido! Realize o cadastro e tente novamente.</b></font>"), Toast.LENGTH_LONG).show();
        }

    }

}