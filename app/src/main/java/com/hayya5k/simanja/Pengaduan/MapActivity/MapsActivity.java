package com.hayya5k.simanja.Pengaduan.MapActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
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
import com.google.android.gms.maps.model.LatLng;
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

public class MapsActivity extends AppCompatActivity
        implements GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnCameraIdleListener,
        OnMapReadyCallback, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraMoveStartedListener {
    private LatLng defaultla_lang = new LatLng(-6.178306,106.631889);
    private LatLng set_latlong = new LatLng(0,0);
    private TextView txt_alamat;
    private GoogleMap mMap;
    private float zoomLevel = 20.0f;
//    Marker marker;
    private ImageView img_pin;
    private FusedLocationProviderClient fusedLocationProviderClient;
//    Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        txt_alamat =  findViewById(R.id.txt_alamat);
        img_pin =  findViewById(R.id.img_pin);

        final SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        final KProgressHUD hud = new KProgressHUD(this);
        Utils.ShowProgress(hud,null,null,false);

        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION};
        Permissions.check(MapsActivity.this, permissions, null, null, new PermissionHandler() {
            @SuppressLint("MissingPermission")
            @Override
            public void onGranted() {
                fusedLocationProviderClient.getLastLocation()
                        .addOnSuccessListener(MapsActivity.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                hud.dismiss();
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    defaultla_lang = new LatLng(location.getLatitude(),location.getLongitude());
                                    Log.d("MapsActivity", "onSuccess: "+ defaultla_lang.latitude+" - "+defaultla_lang.longitude);
                                    mapFragment.getMapAsync(MapsActivity.this);
                                }
                            }
                        });
            }
            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                hud.dismiss();
                final CFAlertDialog.Builder mbuilder = new CFAlertDialog.Builder(MapsActivity.this)
                        .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                        .setTitle(getResources().getString(R.string.konfirmasi))
                        .setMessage(getResources().getString(R.string.membutuhkan_izin_lokasi))
                        .setOuterMargin(24)
                        .setCancelable(false)
                        .addButton(getResources().getString(R.string.izinkan), -1, getResources().getColor(R.color.colorPrimary), CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                mbuilder.show();
            }
        });

//        setUpMapIfNeeded();

        findViewById(R.id.btn_pilih).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String lat = String.valueOf(set_latlong.latitude);
                String lng = String.valueOf(set_latlong.longitude);
                Intent i = new Intent();
                i.putExtra("latitude",lat);
                i.putExtra("longitude",lng);
                i.putExtra("alamat",txt_alamat.getText());
                setResult(RESULT_OK,i);
                finish();
            }
        });

    }

//    private void setUpMapIfNeeded() {
//        // Do a null check to confirm that we have not already instantiated the map.
//        if (mMap == null) {
//            // Try to obtain the map from the SupportMapFragment.
//            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
//            String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION,
//                    Manifest.permission.ACCESS_FINE_LOCATION};
//            Permissions.check(MapsActivity.this, permissions, null, null, new PermissionHandler() {
//                @SuppressLint("MissingPermission")
//                @Override
//                public void onGranted() {
//                    mMap.setMyLocationEnabled(true);
//
//                }
//                @Override
//                public void onDenied(Context context, ArrayList<String> deniedPermissions) {
//                }
//            });
//            // Check if we were successful in obtaining the map.
//            if (mMap != null) {
//                mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
//                    @Override
//                    public void onMyLocationChange(Location arg0) {
//                        mMap.addMarker(new MarkerOptions().position(new LatLng(arg0.getLatitude(), arg0.getLongitude())).title("It's Me!"));
//                    }
//                });
//            }
//        }
//    }

    @Override
    public void onMapReady(final GoogleMap map) {
        mMap = map;


        LatLng latLng = new LatLng(defaultla_lang.latitude,defaultla_lang.longitude);
        final String address = getAddress(this,defaultla_lang.latitude,defaultla_lang.longitude);
        txt_alamat.setText(address);
//        if(marker != null){
//            marker.remove();
//        }
//        marker = mMap.addMarker(new MarkerOptions()
//                .position(latLng).title(address)
//                .icon(BitmapDescriptorFactory
//                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnCameraIdleListener(this);

        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION};
        Permissions.check(MapsActivity.this, permissions, null, null, new PermissionHandler() {
            @SuppressLint("MissingPermission")
            @Override
            public void onGranted() {
                mMap.setMyLocationEnabled(true);
                map.isMyLocationEnabled();
            }
            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
            }
        });
    }

    @Override
    public void onMapClick(LatLng point) {
//        mTapTextView.setText("tapped, point=" + point);
    }

    @Override
    public void onMapLongClick(LatLng point) {
//        mTapTextView.setText("long pressed, point=" + point);
    }

    @Override
    public void onCameraIdle() {
        set_latlong = new LatLng(mMap.getCameraPosition().target.latitude,mMap.getCameraPosition().target.longitude);
        String alamat = getAddress(MapsActivity.this,set_latlong.latitude,set_latlong.longitude);
        txt_alamat.setText(alamat);
//        if(marker != null){
//            marker.remove();
//        }
//        marker = mMap.addMarker(new MarkerOptions()
//                .position(mMap.getCameraPosition().target).title(alamat)
//                .icon(BitmapDescriptorFactory
//                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));

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

    @Override
    public void onCameraMove() {
//        if(marker != null){
//            marker.remove();
//        }
        mMap.clear();
    }

    @Override
    public void onCameraMoveStarted(int i) {
//        if(marker != null){
//            marker.remove();
//        }
        mMap.clear();


    }
}
