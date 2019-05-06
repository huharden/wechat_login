package com.saliai.wechat_3rdparty.utils.exception;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Map;

/**
 * @Author:chenyiwu
 * @Describtion:
 * @Create Time:2018/6/8
 */
public class GMSOAExceptionAssert {
    /**
     * 功能描述: 判断值是否为空，否则抛出异常
     *
     * @param object
     * @param code
     * @param message
     * @auther: Martin、chen
     * @date: 2018/6/8 17:37
     * @return: void
     */
    public static void isNotNull(Object object, GMSOAExceptionCode code, String message) {

        isNotNull(object, code, message, null);
    }

    /**
     * 功能描述: 判断值是否为空，为空返回异常信息
     *
     * @param object
     * @param code
     * @param message
     * @param errorData
     * @auther: Martin、chen
     * @date: 2018/6/8 17:38
     * @return: void
     */
    public static void isNotNull(Object object, GMSOAExceptionCode code, String message, Object errorData) {

        if (object == null) throw new GMSOAException(code, GMSOAExceptionType.BUSINESS, message, errorData);
    }

    /**
     * 功能描述: 判断值是否为空，否则抛出异常
     *
     * @param object
     * @param errorCode
     * @param message
     * @auther: Martin、chen
     * @date: 2018/6/8 17:39
     * @return: void
     */
    public static void isNotNull(Object object, String errorCode, String message) {

        if (object == null) {
            throw new GMSOAException(errorCode, message);
        }
    }

    /**
     * 功能描述:判断值是否为空，否则返回异常
     *
     * @param object
     * @param errorCode
     * @param message
     * @auther: Martin、chen
     * @date: 2018/6/8 17:40
     * @return: void
     */
    public static void notNull(Object object, String errorCode, String message) {

        if (object == null) {
            throw new GMSOAException(errorCode, message);
        }
    }

    /**
     * 判断是否为空， 如果为空（null/单个空格/多个空格），则抛出异常
     *
     * @param text      判断的字符串
     * @param errorCode 错误码
     * @param message   错误信息
     * @auther: Martin、chen
     * @date: 2018/6/8 17:40
     */
    public static void isNotBlank(String text, String errorCode, String message) {
        if (!StringUtils.isNotBlank(text)) {
            throw new GMSOAException(errorCode, message);
        }
    }

    /**
     * 判断字符串是否为空，如果为空（null/单个空格），则抛出异常
     *
     * @param text      判断的字符串
     * @param errorCode 错误码
     * @param message   错误信息
     * @auther: Martin、chen
     * @date: 2018/6/8 17:40
     */
    public static void isNotEmpty(String text, String errorCode, String message) {
        if (StringUtils.isEmpty(text)) {
            throw new GMSOAException(errorCode, message);
        }
    }

    /**
     * 判断值是否有值， 如果为空或0，则抛出异常
     *
     * @param value     判断的值
     * @param errorCode 错误码
     * @param message   错误信息
     * @auther: Martin、chen
     * @date: 2018/6/8 17:40
     */
    public static void hasValue(Long value, String errorCode, String message) {
        if (value == null || value.equals(0L)) {
            throw new GMSOAException(errorCode, message);
        }
    }

    /**
     * 判断值是否有值， 如果为空或0，则抛出异常
     *
     * @param value     判断的值
     * @param errorCode 错误码
     * @param message   错误信息
     * @auther: Martin、chen
     * @date: 2018/6/8 17:40
     */
    public static void hasValue(Integer value, String errorCode, String message) {
        if (value == null || value.equals(0)) {
            throw new GMSOAException(errorCode, message);
        }
    }

    public static void isNotEmpty(Object[] array, String errorCode, String message) {
        if (ObjectUtils.isEmpty(array)) {
            throw new GMSOAException(errorCode, message);
        }
    }

    /**
     * 功能描述:数组中是否含有空值
     *
     * @param array
     * @param errorCode
     * @param message
     * @auther: Martin、chen
     * @date: 2018/6/8 17:58
     * @return: void
     */
    public static void hasNoNullElements(Object[] array, String errorCode, String message) {

        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] == null) {
                    throw new GMSOAException(errorCode, message);
                }
            }
        }
    }


    /**
     * 功能描述:map是否为空
     *
     * @param map
     * @param errorCode
     * @param message
     * @auther: Martin、chen
     * @date: 2018/6/8 17:59
     * @return: void
     */
    public static void isNotEmpty(Map<?, ?> map, String errorCode, String message) {

        if (CollectionUtils.isEmpty(map)) {
            throw new GMSOAException(errorCode, message);
        }
    }

    /**
     * 判断是否为空， 如果为空（null/单个空格/多个空格），则抛出异常
     *
     * @param text    判断的字符串
     * @param code    错误码
     * @param message 错误信息
     * @auther: Martin、chen
     * @date: 2018/6/8 17:40
     */
    public static void isNotBlank(String text, GMSOAExceptionCode code, String message) {
        isNotBlank(text, code, message, null);
    }

    /**
     * 功能描述:字符串是否为空 否则抛出异常
     *
     * @param text
     * @param code
     * @param message
     * @param errorData
     * @auther: Martin、chen
     * @date: 2018/6/8 18:00
     * @return: void
     */
    public static void isNotBlank(String text, GMSOAExceptionCode code, String message, Object errorData) {

        if (!StringUtils.isNotBlank(text))
            throw new GMSOAException(code, GMSOAExceptionType.BUSINESS, message, errorData);
    }

    /**
     * 判断值是否有值， 如果为空或0，则抛出异常
     *
     * @param value   判断的值
     * @param code    错误码
     * @param message 错误信息
     * @auther: Martin、chen
     * @date: 2018/6/8 17:40
     */
    public static void hasValue(Long value, GMSOAExceptionCode code, String message) {
        hasValue(value, code, message, null);
    }

    public static void hasValue(Long value, GMSOAExceptionCode code, String message, Object errorData) {
        if (value == null || value.equals(0L))
            throw new GMSOAException(code, GMSOAExceptionType.BUSINESS, message, errorData);
    }

    /**
     * 判断值是否有值， 如果为空或0，则抛出异常
     *
     * @param value   判断的值
     * @param code    错误码
     * @param message 错误信息
     * @auther: Martin、chen
     * @date: 2018/6/8 17:40
     */
    public static void hasValue(Integer value, GMSOAExceptionCode code, String message) {
        hasValue(value, code, message, null);
    }

    public static void hasValue(Integer value, GMSOAExceptionCode code, String message, Object errorData) {
        if (value == null || value.equals(0))
            throw new GMSOAException(code, GMSOAExceptionType.BUSINESS, message, errorData);
    }
}
