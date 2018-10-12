package com.junjie.jia.io.mygirls;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;


public class GirlPhotoAdapter extends RecyclerView.Adapter<GirlPhotoAdapter.ViewHolder> {

  private final List<String> mValues;

  public GirlPhotoAdapter(List<String> items) {
    mValues = items;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.photo_item, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.mItem = mValues.get(position);
    holder.mContentView.setText(holder.mItem);
  }

  @Override
  public int getItemCount() {
    return mValues.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    public final TextView mContentView;
    public String mItem;

    public ViewHolder(View view) {
      super(view);
      mView = view;
      mContentView = view.findViewById(R.id.content);
    }
  }
}
