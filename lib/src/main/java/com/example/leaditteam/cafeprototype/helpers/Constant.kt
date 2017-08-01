package com.example.leaditteam.cafeprototype.helpers

/**
 * Created by leaditteam on 13.06.17.
 */
enum class Constant(val value: String? = null) {

    bundleArListName,
    bundleArListDescription,
    bundleArListPrice,
    bundleArListUrl,
    bundleArListInfo("arrayList_info"),
    bundleNumber("number"),
    bundleNameB("name_of_bloc"),
    bundleShowCoins("show_coins"),
    bundleIfMainActivity("if_main_activity"),

    transferDataHelper,
    mainHashmapForData,
    mainHashmapForMenu,
    mainHashmapForContacts,
    mainHashmapForLocation,
    USERS_COINS,
    PROMO_CODE,

    TAG_CALL_FRAGMENT("TAG_CALL_FRAGMENT"),
    TAG_CARD_FRAGMENT("TAG_CARD_FRAGMENT")
}