package com.junjie.jia.io.mygirls;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.junjie.jia.io.mygirls.bean.DataBean;
import com.junjie.jia.io.mygirls.widget.RatioImageView;

import java.util.List;


public class GirlPhotoAdapter extends RecyclerView.Adapter<GirlPhotoAdapter.ViewHolder> {

    private List<DataBean> list;

    private RequestOptions requestOptions = new RequestOptions()
            .placeholder(R.drawable.photo_place_holder)
            .diskCacheStrategy(DiskCacheStrategy.ALL);

    public void setList(List<DataBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.dataBean = list.get(position);
        if (holder.dataBean.getWidth() == 0) {
            Glide.with(holder.imageView.getContext())
                    .asBitmap()
                    .load(list.get(position).getUrl())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            holder.dataBean.setWidth(resource.getWidth());
                            holder.dataBean.setHeight(resource.getHeight());
                        }
                    });
        }

        holder.imageView.setOriginalSize(holder.dataBean.getWidth(),
                holder.dataBean.getHeight());


        Glide.with(holder.imageView.getContext())
                .applyDefaultRequestOptions(requestOptions)
                .load(holder.dataBean.getUrl())
                .into(holder.imageView);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        DataBean dataBean;
        final View mView;
        RatioImageView imageView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            imageView = view.findViewById(R.id.imageView);
        }
    }
}
