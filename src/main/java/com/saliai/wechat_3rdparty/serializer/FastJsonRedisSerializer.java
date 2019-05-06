package com.saliai.wechat_3rdparty.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;

import java.nio.charset.Charset;

/**
 * @Author: zhangzhk
 * @Description: 实现redis键值对以utf-8的方式保存，需结合RdisConfig类实现，依赖为com.alibaba.fastjson
 * @Date: 2018/5/23 14:04
 * @Modify By:
 */
public class FastJsonRedisSerializer<T> implements RedisSerializer {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private Class<T> clazz;

    public FastJsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Nullable
    @Override
    public byte[] serialize(@Nullable Object o) throws SerializationException {
        if (null == o) {
            return new byte[0];
        }
        return JSON.toJSONString(o, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    @Nullable
    @Override
    public Object deserialize(@Nullable byte[] bytes) throws SerializationException {
        if (null == bytes || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return (T) JSON.parseObject(str, clazz);
    }
}
