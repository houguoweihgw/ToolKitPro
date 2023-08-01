package com.hgw.toolkitpro.model;

public class ToolItem {
    private int iconResId; // 工具项的图标资源ID
    private String name;   // 工具项的名称

    public ToolItem(int iconResId, String name) {
        this.iconResId = iconResId;
        this.name = name;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getName() {
        return name;
    }
}
