package com.saliai.wechat_3rdparty.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: chenyiwu
 * @Describtion: 工具类
 * @Create Time:2018/6/8
 */
public class CommonUtils {
    /**
     * 类型转换 Long[] 转为 List(Long)
     */
    public static List<Long> translateLong(Long[] ids) {
        List<Long> result = new ArrayList<Long>();
        if (ids == null || ids.length == 0) {
            return result;
        }
        for (Long id : ids) {
            result.add(id);
        }
        return result;
    }

    public static Long[] convertToArray(List<Long> objects) {
        return objects.toArray(new Long[objects.size()]);
    }

    public static List<Long> translateLong(List<Object> ids) {
        List<Long> result = new ArrayList<Long>();
        if (ids == null || ids.isEmpty()) {
            return result;
        }
        for (Object id : ids) {
            result.add(Long.parseLong(id + ""));
        }
        return result;
    }

    /**
     * 类型转换 String[] 转为 List(String)
     */
    public static List<String> translateString(String[] ids) {
        List<String> result = new ArrayList<String>();
        if (ids == null || ids.length == 0) {
            return result;
        }
        for (String id : ids) {
            result.add(id);
        }
        return result;
    }

    /**
     * 类型转换 String[] 转为 Map(String, String)
     */
    public static Map<String, String> convertToMap(String[] data, String index) {
        Map<String, String> _result = new HashMap<String, String>();
        for (String d : data) {
            String[] _data = d.split(index);
            _result.put(_data[0], _data[1]);
        }
        return _result;
    }

    /**
     * 把 imputstream 转为 string
     */
    public static String InputStreamTOString(InputStream in) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int count = -1;
        while ((count = in.read(data, 0, 1024)) != -1)
            outStream.write(data, 0, count);

        data = null;
        return new String(outStream.toByteArray(), "UTF-8");
    }

    /**
     * 类型转换 list(Long) 转 map
     */
    public static Map<Long, Long> convertToMap(List<Long> ids) {
        Map<Long, Long> _result = new HashMap<Long, Long>();
        if (null != ids && !ids.isEmpty()) {
            for (Long _id : ids) {
                _result.put(_id, 1L);
            }
        }
        return _result;
    }

    /**
     * 功能描述: 中文字符全部删除，标点自动转成半角
     *
     * @param input
     * @auther: Martin、chen
     * @date: 2018/6/8 16:37
     * @return: java.lang.String
     */
    public static String processCharNotEn(String input) {

        if (input == null || input.length() == 0) {
            return null;
        }
        char[] c = input.toCharArray();
        //全角转半角
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);
            }
        }
        //删除中文
        return String.valueOf(c).replaceAll("[\\u4E00-\\u9FA5]", "");
    }
}
