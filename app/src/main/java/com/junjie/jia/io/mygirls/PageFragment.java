package com.junjie.jia.io.mygirls;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class PageFragment extends Fragment {
    private String title;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private List<String> list = new ArrayList<>();
    private GirlPhotoAdapter girlPhotoAdapter;

    public static PageFragment newInstance(@NonNull String title) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            title = args.getString("title");
        }

        for (int i = 0; i < 3; i++) {
            list.add(String.valueOf(i));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page, container, false);
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        recyclerView = rootView.findViewById(R.id.recyclerView);

        swipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright,
            R.color.holo_green_light, R.color.holo_purple, R.color.holo_red_light);
        swipeRefreshLayout.setProgressViewOffset(true, 50, 100);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(3000);
                        int count = list.size();
                        for (int i = 0; i < 3; i++) {
                            list.add(String.valueOf(count + i));
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(false);
                                girlPhotoAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }).start();
            }
        });

        //        Log.i("Fragment","" + swipeRefreshLayout.canChildScrollUp());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        girlPhotoAdapter = new GirlPhotoAdapter(list);
        recyclerView.setAdapter(girlPhotoAdapter);
        return rootView;
    }


}
