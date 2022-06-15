package com.example.credenciais;

import android.Manifest;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class MapaFragment extends Fragment implements OnMapReadyCallback {

    private FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;
    protected SupportMapFragment mapFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mapa, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        ActivityCompat.requestPermissions( this.requireActivity(),
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(view.getContext());
        getLastLocation(this);
        return view;
    }



    // Get a handle to the GoogleMap object and display marker.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        ArrayList<Marker> marcadores = new ArrayList<>();
        AtomicReference<Polyline> polyline = new AtomicReference<>();
        MarkerOptions marker = new MarkerOptions();
        marker.position(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
        marker.title("Ponto Inicial");
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 10));
        marcadores.add(googleMap.addMarker(marker));

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

    @SuppressWarnings("MissingPermission")
    private void getLastLocation(OnMapReadyCallback callback) {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this.requireActivity(), task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        mLastLocation = task.getResult();
                        mapFragment.getMapAsync(callback);
                    } else {
                        Toast.makeText(this.getContext(), "Não foi possível resgatar localização", Toast.LENGTH_LONG).show();
                        Log.d("MAPA", "Deu ruim a localização",task.getException());
                    }
                });
    }
}