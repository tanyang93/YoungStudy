package com.young.study.ui.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


import butterknife.ButterKnife;
import com.young.study.MyApplication;
import com.young.study.ui.view.base.BaseView;

/**
 * Created by edz on 2017/7/20.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    public BaseActivity() {
        super();
    }

    public View getLoadingView(){
        return null;
    }

    public boolean getFirstStart(){
        return false;
    }

    public abstract void initViewAndEvents();


    protected void readyGo(Class<?> clazz){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }

    protected void readyGo(Class<?> clazz,Bundle bundle){
        Intent intent = new Intent(this,clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void readyGoThenKill(Class<?> clazz){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
        finish();
    }

    protected void readyGoThenKill(Class<?> clazz,Bundle bundle){
        Intent intent = new Intent(this,clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    protected void showSnackToast(String msg){
        if (msg != null) {
            Snackbar.make(getLoadingView(),msg,Snackbar.LENGTH_SHORT).show();
        }
    }

    protected void showToast(String msg){
        if (msg != null) {
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setTheme(@StyleRes int resid) {
        super.setTheme(resid);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        initViewAndEvents();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
    }
}
