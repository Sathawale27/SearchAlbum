package dev.sarveshathawale.altimetrikcodingchallange.extensions

import android.view.View


internal fun Boolean.visibleGone(): Int {
    return if (this) {
        View.VISIBLE
    } else {
        View.GONE
    }
}