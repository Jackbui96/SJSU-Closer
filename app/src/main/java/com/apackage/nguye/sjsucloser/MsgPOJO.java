package com.apackage.nguye.sjsucloser;

import java.util.Date;

/**
 * Created by Jack on 04/10/17.
 */

public class MsgPOJO {

    private String msg;
    private String msgUsr;
    private long msgTime;

    public MsgPOJO() {

    }

    public MsgPOJO(String msg, String msgUsr) {
        this.msg = msg;
        this.msgUsr = msgUsr;
        msgTime = new Date().getTime();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgUsr() {
        return msgUsr;
    }

    public void setMsgUsr(String msgUsr) {
        this.msgUsr = msgUsr;
    }

    public long getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(long msgTime) {
        this.msgTime = msgTime;
    }

}
