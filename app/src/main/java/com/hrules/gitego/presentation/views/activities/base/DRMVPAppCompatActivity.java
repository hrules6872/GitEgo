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

package com.hrules.gitego.presentation.views.activities.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.hrules.darealmvp.DRMVPPresenter;
import com.hrules.darealmvp.DRMVPView;
import com.hrules.gitego.presentation.views.activities.base.utils.DRMVPUtils;

@SuppressWarnings("unchecked") public abstract class DRMVPAppCompatActivity<P extends DRMVPPresenter<V>, V extends DRMVPView>
    extends AppCompatActivity {
  private P presenter;

  @CallSuper @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutResId());
    presenter = DRMVPUtils.getDeclaredPresenter(getClass());
    presenter.bind((V) this);
  }

  @CallSuper @Override protected void onDestroy() {
    super.onDestroy();
    presenter.unbind();
  }

  protected @NonNull P getPresenter() {
    return presenter;
  }

  protected abstract @LayoutRes int getLayoutResId();
}
