package com.hayya5k.simanja.Dashboard.MenuDashboard;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseAndParsedRequestListener;
import com.hayya5k.simanja.Config.Config;
import com.hayya5k.simanja.Dashboard.MenuDashboard.ResponseDashboard.ModelDashboard;
import com.hayya5k.simanja.Helper.Constant;
import com.hayya5k.simanja.Helper.Utils;
import com.hayya5k.simanja.Pengaduan.All.DetailPengaduanActivity;
import com.hayya5k.simanja.Pengaduan.TambahPengaduanActivity;
import com.hayya5k.simanja.R;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.pixplicity.easyprefs.library.Prefs;


import android.view.ViewGroup;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */


public class MenuDashboarFragment extends Fragment {


    private RecyclerView recyclerView;

    private MenuDashboardAdapter mAdapter;

    private ArrayList<MenuDashboardModel> modelList = new ArrayList<>();


    public MenuDashboarFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu_dashboar, container, false);




        return view;

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);

    }


    private void findViews(View view) {

        recyclerView = view.findViewById(R.id.recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MenuDashboardAdapter(getActivity(), modelList);

        recyclerView.setHasFixedSize(true);


        // Vertical
        OverScrollDecoratorHelper.setUpOverScroll(recyclerView, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
        try {
            setAdapter(Prefs.getString(Constant.id_user_mobile, ""), Prefs.getString(Constant.token, ""));
        }catch (Exception e){
            Log.d("MenuDashboarFragment", "findViews: "+e.getMessage());
        }

        view.findViewById(R.id.fab_tambah).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity();
                startActivityForResult(new Intent(getContext(), TambahPengaduanActivity.class),222);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            setAdapter(Prefs.getString(Constant.id_user_mobile,""),Prefs.getString(Constant.token,""));

        }
    }

    private void setAdapter(String id_user_mobile, String token) {
        final KProgressHUD hud = new KProgressHUD(getContext());
        Utils.ShowProgress(hud,null,null,true);
        AndroidNetworking.post(Config.URL_DASHBOARD)
                .addBodyParameter("token",token)
                .addBodyParameter("id_user_mobile",id_user_mobile)
                .build()
                .getAsOkHttpResponseAndObject(ModelDashboard.class, new OkHttpResponseAndParsedRequestListener<ModelDashboard>() {
                    @Override
                    public void onResponse(Response okHttpResponse, ModelDashboard response) {

                        hud.dismiss();
                        modelList.clear();
                        Log.d("MenuDashboarFragment", "onResponse: "+response.toString());
                        if(okHttpResponse.isSuccessful()){
                            if(response.getMeta().getCode() == 200){
                                modelList.add(new MenuDashboardModel("All Posting", getContext().getResources().getDrawable(R.drawable.ic_menu_camera),"Lihat semua postingan",response.getData().getPengaduanTotal(), DetailPengaduanActivity.class,"all"));
                                modelList.add(new MenuDashboardModel("On Progress", getContext().getResources().getDrawable(R.drawable.proses),"Postingan diproses" ,response.getData().getPengaduanProses(),DetailPengaduanActivity.class,"proses"));
                                modelList.add(new MenuDashboardModel("Finished", getContext().getResources().getDrawable(R.drawable.finish),"Postingan selesai" ,response.getData().getPengaduanSelesai(),DetailPengaduanActivity.class,"finish"));
                                modelList.add(new MenuDashboardModel("Info", getContext().getResources().getDrawable(R.drawable.info),"Informasi kami", "",null,""));




                                recyclerView.setAdapter(mAdapter);


                                mAdapter.SetOnItemClickListener(new MenuDashboardAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position, MenuDashboardModel model) {

                                        if(model.getaClass() != null) {

                                            Intent i = new Intent(getContext(),model.getaClass());
                                            i.putExtra("judul",model.getTitle());
                                            i.putExtra("status",model.getStatus());
                                            startActivity(i);
                                        }

                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                        hud.dismiss();
                        Log.d("MenuDashboarFragment", "onError: "+anError.getErrorDetail());


                    }
                });





    }

}
