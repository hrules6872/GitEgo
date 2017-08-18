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

package com.hrules.gitego.presentation.views.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.util.TypedValue;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.hrules.gitego.R;
import com.hrules.gitego.presentation.adapters.RepoAdapter;
import com.hrules.gitego.presentation.adapters.decorators.SpaceItemDecoration;
import com.hrules.gitego.presentation.commons.StringUtils;
import com.hrules.gitego.presentation.commons.usernotifications.BriefMessage;
import com.hrules.gitego.presentation.models.GitHubAuthRepo;
import com.hrules.gitego.presentation.models.comparators.GitHubAuthRepoImplicationsDescendingComparator;
import com.hrules.gitego.presentation.presenters.fragments.RepoFragmentPresenter;
import com.hrules.gitego.presentation.views.activities.LoginActivityView;
import com.hrules.gitego.presentation.views.fragments.base.DRMVPFragmentV4;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import static com.hrules.gitego.presentation.commons.StringUtils.createVariationSpannableString;

public final class RepoFragmentView extends DRMVPFragmentV4<RepoFragmentPresenter, RepoFragmentPresenter.Contract>
    implements RepoFragmentPresenter.Contract {
  @BindView(R.id.recyclerView) RecyclerView recyclerView;

  private static final String BUNDLE_RECYCLER_STATE = "BUNDLE_RECYCLER_STATE";

  private Unbinder unbinder;
  private Parcelable recyclerViewState;

  private RepoAdapter adapter;

  @Override public int getLayoutResId() {
    return R.layout.fragment_repo;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initializeViews(view);
  }

  private void initializeViews(@NonNull View view) {
    unbinder = ButterKnife.bind(this, view);

    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    recyclerView.addItemDecoration(new SpaceItemDecoration((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
        getResources().getDimension(R.dimen.itemRepoDivider_size), getResources().getDisplayMetrics()), false, false));

    adapter = new RepoAdapter(new RepoAdapter.RepoAdapterListener() {
      @Override public void onListItemClick(int position) {
        launchBrowser(adapter.getItem(position).getHtmlUrl());
      }
    });
    recyclerView.setAdapter(adapter);
  }

  @Override public void onViewStateRestored(Bundle savedInstanceState) {
    if (savedInstanceState != null) {
      recyclerViewState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_STATE);
    }
    super.onViewStateRestored(savedInstanceState);
  }

  @Override public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelable(BUNDLE_RECYCLER_STATE, recyclerView.getLayoutManager().onSaveInstanceState());
  }

  @Override public void onResume() {
    super.onResume();
    getPresenter().onViewResume();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    if (unbinder != null) {
      unbinder.unbind();
    }
  }

  @SuppressWarnings("ConstantConditions") @Override @UiThread
  public void updateList(@NonNull List<GitHubAuthRepo> list) {
    String textVariation = getString(R.string.text_variationFormatted);
    int colorVariationPositive = ContextCompat.getColor(getActivity(), R.color.variationPositive);
    int colorVariationNegative = ContextCompat.getColor(getActivity(), R.color.variationNegative);

    for (ListIterator<GitHubAuthRepo> iterator = list.listIterator(); iterator.hasNext(); ) {
      GitHubAuthRepo item = iterator.next();

      Spannable stargazersCountSpannable = createVariationSpannableString(textVariation, item.getStargazersCount(),
          item.getGitHubAuthRepoOlder().getStargazersCount(), colorVariationPositive, colorVariationNegative);
      Spannable watchersCountSpannable =
          StringUtils.createVariationSpannableString(textVariation, item.getWatchersCount(),
              item.getGitHubAuthRepoOlder().getWatchersCount(), colorVariationPositive, colorVariationNegative);
      Spannable forksCountSpannable = createVariationSpannableString(textVariation, item.getForksCount(),
          item.getGitHubAuthRepoOlder().getForksCount(), colorVariationPositive, colorVariationNegative);

      iterator.set(item.withCountSpannables(stargazersCountSpannable, watchersCountSpannable, forksCountSpannable));
    }

    Collections.sort(list, new GitHubAuthRepoImplicationsDescendingComparator());
    adapter.update(list);

    if (list.isEmpty()) {
      setListBackgroundColor(R.color.windowBackground);
    } else {
      setListBackgroundColor(R.color.greyBackground);
    }
  }

  @Override @UiThread public void updateListState() {
    if (recyclerViewState != null) {
      recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
    }
  }

  private void launchBrowser(@NonNull String html_url) {
    try {
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(html_url)));
    } catch (Exception ignored) {
    }
  }

  @Override public void showBriefMessage(@NonNull String message) {
    BriefMessage.INSTANCE.showLong(getActivity().findViewById(R.id.rootLayout), message);
  }

  @Override public void showBriefMessageAction(@NonNull String message, @NonNull String action) {
    BriefMessage.INSTANCE.showActionIndefinite(getActivity().findViewById(R.id.rootLayout), message, action,
        new BriefMessage.BriefMessageListener() {
          @Override public void onClick() {
            launchLoginActivity();
          }
        });
  }

  private void launchLoginActivity() {
    startActivity(new Intent(getActivity(), LoginActivityView.class));
    getActivity().finish();
  }

  private void setListBackgroundColor(@ColorRes int color) {
    recyclerView.setBackgroundColor(ContextCompat.getColor(getActivity(), color));
  }
}
