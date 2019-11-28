package com.hayya5k.simanja.Pengaduan.All;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.hayya5k.simanja.Config.Config;
import com.hayya5k.simanja.Helper.Constant;
import com.hayya5k.simanja.Helper.Utils;
import com.hayya5k.simanja.Pengaduan.All.DetailItemPengaduan.DetailItemPengaduanActivity;
import com.hayya5k.simanja.Pengaduan.ModelDetailPengaduan.DataItem;
import com.hayya5k.simanja.Pengaduan.ModelDetailPengaduan.ModelDataPengajuan;
import com.hayya5k.simanja.R;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.pixplicity.easyprefs.library.Prefs;

import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;

import android.app.SearchManager;
import android.widget.EditText;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.Spanned;

import okhttp3.Response;


public class DetailPengaduanActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private Toolbar toolbar;

    private DetailPengaduanAdapter mAdapter;

    private ArrayList<DataItem> modelList = new ArrayList<>();

    private String TAG = "DetailPengaduanActivity";
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pengaduan);


        findViews();



    }

    private void findViews() {
        ctx = this;
        toolbar =  findViewById(R.id.toolbar);
        recyclerView =  findViewById(R.id.recycler_view);

        mAdapter = new DetailPengaduanAdapter(ctx, modelList);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        initToolbar(getIntent().getStringExtra("judul"));
        setAdapter(getIntent().getStringExtra("status"));


    }

    public void initToolbar(String title) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_search, menu);


        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat
                .getActionView(menu.findItem(R.id.action_search));

        SearchManager searchManager = (SearchManager) this.getSystemService(this.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));

        //changing edittext color
        EditText searchEdit = ((EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text));
        searchEdit.setTextColor(Color.WHITE);
        searchEdit.setHintTextColor(Color.WHITE);
        searchEdit.setBackgroundColor(Color.TRANSPARENT);
        searchEdit.setHint("Cari");

        InputFilter[] fArray = new InputFilter[2];
        fArray[0] = new InputFilter.LengthFilter(40);
        fArray[1] = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                for (int i = start; i < end; i++) {

                    if (!Character.isLetterOrDigit(source.charAt(i)))
                        return "";
                }


                return null;


            }
        };
        searchEdit.setFilters(fArray);
        View v = searchView.findViewById(androidx.appcompat.R.id.search_plate);
        v.setBackgroundColor(Color.TRANSPARENT);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<DataItem> filterList = new ArrayList<>();
                if (s.length() > 0) {
                    for (int i = 0; i < modelList.size(); i++) {
                        if (modelList.get(i).getJudul().toLowerCase().contains(s.toString().toLowerCase())) {
                            filterList.add(modelList.get(i));
                            mAdapter.updateList(filterList);
                        }
                    }

                } else {
                    mAdapter.updateList(modelList);
                }
                return false;
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setAdapter(String status) {

            final KProgressHUD hud = new KProgressHUD(DetailPengaduanActivity.this);
            Utils.ShowProgress(hud,null,null,true);
            AndroidNetworking.post(Config.DETAIL_PENGADUAN)
                    .addBodyParameter("id_user", Prefs.getString(Constant.id_user_mobile,""))
                    .addBodyParameter("status",status)
                    .build()
                    .getAsOkHttpResponseAndObject(ModelDataPengajuan.class, new OkHttpResponseAndParsedRequestListener<ModelDataPengajuan>()
                    {
                        @Override
                        public void onResponse(Response okHttpResponse, ModelDataPengajuan response) {
                            hud.dismiss();

                            Log.d(TAG, "onResponse: "+response.toString());
                            if(okHttpResponse.isSuccessful()){
                                if (response.getMeta().getCode() == 200){
                                    if(response.getData() != null) {
                                        for (int i = 0; i < response.getData().size(); i++) {
                                            final DataItem item = new DataItem();
                                            item.setIdPengaduan(response.getData().get(i).getIdPengaduan());
                                            item.setJudul(response.getData().get(i).getJudul());
                                            item.setPesan(response.getData().get(i).getPesan());
                                            item.setAlamat(response.getData().get(i).getAlamat());
                                            item.setFoto(response.getData().get(i).getFoto());
                                            item.setTglInput(response.getData().get(i).getTglInput());
                                            item.setLat(response.getData().get(i).getLat());
                                            item.setLng(response.getData().get(i).getLng());
                                            modelList.add(item);

                                        }

                                        recyclerView.setAdapter(mAdapter);

                                        mAdapter.SetOnItemClickListener(new DetailPengaduanAdapter.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(View view, int position, DataItem model) {

                                                Intent i = new Intent(DetailPengaduanActivity.this, DetailItemPengaduanActivity.class);
                                                i.putExtra("id_pengaduan", model.getIdPengaduan());
                                                Log.d("DetailPengaduanActivity", "onItemClick: " + model.getIdPengaduan());
                                                startActivity(i);

                                            }
                                        });
                                    }else{
                                        dialogNodata();
                                    }

                                }else{

                                    Utils.ShowToast(ctx,response.getMeta().getMessage());

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


        private void dialogNodata(){
            final CFAlertDialog.Builder mbuilder = new CFAlertDialog.Builder(ctx)
                    .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                    .setTitle(getResources().getString(R.string.oops))
                    .setMessage("Data "+getIntent().getStringExtra("judul")+" tidak ada !!")
                    .setOuterMargin(24)
                    .setCancelable(false)
                    .addButton(getResources().getString(R.string.ya), -1, getResources().getColor(R.color.colorPrimary), CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
            mbuilder.show();
        }




}
