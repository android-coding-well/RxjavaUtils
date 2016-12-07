package com.junmeng.rxutil;

import rx.Subscriber;

/**
 * Created by hwj on 2016/9/1.
 */
public abstract class NetSubscriber<T> extends Subscriber<T> {

    public NetSubscriber() {
    }

    @Override
    public void onStart() {
        super.onStart();
        onBefore();
    }

    @Override
    public void onCompleted() {
        onAfter();
    }


    @Override
    public void onError(Throwable e) {
        onFailed(e);
        onAfter();
    }


    @Override
    public void onNext(T t) {
        if (t != null) {
            onSuccess(t);
        } else {
            onFailed(new Throwable("请求失败"));
        }
    }

    /**
     * 在网络请求之前，比如弹出对话框
     */
    public void onBefore() {
    }

    /**
     * 在网络请求之后，比如取消对话框
     */
    public void onAfter() {
    }

    /**
     * 网络请求成功
     *
     * @param t
     */
    public abstract void onSuccess(T t);

    /**
     * 网络请求失败
     *
     * @param e
     */
    public abstract void onFailed(Throwable e);

}
