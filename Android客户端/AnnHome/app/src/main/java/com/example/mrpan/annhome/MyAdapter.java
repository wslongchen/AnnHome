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

/**
 * Created by mrpan on 15/11/18.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    private List<HashMap<String,Object>> items;
    private Context mContext;

    private MyItemClickListener mItemClickListener;
    private MyItemLongClickListener mItemLongClickListener;

    public MyAdapter(Context context,List<HashMap<String,Object>> items){
        this.mContext=context;
        this.items=items;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //设置布局文件
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_favorites, parent,false);
        ViewHolder vh = new ViewHolder(itemView,mItemClickListener,mItemLongClickListener);
        return vh;
    }

    /**
     * 设置Item点击监听
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener){
        this.mItemClickListener = listener;
    }

    /**
     * 设置Item长击监听
     */
    public void setOnItemLongClickListener(MyItemLongClickListener listener){
        this.mItemLongClickListener = listener;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        HashMap<String,Object> hm=items.get(position);
        holder.title.setText(hm.get("title").toString());
       // holder.mImageView.setImageDrawable(mContext.getDrawable(R.drawable.p));
        holder.re.setBackground(mContext.getDrawable(R.mipmap.drawing012));

    }

    @Override
    public int getItemCount() {
        // 返回数据总数
        return items == null ? 0 : items.size();
    }

    /*
    * 设置添加的数据项
    * */
    public void addItems(List<HashMap<String,Object>> itemsAdd){
        //添加数据项
        this.items.addAll(itemsAdd);
    }

    // 重写的自定义ViewHolder
    public static class ViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener,View.OnLongClickListener
    {
        //控件
        public TextView title;
        public ImageView mImageView;
        public RelativeLayout re;

        //上下文
        public Context mContext;

        //监听
        private MyItemClickListener mListener;
        private MyItemLongClickListener mLongClickListener;

        public ViewHolder( View v ,MyItemClickListener listener,MyItemLongClickListener longClickListener) {
            super(v);
            //绑定并设置监听事件
            title = (TextView) v.findViewById(R.id.favorites_title);
            mImageView = (ImageView) v.findViewById(R.id.favorites_pic);
            re=(RelativeLayout)v.findViewById(R.id.picture);
            this.mListener = listener;
            this.mLongClickListener = longClickListener;
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                mListener.onItemClick(v,getPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if(mLongClickListener != null){
                mLongClickListener.onItemLongClick(v, getPosition());
            }
            return true;
        }
    }

    /*
    * 仿Listview声明Item监听接口*/
    public interface MyItemClickListener {
        public void onItemClick(View view,int postion);
    }

    public interface MyItemLongClickListener {
        public void onItemLongClick(View view,int postion);
    }

}
