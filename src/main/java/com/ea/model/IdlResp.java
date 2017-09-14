package com.ea.model;

/**
 * Created by Roy on 2015/9/24 0024.
 */
public class IdlResp {

    private String tId;

    private String t_id;
    /**
     * 业务编码
     */
    private Long state;
    private String msg;

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
