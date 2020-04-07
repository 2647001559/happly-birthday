package com.zjstudio.happly_birthday.tools.common;

import java.io.Serializable;
import java.util.*;
import java.util.function.Supplier;

/**
 * 常用工具类
 * @author 添柴灬少年
 * @date   2018/12/25
 * @version 1.0
 *
 * 方法说明：
 *      1.isNullPointer()  空指针验证     判断是否存在空指针
 *      2.isEmpty()         内容非空验证  支持参数：Object  List  Map  String Set
 *      3.get32ID()   生成一个32位编码的字符串，用于生成主键
 */
public class CommonUtil implements Serializable {

    /*================================================= public =========================================================*/

    /**
     * 空指针验证
     * @param param 对象
     * @return  true:为空     false:不为空
     */
    public static boolean isNullPointer(Object param){
        return !isEmpty(() -> param).isPresent();
    }

    /**
     * 空指针验证
     * @param param 对象
     * @return  true:为空     false:不为空
     */
    public static boolean isNullPointer(Object...param){
        for (Object obj:param){
            if (isNullPointer(obj)){
                return true;
            }
        }
        return false;
    }

    /**
     * 内容非空验证
     * @param params
     * @return true:为空     false:不为空
     */
    public static boolean isEmpty(Object...params){
        for (Object obj:params){
            if (isEmpty(obj)){
                return true;
            }
        }
        return false;
    }

    /**
     * 内容非空验证
     * @param param 对象
     * @return  true:为空     false:不为空
     */
    public static boolean isEmpty(Object param){
        switch (switchParam(param)){
            case "String":
                return ((String)param).length() == 0 || ((String)param).equals("") ? true : false;
            case "list":
                return ((List)param) == null || ((List)param).size() == 0 ? true : false;
            case "map":
                return ((Map)param) == null || ((Map)param).size() == 0 ? true : false;
            case "set":
                return ((Set)param) == null || ((Set)param).size() == 0 ? true : false;
            default:
                return param == null ? true : false;
        }
    }

    /**
     * 生成32位的编码字符串
     * @return
     */
    public static String get32ID(){
        return UUID.randomUUID().toString().replace("-","");
    }



    /*======================================================== private ==================================================*/

    /**
     * 内部方法，采用Java8特性的Optional和Supplier来验证非空
     * @param resolve
     * @param <T>
     * @return
     */
    private static <T> Optional<T> isEmpty(Supplier<T> resolve){
        try{
            T result = resolve.get();
            return Optional.ofNullable(result);
        }catch (NullPointerException e){
            return Optional.empty();
        }
    }


    private static String switchParam(Object param){
        if (param instanceof String){
            return "String";
        } else if (param instanceof List){
            return "list";
        } else if (param instanceof Set){
            return "set";
        } else if (param instanceof Map){
            return "map";
        } else {
            return "object";
        }
    }
}
