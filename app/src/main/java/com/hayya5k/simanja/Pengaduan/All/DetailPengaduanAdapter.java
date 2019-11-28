package com.hayya5k.simanja.Pengaduan.All;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import com.hayya5k.simanja.Config.Config;
import com.hayya5k.simanja.Helper.Utils;
import com.hayya5k.simanja.Pengaduan.ModelDetailPengaduan.DataItem;
import com.hayya5k.simanja.R;


public class DetailPengaduanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<DataItem> modelList;

    private OnItemClickListener mItemClickListener;


    public DetailPengaduanAdapter(Context context, ArrayList<DataItem> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<DataItem> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_detail_pengaduan_list, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final DataItem model = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.txt_judul.setText(model.getJudul());
            genericViewHolder.txt_pesan.setText(model.getPesan());
            genericViewHolder.txt_alamat.setText(model.getAlamat());
            genericViewHolder.txt_tgl_input.setText(model.getTglInput());

            Utils.LoadImage(mContext, Config.URL_IMAGE+model.getFoto(),genericViewHolder.pb,genericViewHolder.img_info);




        }
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private DataItem getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, DataItem model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_info;
        private TextView txt_judul;
        private TextView txt_pesan;
        private TextView txt_alamat;
        private TextView txt_tgl_input;
        private ProgressBar pb;


        public ViewHolder(final View itemView) {
            super(itemView);


            this.img_info = (ImageView) itemView.findViewById(R.id.img_info);
            this.txt_judul = (TextView) itemView.findViewById(R.id.txt_judul);
            this.txt_pesan = (TextView) itemView.findViewById(R.id.txt_pesan);
            this.txt_alamat = (TextView) itemView.findViewById(R.id.txt_alamat);
            this.txt_tgl_input = (TextView) itemView.findViewById(R.id.txt_tgl_input);
            this.pb =  itemView.findViewById(R.id.pb);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));


                }
            });

        }
    }

}

