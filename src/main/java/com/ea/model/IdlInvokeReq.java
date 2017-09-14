package com.ea.model;

import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;

/**
 * Created by Roy on 2015/9/24 0024.
 */
@SuppressWarnings("serial")
public class IdlInvokeReq<T> implements Serializable {

    private String url;
    private RequestMethod requestMethod;
    private String encoding;
    private Integer soTimeout;// 等待数据时间
    private Integer connectionTimeout;// 连接超时时间
    private Integer connmanagerTimeout;// 连接不够用的时候等待超时时间

    private T params;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public Integer getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(Integer soTimeout) {
        this.soTimeout = soTimeout;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Integer getConnmanagerTimeout() {
        return connmanagerTimeout;
    }

    public void setConnmanagerTimeout(Integer connmanagerTimeout) {
        this.connmanagerTimeout = connmanagerTimeout;
    }

    public T getParams() {
        return params;
    }

    public void setParams(T params) {
        this.params = params;
    }
}
