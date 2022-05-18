package com.example.credenciais.Mapper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.credenciais.DB.DataBaseHelper;
import com.example.credenciais.entidades.Perfil;

import java.util.ArrayList;
import java.util.List;

public class PerfilMapper {
    private SQLiteDatabase database;
    private DataBaseHelper dbHelper;

    public PerfilMapper(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    private void open(){
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public void insert(Perfil perfil) {
        try {
            open();
            database.execSQL(("INSERT INTO perfil(nome, sexo, email, senha, telefone, disciplina, turma) VALUES ('" +
                    perfil.getNome() + "','" +
                    perfil.getSexo() + "','" +
                    perfil.getEmail() + "','" +
                    perfil.getSenha() + "','" +
                    perfil.getTelefone() + "','" +
                    perfil.getDisciplina() + "','" +
                    perfil.getTurma() + "')"));
        } catch (Exception e) {
            Log.e("Erro no insert", e.getMessage());
        } finally {
            close();
        }
    }

    public List<Perfil> resgatePerfis() {
        List<Perfil> perfis = new ArrayList<>();
        try {
            open();
            Cursor cursor = database.rawQuery("SELECT nome, sexo, email, senha, telefone, disciplina, turma FROM perfil", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Perfil perfil = cursorToPerfil(cursor);
                perfis.add(perfil);
                cursor.moveToNext();
            }
            cursor.close();
            return perfis;
        } catch (Exception e) {
            Log.e("Erro no resgate", e.getMessage());
            return perfis;
        } finally {
            close();
        }
    }

    public boolean login(String loginEmail, String loginSenha) {
        try {
            open();
            String[] selectionArgs = {loginEmail, loginSenha};
            Cursor cursor = database.rawQuery("SELECT 1 FROM perfil WHERE email = ? AND senha = ?", selectionArgs);
            int count = cursor.getCount();
            cursor.close();
            return count > 0;
        } catch (Exception e) {
            Log.e("Erro no resgate", e.getMessage());
            return false;
        } finally {
            close();
        }
    }

    private Perfil cursorToPerfil(Cursor cursor) {
        Perfil perfil = new Perfil();
        perfil.setNome(cursor.getString(0));
        perfil.setSexo(cursor.getString(1));
        perfil.setEmail(cursor.getString(2));
        perfil.setSenha(cursor.getString(3));
        perfil.setTelefone(cursor.getString(4));
        perfil.setDisciplina(cursor.getString(5));
        perfil.setTurma(cursor.getString(6));
        return perfil;
    }
}
