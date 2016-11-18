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

package com.hrules.gitego.presentation.presenters.activities;

import android.support.annotation.NonNull;
import android.widget.Button;
import com.hrules.darealmvp.DRPresenter;
import com.hrules.darealmvp.DRView;
import com.hrules.gitego.R;

public class AboutActivityPresenter extends DRPresenter<AboutActivityPresenter.AboutView> {
  public void onClickButton(@NonNull Button button) {
    switch (button.getId()) {
      case R.id.about_rateIt:
        getView().goToPlayStore();
        break;

      case R.id.about_sendFeedback:
        getView().sendFeedbackByEmail();
        break;

      case R.id.about_moreApps:
        getView().goToPlayStoreDeveloper();
        break;

      case R.id.about_twitter:
        getView().goToTwitterDeveloper();
        break;

      case R.id.about_sourceCode:
        getView().goToSourceCode();
        break;

      default:
        throw new UnsupportedOperationException();
    }
  }

  public interface AboutView extends DRView {
    void goToPlayStore();

    void sendFeedbackByEmail();

    void goToPlayStoreDeveloper();

    void goToTwitterDeveloper();

    void goToSourceCode();
  }
}
