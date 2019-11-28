package com.hayya5k.simanja.Pengaduan;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListAdapter;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListItem;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.hayya5k.simanja.Config.Config;
import com.hayya5k.simanja.Helper.Constant;
import com.hayya5k.simanja.Helper.ModelMeta.ModelMeta;
import com.hayya5k.simanja.Helper.Utils;
import com.hayya5k.simanja.Pengaduan.MapActivity.MapsActivity;
import com.hayya5k.simanja.R;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.File;

import okhttp3.Response;

public class TambahPengaduanActivity extends AppCompatActivity {

    private String TAG = "RegisterActivity";
    private String id_kategori = "";
    private Context ctx;
    private TextInputEditText edt_judul,edt_kategori,edt_pesan,edt_alamat;
    private File fileimage;
    private String koordinat = "0,0";
    private ImageView img,img_map,img_find_location;
    private ProgressBar pb;
    private String lat = "0";
    private String lng = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pengaduan);
        findView();
    }


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    private void findView(){
        ctx = this;
        edt_judul = findViewById(R.id.edt_judul);
        edt_kategori = findViewById(R.id.edt_kategori);
        edt_alamat = findViewById(R.id.edt_alamat);
        edt_pesan = findViewById(R.id.edt_pesan);
        img             = findViewById(R.id.img);
        img_map         = findViewById(R.id.img_map);
        img_find_location         = findViewById(R.id.img_find_location);
        pb              = findViewById(R.id.pb);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        ImagePicker.Companion.with(TambahPengaduanActivity.this)
                                .crop()	    			//Crop image(Optional), Check Customization for more option
                                .compress(1024)			//Ukuran menjadi dibawah 1 MB
                                .maxResultSize(1080, 1080)
                                .start();

            }
        });




        findViewById(R.id.btn_simpan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DlgKonfirmasi();
            }
        });


        edt_kategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getKategori();
            }
        });



        img_find_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(TambahPengaduanActivity.this, MapsActivity.class),333);
            }
        });

        img_map.setVisibility(View.INVISIBLE);
        img_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivityForResult(new Intent(TambahPengaduanActivity.this, MapsActivity.class),333);
            }
        });

    }

    private void DlgKonfirmasi() {
        final String judul = edt_judul.getText().toString();
        final String alamat = edt_alamat.getText().toString().trim();
        final String pesan = edt_pesan.getText().toString().trim();

        if (fileimage == null) {
            Utils.ShowToast(ctx, "Gambar tidak boleh kosong");
        } else if (lat.matches("0") || lng.matches("0")) {
            Utils.ShowToast(ctx, "Lokasi belum dipilih");
        } else {
            final CFAlertDialog.Builder mbuilder = new CFAlertDialog.Builder(ctx)
                    .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                    .setTitle(getResources().getString(R.string.konfirmasi))
                    .setMessage(getResources().getString(R.string.kirim_pengaduan))
                    .setOuterMargin(24)
                    .setCancelable(false)
                    .addButton(getResources().getString(R.string.ya), -1, getResources().getColor(R.color.colorPrimary), CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();


                            Simpan(Prefs.getString(Constant.id_user_mobile, ""), judul, id_kategori, fileimage, alamat, lat, lng, pesan);
                        }
                    }).addButton(getResources().getString(R.string.batal), -1, getResources().getColor(android.R.color.holo_red_light), CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            mbuilder.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == ImagePicker.REQUEST_CODE) {
                /*
                 * Keterangan :
                 * Mengambil data dari Image Picker
                 * KDV 2019-11-17
                 */
                fileimage = ImagePicker.Companion.getFile(data);
                /*
                 * Keterangan :
                 * Set img View dari File image yang kita set sebelumnya
                 * KDV 2019-11-17
                 */
                img.setImageURI(Uri.fromFile(fileimage));
                /*
                 * Keterangan :
                 * Membuat LOG untuk cek lokasi file
                 * KDV 2019-11-17
                 */
                Log.d(TAG, "onActivityResult: "+fileimage.getPath());
                Log.d(TAG, "onActivityResult: "+fileimage.getAbsolutePath());

            }else{

                if( data != null){
                    Log.d("TambahPengaduanActivity", "onActivityResult: " + data.getStringExtra("latitude"));
                    Log.d("TambahPengaduanActivity", "onActivityResult: " + data.getStringExtra("longitude"));
                    Log.d("TambahPengaduanActivity", "onActivityResult: " + data.getStringExtra("alamat"));

                    edt_alamat.setText(data.getStringExtra("alamat"));
                    lat = data.getStringExtra("latitude");
                    lng = data.getStringExtra("longitude");
                    LoadStaticMap(lat,lng);
                }

            }
        }
    }

    private void Simpan(String id_user,String judul, String kategori, File image, String alamat, String lat,String lng, String pesan){
        final KProgressHUD hud = new KProgressHUD(TambahPengaduanActivity.this);
        Utils.ShowProgress(hud,null,null,false);
        AndroidNetworking.upload(Config.INPUT_ADUAN)
                .addMultipartParameter("id_user",id_user)
                .addMultipartParameter("judul",judul)
                .addMultipartParameter("kategori",kategori)
                .addMultipartFile("foto",image)
                .addMultipartParameter("alamat",alamat)
                .addMultipartParameter("lat",lat)
                .addMultipartParameter("lng",lng)
                .addMultipartParameter("pesan",pesan)
                .build()
                .setUploadProgressListener(new UploadProgressListener() {
                    @Override
                    public void onProgress(long bytesUploaded, long totalBytes) {

                    }
                })
                .getAsOkHttpResponseAndObject(ModelMeta.class, new OkHttpResponseAndParsedRequestListener<ModelMeta>() {
                    @Override
                    public void onResponse(Response okHttpResponse, ModelMeta response) {

                        Log.d(TAG, "onResponse: "+response.toString());
                        hud.dismiss();

                        if(okHttpResponse.isSuccessful()){

                            if(response.getMeta().getCode() == 200) {
                                final CFAlertDialog.Builder mbuilder = new CFAlertDialog.Builder(ctx)
                                        .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                        .setTitle(getResources().getString(R.string.info))
                                        .setMessage(getResources().getString(R.string.tambah_pengaduan_berhasil))
                                        .setOuterMargin(24)
                                        .setCancelable(false)
                                        .addButton(getResources().getString(R.string.tutup), -1, getResources().getColor(R.color.colorPrimary), CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                Intent i = new Intent();
                                                setResult(RESULT_OK);
                                                finish();
                                            }
                                        });
                                mbuilder.show();


                            }else {

                                Utils.ShowToast(getApplicationContext(),response.getMeta().getMessage());
                            }

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        hud.dismiss();
                            Log.d("RegisterActivity", "onError: " + anError.getErrorDetail());
                            Utils.ShowToast(getApplicationContext(),anError.getErrorDetail());

                    }
                });

    }


    private void getKategori(){

        final MaterialSimpleListAdapter adapter = new MaterialSimpleListAdapter(new MaterialSimpleListAdapter.Callback() {
            @Override
            public void onMaterialListItemSelected(MaterialDialog dialog, int index, MaterialSimpleListItem item) {
                id_kategori = "";
                edt_kategori.setText(item.getContent());
                id_kategori = item.getTag().toString().trim();
                Log.d(TAG, "onMaterialListItemSelected: " + id_kategori);
                dialog.dismiss();
            }
        });

        adapter.add(new MaterialSimpleListItem.Builder(ctx)
                .content("Jalan")
                .tag("1")
                .backgroundColor(Color.WHITE)
                .build());

        adapter.add(new MaterialSimpleListItem.Builder(ctx)
                .content("Saluran")
                .tag("2")
                .backgroundColor(Color.WHITE)
                .build());

        adapter.add(new MaterialSimpleListItem.Builder(ctx)
                .content("Jembatan")
                .tag("3")
                .backgroundColor(Color.WHITE)
                .build());

        adapter.add(new MaterialSimpleListItem.Builder(ctx)
                .content("Turap")
                .tag("4")
                .backgroundColor(Color.WHITE)
                .build());

        adapter.add(new MaterialSimpleListItem.Builder(ctx)
                .content("Tandon")
                .tag("5")
                .backgroundColor(Color.WHITE)
                .build());

        new MaterialDialog.Builder(ctx)
                .title("Pilih kategori")
                .adapter(adapter, null)
                .show();
    }

    private void LoadStaticMap(String lat, String lng){
        String url = "https://maps.googleapis.com/maps/api/staticmap?center="+lat+","+lng+"&zoom=20&size=800x400&key="+getResources().getString(R.string.google_maps_key);
        Utils.LoadImageMap(TambahPengaduanActivity.this,url,pb,img_map,img_find_location);
    }

}
