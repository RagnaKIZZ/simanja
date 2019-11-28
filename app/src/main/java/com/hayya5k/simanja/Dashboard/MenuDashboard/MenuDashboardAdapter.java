package com.hayya5k.simanja.Dashboard.MenuDashboard;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.hayya5k.simanja.R;

@SuppressLint("SetTextI18n")
public class MenuDashboardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<MenuDashboardModel> modelList;

    private OnItemClickListener mItemClickListener;


    public MenuDashboardAdapter(Context context, ArrayList<MenuDashboardModel> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<MenuDashboardModel> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_menudashboard_list, viewGroup, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final MenuDashboardModel model = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.itemTxtTitle.setText(model.getTitle());
            genericViewHolder.itemTxtMessage.setText(model.getInfo());
            genericViewHolder.imgUser.setImageDrawable(model.getImage());
            genericViewHolder.txt_jumlah.setText(model.getProses());




        }
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private MenuDashboardModel getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, MenuDashboardModel model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgUser;
        private TextView itemTxtTitle;
        private TextView itemTxtMessage;
        private TextView txt_jumlah;


        public ViewHolder(final View itemView) {
            super(itemView);


            this.imgUser        =  itemView.findViewById(R.id.img_user);
            this.itemTxtTitle   =  itemView.findViewById(R.id.item_txt_title);
            this.itemTxtMessage =  itemView.findViewById(R.id.item_txt_message);
            this.txt_jumlah     =  itemView.findViewById(R.id.txt_jumlah);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));


                }
            });

        }
    }

}

