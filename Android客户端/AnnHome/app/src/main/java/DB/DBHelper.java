package DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by mrpan on 16/3/3.
 */
public class DBHelper extends ToSDCardSQLiteOpenHelper {

    private static final int VERSON = 1;// 默认的数据库版本
    private static DBHelper myDataBase = null;
    private static SQLiteDatabase mDb = null;
    public static final String DATABASE_NAME = "annhome.db";
    public static final String DB_PATH = Environment.getExternalStorageDirectory()
            + "/AnnHome/DataBases/"; // 在手机里存放数据库的位置
    Context context;

    public DBHelper(Context context) {
        super(context,DB_PATH,DATABASE_NAME, null, VERSON);
        checkFile(DB_PATH);
        this.context = context;
    }

    public DBHelper(Context context,String path, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context,path,name, factory, version);
    }

    // 该构造函数有3个参数，因为它把上面函数的第3个参数固定为null了
    public DBHelper(Context context, String path,String name, int version) {
        this(context, path,name, null, version);
    }

    // 该构造函数只有2个参数，在上面函数 的基础山将版本号固定了
    public DBHelper(Context context,String path, String name) {
        this(context, path,name, VERSON);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        checkTables(db);
        Log.v("Database", "OnCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //onCreate(db);
        Log. e("Database", "onUpgrade");
    }

    public static synchronized DBHelper getMyDataBase(Context context){
        if(myDataBase==null){
            myDataBase=new DBHelper(context);
        }
        return myDataBase;
    }

    public synchronized SQLiteDatabase getmDb(){
        if (mDb == null) {
            mDb = SQLiteDatabase.openOrCreateDatabase(DB_PATH + DATABASE_NAME, null);
        }
        return mDb;
    }

    //检查数据库文件
    public static void checkFile(String path)
    {
        File dir = new File(path);

        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    //自动建表
    void checkTables(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "User(user_id integer primary key autoincrement," +
                "user_name varchar(50)," +
                "user_password varchar(200)," +
                "user_nick text," +
                "user_sex INTEGER," +
                "user_sign varchar(300)," +
                "user_info varchar(500)," +
                "user_photo blob," +
                "user_level integer," +
                "time_last date)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "Dialog(dia_id integer primary key autoincrement," +
                "dia_title varchar(50)," +
                "dia_author varchar(50)," +
                "dia_content text," +
                "dia_img blob," +
                "dia_islock integer," +
                "dia_date varchar(50)," +
                "dia_user varchar(50))");
    }
}
