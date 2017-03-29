package com.foo.runtime;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.foo.runtime.inject.ViewInjectUtils;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/3/15 16:35
 */
public class BaseActivity extends AppCompatActivity {

    private Toast mToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewInjectUtils.inject(this);

    }

    protected void show(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }
}
