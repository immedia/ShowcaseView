package com.github.espiandev.showcaseview.target;

import android.app.Activity;

/**
 * Created by curraa01 on 10/09/2013.
 */
public class ActionBarViewTarget {

    private final Activity mActivity;
    private final Type mType;

    public ActionBarViewTarget(Activity activity, Type type) {
        mActivity = activity;
        mType = type;
    }

    public static enum Type {
        SPINNER, OVERFLOW, HOME
    }

}
