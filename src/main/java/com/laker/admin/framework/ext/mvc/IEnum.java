package com.laker.admin.framework.ext.mvc;

import java.io.Serializable;

/**
 * @author laker
 */
public interface IEnum<T extends Serializable> {
    T getValue();
}
