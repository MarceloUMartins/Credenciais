package com.example.credenciais;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.credenciais.Async.JSONParserAsyncTask;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TextView temperaturaHome = view.findViewById(R.id.temperatura);

        JSONParserAsyncTask jsonParserAsyncTask = new JSONParserAsyncTask(temperaturaHome, "https://api.open-meteo.com/v1/forecast?latitude=-3.758008382787574&longitude=-38.517125982773706&current_weather=true");
        jsonParserAsyncTask.execute();
        return view;
    }
}