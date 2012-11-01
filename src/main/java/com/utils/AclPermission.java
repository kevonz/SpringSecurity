package com.utils;

import java.io.Serializable;

public class AclPermission implements Serializable {



    private String pkey;

    private String name;


    /** 完整的构建器 constructor */
    public AclPermission(String pkey, String name) {
        this.pkey = pkey;
        this.name = name;
    }

    /** 默认构建器 constructor */
    public AclPermission() {
    }

    /** 最小化的构建器 constructor */
    public AclPermission(String pkey) {
        this.pkey = pkey;
    }


    public String getPkey() {
        return this.pkey;
    }

    public void setPkey(String pkey) {
        this.pkey = pkey;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


}