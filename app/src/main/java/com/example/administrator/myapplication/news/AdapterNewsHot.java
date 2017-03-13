package com.example.administrator.myapplication.news;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/3/13.
 */

public class AdapterNewsHot extends RecyclerView.Adapter<HolderNewsHot> {

    @Override
    public HolderNewsHot onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType){
            case NewsHotItemType.NO_IMAGE:
//                view = LayoutInflater.from(mContext).inflate(R.layout.layout_tools_item, parent, false);
                break;
            case NewsHotItemType.SINGLE_IMAGE:
                break;
            case NewsHotItemType.MULTI_IMAGE:
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(HolderNewsHot holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
