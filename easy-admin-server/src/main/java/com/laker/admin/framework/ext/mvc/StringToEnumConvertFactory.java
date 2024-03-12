package com.laker.admin.framework.ext.mvc;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import java.util.Objects;
import java.util.Set;

/**
 * @author: laker
 * @date: 2022/11/25
 **/
public class StringToEnumConvertFactory implements ConditionalGenericConverter {


    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return IEnum.class.isAssignableFrom(targetType.getObjectType()) && sourceType.getObjectType() == String.class;
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return null;
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        IEnum[] enums = (IEnum[]) targetType.getObjectType().getEnumConstants();
        for (IEnum anEnum : enums) {
            if (Objects.equals(anEnum.getValue(), source)) {
                return anEnum;
            }
        }
        return null;
    }


}
