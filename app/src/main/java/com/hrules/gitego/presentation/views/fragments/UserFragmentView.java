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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.hrules.gitego.App;
import com.hrules.gitego.R;
import com.hrules.gitego.presentation.commons.StringUtils;
import com.hrules.gitego.presentation.commons.extensions.TextSwitcherFactory;
import com.hrules.gitego.presentation.commons.images.ImageLoader;
import com.hrules.gitego.presentation.commons.usernotifications.BriefMessage;
import com.hrules.gitego.presentation.models.GitHubAuthRepo;
import com.hrules.gitego.presentation.models.GitHubAuthUser;
import com.hrules.gitego.presentation.presenters.fragments.UserFragmentPresenter;
import com.hrules.gitego.presentation.views.activities.LoginActivityView;
import com.hrules.gitego.presentation.views.fragments.base.DRMVPFragmentV4;
import java.util.List;

public final class UserFragmentView extends DRMVPFragmentV4<UserFragmentPresenter, UserFragmentPresenter.Contract>
    implements UserFragmentPresenter.Contract {
  @BindView(R.id.userLogin) TextSwitcher userLogin;
  @BindView(R.id.userName) TextSwitcher userName;
  @BindView(R.id.followers) TextSwitcher followers;
  @BindView(R.id.avatar) ImageView avatar;
  @BindView(R.id.watchersCount) TextSwitcher watchersCount;
  @BindView(R.id.starsGazersCount) TextSwitcher starsCount;
  @BindView(R.id.forksCount) TextSwitcher forksCount;

  private Unbinder unbinder;

  @Override protected int getLayoutResId() {
    return R.layout.fragment_user;
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initializeViews(view);
  }

  private void initializeViews(@NonNull View view) {
    unbinder = ButterKnife.bind(this, view);

    userLogin.setFactory(new TextSwitcherFactory(userLogin, "", R.style.TextSwitcherSmall));
    userName.setFactory(new TextSwitcherFactory(userName, getString(R.string.text_empty), R.style.TextSwitcherLarge));
    followers.setFactory(new TextSwitcherFactory(followers, getString(R.string.text_empty), R.style.TextSwitcherMediumHighLighted));
    watchersCount.setFactory(new TextSwitcherFactory(watchersCount, getString(R.string.text_empty), R.style.TextSwitcherMediumHighLighted));
    starsCount.setFactory(new TextSwitcherFactory(starsCount, getString(R.string.text_empty), R.style.TextSwitcherMediumHighLighted));
    forksCount.setFactory(new TextSwitcherFactory(forksCount, getString(R.string.text_empty), R.style.TextSwitcherMediumHighLighted));
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

  private void launchLoginActivity() {
    startActivity(new Intent(getActivity(), LoginActivityView.class));
    getActivity().finish();
  }

  public void setUserLogin(@NonNull String userLoginString) {
    userLogin.setText(userLoginString);
  }

  @SuppressWarnings("ConstantConditions") @Override @UiThread public void setUserData(@NonNull GitHubAuthUser gitHubAuthUser) {
    ImageLoader.loadRounded(App.getApplication(), gitHubAuthUser.getAvatarUrl(), avatar);

    userName.setText(gitHubAuthUser.getName());

    Spannable spannableUserFollowers =
        StringUtils.createVariationSpannableString(getString(R.string.user_followersFormatted), gitHubAuthUser.getFollowers(),
            gitHubAuthUser.getGitHubAuthUserOlder().getFollowers(), ContextCompat.getColor(getActivity(), R.color.variationPositive),
            ContextCompat.getColor(getActivity(), R.color.variationNegative));
    followers.setText(spannableUserFollowers);
  }

  @SuppressWarnings("ConstantConditions") @Override @UiThread public void setRepoCounters(@NonNull List<GitHubAuthRepo> list) {
    int watchers = 0;
    int stargazers = 0;
    int forks = 0;
    int watchersOlder = 0;
    int stargazersOlder = 0;
    int forksOlder = 0;

    for (GitHubAuthRepo item : list) {
      watchers += item.getWatchersCount();
      stargazers += item.getStargazersCount();
      forks += item.getForksCount();
      watchersOlder += item.getGitHubAuthRepoOlder().getWatchersCount();
      stargazersOlder += item.getGitHubAuthRepoOlder().getStargazersCount();
      forksOlder += item.getGitHubAuthRepoOlder().getForksCount();
    }

    String textVariation = getString(R.string.text_variationFormatted);
    int colorVariationPositive = ContextCompat.getColor(getActivity(), R.color.variationPositive);
    int colorVariationNegative = ContextCompat.getColor(getActivity(), R.color.variationNegative);

    final Spannable spannableWatchersCount =
        StringUtils.createVariationSpannableString(textVariation, watchers, watchersOlder, colorVariationPositive, colorVariationNegative);

    final Spannable spannableStargazersCount =
        StringUtils.createVariationSpannableString(textVariation, stargazers, stargazersOlder, colorVariationPositive,
            colorVariationNegative);

    final Spannable spannableForksCount =
        StringUtils.createVariationSpannableString(textVariation, forks, forksOlder, colorVariationPositive, colorVariationNegative);

    watchersCount.setText(spannableWatchersCount);
    starsCount.setText(spannableStargazersCount);
    forksCount.setText(spannableForksCount);
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
}
