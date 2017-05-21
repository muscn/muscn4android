package com.awecode.muscn.views.nav;


import com.awecode.muscn.model.enumType.MenuType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NavigationItem {
    private String mText;
    private String categoryId;
    int icon;
    boolean isVisible;
    MenuType menuType;

    public NavigationItem(String text, String id, int iconName, boolean isVisible, MenuType menuType) {
        mText = text;
        this.categoryId = id;
        this.icon = iconName;
        this.isVisible = isVisible;
        this.menuType = menuType;
    }
}