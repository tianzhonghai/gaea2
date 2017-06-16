package com.tim.gaea2.core.component;

import com.tim.gaea2.core.shiro.ShiroUser;
import com.tim.gaea2.core.utils.SpringUtil;
import com.tim.gaea2.domain.model.SysMenu;
import org.apache.shiro.SecurityUtils;
import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by tianzhonghai on 2017/6/16.
 */
public class GeaeHelper {
    public static String buildUserMenu(String currentUrl) {
//        if (SecurityUtils.getSubject() == null) {
//            return "";
//        }
//
//        ShiroUser principal = (ShiroUser)SecurityUtils.getSubject().getPrincipal();
//        long userId =principal.getId();

        List<SysMenu> menus = (List<SysMenu>)SecurityUtils.getSubject().getSession().getAttribute("leftMenu");
        if(menus == null)return  "";

        StringBuffer sb = new StringBuffer();
        sb.append("<ul class=\"nav nav-list\">");
        for (SysMenu menu : menus) {
            sb.append(menu.toHtml());
        }
        sb.append("</ul>");
        return sb.toString();
    }
}
