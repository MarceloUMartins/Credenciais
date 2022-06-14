package com.example.credenciais;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.credenciais.entidades.Perfil;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MyAdapter extends ArrayAdapter<Perfil> {
    private List<Perfil> perfis;
    private Activity context;

    public MyAdapter(Activity context, List<Perfil> perfis) {
        super(context, R.layout.item_lista, perfis);
        this.context = context;
        this.perfis = perfis;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if(convertView==null)
            row = inflater.inflate(R.layout.item_lista, null, true);
        TextView textViewCountry = (TextView) row.findViewById(R.id.perfilNomeLista);
        ImageView imageFlag = (ImageView) row.findViewById(R.id.perfilImgLista);

        textViewCountry.setText(perfis.get(position).toString());
        if (perfis.get(position).getUrlFoto() != null) {
            Picasso.get().load(perfis.get(position).getUrlFoto()).into(imageFlag);
        } else {
            imageFlag.setImageBitmap(null);
        }

        return  row;
    }
}
