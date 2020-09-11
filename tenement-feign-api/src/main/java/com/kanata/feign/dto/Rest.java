package com.kanata.feign.dto;

import java.io.Serializable;

public class Rest implements Serializable {

    public static final int OK_CODE = 200;
    public static final int FAILURE_CODE = 500;
    public static final int FAILURE_CODE_MQ_SEND = 1000;//添加消息到消息队列失败

    public static final String DEFAULT_MSG_OK = "成功";
    public static final String DEFAULT_MSG_FAILURE = "失败";
    public static final String DEFAULT_MSG_MQ_SEND = "添加消息到ActiveMQ失败!";

    private long code = 0;

    private long count = 0;

    private Object data;

    private String msg;

    public Rest() {
        super();
    }

    public Rest(long code, long count, Object data, String msg) {
        super();
        this.code = code;
        this.count = count;
        this.data = data;
        this.msg = msg;
    }

    public static Rest ok() {
        return new Rest(OK_CODE, 0, null, DEFAULT_MSG_OK);
    }

    public static Rest ok(String msg) {
        return new Rest(OK_CODE, 0, null, msg);
    }

    public static Rest okData(Object data) {
        return new Rest(OK_CODE, 0, data, DEFAULT_MSG_OK);
    }

    public static Rest okData(Object data, String msg) {
        return new Rest(OK_CODE, 0, data, msg);
    }

    public static Rest failure() {
        return new Rest(FAILURE_CODE, 0, null, DEFAULT_MSG_FAILURE);
    }

    public static Rest failure(String msg) {
        return new Rest(FAILURE_CODE, 0, null, msg);
    }

    public static Rest failureOnMQSending() {
        return new Rest(FAILURE_CODE_MQ_SEND, 0, null, DEFAULT_MSG_MQ_SEND);
    }

    public static Rest failureOnMQSending(String msg) {
        return new Rest(FAILURE_CODE_MQ_SEND, 0, null, msg);
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean retOk() {
        return OK_CODE == this.code;
    }

    public boolean retFailure() {
        return FAILURE_CODE == this.code;
    }

    public boolean retFailureOnSendingMQ() {
        return FAILURE_CODE_MQ_SEND == this.code;
    }
}
