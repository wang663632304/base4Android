package com.yanlu.goocyx.android.net;

import android.os.Handler;
import android.os.Message;
import com.yanlu.goocyx.android.common.error.BaseException;
import com.yanlu.goocyx.android.common.util.Log2;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 数据异步加载
 *
 * @author tianxiang
 * @date 2012-10-11 下午5:11:52
 */
public class AsyncDataTask {
    private static final String TAG = "AsyncDataTask";
    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 128;
    private static final int KEEP_ALIVE = 1;

    private static ThreadPoolExecutor threadPool
            = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE,
            TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    static {

        // 设置保护策略;当队列任务已经满了,或者任务提交到一个已经关闭的executor,则丢弃该任务;<br>
        // 默认的保护策略是AbortPolicy,会抛出RejectedExecutionException
        threadPool
                .setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
    }


    /**
     * 加载数据
     *
     * @date 2012-10-11下午5:06:24
     */
    public static <T> void execute(final String url, final DataLoadListener<T> listener) {
        try {
            listener.preExecute();

            final Handler handler = new Handler() {
                @SuppressWarnings("unchecked")
                public void handleMessage(Message message) {
                    try {
                        listener.postExecute((T) message.obj);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log2.e(TAG, e.toString());
                    }
                }
            };

            if (!threadPool.isShutdown()) {
                threadPool.execute(new Runnable() {
                    public void run() {
                        T sr = null;
                        try {
                            sr = listener.parser(HttpExecuteTemplate.execute(url));
                        } catch (BaseException e) {
                            e.printStackTrace();
                        }
                        handler.sendMessage(handler.obtainMessage(0, sr));
                    }
                });
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            Log2.e(TAG, "execute 异常");
        }
    }
}
