package com.zjstudio.happly_birthday.tools.enums;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 此帮助类严格限定为有code和status的枚举类，如定义枚举类型为 SUCCESS(code,status)这种
 *
 * Copyright xiehao
 * Function: TODO ADD FUNCTION.
 * Reason: TODO ADD REASON.
 * Date:   2017年2月28日
 * @author   xiehao
 * @version  V1.0
 * @since    JDK 1.8
 * @see
 */
public class EnumsUtil{
    /**
     * indexOf,传入的参数ordinal指的是需要的枚举值在设定的枚举类中的顺序，以0开始
     * T
     * @param clazz
     * @param ordinal
     * @return
     * @author   xiehao
     */
    public static <T extends Enum<T>> T indexOf(Class<T> clazz, int ordinal){
        return (T) clazz.getEnumConstants()[ordinal];
    }
    /**
     * nameOf,传入的参数name指的是枚举值的名称，一般是大写加下划线的
     * T
     * @param clazz
     * @param name
     * @return Enum T
     * @author   xiehao
     */
    public static <T extends Enum<T>> T nameOf(Class<T> clazz, String name){

        return (T) Enum.valueOf(clazz, name);
    }
    /**
     * 使用枚举类型对应的code获取枚举类型
     * T
     * @param clazz    枚举类的class
     * @param getCodeMethodName  传入的code的get方法
     * @param code  传入的typeCode值，这个方法为String类型
     * @return
     * @author   xiehao
     */
    public static <T extends Enum<T>> T getByStringTypeCode(Class<T> clazz,String getCodeMethodName, String code){
        T result = null;
        try{
            T[] arr = clazz.getEnumConstants();
            Method targetMethod = clazz.getDeclaredMethod(getCodeMethodName);
            String typeCodeVal = null;
            for(T entity:arr){
                typeCodeVal = targetMethod.invoke(entity).toString();
                if(typeCodeVal.contentEquals(code)){
                    result = entity;
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 使用枚举类型对应的status获取枚举类型
     * T
     * @param clazz    枚举类的class
     * @param getCodeMethodName  传入的code的get方法
     * @param code  传入的typeCode值，这个方法为Integer类型
     * @return
     * @author   xiehao
     */
    public static <T extends Enum<T>> T getByIntegerTypeCode(Class<T> clazz,String getCodeMethodName, Integer code){
        T result = null;
        try{
            T[] arr = clazz.getEnumConstants();
            Method targetMethod = clazz.getDeclaredMethod(getCodeMethodName);
            Integer typeCodeVal = null;
            for(T entity:arr){
                typeCodeVal = Integer.valueOf(targetMethod.invoke(entity).toString());
                if(typeCodeVal.equals(code)){
                    result = entity;
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 使用枚举类型对应的status获取枚举类型
     * T
     * @param clazz   枚举类的class
     * @param getStatusMethodName  传入的status的get方法
     * @param status 传入的typeName值，这个方法为String类型
     * @return
     * @author   xiehao
     */
    public static <T extends Enum<T>> T getByStringTypeName(Class<T> clazz,String getStatusMethodName, String status){
        T result = null;
        try{
            T[] arr = clazz.getEnumConstants();
            Method targetMethod = clazz.getDeclaredMethod(getStatusMethodName);
            String typeNameVal = null;
            for(T entity:arr){
                typeNameVal = targetMethod.invoke(entity).toString();
                if(typeNameVal.contentEquals(status)){
                    result = entity;
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return result;
    }
}