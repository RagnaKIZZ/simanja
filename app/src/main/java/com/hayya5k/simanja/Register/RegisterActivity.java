package com.hayya5k.simanja.Register;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListAdapter;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListItem;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.google.android.material.textfield.TextInputEditText;
import com.hayya5k.simanja.Config.Config;
import com.hayya5k.simanja.Helper.ModelMeta.ModelMeta;
import com.hayya5k.simanja.Helper.Utils;
import com.hayya5k.simanja.R;
import com.hayya5k.simanja.Register.ModelJabatan.ModelJabatan;
import com.kaopiz.kprogresshud.KProgressHUD;

import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    private String TAG = "RegisterActivity";
    private String id_jabatan = "";
    private Context ctx;
    private TextInputEditText edt_username,edt_password,edt_email,edt_nama,edt_alamat,edt_nohp,edt_jabatan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        findView();





    }


    private void findView(){

        ctx = this;

        edt_alamat = findViewById(R.id.edt_alamat);
        edt_jabatan = findViewById(R.id.edt_jabatan);
        edt_nama = findViewById(R.id.edt_nama);
        edt_nohp = findViewById(R.id.edt_nohp);
        edt_password = findViewById(R.id.edt_password);
        edt_username = findViewById(R.id.edt_username);
        edt_email = findViewById(R.id.edt_email);

        findViewById(R.id.txt_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String nama = edt_nama.getText().toString();
                String hp = edt_nohp.getText().toString().trim();
                String email = edt_email.getText().toString().trim();
                String alamat = edt_alamat.getText().toString().trim();
                String username = edt_username.getText().toString().trim();
                String password = edt_password.getText().toString().trim();
                Registrasi(username,password,email,nama,alamat,hp,id_jabatan);
            }
        });


        edt_jabatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJabatan();
            }
        });


    }

    private void Registrasi(String username,String password,String email,String nama,String alamat,String no_hp,String jabatan){
        /*
         * Keterangan :
         * Memebuat ProgressHud baru
         * KDV 2019-11-02
         */
        final KProgressHUD hud = new KProgressHUD(RegisterActivity.this);
        Utils.ShowProgress(hud,null,null,false);

        /*
         * Keterangan :
         * Membuat methode POST REGISTER
         * KDV 2019-11-02
         */
        AndroidNetworking.post(Config.URL_REGISTER)
                .addBodyParameter("username",username)
                .addBodyParameter("password",password)
                .addBodyParameter("email",email)
                .addBodyParameter("nama",nama)
                .addBodyParameter("alamat",alamat)
                .addBodyParameter("no_hp",no_hp)
                .addBodyParameter("jabatan",jabatan)
                .build()
                .getAsOkHttpResponseAndObject(ModelMeta.class, new OkHttpResponseAndParsedRequestListener<ModelMeta>() {
                    @Override
                    public void onResponse(Response okHttpResponse, ModelMeta response) {
                        /*
                         * Keterangan :
                         * Membuat Log untuk mengecek di logcat nantinya
                         * KDV 2019-11-02
                         */
                        Log.d("RegisterActivity", "onResponse: "+response.toString());

                        /*
                         * Keterangan :
                         * Menyembunyikan loading hud
                         * KDV 2019-11-02
                         */
                        hud.dismiss();

                        if(okHttpResponse.isSuccessful()){

                            if(response.getMeta().getCode() == 200) {

                                Utils.ShowToast(getApplicationContext(), response.getMeta().getMessage());

                                /*
                                 * Keterangan :
                                 * Menutup aplikasi jika tambah data berhasil
                                 * KDV 2019-11-03
                                 */

                                finish();
                            }else {
                                /*
                                 * Keterangan :
                                 * Jika gagal maka menampilkan pesan
                                 * KDV 2019-11-03
                                 */

                                Utils.ShowToast(getApplicationContext(),response.getMeta().getMessage());
                            }

                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                        Log.d("RegisterActivity", "onError: "+anError.getErrorDetail());
                        Log.d("RegisterActivity", "onError: "+anError.getErrorCode());
                        Log.d("RegisterActivity", "onError: "+anError.getErrorBody());

                        hud.dismiss();

                        Utils.ShowToast(getApplicationContext(),anError.getErrorDetail());

                    }
                });

    }

    private void getJabatan(){
        final KProgressHUD hud = new KProgressHUD(RegisterActivity.this);
        Utils.ShowProgress(hud,null,null,false);
        AndroidNetworking.post(Config.URL_JABATAN)
                .setPriority(Priority.HIGH)
                .build().getAsOkHttpResponseAndObject(ModelJabatan.class, new OkHttpResponseAndParsedRequestListener<ModelJabatan>() {
            @Override
            public void onResponse(Response okHttpResponse, ModelJabatan response) {
                hud.dismiss();
                if(okHttpResponse.isSuccessful()){
                    if(response.getMeta().getCode() == Config.CodeSukses){
                        if(response.getData().size()>0) {
                            final MaterialSimpleListAdapter adapter = new MaterialSimpleListAdapter(new MaterialSimpleListAdapter.Callback() {
                                @Override
                                public void onMaterialListItemSelected(MaterialDialog dialog, int index, MaterialSimpleListItem item) {
                                    id_jabatan = "";
                                    edt_jabatan.setText(item.getContent());
                                    id_jabatan = item.getTag().toString().trim();
                                    Log.d(TAG, "onMaterialListItemSelected: " + id_jabatan);
                                    dialog.dismiss();
                                }
                            });
                            for (int i = 0; i<response.getData().size(); i++){
                                    final String id_jabatan = response.getData().get(i).getIdJabatan();
                                    final String nama_jabatan = response.getData().get(i).getNamaJabatan();

                                    Log.d(TAG, "onResponse: "+id_jabatan);
                                    Log.d(TAG, "onResponse: "+nama_jabatan);
                                    adapter.add(new MaterialSimpleListItem.Builder(ctx)
                                            .content(nama_jabatan)
                                            .tag(id_jabatan)
                                            .backgroundColor(Color.WHITE)
                                            .build());
                            }
                            new MaterialDialog.Builder(ctx)
                                    .title("Pilih Bank")
                                    .adapter(adapter, null)
                                    .show();
                        }else {
                            Utils.ShowToast(ctx,"Data bank tidak ditemukan");
                        }
                    }
                }
            }

            @Override
            public void onError(ANError anError) {
                    hud.dismiss();
                    Log.d("RegisterActivity", "onError: "+anError.getErrorDetail());
            }
        });
    }






}
