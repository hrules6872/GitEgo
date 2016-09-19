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

package com.hrules.gitego.presentation.adapters;

import android.support.annotation.NonNull;
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
import java.util.List;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.ViewHolder> {
  private List<GitHubAuthRepo> items;
  private final RepoAdapterListener listener;

  public RepoAdapter(@NonNull List<GitHubAuthRepo> items, @NonNull RepoAdapterListener listener) {
    this.items = items;
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
        listener.onListItemClick(viewHolder.getAdapterPosition(), v);
      }
    });
    return viewHolder;
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    GitHubAuthRepo item = items.get(position);
    holder.repoName.setText(item.getName());
    holder.repoFork.setVisibility(item.isFork() ? View.VISIBLE : View.GONE);
    holder.watchersCount.setText(item.getWatchers_countSpannable());
    holder.starsGazersCount.setText(item.getStargazers_countSpannable());
    holder.forksCount.setText(item.getForks_countSpannable());
  }

  @Override public int getItemCount() {
    return items.size();
  }

  public void add(int position, @NonNull GitHubAuthRepo item) {
    items.add(position, item);
    notifyItemInserted(position);
  }

  public void remove(int position) {
    items.remove(position);
    notifyItemRemoved(position);
  }

  public void update(@NonNull List<GitHubAuthRepo> list) {
    this.items = list;
    notifyDataSetChanged();
  }

  public GitHubAuthRepo getItem(int position) {
    if (position > items.size()) {
      return new GitHubAuthRepo();
    }
    return items.get(position);
  }

  public List<GitHubAuthRepo> getItems() {
    return items;
  }
}
