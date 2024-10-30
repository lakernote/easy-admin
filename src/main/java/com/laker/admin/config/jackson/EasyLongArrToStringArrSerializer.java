package com.laker.admin.config.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Collection;

/**
 * Long集合转String序列化 防止Long类型精度丢失
 */
public class EasyLongArrToStringArrSerializer extends JsonSerializer<Collection> {

    @Override
    public void serialize(Collection values, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        for (Object value : values) {
            if (value instanceof Long) {
                // 将 Long 值转换为字符串，防止精度丢失
                gen.writeString(value.toString());
            } else {
                // 非 Long 类型直接写入
                gen.writeObject(value);
            }
        }
        gen.writeEndArray();
    }
}