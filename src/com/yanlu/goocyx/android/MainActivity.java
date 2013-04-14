package com.yanlu.goocyx.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.yanlu.goocyx.android.common.util.Log2;
import com.yanlu.goocyx.android.net.AsyncDataTask;
import com.yanlu.goocyx.android.net.SimpleListener;

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //测试http请求
        AsyncDataTask.execute("http://g.cn", new SimpleListener(){

            public void postExecute(String ret) {
                ((TextView)findViewById(R.id.main_tv_content)).setText(ret);
                Log2.d("", ret);
            }

        });
    }

}
