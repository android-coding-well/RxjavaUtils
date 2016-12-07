package com.junmeng.rxjava;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by HWJ on 2016/12/7.
 */

public interface GithubService {

    @GET("users/{user}/repos")
    Observable<List<Repo>> listRepos(@Path("user") String user);
}
