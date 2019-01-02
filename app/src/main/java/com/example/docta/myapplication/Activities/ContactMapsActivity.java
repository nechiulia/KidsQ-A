package com.example.docta.myapplication.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.docta.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ContactMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button btn_sw;
    private Button btn_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_maps);
        if (savedInstanceState == null) {
            String title = getString(R.string.Title_Contact);
            this.setTitle(title);
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btn_sw=findViewById(R.id.contact_btn_changeView);
        btn_sw.setOnClickListener(this::changeType);

        btn_back=findViewById(R.id.contact_btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


    }

    public void changeType(View view)
    {

        if(mMap.getMapType()==GoogleMap.MAP_TYPE_NORMAL)
        {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            btn_sw.setText("NORMAL VIEW");

        }
        else {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            btn_sw.setText("STREET VIEW");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng cibernetica = new LatLng(44.447850, 26.099152);
        mMap.addMarker(new MarkerOptions().position(cibernetica).title(getString(R.string.contactmaps_marker_dosbrains)).icon(BitmapDescriptorFactory.fromResource(R.drawable.hackerman2)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cibernetica));


    }
}
