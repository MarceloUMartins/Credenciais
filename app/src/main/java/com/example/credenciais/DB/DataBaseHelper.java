package com.example.credenciais.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "credenciais.db";
    private static final int DATABASE_VERSION = 1;
    public static String TABLE_PERFIL = "perfil";
    private static final String CREATE_PERFIL = "CREATE TABLE perfil"
            + "(nome text not null, "
            + "sexo text not null, "
            + "email text not null, "
            + "senha text not null, "
            + "telefone text not null, "
            + "disciplina text not null, "
            + "turma text not null);";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PERFIL);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERFIL);
        onCreate(db);
    }
}
