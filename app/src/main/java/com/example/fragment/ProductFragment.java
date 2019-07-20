package com.example.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductFragment extends Fragment {
    private AdapteProduct mAdapteProduct;
    private RecyclerView rvlistProduct;
    private ArrayList<ProductEntity> mlistProductEntity;
    private static ArrayList<BuyProductEntity> mlistBuy = new ArrayList<>();

    Button btnCart, btnOrder;

    private TextView tvUserName, tvTotal, tvCountProdct;

    RelativeLayout relativeLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        setdata();
        //setHasOptionsMenu(true);

        btnCart = view.findViewById(R.id.btncart);
        btnOrder = view.findViewById(R.id.btnOrder);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        tvCountProdct = view.findViewById(R.id.tvCount);
        tvUserName = view.findViewById(R.id.tvUserName);
        rvlistProduct = view.findViewById(R.id.rcvItem);
        mAdapteProduct = new AdapteProduct(mlistProductEntity);
        rvlistProduct.setAdapter(mAdapteProduct);
        setCountBuyProduct(mlistBuy);
        tvTotal = view.findViewById(R.id.tvTotal);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder diaBuilder = new AlertDialog.Builder(getActivity());
                diaBuilder.setTitle("Bạn có muốn đặt hàng không ? ");
                diaBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                        mlistBuy.clear();
                        tvTotal.setText((totalPrice(mlistBuy)) + "$");
                        setCountBuyProduct(mlistBuy);


                    }
                });
                diaBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                diaBuilder.create();
                diaBuilder.show();

            }
        });
        tvTotal.setText((totalPrice(mlistBuy)) + "$");


        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuyFragment buyFragment = BuyFragment.newInstance(mlistBuy);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flTest, buyFragment).addToBackStack("tab").commit();
            }
        });
        tvCountProdct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuyFragment buyFragment = BuyFragment.newInstance(mlistBuy);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flTest, buyFragment).addToBackStack("tab").commit();
            }
        });
        mAdapteProduct.setOnClickListener(new AdapteProduct.OnClickListener() {
            @Override
            public void onClick(int position, View view, boolean check) {
                addProduct(mlistProductEntity.get(position));
//                Toast.makeText(getActivity(), "" + mlistBuy.get(0).getNumberAmount(), Toast.LENGTH_SHORT).show();
                tvTotal.setText((totalPrice(mlistBuy)) + "$");
                setCountBuyProduct(mlistBuy);

            }
        });
        Bundle bundle = getArguments();
        String userName = bundle.getString("userName");
        tvUserName.setText("Welcome " + userName);
        return view;
    }


    public static ProductFragment newInstance(String userName) {

        Bundle args = new Bundle();
        args.putString("userName", userName);
        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setdata() {
        mlistProductEntity = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ProductEntity productEntity = new ProductEntity();
            productEntity.setName("lol" + i);
            productEntity.setCost("1000");
            productEntity.setImg("https://www.kfc.mu/media/1041/large-piece-meal.jpg?crop=0,0,0,0&cropmode=percentage&width=950&height=640&rnd=132064404080000000");
            mlistProductEntity.add(productEntity);
        }


    }

    public void zoomInImage(int position) {
        String urlImage = mlistProductEntity.get(position).getImg();
        ZoomImgFragment zoomImgFragment = ZoomImgFragment.newInstance(urlImage);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flTest, zoomImgFragment).addToBackStack("tag").commit();
    }

    public void addProduct(ProductEntity productEntity) {

        if (mlistBuy.size() > 0) {
            int index = getIndexOfProductInList(productEntity);
            if (index == -1) {
                BuyProductEntity buyProductEntity = new BuyProductEntity();
                buyProductEntity.setName(productEntity.getName());
                buyProductEntity.setCost(productEntity.getCost());
                buyProductEntity.setImg(productEntity.getImg());
                buyProductEntity.setNumberAmount(1);
                mlistBuy.add(buyProductEntity);
                Toast.makeText(getActivity(), "Đã thêm " + buyProductEntity.getName() + " vào giỏ hàng", Toast.LENGTH_SHORT).show();
            } else {
                BuyProductEntity buyProductOldEntity = mlistBuy.get(index);
                int oldAmount = buyProductOldEntity.getNumberAmount();
                buyProductOldEntity.setNumberAmount(oldAmount + 1);
                mlistBuy.set(index, buyProductOldEntity);
                Toast.makeText(getActivity(), mlistBuy.get(index).getName() + " : " + mlistBuy.get(index).getNumberAmount(), Toast.LENGTH_SHORT).show();
            }
        } else {
            BuyProductEntity buyProductEntity = new BuyProductEntity();
            buyProductEntity.setName(productEntity.getName());
            buyProductEntity.setCost(productEntity.getCost());
            buyProductEntity.setImg(productEntity.getImg());
            buyProductEntity.setNumberAmount(1);
            mlistBuy.add(buyProductEntity);
            Toast.makeText(getActivity(), "Đã thêm " + buyProductEntity.getName() + " vào giỏ hàng", Toast.LENGTH_SHORT).show();
        }
    }

    public int getIndexOfProductInList(ProductEntity productEntity) {
        for (int i = 0; i < mlistBuy.size(); i++) {
            if (productEntity.getName() == mlistBuy.get(i).getName()) {
                return i;
            }
        }
        return -1;
    }

    //    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.mnCart:
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                BuyFragment buyFragment = BuyFragment.newInstance(mlistBuy);
//                fragmentManager.beginTransaction().replace(R.id.flTest, buyFragment, buyFragment.getTag()).addToBackStack("TAG").commit();
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

    public void setCountBuyProduct(ArrayList<BuyProductEntity> buyProductEntities) {
        int count = mlistBuy.size();
        if (count == 00) {
            tvCountProdct.setVisibility(View.GONE);
        } else {
            tvCountProdct.setVisibility(View.VISIBLE);
            tvCountProdct.setText(String.valueOf(count));
        }

    }
}
