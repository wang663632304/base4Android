package com.yanlu.goocyx.android;

import android.app.Activity;
import android.os.Bundle;
import com.yanlu.goocyx.android.common.error.ErrorInfo;
import com.yanlu.goocyx.android.common.util.Log2;

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log2.d("", ErrorInfo.NETWORK_TIME_OUT.getMsg());
    }
}
