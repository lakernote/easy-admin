package com.laker.admin.framework.ext.desensitization;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@JacksonAnnotationsInside //主要用于标记其他自定义注解，这意味着你可以在一个 Jackson 注解内部使用其他自定义注解，以组合各种注解来实现更复杂的序列化和反序列化逻辑。
@JsonSerialize(using = SensitiveInfoSerializer.class) // 用于指定在序列化时应该使用哪个自定义序列化器类
@Retention(RetentionPolicy.RUNTIME) // 运行时生效。
@Target(ElementType.FIELD) // 用在字段上。
public @interface Desensitization {

    /**
     * 脱敏数据类型，在CUSTOMIZE_RULE的时候，startInclude和endExclude生效
     */
    DesensitizationTypeEnum type() default DesensitizationTypeEnum.DEFAULT;

    /**
     * 脱敏开始位置（包含）
     */
    int startInclude() default 0;

    /**
     * 脱敏结束位置（不包含）
     */
    int endExclude() default 0;

}