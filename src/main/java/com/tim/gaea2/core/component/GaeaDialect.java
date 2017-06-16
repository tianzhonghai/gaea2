package com.tim.gaea2.core.component;

import org.thymeleaf.dialect.AbstractDialect;

/**
 * Created by tianzhonghai on 2017/6/16.
 */
public class GaeaDialect extends AbstractDialect {
    private static final String PREFIX = "gaea";

    @Override
    public String getPrefix() {
        return PREFIX;
    }
}
