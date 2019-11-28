package com.hayya5k.simanja.Dashboard.Profile;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.hayya5k.simanja.Config.Config;
import com.hayya5k.simanja.Dashboard.MainActivity;
import com.hayya5k.simanja.Dashboard.Profile.GantiPassword.GantiPasswordActivity;
import com.hayya5k.simanja.Dashboard.Profile.ModelUserProfile.ModelUserProfile;
import com.hayya5k.simanja.Helper.Constant;
import com.hayya5k.simanja.Helper.ModelMeta.Meta;
import com.hayya5k.simanja.Helper.ModelMeta.ModelMeta;
import com.hayya5k.simanja.Helper.Utils;
import com.hayya5k.simanja.Login.LoginActivity;
import com.hayya5k.simanja.Login.ModelLogin.ModelLogin;
import com.hayya5k.simanja.Pengaduan.TambahPengaduanActivity;
import com.hayya5k.simanja.R;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.File;
import java.util.Objects;

import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Activity {

    private TextInputEditText edt_username,edt_email,edt_nama,edt_alamat,edt_nohp;
    private ImageView img_user;
    private String id_user_mobile ="";
    private ProgressBar pb;
    private File fileimage;
    private String TAG = "ProfileFragment";
    private String url_foto_profile ="";


    public ProfileFragment() {
        // Required empty public constructor
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.fragment_profile, container, false);
//
//
//
//
//        return view;
//
//    }
//
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        findViews(view);
//
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        findViews();
    }

    @SuppressLint("SetTextI18n")
    private void findViews(){

        edt_alamat = findViewById(R.id.edt_alamat);
        edt_nama = findViewById(R.id.edt_nama);
        edt_nohp = findViewById(R.id.edt_nohp);
        edt_username = findViewById(R.id.edt_username);
        edt_email = findViewById(R.id.edt_email);
        img_user = findViewById(R.id.img_user);
        pb = findViewById(R.id.pb);

        GetUser(Prefs.getString(Constant.id_user_mobile,""));

        findViewById(R.id.btn_gantipass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileFragment.this, GantiPasswordActivity.class));
            }
        });


        img_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(ProfileFragment.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Ukuran menjadi dibawah 2 MB
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });


        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUpdate();
            }
        });



    }

    private void DialogUpdate(){
        final CFAlertDialog.Builder mbuilder = new CFAlertDialog.Builder(ProfileFragment.this)
                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                .setTitle(getResources().getString(R.string.konfirmasi))
                .setMessage("Update data profile ?")
                .setOuterMargin(24)
                .setCancelable(false)
                .addButton(getResources().getString(R.string.ya), -1, getResources().getColor(R.color.colorPrimary), CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        String nama = edt_nama.getText().toString();
                        String hp = edt_nohp.getText().toString().trim();
                        String email = edt_email.getText().toString().trim();
                        String alamat = edt_alamat.getText().toString().trim();
                        String username = edt_username.getText().toString().trim();


                        UpdateUser(id_user_mobile,nama,username,hp,alamat);
                    }
                })
                .addButton(getResources().getString(R.string.batal), -1, getResources().getColor(android.R.color.holo_red_light), CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        mbuilder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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

                /*
                 * Keterangan :
                 * Membuat LOG untuk cek lokasi file
                 * KDV 2019-11-17
                 */
                Log.d(TAG, "onActivityResult: "+fileimage.getPath());
                Log.d(TAG, "onActivityResult: "+fileimage.getAbsolutePath());


                UpdateFoto(Prefs.getString(Constant.id_user_mobile,""),fileimage);

            }
        }
    }



    private void GetUser(String id_user){
        final KProgressHUD hud = new KProgressHUD(ProfileFragment.this);
        Utils.ShowProgress(hud,null,null,true);
        AndroidNetworking.post(Config.PROFILE)
                .addBodyParameter("id_user",id_user)
                .build()
                .getAsOkHttpResponseAndObject(ModelUserProfile.class, new OkHttpResponseAndParsedRequestListener<ModelUserProfile>()
                {
                    @Override
                    public void onResponse(Response okHttpResponse, ModelUserProfile response) {
                        hud.dismiss();

                        Log.d("LoginActivity", "onResponse: "+response.toString());
                        if(okHttpResponse.isSuccessful()){
                            if (response.getMeta().getCode() == 200){

                                if(response.getData() != null ) {

                                    id_user_mobile = response.getData().get(0).getIdUserMobile();
                                    String username = response.getData().get(0).getUsername();
                                    String email = response.getData().get(0).getEmail();
                                    String nama = response.getData().get(0).getNama();
                                    String alamat = response.getData().get(0).getAlamat();
                                    String no_hp = response.getData().get(0).getNoHp();
                                    url_foto_profile = Config.URL_IMAGE + response.getData().get(0).getFoto();


                                    edt_alamat.setText(alamat);
                                    edt_username.setText(username);
                                    edt_nama.setText(nama);
                                    edt_nohp.setText(no_hp);
                                    edt_email.setText(email);



                                    Prefs.putString(Constant.id_user_mobile,id_user_mobile);
                                    Prefs.putString(Constant.username,username);
                                    Prefs.putString(Constant.email,email);
                                    Prefs.putString(Constant.nama,nama);
                                    Prefs.putString(Constant.alamat,alamat);
                                    Prefs.putString(Constant.no_hp,no_hp);
                                    Prefs.putString(Constant.foto,url_foto_profile);

                                    setResult(RESULT_OK);

                                    try {
                                        Utils.LoadImage(ProfileFragment.this, url_foto_profile, pb, img_user);
                                    }catch (Exception e){
                                        Log.d("LoadImage", "onResponse: "+e.getMessage());
                                    }




                                }else{
                                    Utils.ShowToast(ProfileFragment.this,"Data tidak ditemukan !!");
                                }


                            }else{

                                Utils.ShowToast(ProfileFragment.this,response.getMeta().getMessage());

                            }
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        hud.dismiss();
                        Log.d("LoginActivity", "onError: "+anError.getErrorDetail());

                    }
                });

    }

    private void UpdateUser(String id_user_mobile,String nama,String username,String no_hp,String alamat){
        final KProgressHUD hud = new KProgressHUD(ProfileFragment.this);
        Utils.ShowProgress(hud,null,null,true);
        AndroidNetworking.post(Config.UPDATE_PROFILE)
                .addBodyParameter("id_user_mobile",id_user_mobile)
                .addBodyParameter("nama",nama)
                .addBodyParameter("username",username)
                .addBodyParameter("no_hp",no_hp)
                .addBodyParameter("no_hp_lama",Prefs.getString(Constant.no_hp,""))
                .addBodyParameter("alamat",alamat)
                .build()
                .getAsOkHttpResponseAndObject(ModelMeta.class, new OkHttpResponseAndParsedRequestListener<ModelMeta>()
                {
                    @Override
                    public void onResponse(Response okHttpResponse, ModelMeta response) {
                        hud.dismiss();

                        Log.d("LoginActivity", "onResponse: "+response.toString());
                        if(okHttpResponse.isSuccessful()){
                            if (response.getMeta().getCode() == 200){

                                Utils.ShowToast(ProfileFragment.this,response.getMeta().getMessage());

                                GetUser(Prefs.getString(Constant.id_user_mobile,""));

                                }else{
                                    Utils.ShowToast(ProfileFragment.this,response.getMeta().getMessage());
                                }


                            }else{

                                Utils.ShowToast(ProfileFragment.this,response.getMeta().getMessage());

                            }

                    }

                    @Override
                    public void onError(ANError anError) {
                        hud.dismiss();
                        Log.d("LoginActivity", "onError: "+anError.getErrorDetail());

                    }
                });

    }

    private void UpdateFoto(String id_user_mobile, final File fileimage){
        final KProgressHUD hud = new KProgressHUD(ProfileFragment.this);
        Utils.ShowProgress(hud,null,null,false);
        AndroidNetworking.upload(Config.UPDATE_FOTO_PROFILE)
                .addMultipartParameter("id_user_mobile",id_user_mobile)
                .addMultipartFile("foto",fileimage)
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
                                img_user.setImageURI(Uri.fromFile(fileimage));

                            }else {

                                Utils.ShowToast(ProfileFragment.this,response.getMeta().getMessage());
                            }

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        hud.dismiss();
                        Log.d(TAG, "onError: " + anError.getErrorDetail());
                        Utils.ShowToast(ProfileFragment.this,anError.getErrorDetail());

                    }
                });

    }



}
