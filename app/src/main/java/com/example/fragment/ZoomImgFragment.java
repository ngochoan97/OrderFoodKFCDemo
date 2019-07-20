package com.example.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 */
public class ZoomImgFragment extends Fragment {

ImageView img;
    public ZoomImgFragment() {
        // Required empty public constructor
    }

    public static ZoomImgFragment newInstance(String url) {

        Bundle args = new Bundle();
        args.putString("url",url);
        ZoomImgFragment fragment = new ZoomImgFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_zoom_img,container,false);
        img=view.findViewById(R.id.imgProduct);
        Bundle bundle= getArguments();
        Glide.with(getActivity()).load(bundle.getString("url")).into(img);
        return view;

    }

}
