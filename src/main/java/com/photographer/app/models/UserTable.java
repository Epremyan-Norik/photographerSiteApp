package com.photographer.app.models;

import javax.persistence.*;

@Entity
@Table(name = "user_table", schema = "public", catalog = "site_test")
public class UserTable {
    private int id;
    private String username;
    private String uPassword;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "u_password")
    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserTable userTable = (UserTable) o;

        if (id != userTable.id) return false;
        if (username != null ? !username.equals(userTable.username) : userTable.username != null) return false;
        if (uPassword != null ? !uPassword.equals(userTable.uPassword) : userTable.uPassword != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (uPassword != null ? uPassword.hashCode() : 0);
        return result;
    }
}
