package com.tim.gaea2.core.component.processor.element;

import com.tim.gaea2.core.component.GeaeHelper;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.element.AbstractTextChildModifierElementProcessor;
import org.thymeleaf.processor.element.AbstractUnescapedTextChildModifierElementProcessor;

/**
 * Created by tianzhonghai on 2017/6/16.
 */
public class LeftMenuElementProcessor extends AbstractUnescapedTextChildModifierElementProcessor {

    public static final LeftMenuElementProcessor create() {
        return new LeftMenuElementProcessor();
    }

    private static final String ELEMENT_NAME = "leftmenu";
    private static final int PRECEDENCE = 300;

    protected LeftMenuElementProcessor() {
        super(ELEMENT_NAME);
    }

//    @Override
//    protected String getText(Arguments arguments, Element element) {
//        return GeaeHelper.buildUserMenu("");
//    }

    @Override
    public int getPrecedence() {
        return PRECEDENCE;
    }



    @Override
    protected String getText(Arguments arguments, Element element) {
        return GeaeHelper.buildUserMenu("");
    }
}
