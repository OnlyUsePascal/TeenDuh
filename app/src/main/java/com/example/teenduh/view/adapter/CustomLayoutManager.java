package com.example.teenduh.view.adapter;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

public class CustomLayoutManager extends LinearLayoutManager {
  public CustomLayoutManager(Context context) {
    super(context, VERTICAL, false);
  }
  
  public CustomLayoutManager(Context context, int orientation, boolean reverseLayout) {
    super(context, orientation, reverseLayout);
  }
  
  public CustomLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }
  
  @Override
  public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state,
                                     int position) {
    RecyclerView.SmoothScroller smoothScroller = new TopSnappedSmoothScroller(recyclerView.getContext());
    smoothScroller.setTargetPosition(position);
    startSmoothScroll(smoothScroller);
  }
  
  private class TopSnappedSmoothScroller extends LinearSmoothScroller {
    public TopSnappedSmoothScroller(Context context) {
      super(context);
      
    }
  
    @Override
    public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
      return (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2);
    }
  
    @Override
    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
      return 1.5f; //pass as per your requirement
    }
    
    @Override
    public PointF computeScrollVectorForPosition(int targetPosition) {
      return CustomLayoutManager.this
                 .computeScrollVectorForPosition(targetPosition);
    }
  }
}
