package com.example.credenciais;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class MapaFragment extends Fragment implements OnMapReadyCallback {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapa, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    // Get a handle to the GoogleMap object and display marker.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        ArrayList<Marker> marcadores = new ArrayList<>();
        AtomicReference<Polyline> polyline = new AtomicReference<>();
        googleMap.setOnMapClickListener(latLng -> {
            if (marcadores.size() < 2) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Ponto " + marcadores.size() + 1);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                marcadores.add(googleMap.addMarker(markerOptions));

                if (marcadores.size() == 2) {
                    PolylineOptions polylineOptions = new PolylineOptions();
                    polylineOptions.addAll(marcadores.stream().map(Marker::getPosition).collect(Collectors.toList()));
                    polylineOptions.color(Color.RED);
                    polyline.set(googleMap.addPolyline(polylineOptions));
                }
            }
        });

        googleMap.setOnMarkerClickListener(macador -> {
            marcadores.stream().filter(m -> m.getId().equals(macador.getId())).findFirst().ifPresent(Marker::remove);
            marcadores.remove(macador);
            polyline.get().remove();
            return false;
        });
    }
}