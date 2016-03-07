package utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mrpan.annhome.R;

import java.util.List;

import entity.Dialog;

/**
 * Created by mrpan on 16/2/16.
 */
public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListAdapter.ViewHolder>{
    private List<Dialog> articles;

    private Context mContext;

    public FavoriteListAdapter( Context context , List<Dialog> actors)
    {
        this.mContext = context;
        this.articles = actors;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i )
    {
        // 给ViewHolder设置布局文件
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items_favorites, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int i )
    {
        // 给ViewHolder设置元素
        Dialog p = articles.get(i);
        viewHolder.favorite_title.setText(p.getTitle());
        viewHolder.favorite_total.setText(p.getDate()+"  from "+p.getAuthor());
        if(p.getImage().length>0)
        {
            viewHolder.favorite_pic.setImageBitmap(BitmapUtils.Bytes2Bimap(p.getImage()));
        }
        else
            viewHolder.favorite_pic.setImageResource(R.mipmap.drawing012);
    }

    @Override
    public int getItemCount()
    {
        // 返回数据总数
        return articles == null ? 0 : articles.size();
    }

    // 重写的自定义ViewHolder
    public static class ViewHolder
            extends RecyclerView.ViewHolder
    {
        public TextView favorite_title;

        public ImageView favorite_pic;

        public TextView favorite_total;

        public ViewHolder( View v )
        {
            super(v);
            favorite_title = (TextView) v.findViewById(R.id.favorites_title);
            favorite_total = (TextView) v.findViewById(R.id.favorites_total);
            favorite_pic = (ImageView) v.findViewById(R.id.favorites_pic);
        }
    }
}
