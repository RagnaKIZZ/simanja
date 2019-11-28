package com.hayya5k.simanja.Pengaduan.All.DetailItemPengaduan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hayya5k.simanja.Pengaduan.All.DetailItemPengaduan.ModelDetailItemPengaduan.KomentarItem;
import com.hayya5k.simanja.R;

import java.util.ArrayList;


public class DetailItemPengaduanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<KomentarItem> modelList;

    private OnItemClickListener mItemClickListener;


    public DetailItemPengaduanAdapter(Context context, ArrayList<KomentarItem> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<KomentarItem> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_detailitem_pengaduan, viewGroup, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final KomentarItem model = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.txt_komentar.setText(model.getKomentar());
            genericViewHolder.txt_user_email.setText(model.getUsername()+" - "+model.getEmail());




        }
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private KomentarItem getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, KomentarItem model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView txt_komentar;
        private TextView txt_user_email;


        public ViewHolder(final View itemView) {
            super(itemView);


            this.txt_komentar = (TextView) itemView.findViewById(R.id.txt_komentar);
            this.txt_user_email = (TextView) itemView.findViewById(R.id.txt_user_email);


//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
//
//
//                }
//            });

        }
    }

}

