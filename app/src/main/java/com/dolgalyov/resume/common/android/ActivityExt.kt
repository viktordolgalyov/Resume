package com.dolgalyov.resume.common.android

import android.app.Activity
import android.graphics.Color
import android.view.View
import androidx.core.view.ViewCompat

typealias OnSystemInsetsChangedListener = (statusBarSize: Int, navigationBarSize: Int) -> Unit

fun Activity.setWindowTransparency(listener: OnSystemInsetsChangedListener = { _, _ -> }) {
    removeSystemInsets(window.decorView, listener)
    window.navigationBarColor = Color.TRANSPARENT
    window.statusBarColor = Color.TRANSPARENT
}

private fun removeSystemInsets(view: View, listener: OnSystemInsetsChangedListener) {
    ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->

        val desiredBottomInset = calculateDesiredBottomInset(
            view,
            insets.systemWindowInsetTop,
            insets.systemWindowInsetBottom,
            listener
        )

        ViewCompat.onApplyWindowInsets(
            view,
            insets.replaceSystemWindowInsets(0, 0, 0, desiredBottomInset)
        )
    }
}

private fun calculateDesiredBottomInset(
    view: View,
    topInset: Int,
    bottomInset: Int,
    listener: OnSystemInsetsChangedListener
): Int {
    val hasKeyboard = isKeyboardAppeared(view, bottomInset)
    val desiredBottomInset = if (hasKeyboard) bottomInset else 0
    listener(topInset, if (hasKeyboard) 0 else bottomInset)
    return desiredBottomInset
}

private fun isKeyboardAppeared(view: View, bottomInset: Int) =
    bottomInset / view.resources.displayMetrics.heightPixels.toDouble() > .25