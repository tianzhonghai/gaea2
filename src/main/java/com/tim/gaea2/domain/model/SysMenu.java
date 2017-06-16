package com.tim.gaea2.domain.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianzhonghai on 2017/6/16.
 */
public class SysMenu {
    private int menuId;
    private String menuName;
    private String url;
    private List<SysSubMenu> subMenus;

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<SysSubMenu> getSubMenus() {
        return subMenus;
    }

    public void setSubMenus(List<SysSubMenu> subMenus) {
        this.subMenus = subMenus;
    }

    public SysMenu() {
        this.subMenus = new ArrayList<>();
    }

    public String toHtml() {
        StringBuffer sbMenu = new StringBuffer();
        sbMenu.append("<li>");
        String menuFmt = "<a href=\"%s\" class=\"dropdown-toggle\"><i class=\"icon-list\"></i><span class=\"menu-text\"> %s </span><b class=\"arrow icon-angle-down\"></b></a>";
        sbMenu.append(String.format(menuFmt,this.url,this.menuName));

        StringBuffer sbSubMenu = new StringBuffer();
        if(subMenus != null && subMenus.size() > 0) {
            sbSubMenu.append("<ul class=\"submenu\">");
            String fmt = "<li><a href=\"%s\"><i class=\"fa fa-double-angle-right\"></i>%s</a></li>";
            for (SysSubMenu subMenu: subMenus) {
                sbSubMenu.append(String.format(fmt,subMenu.getUrl(),subMenu.getMenuName()));
            }
            sbSubMenu.append("</ul>");
        }
        sbMenu.append(sbSubMenu.toString());
        sbMenu.append("</li>");
        return sbMenu.toString();
    }
}
