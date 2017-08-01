package com.example.leaditteam.cafeprototype.helpers

import android.view.View

/**
 * Created by leaditteam on 13.06.17.
 */

fun fadeIn(v: View, delay: Int) {
    v.alpha = 0f
    v.scaleX = 0f
    v.scaleY = 0f
    v.translationY = (v.height / 2).toFloat()
    v.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .translationY(0f)
            .setDuration(delay.toLong()) //300L standart
            .start()
}