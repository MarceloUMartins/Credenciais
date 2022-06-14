package com.example.credenciais;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.credenciais.Mapper.PerfilMapper;
import com.example.credenciais.entidades.Perfil;

import java.util.List;

public class UsuarioFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usuario, container, false);
        PerfilMapper perfilMapper = new PerfilMapper(this.getContext());

        List<Perfil> data = perfilMapper.resgatePerfis();

        MyAdapter adapter = new MyAdapter(this.getActivity(), data);

        ListView listView = view.findViewById(R.id.listaPerfis);

        listView.setAdapter(adapter);
        return view;
    }
}