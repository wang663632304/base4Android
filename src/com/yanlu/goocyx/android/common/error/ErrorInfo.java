package com.yanlu.goocyx.android.common.error;

import com.yanlu.goocyx.android.GlobalApplication;
import com.yanlu.goocyx.android.R;

/**
 * User: captain_miao
 * Date: 13-4-6
 */
public enum ErrorInfo {
    NETWORK_TIME_OUT(309, GlobalApplication.getStringFromValues(R.string.timeout)),
    UNKNOWN_NETWORK_ERROR(310, GlobalApplication.getStringFromValues(R.string.unknown_network_error)),
    USER_LOGIN_OTHER_FAIL(333, GlobalApplication.getStringFromValues(R.string.timeout));


    private ErrorInfo(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


   	public Integer getCode() {
   		return code;
   	}

   	public String getMsg() {
   		return msg;
   	}

    private Integer code;
    private String msg;
}
