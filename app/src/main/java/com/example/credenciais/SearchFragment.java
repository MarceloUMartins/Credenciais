package com.example.credenciais;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.credenciais.Mapper.PerfilMapper;
import com.example.credenciais.entidades.Perfil;

import java.util.List;

public class SearchFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        PerfilMapper perfilMapper = new PerfilMapper(this.getContext());

        List<Perfil> data = perfilMapper.resgatePerfis();

        ArrayAdapter<Perfil> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, data);

        ListView listView = view.findViewById(R.id.listaPerfis);

        listView.setAdapter(adapter);
        return view;
    }
}