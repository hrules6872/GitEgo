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
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.hrules.darealmvp.DRFragmentV4;
import com.hrules.gitego.App;
import com.hrules.gitego.R;
import com.hrules.gitego.presentation.commons.StringUtils;
import com.hrules.gitego.presentation.commons.images.ImageLoader;
import com.hrules.gitego.presentation.commons.usernotifications.BriefMessage;
import com.hrules.gitego.presentation.commons.usernotifications.BriefMessageListener;
import com.hrules.gitego.presentation.communicator.BoolStateMessage;
import com.hrules.gitego.presentation.communicator.CommunicatorConstants;
import com.hrules.gitego.presentation.communicator.base.Communicator;
import com.hrules.gitego.presentation.models.GitHubAuthRepo;
import com.hrules.gitego.presentation.models.GitHubAuthUser;
import com.hrules.gitego.presentation.presenters.fragments.UserFragmentPresenter;
import com.hrules.gitego.presentation.views.activities.LoginActivityView;
import java.util.List;

public class UserFragmentView extends DRFragmentV4<UserFragmentPresenter, UserFragmentPresenter.UserView>
    implements UserFragmentPresenter.UserView {
  @BindView(R.id.userLogin) TextView userLogin;
  @BindView(R.id.userName) TextView userName;
  @BindView(R.id.followers) TextView followers;
  @BindView(R.id.avatar) ImageView avatar;
  @BindView(R.id.watchersCount) TextView watchersCount;
  @BindView(R.id.starsGazersCount) TextView starsCount;
  @BindView(R.id.forksCount) TextView forksCount;

  private Unbinder unbinder;
  private Communicator communicator;

  @Override protected int getLayoutResource() {
    return R.layout.user_fragment;
  }

  @Override public void initializeViews(@NonNull View view) {
    unbinder = ButterKnife.bind(this, view);
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

  @Override public void onResume() {
    super.onResume();
    getPresenter().onResume();
  }

  public void launchLoginActivity() {
    startActivity(new Intent(getActivity(), LoginActivityView.class));
    getActivity().finish();
  }

  public void setUserLogin(@NonNull String userLogin) {
    this.userLogin.setText(userLogin);
  }

  @Override public void setUserData(@NonNull GitHubAuthUser gitHubAuthUser) {
    new ImageLoader(App.getApplication()).load(gitHubAuthUser.getAvatar_url(), avatar);

    userName.setText(gitHubAuthUser.getName());

    Spannable spannableUserFollowers =
        StringUtils.createVariationSpannableString(getString(R.string.user_followersFormatted),
            gitHubAuthUser.getFollowers(), gitHubAuthUser.getGitHubAuthUserOlder().getFollowers(),
            ContextCompat.getColor(getActivity(), R.color.variationPositive),
            ContextCompat.getColor(getActivity(), R.color.variationNegative));
    followers.setText(spannableUserFollowers, TextView.BufferType.SPANNABLE);
  }

  @Override public void setRepoCounters(@NonNull List<GitHubAuthRepo> list) {
    int watchers = 0;
    int stargazers = 0;
    int forks = 0;
    int watchersOlder = 0;
    int stargazersOlder = 0;
    int forksOlder = 0;

    for (GitHubAuthRepo item : list) {
      watchers += item.getWatchers_count();
      stargazers += item.getStargazers_count();
      forks += item.getForks_count();
      watchersOlder += item.getGitHubAuthRepoOlder().getWatchers_count();
      stargazersOlder += item.getGitHubAuthRepoOlder().getStargazers_count();
      forksOlder += item.getGitHubAuthRepoOlder().getForks_count();
    }

    String textVariation = getString(R.string.text_variationFormatted);
    int colorVariationPositive = ContextCompat.getColor(getActivity(), R.color.variationPositive);
    int colorVariationNegative = ContextCompat.getColor(getActivity(), R.color.variationNegative);

    final Spannable spannableWatchersCount =
        StringUtils.createVariationSpannableString(textVariation, watchers, watchersOlder, colorVariationPositive,
            colorVariationNegative);

    final Spannable spannableStargazersCount =
        StringUtils.createVariationSpannableString(textVariation, stargazers, stargazersOlder, colorVariationPositive,
            colorVariationNegative);

    final Spannable spannableForksCount =
        StringUtils.createVariationSpannableString(textVariation, forks, forksOlder, colorVariationPositive,
            colorVariationNegative);

    watchersCount.setText(spannableWatchersCount, TextView.BufferType.SPANNABLE);
    starsCount.setText(spannableStargazersCount, TextView.BufferType.SPANNABLE);
    forksCount.setText(spannableForksCount, TextView.BufferType.SPANNABLE);
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
    new BriefMessage().showActionIndefinite(getActivity().findViewById(R.id.rootLayout), getString(message),
        getString(action), new BriefMessageListener() {
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
}
