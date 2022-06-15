package com.example.credenciais;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.credenciais.Async.JSONParserAsyncTask;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.OnMapReadyCallback;

public class HomeFragment extends Fragment {

    private FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(view.getContext());
        TextView temperaturaHome = view.findViewById(R.id.temperatura);
        getLastLocation(temperaturaHome);
        return view;
    }

    @SuppressWarnings("MissingPermission")
    private void getLastLocation(TextView temperaturaHome) {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this.requireActivity(), task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        mLastLocation = task.getResult();
                        JSONParserAsyncTask jsonParserAsyncTask = new JSONParserAsyncTask(temperaturaHome, "https://api.open-meteo.com/v1/forecast?latitude=" + mLastLocation.getLatitude() + "&longitude=" + mLastLocation.getLongitude() + "&current_weather=true");
                        jsonParserAsyncTask.execute();
                    } else {
                        Toast.makeText(this.getContext(), "Não foi possível resgatar localização", Toast.LENGTH_LONG).show();
                        Log.d("MAPA", "Deu ruim a localização",task.getException());
                    }
                });
    }
}