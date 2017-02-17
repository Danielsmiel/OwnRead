package com.example.user.ownread.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.ownread.R;
import com.example.user.ownread.bean.ContentItemBean;
import com.example.user.ownread.utils.ImageLoaderUtils;

import java.util.ArrayList;

/**
 * Created by user on 2016/7/28.
 * 然而这个类并没有什么卵用
 */
public class ContentAdapter extends BaseAdapter {
    Context context;
    ArrayList<ContentItemBean.Datas> dataList;

    public ContentAdapter(Context context, ArrayList<ContentItemBean.Datas> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (holder == null) {
            holder = new ViewHolder();
            holder.typeface = Typeface.createFromAsset(context.getAssets(), "fonts/PMingLiU.ttf");
            view = LayoutInflater.from(context).inflate(R.layout.frag_item_content, viewGroup, false);
            holder.thumbnailIv = (ImageView) view.findViewById(R.id.item_content_thumbnail_iv);
            holder.typeTv = (TextView) view.findViewById(R.id.item_content_type);
            holder.typeTv.setTypeface(holder.typeface);
            holder.titleTv = (TextView) view.findViewById(R.id.item_content_title);
            holder.titleTv.setTypeface(holder.typeface);
            holder.contentTv = (TextView) view.findViewById(R.id.item_content_content);
            holder.contentTv.setTypeface(holder.typeface);
            holder.authorTv = (TextView) view.findViewById(R.id.item_content_author);
            holder.authorTv.setTypeface(holder.typeface);
            holder.commentBtn = (Button) view.findViewById(R.id.item_content_comment);
            holder.commentBtn.setTypeface(holder.typeface);
            holder.likeBtn = (Button) view.findViewById(R.id.item_content_like);
            holder.commentBtn.setTypeface(holder.typeface);
            holder.viewTv = (TextView) view.findViewById(R.id.item_content_view);
            holder.viewTv.setTypeface(holder.typeface);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ImageLoaderUtils.getImageByLoader(dataList.get(i).thumbnail, holder.thumbnailIv);
        holder.typeTv.setText(dataList.get(i).category);
        holder.titleTv.setText(dataList.get(i).title);
        holder.contentTv.setText(dataList.get(i).excerpt);
        return view;
    }

    class ViewHolder {
        ImageView thumbnailIv;
        TextView typeTv;
        TextView titleTv;
        TextView contentTv;
        TextView authorTv;
        Typeface typeface;
        Button commentBtn;
        Button likeBtn;
        TextView viewTv;
    }

    public void setAdapter(ArrayList<ContentItemBean.Datas> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }
}
