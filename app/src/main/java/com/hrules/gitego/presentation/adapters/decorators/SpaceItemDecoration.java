/*
 * Copyright (c) 2016. Héctor de Isidro - hrules6872
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hrules.gitego.presentation.adapters.decorators;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/*
 * +info: https://gist.github.com/hrules6872/b68bf3762e4d1243e480
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
  private static final boolean DEFAULT_ADD_SPACE_ABOVE_FIRST_ITEM = false;
  private static final boolean DEFAULT_ADD_SPACE_BELOW_LAST_ITEM = false;

  private final int space;
  private final boolean addSpaceAboveFirstItem;
  private final boolean addSpaceBelowLastItem;

  public SpaceItemDecoration(int space) {
    this(space, DEFAULT_ADD_SPACE_ABOVE_FIRST_ITEM, DEFAULT_ADD_SPACE_BELOW_LAST_ITEM);
  }

  public SpaceItemDecoration(int space, boolean addSpaceAboveFirstItem,
      boolean addSpaceBelowLastItem) {
    this.space = space;
    this.addSpaceAboveFirstItem = addSpaceAboveFirstItem;
    this.addSpaceBelowLastItem = addSpaceBelowLastItem;
  }

  @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
      RecyclerView.State state) {
    super.getItemOffsets(outRect, view, parent, state);
    if (space <= 0) {
      return;
    }

    if (addSpaceAboveFirstItem && parent.getChildLayoutPosition(view) < 1
        || parent.getChildLayoutPosition(view) >= 1) {
      if (getOrientation(parent) == LinearLayoutManager.VERTICAL) {
        outRect.top = space;
      } else {
        outRect.left = space;
      }
    }

    if (addSpaceBelowLastItem
        && parent.getChildAdapterPosition(view) == getTotalItemCount(parent) - 1) {
      if (getOrientation(parent) == LinearLayoutManager.VERTICAL) {
        outRect.bottom = space;
      } else {
        outRect.right = space;
      }
    }
  }

  private int getTotalItemCount(RecyclerView parent) {
    return parent.getAdapter().getItemCount();
  }

  private int getOrientation(RecyclerView parent) {
    if (parent.getLayoutManager() instanceof LinearLayoutManager) {
      return ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
    } else {
      throw new IllegalStateException(
          "SpaceItemDecoration can only be used with a LinearLayoutManager.");
    }
  }
}