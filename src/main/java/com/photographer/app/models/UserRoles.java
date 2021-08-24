package com.photographer.app.models;

import javax.persistence.*;

@Entity
@Table(name = "user_roles", schema = "public", catalog = "site_test")
public class UserRoles {
    private int roleId;
    private int userId;
    private int id;

    @Basic
    @Column(name = "role_id")
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRoles userRoles = (UserRoles) o;

        if (roleId != userRoles.roleId) return false;
        if (userId != userRoles.userId) return false;
        if (id != userRoles.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roleId;
        result = 31 * result + userId;
        result = 31 * result + id;
        return result;
    }
}
