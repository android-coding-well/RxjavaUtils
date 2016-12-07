package com.junmeng.rxutil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

/**
 * 带有加载框的订阅者
 * @param <T>
 */
public abstract class NetProgressSubscriber<T> extends NetSubscriber<T> implements DialogInterface.OnCancelListener{
    private static final String TAG = "NetProgressSubscriber";
    ProgressDialog progressDialog;
    public NetProgressSubscriber(Context context){
        initProgressDialog(context, "正在加载，请稍候...");
    }
    public NetProgressSubscriber(Context context, String dialogHint){
        initProgressDialog(context, dialogHint);
    }

    private void initProgressDialog(Context context, String dialogHint) {
        progressDialog=new ProgressDialog(context);
        progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(this);
        progressDialog.setMessage(dialogHint);
    }

    @Override
    public void onBefore() {
        super.onBefore();
        if(progressDialog!=null&&!progressDialog.isShowing()){
            progressDialog.show();
        }
    }

    @Override
    public void onAfter() {
        super.onAfter();
        if(progressDialog!=null&&progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    /**
     * 加载框被取消
     * @param dialogInterface
     */
    @Override
    public void onCancel(DialogInterface dialogInterface) {

        Log.i(TAG,"对话框被取消");
        //在对话框消失的同时取消订阅
        if(!this.isUnsubscribed()){
            this.unsubscribe();
        }
    }
}
