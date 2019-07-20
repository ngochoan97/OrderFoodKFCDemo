package com.example.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapteBuyProduct extends RecyclerView.Adapter<AdapteBuyProduct.MyViewHolde> {
    ArrayList<BuyProductEntity> mlistBuy;
    Context mContext;
    public AdapteBuyProduct(ArrayList<BuyProductEntity> mlistProducts) {
        this.mlistBuy = mlistProducts;
    }

    private OnClickListener onClickListener;

    @NonNull
    @Override
    public MyViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_buy_item, parent, false);
        mContext= parent.getContext();
        return new MyViewHolde(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolde holder, int position) {
        BuyProductEntity product = mlistBuy.get(position);
        String name = product.getName();
        int amount = product.getNumberAmount();
        holder.tvName.setText(name);
        holder.tvAmount.setText(amount + "");
        Glide.with(mContext).load(product.getImg()).placeholder(R.mipmap.ic_launcher).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mlistBuy.size();
    }

    public class MyViewHolde extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvName, tvAmount;
        ImageView imageView;
        public MyViewHolde(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvName = itemView.findViewById(R.id.tvName);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            imageView= itemView.findViewById(R.id.imgProduct);
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
