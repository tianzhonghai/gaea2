package com.tim.gaea2.domain.entity;

/**
 * Created by tianzhonghai on 2017/5/8.
 */
public class RoleAndPermissionPO {
    private Long roleId;

    private Long permissionId;

    private String permissionSign;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionSign() {
        return permissionSign;
    }

    public void setPermissionSign(String permissionSign) {
        this.permissionSign = permissionSign;
    }
}
