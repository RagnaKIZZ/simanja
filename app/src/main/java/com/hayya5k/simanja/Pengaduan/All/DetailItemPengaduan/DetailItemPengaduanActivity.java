package com.hayya5k.simanja.Pengaduan.All.DetailItemPengaduan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hayya5k.simanja.Config.Config;
import com.hayya5k.simanja.Helper.Constant;
import com.hayya5k.simanja.Helper.Utils;
import com.hayya5k.simanja.Pengaduan.All.DetailItemPengaduan.ModelDetailItemPengaduan.KomentarItem;
import com.hayya5k.simanja.Pengaduan.All.DetailItemPengaduan.ModelDetailItemPengaduan.ModelDetailItemPengaduan;
import com.hayya5k.simanja.R;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import okhttp3.Response;

public class DetailItemPengaduanActivity extends AppCompatActivity {

    private TextView txt_nama,txt_hp,txt_email,txt_alamat,txt_title,txt_keterangan;
    private ImageView img_map,img_pengaduan;
    private ProgressBar pb_pengaduan,pb,pb_data;
    private RecyclerView rv;

    private Toolbar toolbar;
    private String lat = "0";
    private String lng = "0";


    private DetailItemPengaduanAdapter mAdapter;

    private Context ctx;
    private String TAG = "DetailItemPengaduanActivity";

    private ArrayList<KomentarItem> modelList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item_pengaduan);


        findView();

    }

    private void findView(){

        ctx = this;


        toolbar = (Toolbar) findViewById(R.id.toolbar);




        rv =  findViewById(R.id.rv);
        img_pengaduan       =  findViewById(R.id.img_pengaduan);
        img_map             =  findViewById(R.id.img_map);
        txt_nama            =  findViewById(R.id.txt_nama);
        txt_email           =  findViewById(R.id.txt_email);
        txt_alamat          =  findViewById(R.id.txt_alamat);
        txt_hp              =  findViewById(R.id.txt_hp);
        txt_keterangan      =  findViewById(R.id.txt_keterangan);
        txt_title           =  findViewById(R.id.txt_title);
        pb                  =  findViewById(R.id.pb);
        pb_data             =  findViewById(R.id.pb_data);
        pb_pengaduan        =  findViewById(R.id.pb_pengaduan);




        mAdapter = new DetailItemPengaduanAdapter(ctx, modelList);

        rv.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        rv.setLayoutManager(layoutManager);

        Log.d("DetailPengaduanActivity", "onItemClick: "+getIntent().getStringExtra("id_pengaduan"));
        LoadData(getIntent().getStringExtra("id_pengaduan"));
    }


    private void LoadData(String id_pengaduan){
        pb_data.setVisibility(View.VISIBLE);
        AndroidNetworking.post(Config.DETAIL_ITEMPENGADUAN)
                .addBodyParameter("id_pengaduan",id_pengaduan)
                .build()
                .getAsOkHttpResponseAndObject(ModelDetailItemPengaduan.class, new OkHttpResponseAndParsedRequestListener<ModelDetailItemPengaduan>() {
                    @Override
                    public void onResponse(Response okHttpResponse, final ModelDetailItemPengaduan response) {
                        pb_data.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "onResponse: "+response.toString());

                        if(okHttpResponse.isSuccessful()){
                            if(response.getMeta().getCode() == Config.CodeSukses){


                                txt_nama.setText(Prefs.getString(Constant.nama,""));
                                txt_hp.setText(Prefs.getString(Constant.no_hp,""));
                                txt_email.setText(Prefs.getString(Constant.email,""));

                                txt_alamat.setText(response.getData().get(0).getAlamat());

                                lat = response.getData().get(0).getLat();
                                lng = response.getData().get(0).getLng();

                                txt_title.setText(response.getData().get(0).getJudul());
                                txt_keterangan.setText(response.getData().get(0).getPesan());

                                toolbar.setTitle(response.getData().get(0).getJudul());
                                setSupportActionBar(toolbar);

                                LoadStaticMap(lat,lng);

                                try {

                                    Utils.LoadImage(ctx, Config.URL_IMAGE + response.getData().get(0).getFoto(), pb_pengaduan, img_pengaduan);

                                }catch (Exception e){

                                }

                                img_map.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        MapsDetailPengaduanActivity.set_latlong = new LatLng(Double.parseDouble(response.getData().get(0).getLat()),Double.parseDouble(response.getData().get(0).getLng()));
                                        startActivity(new Intent(DetailItemPengaduanActivity.this,MapsDetailPengaduanActivity.class));
                                    }
                                });


                                if(response.getKomentar() != null) {
                                    for (int i = 0; i < response.getKomentar().size(); i++) {
                                        final KomentarItem item = new KomentarItem();
                                        item.setEmail(response.getKomentar().get(i).getEmail());
                                        item.setKomentar(response.getKomentar().get(i).getKomentar());
                                        item.setUsername(response.getKomentar().get(i).getUsername());

                                        modelList.add(item);
                                    }

                                    rv.setAdapter(mAdapter);
                                }


                            }else{
                                Utils.ShowToast(ctx,response.getMeta().getMessage());
                            }
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        pb_data.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "onError: "+anError.getErrorDetail());
                    }
                });
    }

    private void LoadStaticMap(String lat, String lng){
        String url = "https://maps.googleapis.com/maps/api/staticmap?center="+lat+","+lng+"&zoom=20&size=800x400&key="+getResources().getString(R.string.google_maps_key);
        try {
            Utils.LoadImage(DetailItemPengaduanActivity.this, url, pb, img_map);
        }catch (Exception e){

        }
    }
}
