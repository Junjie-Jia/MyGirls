package com.junjie.jia.io.mygirls.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.junjie.jia.io.mygirls.R;
import com.junjie.jia.io.mygirls.ui.WebViewActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author : Victor Jia
 * Date  :  2018/11/15.
 */
public class GankTextAdapter extends GankAdapter<GankTextAdapter.ViewHolder> {
    private static final String TAG = GankTextAdapter.class.getSimpleName();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.text_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i(TAG, ":    " + list.get(getAdapterPosition()).toString());
                    WebViewActivity.startWebViewActivity(view.getContext(), list.get(getAdapterPosition()).getUrl(),
                            list.get(getAdapterPosition()).getDesc());
                }
            });
        }
    }
}
