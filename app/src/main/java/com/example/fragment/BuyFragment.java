package com.example.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BuyFragment extends Fragment {
    private ArrayList<BuyProductEntity> mlistBuyProductEntity;
    private RecyclerView rcvBuyProduct;
    private AdapteBuyProduct mAdapteBuyProduct;
    private TextView tvTotalPrice, tvCartEmpty;
    private LinearLayout llTotal;
    private ImageButton imageButton;


    public static BuyFragment newInstance(ArrayList<BuyProductEntity> listBuy) {
        Bundle args = new Bundle();
        args.putSerializable("listBuy", listBuy);
        BuyFragment fragment = new BuyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        View view = inflater.inflate(R.layout.fragment_buy, container, false);

        //setHasOptionsMenu(true);

        mlistBuyProductEntity = (ArrayList<BuyProductEntity>) bundle.getSerializable("listBuy");
        rcvBuyProduct = view.findViewById(R.id.rcvListBuy);
        mAdapteBuyProduct = new AdapteBuyProduct(mlistBuyProductEntity);
        rcvBuyProduct.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        rcvBuyProduct.setAdapter(mAdapteBuyProduct);
        mAdapteBuyProduct.notifyDataSetChanged();
        imageButton=view.findViewById(R.id.ibtnCart);
        tvTotalPrice = view.findViewById(R.id.tvTotalPrice);
        tvTotalPrice.setText(String.valueOf(totalPrice(mlistBuyProductEntity)) + " ($)");
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        tvCartEmpty = view.findViewById(R.id.tvCartEmpty);
        llTotal = view.findViewById(R.id.llTotal);
        if (mlistBuyProductEntity.size() == 0) {
            llTotal.setVisibility(View.GONE);
            rcvBuyProduct.setVisibility(View.GONE);
            tvCartEmpty.setVisibility(View.VISIBLE);
        } else {
            llTotal.setVisibility(View.VISIBLE);
            rcvBuyProduct.setVisibility(View.VISIBLE);
            tvCartEmpty.setVisibility(View.GONE);
        }

        return view;
    }

    //@Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        getActivity().getMenuInflater().inflate(R.menu.back_bar, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.mnCart:
//                getActivity().getSupportFragmentManager().popBackStack();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public long totalPrice(ArrayList<BuyProductEntity> buyProductEntities) {
        long result = 0;
        for (BuyProductEntity buyProductEntity : buyProductEntities) {
            result += (Integer.parseInt(buyProductEntity.getCost()) * buyProductEntity.getNumberAmount());
        }
        return result;
    }

//
}
