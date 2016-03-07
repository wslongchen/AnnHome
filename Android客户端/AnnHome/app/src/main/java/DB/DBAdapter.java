package DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import entity.Dialog;

/**
 * Created by mrpan on 16/3/3.
 */
public class DBAdapter {

    SQLiteDatabase mDb = null;
    private static DBAdapter dbAdapter=null;


    public DBAdapter(Context context){
        mDb=DBHelper.getMyDataBase(context).getWritableDatabase();
    }

    public static DBAdapter getDBAdapter(Context context){
        if(dbAdapter==null){
            dbAdapter=new DBAdapter(context);
        }
        return dbAdapter;
    }


    //保存文章
    public long SaveArticle(Dialog dialog,boolean isLock){
        ContentValues values = new ContentValues();

        values.put("dia_title", dialog.getTitle());
        values.put("dia_author", dialog.getAuthor());
        values.put("dia_islock", isLock == true ? 1 : 0);
        values.put("dia_content", dialog.getContent());
        values.put("dia_img", dialog.getImage());
        values.put("dia_date", dialog.getDate());
        values.put("dia_user", dialog.getUser());

        return mDb.insert("Dialog", null, values);
    }

    //显示文章
    public List<Dialog> getArticles(String user,int index){
        List<Dialog> articles=new ArrayList<Dialog>();
        Cursor cs=null;
        if (!user.trim().equals("")) {
            cs = ArticleCursorByUser(user,index);
        }
        if (cs != null) {

            while (cs.moveToNext()) {
                Dialog d = new Dialog();
                d.setID(cs.getString(cs.getColumnIndex("dia_id")));
                d.setTitle(cs.getString(cs.getColumnIndex("dia_title")));
                d.setAuthor(cs.getString(cs.getColumnIndex("dia_author")));
                d.setContent(cs.getString(cs.getColumnIndex("dia_content")));
                d.setImage(cs.getBlob(cs.getColumnIndex("dia_img")));
                d.setIsLock(cs.getInt(cs.getColumnIndex("dia_islock")) == 1 ? true
                        : false);
                d.setDate(cs.getString(cs.getColumnIndex("dia_date")));
                d.setUser(cs.getString(cs.getColumnIndex("dia_user")));
                articles.add(d);
            }
        }
        cs.close();
        return articles;
    }

    private Cursor ArticleCursorByUser(String user,int index){
        return mDb.query("Dialog",null,"dia_user='"+user+"'",null,null,null,"dia_id",null);
    }

    public boolean isHave(String User,String title){
        Cursor cs=mDb.query("Dialog",null,"dia_user='"+User+"' and dia_title='"+title+"'",null,null,null,null);
        if(cs.getCount()>0)
            return true;
        return false;
    }

    public int DeleteDialog(String user,String title){
        return mDb.delete("Dialog","dia_user='"+user+"' and dia_title='"+title+"'",null);
    }
}
