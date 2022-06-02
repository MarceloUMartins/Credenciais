package com.example.credenciais;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.utils.widget.ImageFilterView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.credenciais.Mapper.PerfilMapper;
import com.example.credenciais.entidades.Perfil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class Cadastro extends AppCompatActivity {

    private EditText nome;
    private RadioGroup sexos;
    private EditText email;
    private EditText senha;
    private EditText telefone;
    private EditText disciplina;
    private EditText turma;
    private PerfilMapper perfilMapper;
    private Button altButton;
    private ImageFilterView fotoPerfil;

    private ImageView ivStorage;

    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        Toolbar toolbar = findViewById(R.id.toolbarCadastro);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        perfilMapper = new PerfilMapper(this);

        nome = findViewById(R.id.editTextNome);
        sexos = findViewById(R.id.radioGroup);
        email = findViewById(R.id.editTextEmail);
        senha = findViewById(R.id.editTextSenha);
        telefone = findViewById(R.id.editTextTelefone);
        disciplina = findViewById(R.id.editTextDisciplina);
        turma = findViewById(R.id.editTextTurma);
        fotoPerfil = findViewById(R.id.profileImg);

        storageReference = FirebaseStorage.getInstance().getReference();

        altButton = findViewById(R.id.alterarImg);
        altButton.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 1);
        });

        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BitmapDrawable bmp = (BitmapDrawable) ivStorage.getDrawable();
                Bitmap bitmap = bmp.getBitmap();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos );


                byte[] imagemArray = baos.toByteArray();

                StorageReference refPasta = storageReference.child("Imagens");
                StorageReference refImagem = refPasta.child(".Jpeg");
                UploadTask task = refImagem.putBytes(imagemArray);

                task.addOnFailureListener(Cadastro.this, (e)-> {
                    Toast.makeText(Cadastro.this,
                            "Falha no Upload:" + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }).addOnSuccessListener(Cadastro.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> url = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                        while (!url.isComplete())
                            ;
                        Toast.makeText(Cadastro.this,
                                "Sucesso" + url.getResult().toString(),
                                Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        Button button = findViewById(R.id.registrar);
        button.setOnClickListener(v -> {
            if (validacao()) {
                salvandoPerfil();
            }
        });
    }

    private void salvandoPerfil() {
        Perfil perfil = new Perfil();
        RadioButton sexo = findViewById(sexos.getCheckedRadioButtonId());
        perfil.setNome(nome.getText().toString());
        perfil.setSexo(sexo.getText().toString());
        perfil.setEmail(email.getText().toString());
        perfil.setSenha(senha.getText().toString());
        perfil.setTelefone(telefone.getText().toString());
        perfil.setDisciplina(disciplina.getText().toString());
        perfil.setTurma(turma.getText().toString());

        perfilMapper.insert(perfil);
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);

    }


    private boolean validacao() {
        boolean valido = true;

        if (nome.getText().toString().trim().equalsIgnoreCase("")) {
            nome.setError("O Campo nao pode ficar em branco");
            valido = false;
        }

        if (sexos.getCheckedRadioButtonId() == -1) {
            Toast.makeText(Cadastro.this, Html.fromHtml("<font color='red' ><b>Necessário selecionar sexo.</b></font>"), Toast.LENGTH_LONG).show();
        }

        if (email.getText().toString().trim().equalsIgnoreCase("")) {
            email.setError("O Campo nao pode ficar em branco");
            valido = false;
        }

        if (senha.getText().toString().trim().equalsIgnoreCase("")) {
            senha.setError("O Campo nao pode ficar em branco");
            valido = false;
        } else if (senha.getText().length() < 6) {
            senha.setError("A senha deve ter ao menos 6 caracteres");
            valido = false;
        }

        if (telefone.getText().toString().trim().equalsIgnoreCase("")) {
            telefone.setError("O Campo nao pode ficar em branco");
            valido = false;
        }

        if (disciplina.getText().toString().trim().equalsIgnoreCase("")) {
            disciplina.setError("O Campo nao pode ficar em branco");
            valido = false;
        }

        if (turma.getText().toString().trim().equalsIgnoreCase("")) {
            turma.setError("O Campo nao pode ficar em branco");
            valido = false;
        }

        return valido;
    }




    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    protected void onActivityResult(int requestCod, int resultCode, Intent dados) {

        super.onActivityResult(requestCod, resultCode, dados);
        if (requestCod == 1) {
            try {
                Bitmap fotoRegistrada = (Bitmap) dados.getExtras().get("data");
                fotoPerfil.setImageBitmap(fotoRegistrada);
            } catch (Exception e) {
            }
        }

    }
}