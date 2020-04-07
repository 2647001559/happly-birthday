package com.zjstudio.happly_birthday.tools.excel.exception;

/**
 * <p>
 * TODO 自定义easyexcel封装工具异常类
 * </p>
 *
 * @author 添柴灬少年
 * @version 1.0
 * @date 2020/1/9 9:23
 **/
public class EasyExcelException extends RuntimeException {

    private static final long serialVersionUID = -5456062088984840434L;

    public EasyExcelException() {
        super();
    }

    public EasyExcelException(String message) {
        super(message);
    }

    public EasyExcelException(String message, Throwable cause) {
        super(message, cause);
    }

    public EasyExcelException(Throwable cause) {
        super(cause);
    }
}
