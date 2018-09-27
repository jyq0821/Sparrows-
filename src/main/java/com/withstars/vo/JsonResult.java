package com.withstars.vo;

/**
 * 通过此对象封装控制层对象
 *
 * @author Administrator
 * @time 2018年9月12日 下午3:35:26
 */
public class JsonResult {

    /**
     * 状态码：1表示正确，0表示错误
     */
    private int state = 1;
    /**
     * 状态对应信息
     */
    private String message = "ok";
    /**
     * 正确数据通过此属性进行封装
     */
    private Object data;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    // 构造函数
    public JsonResult() {

    }

    public JsonResult(Object data) {
        this.data = data;
    }


    public JsonResult(Throwable e) {
        this.state = 0;
        this.message = e.getMessage();
    }

    public JsonResult(int state, String message, Object data) {
        this.state = state;
        this.message = message;
        this.data = data;
    }

}
