package com.example.credenciais;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Cadastro extends AppCompatActivity {

    private EditText nome;
    private RadioGroup sexos;
    private EditText email;
    private EditText senha;
    private EditText telefone;
    private EditText disciplina;
    private EditText turma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        Toolbar toolbar = findViewById(R.id.toolbarCadastro);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        nome = findViewById(R.id.editTextNome);
        sexos = findViewById(R.id.radioGroup);
        email = findViewById(R.id.editTextEmail);
        senha = findViewById(R.id.editTextSenha);
        telefone = findViewById(R.id.editTextTelefone);
        disciplina = findViewById(R.id.editTextDisciplina);
        turma = findViewById(R.id.editTextTurma);

        Button button = findViewById(R.id.registrar);
        button.setOnClickListener(v -> validacao());
    }



    public boolean validacao() {
        boolean valido = true;

        if (nome.getText().toString().trim().equalsIgnoreCase("")) {
            nome.setError("This field can not be blank");
            valido = false;
        }

        if (sexos.getCheckedRadioButtonId() == -1) {
            Toast.makeText(Cadastro.this, Html.fromHtml("<font color='red' ><b>Necessário selecionar sexo.</b></font>"), Toast.LENGTH_LONG).show();
        }

        if (email.getText().toString().trim().equalsIgnoreCase("")) {
            email.setError("This field can not be blank");
            valido = false;
        }

        if (senha.getText().toString().trim().equalsIgnoreCase("")) {
            senha.setError("This field can not be blank");
            valido = false;
        } else if (senha.getText().length() < 6) {
            senha.setError("A senha deve ter ao menos 6 caracteres");
            valido = false;
        }

        if (telefone.getText().toString().trim().equalsIgnoreCase("")) {
            telefone.setError("This field can not be blank");
            valido = false;
        }

        if (disciplina.getText().toString().trim().equalsIgnoreCase("")) {
            disciplina.setError("This field can not be blank");
            valido = false;
        }

        if (turma.getText().toString().trim().equalsIgnoreCase("")) {
            turma.setError("This field can not be blank");
            valido = false;
        }

        return valido;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}