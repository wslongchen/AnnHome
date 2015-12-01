package com.example.mrpan.annhome;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by mrpan on 15/11/18.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<HashMap<String,Object>> items;
    private Context mContext;

    public MyAdapter(Context context,List<HashMap<String,Object>> items){
        this.mContext=context;
        this.items=items;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //设置布局文件
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.articles,parent,false);

        return new MyAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        HashMap<String,Object> hm=items.get(position);
        holder.title.setText(hm.get("title").toString());
       // holder.mImageView.setImageDrawable(mContext.getDrawable(R.drawable.p));
        holder.re.setBackground(mContext.getDrawable(R.drawable.drawing012));

    }

    @Override
    public int getItemCount() {
        // 返回数据总数
        return items == null ? 0 : items.size();
    }

    public void addItems(List<HashMap<String,Object>> itemsAdd){
        //添加数据项
        this.items.addAll(itemsAdd);
    }

    // 重写的自定义ViewHolder
    public static class ViewHolder
            extends RecyclerView.ViewHolder
    {
        public TextView title;

        public ImageView mImageView;

        public RelativeLayout re;

        public ViewHolder( View v )
        {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            mImageView = (ImageView) v.findViewById(R.id.pic);
            re=(RelativeLayout)v.findViewById(R.id.picture);
        }
    }

}
