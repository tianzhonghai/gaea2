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
}
