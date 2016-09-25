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
import com.hrules.darealmvp.DRFragmentV4;
import com.hrules.gitego.R;
import com.hrules.gitego.presentation.adapters.RepoAdapter;
import com.hrules.gitego.presentation.adapters.RepoAdapterListener;
import com.hrules.gitego.presentation.adapters.decorators.SpaceItemDecoration;
import com.hrules.gitego.presentation.commons.StringUtils;
import com.hrules.gitego.presentation.commons.usernotifications.BriefMessage;
import com.hrules.gitego.presentation.commons.usernotifications.BriefMessageListener;
import com.hrules.gitego.presentation.communicator.BoolStateMessage;
import com.hrules.gitego.presentation.communicator.CommunicatorConstants;
import com.hrules.gitego.presentation.communicator.base.Communicator;
import com.hrules.gitego.presentation.models.GitHubAuthRepo;
import com.hrules.gitego.presentation.models.comparators.GitHubAuthRepoImplicationsDescendingComparator;
import com.hrules.gitego.presentation.presenters.fragments.RepoFragmentPresenter;
import com.hrules.gitego.presentation.views.activities.LoginActivityView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepoFragmentView extends DRFragmentV4<RepoFragmentPresenter, RepoFragmentPresenter.RepoView>
    implements RepoFragmentPresenter.RepoView {
  private static final String BUNDLE_RECYCLER_STATE = "BUNDLE_RECYCLER_STATE";

  @BindView(R.id.recyclerView) RecyclerView recyclerView;

  private Unbinder unbinder;
  private Parcelable recyclerViewState;
  private Communicator communicator;

  private RepoAdapter adapter;

  @Override public int getLayoutResource() {
    return R.layout.repo_fragment;
  }

  @Override public void initializeViews(View view) {
    unbinder = ButterKnife.bind(this, view);

    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    recyclerView.addItemDecoration(new SpaceItemDecoration(
        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getResources().getDimension(R.dimen.itemRepoDivider_size),
            getResources().getDisplayMetrics()), false, false));

    adapter = new RepoAdapter(new ArrayList<GitHubAuthRepo>(), new RepoAdapterListener() {
      @Override public void onListItemClick(int position, @NonNull View view) {
        getPresenter().onListItemClick(adapter.getItem(position));
      }
    });
    recyclerView.setAdapter(adapter);
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof Activity) {
      try {
        communicator = (Communicator) context;
      } catch (ClassCastException e) {
        throw new ClassCastException(context.toString() + " must implement Communicator delegate");
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

  public void launchLoginActivity() {
    startActivity(new Intent(getActivity(), LoginActivityView.class));
    getActivity().finish();
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

  @Override public void launchBrowser(@NonNull String html_url) {
    try {
      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(html_url)));
    } catch (Exception ignored) {
    }
  }

  @Override public void showLoading() {
    if (communicator != null) {
      communicator.onMessage(new BoolStateMessage(CommunicatorConstants.ACTION_SHOW_LOADING, true));
    }
  }

  @Override public void hideLoading() {
    if (communicator != null) {
      communicator.onMessage(new BoolStateMessage(CommunicatorConstants.ACTION_SHOW_LOADING, false));
    }
  }

  @Override public void showBriefMessage(@StringRes int message) {
    new BriefMessage().showLong(getActivity().findViewById(R.id.rootLayout), getString(message));
  }

  @Override public void showBriefMessageAction(@StringRes int message, @StringRes int action) {
    new BriefMessage().showActionIndefinite(getActivity().findViewById(R.id.rootLayout), getString(message), getString(action),
        new BriefMessageListener() {
          @Override public void onClick() {
            getPresenter().doLogin();
          }
        });
  }

  @Override public void unbind() {
    if (unbinder != null) {
      unbinder.unbind();
    }
  }

  private void setListBackgroundColor(@ColorRes int color) {
    recyclerView.setBackgroundColor(ContextCompat.getColor(getActivity(), color));
  }
}
