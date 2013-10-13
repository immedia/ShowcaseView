package com.github.espiandev.showcaseview.target;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import android.app.Activity;
import android.graphics.Point;
import android.view.View;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class ViewTargetTests {

    static final int VIEW_LEFT = 42;
    static final int VIEW_TOP = 92;
    static final int VIEW_DIMENSION = 1020;
    static final int VIEW_RES_ID = 2020200;

    @Mock
    View mView;
    @Mock
    Activity mActivity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(mView.getWidth()).thenReturn(VIEW_DIMENSION);
        when(mView.getHeight()).thenReturn(VIEW_DIMENSION);
    }

    @Test
    public void viewTargetReturnsCorrectPointForInsertToView() {
        when(mView.getLeft()).thenReturn(VIEW_LEFT);
        when(mView.getTop()).thenReturn(VIEW_TOP);
        Point p = new ViewTarget(mView).getTargetPoint();
        assertEquals(VIEW_LEFT + VIEW_DIMENSION / 2, p.x);
        assertEquals(VIEW_TOP + VIEW_DIMENSION / 2, p.y);
        verify(mView, never()).getLocationInWindow(Matchers.<int[]>any());
    }

    @Test
    public void viewTargetReturnsCorrectPointForInsertToDecor() {
        ViewTarget target = new ViewTarget(mView);
        target.setInsertToDecor(true);
        when(mView.getLeft()).thenReturn(VIEW_LEFT);
        when(mView.getTop()).thenReturn(VIEW_TOP);
        Point p = target.getTargetPoint();
        assertEquals(VIEW_LEFT + VIEW_DIMENSION / 2, p.x);
        assertEquals(VIEW_TOP + VIEW_DIMENSION / 2, p.y);
    }

    @Test
    public void viewTargetFindsViewById() {
        new ViewTarget(mActivity, VIEW_RES_ID);
        verify(mActivity).findViewById(VIEW_RES_ID);
    }

}
