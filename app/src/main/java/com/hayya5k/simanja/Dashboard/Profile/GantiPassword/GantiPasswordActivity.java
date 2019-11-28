package com.hayya5k.simanja.Dashboard.Profile.GantiPassword;

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
import com.hayya5k.simanja.Helper.Constant;
import com.hayya5k.simanja.Helper.ModelMeta.ModelMeta;
import com.hayya5k.simanja.Helper.Utils;
import com.hayya5k.simanja.R;
import com.hayya5k.simanja.Register.ModelJabatan.ModelJabatan;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.pixplicity.easyprefs.library.Prefs;

import okhttp3.Response;

public class GantiPasswordActivity extends AppCompatActivity {

    private String TAG = "RegisterActivity";
    private String id_jabatan = "";
    private Context ctx;
    private TextInputEditText edt_ulangipassword,edt_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gantipassword);



        findView();





    }


    private void findView(){

        ctx = this;

        edt_ulangipassword = findViewById(R.id.edt_ulangipassword);
        edt_password = findViewById(R.id.edt_password);




        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = edt_password.getText().toString().trim();
                String ulangipassword = edt_ulangipassword.getText().toString().trim();

                    Update(Prefs.getString(Constant.id_user_mobile,""),password, ulangipassword);

            }
        });





    }

    private void Update(String id_user,String password,String konfirmasi_password){

        final KProgressHUD hud = new KProgressHUD(GantiPasswordActivity.this);
        Utils.ShowProgress(hud,null,null,false);

        AndroidNetworking.post(Config.UPDATE_PASSWORD)
                .addBodyParameter("id_user",id_user)
                .addBodyParameter("password",password)
                .addBodyParameter("konfirmasi_password",konfirmasi_password)
                .build()
                .getAsOkHttpResponseAndObject(ModelMeta.class, new OkHttpResponseAndParsedRequestListener<ModelMeta>() {
                    @Override
                    public void onResponse(Response okHttpResponse, ModelMeta response) {

                        hud.dismiss();
                        Log.d(TAG, "onResponse: "+response.toString());

                        if(okHttpResponse.isSuccessful()){

                            if(response.getMeta().getCode() == 200) {

                                Utils.ShowToast(getApplicationContext(), response.getMeta().getMessage());


                                finish();
                            }else {

                                Utils.ShowToast(getApplicationContext(),response.getMeta().getMessage());
                            }

                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                        Log.d(TAG, "onError: "+anError.getErrorDetail());
                        Log.d(TAG, "onError: "+anError.getErrorCode());
                        Log.d(TAG, "onError: "+anError.getErrorBody());

                        hud.dismiss();

                        Utils.ShowToast(getApplicationContext(),anError.getErrorDetail());

                    }
                });

    }





}
