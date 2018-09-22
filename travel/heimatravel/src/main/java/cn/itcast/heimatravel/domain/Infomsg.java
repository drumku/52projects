package cn.itcast.heimatravel.domain;

public class Infomsg {
    private boolean flag;
    private String msg;
    private Object data_along;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData_along() {
        return data_along;
    }

    public void setData_along(Object data_along) {
        this.data_along = data_along;
    }
}
