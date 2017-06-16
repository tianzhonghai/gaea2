package com.tim.gaea2.core.component.processor.attribute;

import com.tim.gaea2.core.component.GeaeHelper;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.attr.AbstractTextChildModifierAttrProcessor;
import org.thymeleaf.processor.attr.AbstractUnescapedTextChildModifierAttrProcessor;

/**
 * 左侧菜单
 * Created by tianzhonghai on 2017/6/16.
 */
public class LeftMenuAttrProcessor extends AbstractUnescapedTextChildModifierAttrProcessor {

    public static final LeftMenuAttrProcessor create() {
        return new LeftMenuAttrProcessor();
    }

    protected LeftMenuAttrProcessor() {
        super(ATTRIBUTE_NAME);
    }

    private static final String ATTRIBUTE_NAME = "leftMenu";
    private static final int PRECEDENCE = 300;

    @Override
    protected String getText(Arguments arguments, Element element, String s) {
        return GeaeHelper.buildUserMenu("");
    }

    @Override
    public int getPrecedence() {
        return PRECEDENCE;
    }
}
