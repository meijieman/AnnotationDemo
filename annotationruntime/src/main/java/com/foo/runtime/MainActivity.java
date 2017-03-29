package com.foo.runtime;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.foo.runtime.inject.ContentView;
import com.foo.runtime.inject.OnClick;
import com.foo.runtime.inject.ViewInject;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.btn_s)
    private Button mBtn_s; // 可以访问 private 对象
    @ViewInject(R.id.btn_f)
    Button mBtn_f;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBtn_f.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBtn_f.setText("第一个按钮");
            }
        }, 2000);
        mBtn_s.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBtn_s.setText("第二个按钮");
            }
        }, 3000);
    }

    @OnClick({R.id.btn_f, R.id.btn_s})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.btn_f:
                show("点击了 first 按钮");
                break;
            case R.id.btn_s:
                show("点击了 second 按钮");
                break;
        }
    }

}
