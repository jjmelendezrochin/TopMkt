package com.topmas.top;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.topmas.top.Constants.TAG_LATITUD;
import static com.topmas.top.Constants.TAG_LONGITUD;
import static com.topmas.top.Constants.TAG_TIENDA;

public class MapaTienda extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    private Double latitud;
    private Double longitud;
    private String Tienda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_tienda);

        Intent i = getIntent();
        //pid = i.getStringExtra(TAG_ID);
        latitud = getIntent().getDoubleExtra(TAG_LATITUD,0.00);
        longitud = i.getDoubleExtra(TAG_LONGITUD, 0.00);
        Tienda = i.getStringExtra(TAG_TIENDA);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Mexico and move the camera
        LatLng UbicacionTienda = new LatLng(latitud, longitud);
        mMap.addMarker(new MarkerOptions().position(UbicacionTienda).title(Tienda));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(UbicacionTienda));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
    }
}
