package com.example.thampotter.quanlynhac.Fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.example.thampotter.quanlynhac.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_PlayNhac extends Fragment {

    View view;
    CircleImageView circleImageViewPlayNhac;
    ObjectAnimator objectAnimator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.playnhac_fragment,container,false);
        circleImageViewPlayNhac = view.findViewById(R.id.circleimageviewplaynhac);
        objectAnimator = ObjectAnimator.ofFloat(circleImageViewPlayNhac,"rotation",0f,360f);
        objectAnimator.setDuration(10000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
        return view;
    }

    public void Playnhac(String hinhanh) {
        if (!hinhanh.equals("")){
            Picasso.with(getContext()).load(hinhanh).into(circleImageViewPlayNhac);
        }else {
            circleImageViewPlayNhac.setImageResource(R.drawable.icon_disk);
        }
    }
}
