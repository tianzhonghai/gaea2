package com.tim.gaea2.core.component;

import com.tim.gaea2.core.component.processor.attribute.LeftMenuAttrProcessor;
import com.tim.gaea2.core.component.processor.element.LeftMenuElementProcessor;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by tianzhonghai on 2017/6/16.
 */
public class GaeaDialect extends AbstractDialect {
    private static final String PREFIX = "gaea";
    private static final Set<IProcessor> processors = new HashSet<IProcessor>();

    static {
        processors.add(LeftMenuAttrProcessor.create());
        processors.add(LeftMenuElementProcessor.create());
    }

    public GaeaDialect() {
        super();
    }

    @Override
    public String getPrefix() {
        return PREFIX;
    }

    @Override
    public Set<IProcessor> getProcessors() {
        return Collections.unmodifiableSet(processors);
    }
}
