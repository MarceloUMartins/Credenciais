package com.example.credenciais;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.example.credenciais.entidades.Perfil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Perfil perfil = (Perfil) getIntent().getSerializableExtra("perfil");
        TextView nomePerfil = findViewById(R.id.nomePerfil);
        nomePerfil.setText("Bem-vindo, "+ perfil.getNome());
    }
}