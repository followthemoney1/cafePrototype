package com.example.leaditteam.cafeprototype.helpers.containers;

import java.io.Serializable;

/**
 * Created by leaditteam on 22.03.17.
 */

public class ContainerForMenu implements Serializable {
    Boolean isNeedShowCoins;
    String menuName;
    String menuUrl;

    public void setIsNeedShowCoins(Boolean isNeedShowCoins) {
        this.isNeedShowCoins = isNeedShowCoins;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Boolean getIsNeedShowCoins() {
        return isNeedShowCoins;
    }

    public String getMenuName() {
        return menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public ContainerForMenu(Boolean is_show_coins, String menu_name, String menu_url) {

        this.isNeedShowCoins = is_show_coins;
        this.menuName = menu_name;
        this.menuUrl = menu_url;
    }
}
