package com.yanlu.goocyx.android.net;

/**
 * User: captain_miao
 * Date: 13-4-14
 * Time: 下午8:51
 */
public interface DataLoadListener<T> {
    //加载数据之前执行
    void preExecute();

    //负责将http请求返回,解析成T
    T parser(String response);

    //加载数据完成之后执行
    void postExecute(T ret);
}
