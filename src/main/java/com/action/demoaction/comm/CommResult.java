package com.action.demoaction.comm;

public class CommResult {

    private String code;
    private String msg;
    private Object data;

    public CommResult() {
    }

    public static CommResult success() {
        return new CommResult("200","成功",null);
    }

    public static CommResult success(Object o) {
        return new CommResult("200","成功",o);
    }

    public static CommResult fail() {
        return new CommResult("500","失败",null);
    }
    public static CommResult fail(Object o) {
        return new CommResult("500","失败",o);
    }

    public CommResult(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
