package com.example.fragment;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapteProduct extends RecyclerView.Adapter<AdapteProduct.MyViewHolder> {
    ArrayList<ProductEntity> mlistBuyProductEntities;
    private Context mContext;
    //  boolean isImageToScreen=false;


    public AdapteProduct(ArrayList<ProductEntity> mlistProductEntities) {
        this.mlistBuyProductEntities = mlistProductEntities;
    }

    private OnClickListener onClickListener;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_product, parent, false);
        mContext = parent.getContext();
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final ProductEntity productEntity = mlistBuyProductEntities.get(position);
        String name = productEntity.getName();
        String cost = productEntity.getCost();
        holder.tvName.setText(name);
        holder.tvCost.setText(cost);
        Glide.with(mContext).load(productEntity.getImg()).placeholder(R.mipmap.duiga).into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) mContext;
                ZoomImgFragment zoomImgFragment = ZoomImgFragment.newInstance(productEntity.getImg());
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.flTest, zoomImgFragment, zoomImgFragment.getTag()).addToBackStack("").commit();
//                ProductFragment productFragment = mContext;
//                productFragment.zoomInImage(position);
//                if(isImageToScreen) {
//                    isImageToScreen=false;
//                    holder.img.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//                    holder.img.setAdjustViewBounds(true);
//                }else{
//                    isImageToScreen=true;
//                    holder.img.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//                    holder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mlistBuyProductEntities.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvName, tvCost;
        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvCost = itemView.findViewById(R.id.tvCost);
            img = itemView.findViewById(R.id.imgProduct);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListener.onClick(getAdapterPosition(), view, false);
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int position, View view, boolean check);
    }
}
