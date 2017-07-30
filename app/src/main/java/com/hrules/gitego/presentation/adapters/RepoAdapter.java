/*
 * Copyright (c) 2017. Héctor de Isidro - hrules6872
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

package com.hrules.gitego.presentation.adapters;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.hrules.gitego.R;
import com.hrules.gitego.presentation.models.GitHubAuthRepo;
import java.util.ArrayList;
import java.util.List;

public final class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder> {
  private final List<GitHubAuthRepo> items = new ArrayList<>();
  private final RepoAdapterListener listener;

  public interface RepoAdapterListener {
    void onListItemClick(int position);
  }

  public RepoAdapter(@NonNull RepoAdapterListener listener) {
    this.listener = listener;
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.cardView) CardView cardView;
    @BindView(R.id.repoName) TextView repoName;
    @BindView(R.id.repoFork) TextView repoFork;
    @BindView(R.id.watchersCount) TextView watchersCount;
    @BindView(R.id.starsGazersCount) TextView starsGazersCount;
    @BindView(R.id.forksCount) TextView forksCount;

    ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo, parent, false);
    final ViewHolder viewHolder = new ViewHolder(view);
    view.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        listener.onListItemClick(viewHolder.getAdapterPosition());
      }
    });
    return viewHolder;
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    GitHubAuthRepo item = items.get(position);
    holder.repoName.setText(item.getName());
    holder.repoFork.setVisibility(item.isFork() ? View.VISIBLE : View.GONE);

    holder.watchersCount.setText(item.getWatchersCountSpannable());
    holder.watchersCount.setEnabled(item.getWatchersCount() != 0);
    setDrawableEnabled(holder.watchersCount, 0, item.getWatchersCount() != 0);

    holder.starsGazersCount.setText(item.getStargazersCountSpannable());
    holder.starsGazersCount.setEnabled(item.getStargazersCount() != 0);
    setDrawableEnabled(holder.starsGazersCount, 0, item.getStargazersCount() != 0);

    holder.forksCount.setText(item.getForksCountSpannable());
    holder.forksCount.setEnabled(item.getForksCount() != 0);
    setDrawableEnabled(holder.forksCount, 0, item.getForksCount() != 0);
  }

  private void setDrawableEnabled(@NonNull TextView textView, int drawablePosition, boolean enabled) {
    Drawable[] drawables = textView.getCompoundDrawables();
    if (drawables.length >= drawablePosition && drawables[drawablePosition] != null) {
      Drawable drawable = drawables[drawablePosition];
      drawable = DrawableCompat.wrap(drawable);
      DrawableCompat.setTint(drawable, ContextCompat.getColor(textView.getContext(), R.color.repoItemDrawableDisabled));
      DrawableCompat.setTintMode(drawable, enabled ? PorterDuff.Mode.DST : PorterDuff.Mode.SRC_ATOP);
      textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }
  }

  @Override public int getItemCount() {
    return items.size();
  }

  public void update(@NonNull List<GitHubAuthRepo> list) {
    this.items.clear();
    this.items.addAll(list);
    notifyDataSetChanged();
  }

  public GitHubAuthRepo getItem(int position) {
    if (position > items.size()) {
      return GitHubAuthRepo.builder().build();
    }
    return items.get(position);
  }
}
