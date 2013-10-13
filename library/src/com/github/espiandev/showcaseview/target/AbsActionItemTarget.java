package com.github.espiandev.showcaseview.target;

import android.app.Activity;
import android.view.View;
import android.view.ViewParent;

/**
 * Created by curraa01 on 10/09/2013.
 */
public abstract class AbsActionItemTarget {

    protected final ViewParent getActionBarView(Activity activity) {

        // Find the view for the home button
        View homeButton = getHomeButtonView(activity);
        ViewParent p = homeButton.getParent().getParent(); //ActionBarView

        String className1 = p.getClass().getName();
        if (!className1.contains("ActionBarView")) {
            p = p.getParent();
            String className2 = p.getClass().getName();
            if (!className2.contains("ActionBarView"))
                throw new IllegalStateException("Cannot find ActionBarView for " +
                        "Activity, instead found " + className1 + " and " + className2);
        }
        return p;

    }

    private View getHomeButtonView(Activity activity) {

        View homeButton = activity.findViewById(android.R.id.home);

        // If the home button is null, we try to find the ActionBarSherlock one instead
        if (homeButton == null) {
            int homeId = activity.getResources().getIdentifier("abs__home", "id", activity.getPackageName());
            homeButton = activity.findViewById(homeId);
        }

        if (homeButton == null)
            throw new RuntimeException("insertShowcaseViewWithType cannot be used when the theme " +
                    "has no ActionBar");

        return homeButton;
    }

    protected Class getActionBarViewClass(View actionBarView) {
        return actionBarView.getClass();
    }

    protected Class getAbsActionBarViewClass(View actionBarView) {
        return actionBarView.getClass().getSuperclass();
    }

}
