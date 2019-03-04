package org.openokr.common.vo.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 接口返回通用参数
 *
 * @Author fyh
 * @Date 2018/08/15
 */
@Data
public class ResponseData<T> implements Serializable {
    /**
     * 状态码；0=成功，其他的都是失败
     */
    @ApiModelProperty(value = "状态码：0=成功，其他的失败", example = "0")
    private int code = 0;
    /**
     * 提示信息
     */
    @ApiModelProperty(value = "提示信息", example = "success")
    private String message;
    /**
     * 业务数据
     */
    @ApiModelProperty(value = "业务数据")
    private T data;

    @ApiModelProperty(value = "成功状态")
    private Boolean success;

    public ResponseData() {
        code = 0;
        message = "success";
        data = null;
    }

    public ResponseData(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseData(int code, String message, T data, Boolean success) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.success = success;
    }

    public ResponseData(int code, String msg) {
        this(code, msg, null);
    }

    public ResponseData(T data) {
        this(0, "success", data);
    }

    public static ResponseData success(Object data) {
        return new ResponseData(0, "success", data, true);
    }

    public static ResponseData error(int code, String message) {
        return new ResponseData(code, message, null, false);
    }

}
