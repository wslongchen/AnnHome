package com.example.mrpan.annhome;

import android.os.Environment;

/**
 * Created by mrpan on 15/12/6.
 */
public class Config {

    /**
     * 是否只在wifi 下使用 的key
     *
     * values 1 表示 仅在wifi下使用 values 0 表示 都可以使用
     */
    public static String TYPE_CONN = "TYPE_CONN";
    public static int TYPE_ALL = 0;
    public static int TYPE_WIFI = 1;
    public static String DIR_PATH = Environment.getExternalStorageDirectory()
            .toString()  +"AnnHome/Cache/";
    public static String DIR_IMAGE_PATH = Environment.getExternalStorageDirectory()
            .toString() + "AnnHome/Image/";

}
