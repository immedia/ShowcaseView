package com.github.espiandev.showcaseview.target;

import android.app.Activity;
import android.graphics.Point;
import android.view.View;
import android.view.ViewParent;

import java.lang.reflect.Field;

/**
 * Created by curraa01 on 10/09/2013.
 */
public class ActionItemTarget extends AbsActionItemTarget implements Target {

    private final Activity mActivity;
    private final int mMenuResId;
    private ViewTarget mInternalTarget;
    private Field mChildrenField;
    private Object mMenuView;

    public ActionItemTarget(Activity activity, int menuResId) {
        mActivity = activity;
        mMenuResId = menuResId;
    }

    @Override
    public Point getTargetPoint() {
        if (mInternalTarget != null) {
            return mInternalTarget.getTargetPoint();
        }
        return null;
    }

    public void init() {

        try {
            ViewParent p = getActionBarView(mActivity);
            Class absAbv = getAbsActionBarViewClass((View) p);

            Field mAmpField = absAbv.getDeclaredField("mActionMenuPresenter");
            mAmpField.setAccessible(true);
            Object mAmp = mAmpField.get(p);

            // Want an ActionItem, so find it
            Field mAmvField = mAmp.getClass().getSuperclass().getDeclaredField("mMenuView");
            mAmvField.setAccessible(true);
            mMenuView = mAmvField.get(mAmp);

            if (mMenuView.getClass().toString().contains("com.actionbarsherlock")) {
                // There are thousands of superclasses to traverse up
                // Have to get superclasses because mChildren is private
                mChildrenField = mMenuView.getClass().getSuperclass().getSuperclass()
                        .getSuperclass().getSuperclass().getDeclaredField("mChildren");
            } else {
                mChildrenField = mMenuView.getClass().getSuperclass().getSuperclass().getDeclaredField("mChildren");
            }

            mChildrenField.setAccessible(true);
            Object[] mChs = (Object[]) mChildrenField.get(mMenuView);
            for (Object mCh : mChs) {
                if (mCh != null) {
                    final View v = (View) mCh;
                    if (v.getId() == mMenuResId) {
                        v.post(new Runnable() {
                            @Override
                            public void run() {
                                mInternalTarget = new ViewTarget(v);
                            }
                        });
                    }

                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }
}
