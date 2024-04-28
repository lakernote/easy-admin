package com.laker.admin.framework.ext.desensitization;

import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;

/**
 * 数据脱敏序列化器
 */
public class SensitiveInfoSerializer extends JsonSerializer<String> implements ContextualSerializer {

    private boolean useMasking = false;
    private DesensitizationTypeEnum type;
    private int startInclude;
    private int endExclude;

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        if (useMasking && value != null) {
            switch (type) {
                // 自定义类型脱敏
                case CUSTOMIZE_RULE:
                    jsonGenerator.writeString(StrUtil.hide(value, startInclude, endExclude));
                    break;
                // userId脱敏
                case USER_ID:
                    jsonGenerator.writeString(String.valueOf(DesensitizedUtil.userId()));
                    break;
                // 中文姓名脱敏
                case CHINESE_NAME:
                    jsonGenerator.writeString(DesensitizedUtil.chineseName(value));
                    break;
                // 身份证脱敏
                case ID_CARD:
                    jsonGenerator.writeString(DesensitizedUtil.idCardNum(value, 1, 2));
                    break;
                // 固定电话脱敏
                case FIXED_PHONE:
                    jsonGenerator.writeString(DesensitizedUtil.fixedPhone(value));
                    break;
                // 手机号脱敏
                case MOBILE_PHONE:
                    jsonGenerator.writeString(DesensitizedUtil.mobilePhone(value));
                    break;
                // 地址脱敏
                case ADDRESS:
                    jsonGenerator.writeString(DesensitizedUtil.address(value, 8));
                    break;
                // 邮箱脱敏
                case EMAIL:
                    jsonGenerator.writeString(DesensitizedUtil.email(value));
                    break;
                // 密码脱敏
                case PASSWORD:
                    jsonGenerator.writeString(DesensitizedUtil.password(value));
                    break;
                // 中国车牌脱敏
                case CAR_LICENSE:
                    jsonGenerator.writeString(DesensitizedUtil.carLicense(value));
                    break;
                // 银行卡脱敏
                case BANK_CARD:
                    jsonGenerator.writeString(DesensitizedUtil.bankCard(value));
                    break;
                case DEFAULT:
                    jsonGenerator.writeString(value);
                default:
                    jsonGenerator.writeString(value);
            }
        } else {
            jsonGenerator.writeObject(value);
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) {
        if (property != null) {
            Desensitization desensitization = property.getAnnotation(Desensitization.class);
            if (desensitization != null) {
                this.type = desensitization.type();
                this.startInclude = desensitization.startInclude();
                this.endExclude = desensitization.endExclude();
                useMasking = true;
            }
        }
        return this;
    }
}