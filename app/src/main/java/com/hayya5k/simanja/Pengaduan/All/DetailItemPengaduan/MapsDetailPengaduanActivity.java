package com.hayya5k.simanja.Pengaduan.All.DetailItemPengaduan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.hayya5k.simanja.Helper.Utils;
import com.hayya5k.simanja.R;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsDetailPengaduanActivity extends AppCompatActivity {
    public static LatLng set_latlong = new LatLng(0,0);
    private TextView txt_alamat;
    private GoogleMap mMap;
    private float zoomLevel = 20.0f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity_pengaduan);
        txt_alamat =  findViewById(R.id.txt_alamat);

        final SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {
                mMap = map;


                final String address = getAddress(MapsDetailPengaduanActivity.this,set_latlong.latitude,set_latlong.longitude);
                txt_alamat.setText(address);

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(set_latlong, zoomLevel));

                mMap.addMarker(new MarkerOptions()
                        .position(set_latlong).title(address)
                        .icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
        });


        findViewById(R.id.btn_getlokasi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:"+set_latlong.latitude+","+set_latlong.longitude+"?z=20");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }else{
                    Utils.ShowToast(MapsDetailPengaduanActivity.this,"Google maps tidak terinstal");
                }
            }
        });



    }


    public String getAddress(Context ctx, double lat, double lng){
        String fullAdd=null;
        try{
            Geocoder geocoder= new Geocoder(ctx, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(lat,lng,1);
            if(addresses.size()>0){
                Address address = addresses.get(0);
                fullAdd = address.getAddressLine(0);
                /*
                 * Keterangan :
                 * String Location = address.getLocality();
                    String zip = address.getPostalCode();
                    String Country = address.getCountryName();
                 * KDV 2019-11-20
                 */
            }


        }catch(IOException ex){
            ex.printStackTrace();
        }
        return fullAdd;
    }


}
