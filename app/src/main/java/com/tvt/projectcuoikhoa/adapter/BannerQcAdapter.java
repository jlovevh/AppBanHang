package com.tvt.projectcuoikhoa.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tvt.projectcuoikhoa.R;
import com.tvt.projectcuoikhoa.model.BannerQc;

import java.util.List;

public class BannerQcAdapter extends PagerAdapter {
    private Context context;
    private List<BannerQc> arrBannerQc;

    public BannerQcAdapter( Context context, List<BannerQc> arrBannerQc) {
        this.context = context;
        this.arrBannerQc = arrBannerQc;

    }

//    public void setData(List<BannerQc> arrBannerQc){
//        this.arrBannerQc.clear();
//        this.arrBannerQc.addAll(arrBannerQc);
//        this.notifyDataSetChanged();
//    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view =LayoutInflater.from(context).inflate(R.layout.item_banner_qc,container,false);

        ImageView imgBannerQc=view.findViewById(R.id.img_banner_qc);

        Picasso.with(context).load(arrBannerQc.get(position).getUrlBanner()).error(R.mipmap.ic_launcher).into(imgBannerQc);

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return arrBannerQc.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
         container.removeView((View) object);
    }
}
