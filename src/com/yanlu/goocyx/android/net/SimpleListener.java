package com.yanlu.goocyx.android.net;

/**
 * User: captain_miao
 * Date: 13-4-14
 * Time: 下午9:37
 */
public abstract class SimpleListener implements DataLoadListener<String>  {
    public void preExecute() {

    }

    public String parser(String response) {
        return response;
    }

}
