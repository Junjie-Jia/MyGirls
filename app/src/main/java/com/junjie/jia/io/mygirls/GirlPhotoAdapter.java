package com.junjie.jia.io.mygirls;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.junjie.jia.io.mygirls.bean.DataBean;

import java.util.List;


public class GirlPhotoAdapter extends RecyclerView.Adapter<GirlPhotoAdapter.ViewHolder> {

    private List<DataBean> list;

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
        if(holder.dataBean.getWidth() == 0){
            Glide.with(holder.imageView.getContext())
                    .asBitmap()
                    .load(list.get(position).getUrl())
                    .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                            int imageViewWidth = holder.imageView.getMeasuredWidth();
//                            ViewGroup.LayoutParams layoutParams = holder.imageView.getLayoutParams();
//                            layoutParams.height = Math.round((float)imageViewWidth * resource.getHeight() / resource.getWidth());
//                            holder.imageView.setLayoutParams(layoutParams);
//                            holder.imageView.setMinimumHeight(layoutParams.height);
                            holder.dataBean.setWidth(resource.getWidth());
                            holder.dataBean.setHeight(resource.getHeight());
                        }
                    });
        }


        ViewTreeObserver vto = holder.imageView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int thumbWidth = holder.dataBean.getWidth();
                        int thumbHeight = holder.dataBean.getHeight();
                        if (thumbWidth > 0 && thumbHeight > 0) {
                            int width = holder.imageView.getMeasuredWidth();
                            int height = Math.round(width * ((float) thumbHeight / thumbWidth));
                            holder.imageView.getLayoutParams().height = height;
                            holder.imageView.setMinimumHeight(height);
                        }
                        holder.imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                }
        );

        Glide.with(holder.imageView.getContext()).load(list.get(position).getUrl()).into(holder.imageView);

    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        if(holder.imageView != null){
            holder.imageView.setImageBitmap(null);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        DataBean dataBean;
        final View mView;
        final ImageView imageView;

        ViewHolder(View view) {
            super(view);
            mView = view;
            imageView = view.findViewById(R.id.imageView);
        }
    }
}
