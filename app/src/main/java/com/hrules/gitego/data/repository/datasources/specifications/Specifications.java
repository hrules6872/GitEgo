package com.hrules.gitego.data.repository.datasources.specifications;

import android.support.annotation.NonNull;
import com.hrules.gitego.data.repository.datasources.api.specifications.AuthRepoAPIGetAuthRepoSpecification;
import com.hrules.gitego.data.repository.datasources.api.specifications.AuthRepoSubscribersAPIGetAuthRepoSubscribersSpecification;
import com.hrules.gitego.data.repository.datasources.api.specifications.AuthUserAPIGetAuthUserSpecification;
import com.hrules.gitego.data.repository.datasources.bdd.specifications.AuthRepoBddDeleteAuthRepoSpecification;
import com.hrules.gitego.data.repository.datasources.bdd.specifications.AuthRepoBddGetAuthRepoSpecification;
import com.hrules.gitego.data.repository.datasources.bdd.specifications.AuthUserBddGetAuthUserSpecification;

public final class Specifications {
  public static @NonNull Class[] get() {
    return new Class[] {
        AuthRepoAPIGetAuthRepoSpecification.class, AuthRepoSubscribersAPIGetAuthRepoSubscribersSpecification.class,
        AuthUserAPIGetAuthUserSpecification.class, AuthRepoBddDeleteAuthRepoSpecification.class, AuthRepoBddGetAuthRepoSpecification.class,
        AuthUserBddGetAuthUserSpecification.class
    };
  }
}
