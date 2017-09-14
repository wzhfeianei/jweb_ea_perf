package com.ea.model;

import java.io.Serializable;

/**
 * Created by Roy on 2015/9/24 0024.
 */
@SuppressWarnings("serial")
public class IdlInvokeResp<T extends IdlResp> implements Serializable {
    /**
     * http错误编码
     */
    private Integer httpStatusCode;
    /**
     * invoke时候的异常错误编码
     */
    private int invokeErrorCode;
    private T invokeObj;
    private String responseStr;

    /**
     * 请求成功
     * @return
     */
    public boolean isSuccess() {
        if (httpStatusCode != null && httpStatusCode == 200
                && invokeErrorCode == 0
                && invokeObj != null && invokeObj.getState() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 只有http请求正常
     * @return
     */
    public boolean isOnlyHttpSuccess() {
        if (httpStatusCode != null && httpStatusCode == 200
                && (invokeErrorCode != 0 || invokeObj == null || invokeObj.getState() == null || invokeObj.getState() != 0)) {
            return true;
        }
        return false;
    }

    /**
     * http请求成功但是结果错误
     * @return
     */
    public boolean isResultFail(){
    	if(null != httpStatusCode && httpStatusCode == 200 && invokeErrorCode == 0 && null != invokeObj && invokeObj.getState() != 0){
    		return true;
    	}
    	return false;
    }
    
    /**
     * 请求失败
     * @return
     */
    public boolean isFailed() {
        if (httpStatusCode == null || httpStatusCode != 200
                || invokeErrorCode != 0
                || invokeObj == null || invokeObj.getState() == null || invokeObj.getState() != 0) {
            return true;
        }
        return false;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public int getInvokeErrorCode() {
        return invokeErrorCode;
    }

    public void setInvokeErrorCode(int invokeErrorCode) {
        this.invokeErrorCode = invokeErrorCode;
    }

    public T getInvokeObj() {
        return invokeObj;
    }

    public void setInvokeObj(T invokeObj) {
        this.invokeObj = invokeObj;
    }

    public String getResponseStr() {
        return responseStr;
    }

    public void setResponseStr(String responseStr) {
        this.responseStr = responseStr;
    }
}
