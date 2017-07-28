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

package com.hrules.gitego.presentation.views.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.hrules.gitego.R;
import com.hrules.gitego.presentation.adapters.RepoAdapter;
import com.hrules.gitego.presentation.adapters.decorators.SpaceItemDecoration;
import com.hrules.gitego.presentation.bus.BoolStateBus;
import com.hrules.gitego.presentation.bus.base.Bus;
import com.hrules.gitego.presentation.bus.constants.BusActionConstants;
import com.hrules.gitego.presentation.commons.StringUtils;
import com.hrules.gitego.presentation.commons.usernotifications.BriefMessage;
import com.hrules.gitego.presentation.models.GitHubAuthRepo;
import com.hrules.gitego.presentation.models.comparators.GitHubAuthRepoImplicationsDescendingComparator;
import com.hrules.gitego.presentation.presenters.fragments.RepoFragmentPresenter;
import com.hrules.gitego.presentation.views.activities.LoginActivityView;
import com.hrules.gitego.presentation.views.fragments.base.DRMVPFragmentV4;
import java.util.Collections;
import java.util.List;

public class RepoFragmentView extends DRMVPFragmentV4<RepoFragmentPresenter, RepoFragmentPresenter.Contract>
    implements RepoFragmentPresenter.Contract {
  @BindView(R.id.recyclerView) RecyclerView recyclerView;

  private static final String BUNDLE_RECYCLER_STATE = "BUNDLE_RECYCLER_STATE";

  private Unbinder unbinder;
  private Parcelable recyclerViewState;
  private Bus bus;

  private RepoAdapter adapter;

  @Override public int getLayoutResId() {
    return R.layout.repo_fragment;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initializeViews(view);
  }

  private void initializeViews(@NonNull View view) {
    unbinder = ButterKnife.bind(this, view);

    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    recyclerView.addItemDecoration(new SpaceItemDecoration(
        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getResources().getDimension(R.dimen.itemRepoDivider_size),
            getResources().getDisplayMetrics()), false, false));

    adapter = new RepoAdapter(new RepoAdapter.RepoAdapterListener() {
      @Override public void onListItemClick(int position) {
        launchBrowser(adapter.getItem(position).getHtml_url());
      }
    });
    recyclerView.setAdapter(adapter);
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof Activity) {
      try {
        bus = (Bus) context;
      } catch (ClassCastException e) {
        throw new ClassCastException(context.toString() + " must implement Bus delegate");
      }
    }
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

  @Override public void updateList(@NonNull List<GitHubAuthRepo> list) {
    String textVariation = getString(R.string.text_variationFormatted);
    int colorVariationPositive = ContextCompat.getColor(getActivity(), R.color.variationPositive);
    int colorVariationNegative = ContextCompat.getColor(getActivity(), R.color.variationNegative);

    for (GitHubAuthRepo item : list) {
      item.setWatchers_countSpannable(StringUtils.createVariationSpannableString(textVariation, item.getWatchers_count(),
          item.getGitHubAuthRepoOlder().getWatchers_count(), colorVariationPositive, colorVariationNegative));

      item.setStargazers_countSpannable(StringUtils.createVariationSpannableString(textVariation, item.getStargazers_count(),
          item.getGitHubAuthRepoOlder().getStargazers_count(), colorVariationPositive, colorVariationNegative));

      item.setForks_countSpannable(
          StringUtils.createVariationSpannableString(textVariation, item.getForks_count(), item.getGitHubAuthRepoOlder().getForks_count(),
              colorVariationPositive, colorVariationNegative));
    }

    Collections.sort(list, new GitHubAuthRepoImplicationsDescendingComparator());
    adapter.update(list);

    if (list.size() > 0) {
      setListBackgroundColor(R.color.greyBackground);
    } else {
      setListBackgroundColor(R.color.windowBackground);
    }
  }

  @Override public void updateListState() {
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

  @Override public void showLoading() {
    if (bus != null) {
      bus.onEvent(new BoolStateBus(BusActionConstants.ACTION_SHOW_LOADING, true));
    }
  }

  @Override public void hideLoading() {
    if (bus != null) {
      bus.onEvent(new BoolStateBus(BusActionConstants.ACTION_SHOW_LOADING, false));
    }
  }

  @Override public void showBriefMessage(@StringRes int message) {
    BriefMessage.showLong(getActivity().findViewById(R.id.rootLayout), getString(message));
  }

  @Override public void showBriefMessageAction(@StringRes int message, @StringRes int action) {
    BriefMessage.showActionIndefinite(getActivity().findViewById(R.id.rootLayout), getString(message), getString(action),
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
