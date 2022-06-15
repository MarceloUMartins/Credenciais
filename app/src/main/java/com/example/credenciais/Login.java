package com.example.credenciais;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.credenciais.Mapper.PerfilMapper;
import com.example.credenciais.entidades.Perfil;

public class Login extends AppCompatActivity {

    private PerfilMapper perfilMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbarLogin);
        setSupportActionBar(toolbar);

        perfilMapper = new PerfilMapper(this);

        Button buttonregistrar = findViewById(R.id.buttonRegistrar);
        buttonregistrar.setOnClickListener(v -> registrar());

        Button buttonLogin = findViewById(R.id.button);
        buttonLogin.setOnClickListener(v -> logIn());

        ActivityCompat.requestPermissions( this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    public void registrar() {
        Intent intent = new Intent(this, Cadastro.class);
        startActivity(intent);
    }

    public void logIn() {
        EditText emailLogin = findViewById(R.id.editTextLoginEmail);
        EditText senhaLogin =findViewById(R.id.editTextLoginSenha);

        if (perfilMapper.login(emailLogin.getText().toString(), senhaLogin.getText().toString())) {
            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
        } else {
            Toast.makeText(Login.this, Html.fromHtml("<font color='red' ><b>Login inválido! Realize o cadastro e tente novamente.</b></font>"), Toast.LENGTH_LONG).show();
        }

    }

}