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

package com.hrules.gitego.data.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.hrules.gitego.data.exceptions.NetworkIOException;
import com.hrules.gitego.data.exceptions.NetworkUnauthorizedException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;

public class Network {
  private final OkHttpClient client = new OkHttpClient();

  public String post(@NonNull RequestNetwork requestNetwork) throws NetworkIOException, NetworkUnauthorizedException {
    Request request = new Request.Builder().url(requestNetwork.getUrl())
        .headers(makeHeaders(requestNetwork.getMapHeaders()))
        .post(makePostParams(requestNetwork.getMapParams()))
        .build();

    Response response;
    try {
      response = client.newCall(request).execute();
      if (!response.isSuccessful()) throw new NetworkUnauthorizedException();
      return response.body().string();
    } catch (IOException e) {
      throw new NetworkIOException(e.getMessage());
    }
  }

  public String get(@NonNull RequestNetwork requestNetwork) throws NetworkIOException, NetworkUnauthorizedException {
    Request request = new Request.Builder().url(makeGetParams(requestNetwork.getUrl(), requestNetwork.getMapParams()))
        .headers(makeHeaders(requestNetwork.getMapHeaders()))
        .build();

    Response response;
    try {
      response = client.newCall(request).execute();
      if (!response.isSuccessful()) throw new NetworkUnauthorizedException();
      return response.body().string();
    } catch (IOException e) {
      throw new NetworkIOException(e.getMessage());
    }
  }

  private Headers makeHeaders(@Nullable Map<String, String> mapHeaders) {
    if (mapHeaders == null) {
      return Headers.of(new HashMap<String, String>());
    }
    return Headers.of(mapHeaders);
  }

  private RequestBody makePostParams(@Nullable Map<String, String> mapParams) {
    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

    if (mapParams == null || mapParams.isEmpty()) {
      return RequestBody.create(mediaType, new byte[0]);
    }

    StringBuilder content = new StringBuilder();
    for (Map.Entry<String, String> entry : mapParams.entrySet()) {
      if (content.length() > 0) {
        content.append('&');
      }
      content.append(URLEncoder.encode(entry.getKey())).append('=').append(URLEncoder.encode(entry.getValue()));
    }
    byte[] contentBytes = content.toString().getBytes(Util.UTF_8);
    return RequestBody.create(mediaType, contentBytes);
  }

  private String makeGetParams(@NonNull String url, @Nullable Map<String, String> mapParams) {
    if (mapParams == null || mapParams.isEmpty()) {
      return url;
    }

    StringBuilder params = new StringBuilder();
    for (Map.Entry<String, String> entry : mapParams.entrySet()) {
      if (params.length() > 0) {
        params.append('&');
      }
      params.append(URLEncoder.encode(entry.getKey())).append('=').append(URLEncoder.encode(entry.getValue()));
    }
    return url + "?" + params.toString();
  }
}
