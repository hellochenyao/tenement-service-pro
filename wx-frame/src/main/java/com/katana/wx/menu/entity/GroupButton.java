package com.katana.wx.menu.entity;

/**
 * 基础Button
 *
 * @author katana
 */
public class GroupButton extends BasicMenu {
    private BasicMenu[] sub_button; // 菜单标题，

    public BasicMenu[] getSub_button() {
        return sub_button;
    }

    public void setSub_button(BasicMenu[] subButton) {
        sub_button = subButton;
    }

}
