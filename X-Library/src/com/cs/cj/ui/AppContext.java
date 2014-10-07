package com.cs.cj.ui;

import android.content.Context;

public class AppContext {

    private static Context sContext;

    public static void init(Context context) {
        sContext = context;
    }

    public static Context getInstance() {
        return sContext;
    }

    private AppContext() {

    }
}