package com.example.mrpan.annhome;

/**
 * Created by mrpan on 15/12/12.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.HashMap;
import java.util.List;

import http.HttpResponseCallBack;

public class NewListAdapter extends BaseAdapter {

    private ImageLoader imageLoader = null;
    private DisplayImageOptions options = null;

    //容器
    static class ViewHolder {
        ImageView ivPreview;
        TextView tvTitle;
        TextView tvContent;
        TextView tvReview;
    }

    private Context context;
    private List<HashMap<String, Object>> news;

    public NewListAdapter(Context context,List<HashMap<String, Object>> news) {
        this.context = context;
        this.news = news;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));

        options = new DisplayImageOptions.Builder()
                .displayer(new RoundedBitmapDisplayer(0xff000000, 10))
                .cacheInMemory()
                .cacheOnDisc()
                .build();
    }

    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public HashMap<String,Object> getItem(int position) {
        return news.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview, null);
            holder = new ViewHolder();
            holder.ivPreview = (ImageView) convertView.findViewById(R.id.ivPreview);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            holder.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
            holder.tvReview = (TextView) convertView.findViewById(R.id.tvReview);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //设置显示数据
        imageLoader.displayImage(getItem(position).get("uri").toString(), holder.ivPreview, options);
        //holder.ivPreview.setImageBitmap((Bitmap)getItem(position).get("img"));
        holder.tvTitle.setText(getItem(position).get("title").toString());
        holder.tvContent.setText(getItem(position).get("content").toString());
//        holder.tvReview.setText(getItem(position).get("review").toString());

        return convertView;
    }
    //添加项
    public void addNews(List<HashMap<String, Object>> addNews) {
        for(HashMap<String, Object> hm:addNews) {
            news.add(hm);
        }
    }

}
