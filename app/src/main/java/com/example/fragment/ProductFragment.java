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

        ProductEntity productEntity1 = new ProductEntity();
        productEntity1.setName("Pizza Panda");
        productEntity1.setCost("15");
        productEntity1.setImg("https://www.pizzagiovanni.cz/wp-content/uploads/funghi.jpg");
        ProductEntity productEntity2 = new ProductEntity();
        productEntity2.setName("Combo 1");
        productEntity2.setCost("25");
        productEntity2.setImg("https://www.kfc.mu/media/1041/large-piece-meal.jpg?crop=0,0,0,0&cropmode=percentage&width=950&height=640&rnd=132064404080000000");
        ProductEntity productEntity3 = new ProductEntity();
        productEntity3.setName("Đùi Gà");
        productEntity3.setCost("10");
        productEntity3.setImg("data:image/jpeg;base64,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");
        ProductEntity productEntity4 = new ProductEntity();
        productEntity1.setName("ComBo 2");
        productEntity1.setCost("35");
        productEntity1.setImg("https://images.foody.vn/res/g2/15658/prof/s576x330/foody-upload-api-foody-mobile-jhjhjh-190612135623.jpg");
        ProductEntity productEntity5 = new ProductEntity();
        productEntity1.setName("HamBerGer Bò");
        productEntity1.setCost("7");
        productEntity1.setImg("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSfbF3uAVCZXxDoBcindfYg89JS6P-YM_O2vTh_ZxJSJ43e1SIKJQ");


        mlistProductEntity.add(productEntity1);
        mlistProductEntity.add(productEntity2);
        mlistProductEntity.add(productEntity3);
        mlistProductEntity.add(productEntity4);
        mlistProductEntity.add(productEntity5);
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
