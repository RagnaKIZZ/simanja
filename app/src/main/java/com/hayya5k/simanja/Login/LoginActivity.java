package com.hayya5k.simanja.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.google.android.material.textfield.TextInputEditText;
import com.hayya5k.simanja.Config.Config;
import com.hayya5k.simanja.Dashboard.MainActivity;
import com.hayya5k.simanja.Helper.Constant;
import com.hayya5k.simanja.Helper.Utils;
import com.hayya5k.simanja.Login.ModelLogin.ModelLogin;
import com.hayya5k.simanja.R;
import com.hayya5k.simanja.Register.RegisterActivity;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.pixplicity.easyprefs.library.Prefs;

import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findView();




    }


    private void findView(){


        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                TextInputEditText edt_nohp = findViewById(R.id.edt_nohp);
                TextInputEditText edt_password = findViewById(R.id.edt_password);


                Login(edt_nohp.getText().toString(),edt_password.getText().toString());



            }
        });

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));

            }
        });





    }


    private void Login(String hp,String password){
        final KProgressHUD hud = new KProgressHUD(LoginActivity.this);
        Utils.ShowProgress(hud,null,null,true);
        AndroidNetworking.post(Config.URL_LOGIN)
                .addBodyParameter("no_hp",hp)
                .addBodyParameter("password",password)
                .build()
                .getAsOkHttpResponseAndObject(ModelLogin.class, new OkHttpResponseAndParsedRequestListener<ModelLogin>()
                {
                    @Override
                    public void onResponse(Response okHttpResponse, ModelLogin response) {
                        hud.dismiss();

                        Log.d("LoginActivity", "onResponse: "+response.toString());
                        if(okHttpResponse.isSuccessful()){
                            if (response.getMeta().getCode() == 200){

                                /*
                                 * Keterangan :
                                 * Jika berhasil login maka mengambil data dan pindah halaman ke DASHBOARD;
                                 * KDV 2019-11-08
                                 */
                                Utils.ShowToast(LoginActivity.this,response.getMeta().getMessage());

                                String id_user_mobile = response.getData().getIdUserMobile();
                                String username = response.getData().getUsername();
                                String email = response.getData().getEmail();
                                String nama = response.getData().getNama();
                                String alamat = response.getData().getAlamat();
                                String no_hp = response.getData().getNoHp();
                                String foto = Config.URL_IMAGE+response.getData().getFoto();
                                String token = response.getToken();


                                Prefs.putString(Constant.id_user_mobile,id_user_mobile);
                                Prefs.putString(Constant.username,username);
                                Prefs.putString(Constant.email,email);
                                Prefs.putString(Constant.nama,nama);
                                Prefs.putString(Constant.alamat,alamat);
                                Prefs.putString(Constant.no_hp,no_hp);
                                Prefs.putString(Constant.foto,foto);
                                Prefs.putString(Constant.token,token);
                                Prefs.putBoolean(Constant.isLogin,true);

                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                finish();

                            }else{

                                Utils.ShowToast(LoginActivity.this,response.getMeta().getMessage());

                            }
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("LoginActivity", "onError: "+anError.getErrorDetail());

                    }
                });

    }



}
