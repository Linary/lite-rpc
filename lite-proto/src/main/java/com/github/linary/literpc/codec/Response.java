package com.github.linary.literpc.codec;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表示RPC的返回
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    /**
     * 服务返回编码，0成功，非0失败
     */
    private int code = 0;

    /**
     * 具体的错误信息
     */
    private String message = "ok";

    /**
     * 返回的数据
     */
    private Object data;
}
