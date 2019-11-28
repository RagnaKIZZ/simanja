package com.hayya5k.simanja.Dashboard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.android.material.navigation.NavigationView;
import com.hayya5k.simanja.Config.Config;
import com.hayya5k.simanja.Dashboard.About.AboutFragment;
import com.hayya5k.simanja.Dashboard.MenuDashboard.MenuDashboarFragment;
import com.hayya5k.simanja.Dashboard.Profile.ProfileFragment;
import com.hayya5k.simanja.Helper.Constant;
import com.hayya5k.simanja.Helper.Utils;
import com.hayya5k.simanja.Login.LoginActivity;
import com.hayya5k.simanja.R;
import com.pixplicity.easyprefs.library.Prefs;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private View header_view;
    private int fragOpened;
    private Context ctx;
    private DrawerLayout drawer;
    private long backpress;
    private ProgressBar pb;
    private ImageView img;
    private TextView txt_namauser,txt_emailuser;
    private String url_foto_profile;
    public static boolean onsearch_produk = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findView();

    }

    private void findView(){
        ctx = this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);


        NavigationView navigationView = findViewById(R.id.nav_view);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


        /*
         * Keterangan :
         * Deklarasi Header View Drawer
         * KDV 2019-11-09
         */
        header_view = navigationView.getHeaderView(0);

        /*
         * Keterangan :
         * Mendelakrasikan view2 didalam Header
         * KDV 2019-11-09
         */
        txt_namauser  = header_view.findViewById(R.id.txt_namauser);
        txt_emailuser = header_view.findViewById(R.id.txt_emailuser);
        pb = header_view.findViewById(R.id.pb);
        img = header_view.findViewById(R.id.img);

        url_foto_profile = Prefs.getString(Constant.foto,"");

        Log.d("MainActivity", "findView: "+url_foto_profile);

        try {
            Utils.LoadImage(MainActivity.this, url_foto_profile, pb, img);
        }catch (Exception e){
            Log.d("LoadImage", "onResponse: "+e.getMessage());
        }

        /*
         * Keterangan :
         * Mengeset value view yang ada pada header
         * KDV 2019-11-09
         */
        txt_namauser.setText(Prefs.getString("nama",""));
        txt_emailuser.setText(Prefs.getString("email",""));


        ShowFrag(new MenuDashboarFragment());

        findViewById(R.id.txt_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogLogout();
            }
        });

        fragOpened = R.id.nav_dashboard;
    }



//    @Override
//    public void onBackPressed() {
//        /**
//         * Keterangan :
//         * Variable drawer
//         * KDV 2019-11-09
//         **/
//
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame);
            if (!onsearch_produk) {
                if (!(fragment instanceof OnBackPressedListner) || !((OnBackPressedListner) fragment).onBackPressed()) {
                    if (backpress + 2000 > System.currentTimeMillis()) super.onBackPressed();
                    else
                        Utils.ShowToast(MainActivity.this, getResources().getString(R.string.tekan_sekali_lagi_untuk_menutup));
                    backpress = System.currentTimeMillis();
                } else {

                }
            }
        }
    }


    public interface OnBackPressedListner{
        boolean onBackPressed();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {

            DialogLogout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        final int id = item.getItemId();
        drawer.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (id == R.id.nav_dashboard) {

                    if(fragOpened != id ) {
                        fragOpened = id;
                        ShowFrag(new MenuDashboarFragment());
                    }


                } else if (id == R.id.nav_profile) {
                    startActivityForResult(new Intent(MainActivity.this,ProfileFragment.class),77);
//                    if(fragOpened != id ) {
//                        fragOpened = id;
//                        ShowFrag(new ProfileFragment());
//                    }

                } else if (id == R.id.nav_about) {
                    if(fragOpened != id ) {
                        fragOpened = id;
                        ShowFrag(new AboutFragment());
                    }
                }
            }
        },300);



        drawer.closeDrawer(GravityCompat.START);
        return false;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            url_foto_profile = Prefs.getString(Constant.foto,"");
            txt_namauser.setText(Prefs.getString("nama",""));
            txt_emailuser.setText(Prefs.getString("email",""));

            Log.d("MainActivity", "findView: "+url_foto_profile);

            try {
                Utils.LoadImage(MainActivity.this, url_foto_profile, pb, img);
            }catch (Exception e){
                Log.d("LoadImage", "onResponse: "+e.getMessage());
            }
        }
    }

    private void DialogLogout(){
        final CFAlertDialog.Builder mbuilder = new CFAlertDialog.Builder(ctx)
                .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                .setTitle(getResources().getString(R.string.konfirmasi))
                .setMessage(getResources().getString(R.string.yakin_untuk_keluar))
                .setOuterMargin(24)
                .setCancelable(false)
                .addButton(getResources().getString(R.string.ya), -1, getResources().getColor(R.color.colorPrimary), CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
                        finish();
                        Prefs.clear();
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


    private boolean ShowFrag(Fragment fragment) {

        if (fragment != null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();

            return true;
        }

        return false;
    }







}
