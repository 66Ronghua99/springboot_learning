package com.ronghua.springboot_quick.entity.aop;

public enum EntityType {
    PRODUCT("product"), APPUSER("app_user");

    private String presentName;

    EntityType(String presentName) {
        this.presentName = presentName;
    }

    @Override
    public String toString() {
        return presentName;
    }

}
