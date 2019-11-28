package com.hayya5k.simanja.Helper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.kaopiz.kprogresshud.KProgressHUD;

public class Utils {



    public static void ShowProgress(@NonNull KProgressHUD hud, String judul, String deksripsi, boolean disable_backpress){
        /*
         * Keterangan :
         * Kondisi ketika judul diisi null, maka menampilkan pesan Loading (Default yang kita tentukan)
         * KDV
         */
        if(judul == null){
            judul = "Loading";
        }
        /*
         * Keterangan :
         * Kondisi ketika deskripsi diisi null, maka menampilkan pesan Mohon tunggu (Default yang kita tentukan)
         * KDV
         */
        if (deksripsi == null){
            deksripsi = "Mohon tunggu...";
            Log.d("Utils", "ShowProgress: "+ deksripsi);
        }
        hud.setLabel(judul);
        hud.setDimAmount(0.5f);
        hud.setDetailsLabel(deksripsi);
        hud.setCancellable(disable_backpress);
        hud.setCornerRadius(14);
        hud.show();

    }

    /*
     * Keterangan :
     * Membuat function Toast untuk dipanggil
     * KDV
     */
    public static void ShowToast(Context context,String pesan){
        Toast.makeText(context,pesan,Toast.LENGTH_SHORT).show();
    }


    public static void LoadImageMap(@NonNull Context context, @NonNull String url, @NonNull final ProgressBar pb, @NonNull final ImageView img, final ImageView img2){
        pb.setVisibility(View.VISIBLE);
        Glide.with(context)
                .load(url)
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        pb.setVisibility(View.INVISIBLE);
                        img2.setVisibility(View.INVISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        pb.setVisibility(View.INVISIBLE);
                        img.setVisibility(View.VISIBLE);
                        img2.setVisibility(View.INVISIBLE);
                        return false;
                    }
                }).into(img);
    }

    public static void LoadImage(@NonNull Context context, @NonNull String url, @NonNull final ProgressBar pb, @NonNull final ImageView img){
        pb.setVisibility(View.VISIBLE);
        Glide.with(context)
                .load(url)
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        pb.setVisibility(View.INVISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        pb.setVisibility(View.INVISIBLE);
                        img.setVisibility(View.VISIBLE);
                        return false;
                    }
                }).into(img);
    }



}
