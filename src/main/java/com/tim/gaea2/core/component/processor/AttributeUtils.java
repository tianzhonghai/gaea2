package com.tim.gaea2.core.component.processor;

import org.thymeleaf.dom.Element;
import org.thymeleaf.util.StringUtils;
import org.thymeleaf.util.Validate;

/**
 * Created by tianzhonghai on 2017/6/16.
 */
public final class AttributeUtils {

    private AttributeUtils() {
        throw new UnsupportedOperationException();
    }

    public static String getRawValue(final Element element, final String attributeName) {
        Validate.notNull(element, "element must not be null");
        Validate.notEmpty(attributeName, "attributeName must not be empty");

        final String rawValue = StringUtils.trim(element.getAttributeValue(attributeName));
        Validate.notEmpty(rawValue, "value of '" + attributeName + "' must not be empty");

        return rawValue;
    }
}