package com.example.teenduh.view.adapter;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.rxjava3.annotations.NonNull;

public class ItemSpacingDecoration extends RecyclerView.ItemDecoration{
  private final int space;

  public ItemSpacingDecoration(int space) {
    this.space = space;
  }
  @Override
  public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
    outRect.right = space;
  }

}