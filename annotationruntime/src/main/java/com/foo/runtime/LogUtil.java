package com.foo.runtime;

import android.util.Log;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/3/16 16:48
 */
public class LogUtil {

    private static final String PREFIX_TAG = "Elegant_";


    public static void i(String tag, String msg) {
        if (tag == null) {
            tag = "";
        }
        Log.i(PREFIX_TAG + tag, msg);
    }

    public static void w(String tag, String msg) {
        if (tag == null) {
            tag = "";
        }
        Log.w(PREFIX_TAG + tag, msg);
    }

    public static void e(String tag, String msg) {
        if (tag == null) {
            tag = "";
        }
        Log.e(PREFIX_TAG + tag, msg);
    }
}
