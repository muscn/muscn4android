package com.awecode.muscn.views.nav;


import com.awecode.muscn.model.enumType.MenuType;

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

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public MenuType getMenuType() {
        return menuType;
    }

    public void setMenuType(MenuType menuType) {
        this.menuType = this.menuType;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }


//    private NavigationItem(Parcel in) {
//        // This order must match the order in writeToParcel()
//        categoryId = in.readString();
//        mText = in.readString();
//        icon = in.readString();
//        // Continue doing this for the rest of your member data
//    }
//
//    // Just cut and paste this for now
//    public static final Creator<NavigationItem> CREATOR = new Creator<NavigationItem>() {
//        public NavigationItem createFromParcel(Parcel in) {
//            return new NavigationItem(in);
//        }
//
//        public NavigationItem[] newArray(int size) {
//            return new NavigationItem[size];
//        }
//    };
//
//    // Just cut and paste this for now
//    public int describeContents() {
//        return 0;
//    }
//
//    public void writeToParcel(Parcel out, int flags) {
//        // Again this order must match the Question(Parcel) constructor
//        out.writeString(categoryId);
//        out.writeString(mText);
//        out.writeString(icon);
//        // Again continue doing this for the rest of your member data
//    }

}
