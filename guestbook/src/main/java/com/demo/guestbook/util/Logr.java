package com.demo.guestbook.util;

import android.util.Log;

public class Logr {

	public static void d(String msg) {
		Log.d("App.dbg", msg);
	}

	public static void e(String msg) {
		Log.e("App.ERR!", msg);
	}

}
