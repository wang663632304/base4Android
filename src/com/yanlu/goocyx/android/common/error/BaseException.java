package com.yanlu.goocyx.android.common.error;

import android.content.res.Resources;
import android.text.TextUtils;
import com.yanlu.goocyx.android.GlobalApplication;
import com.yanlu.goocyx.android.R;

/**
 * User: captain_miao
 * Date: 13-4-6
 */
public class BaseException extends Exception {
    //上层处理的错误
    private String error;
    //原始错误
    private String oriError;
    private int error_code;

    public String getError() {

        String result;

        if (!TextUtils.isEmpty(error)) {
            result = error;
        } else {

            String name = "code" + error_code;
            int i = GlobalApplication.getContext().getResources()
                    .getIdentifier(name, "string", GlobalApplication.getContext().getPackageName());

            try {
                result = GlobalApplication.getStringFromValues(i);

            } catch (Resources.NotFoundException e) {

                if (!TextUtils.isEmpty(oriError)) {
                    result = oriError;
                } else {

                    result = ErrorInfo.UNKNOWN_NETWORK_ERROR.getMsg() + error_code;
                }
            }
        }

        return result;
    }

    @Override
    public String getMessage() {
        return getError();
    }


    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public int getError_code() {
        return error_code;
    }

    public BaseException() {

    }

    public BaseException(String detailMessage) {
        error = detailMessage;
    }

    public BaseException(String detailMessage, Throwable throwable) {
        error = detailMessage;
    }

    public BaseException(ErrorInfo errorCode) {
        error = errorCode.getMsg();
    }

    public BaseException(ErrorInfo errorCode, Throwable throwable) {
        error = errorCode.getMsg();
    }

    public void setOriError(String oriError) {
        this.oriError = oriError;
    }

}
