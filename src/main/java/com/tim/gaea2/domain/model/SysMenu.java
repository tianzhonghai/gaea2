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
    private String iconClass;
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

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    public String toHtml(String currentUrl) {
        StringBuffer sbSubMenu = new StringBuffer();
        boolean isActive = false;
        if(subMenus != null && subMenus.size() > 0) {
            sbSubMenu.append("<ul class=\"submenu\">");
            String fmt = "<li><a href=\"%s\"><i class=\"fa fa-double-angle-right\"></i>%s</a></li>";
            for (SysSubMenu subMenu: subMenus) {
                sbSubMenu.append(String.format(fmt,subMenu.getUrl(),subMenu.getMenuName()));
                if(subMenu.getUrl().equalsIgnoreCase(currentUrl)){
                    isActive=true;
                }
            }
            sbSubMenu.append("</ul>");
        }

        StringBuffer sbMenu = new StringBuffer();
        sbMenu.append("<li");

        if(this.url.equalsIgnoreCase(currentUrl)){
            sbMenu.append(" class=\"active open\"");
        }
        if(isActive){
            sbMenu.append(" class=\"active open\"");
        }

        sbMenu.append(">");

        String menuFmt = "<a href=\"%s\" %s><i class=\"%s\"></i><span class=\"menu-text\"> %s </span><b class=\"arrow icon-angle-down\"></b></a>";

        String dropdownClass="";
        if(this.subMenus != null && this.subMenus.size() > 0){
            dropdownClass = "class=\"dropdown-toggle\"";
        }
        sbMenu.append(String.format(menuFmt,this.url,dropdownClass,this.iconClass,this.menuName));

        sbMenu.append(sbSubMenu.toString());
        sbMenu.append("</li>");
        return sbMenu.toString();
    }
}
