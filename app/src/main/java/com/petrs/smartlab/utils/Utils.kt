package com.petrs.smartlab.utils

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat


fun colorized(context: Context, text: String, word: String, @ColorRes color: Int): Spannable {
    val spannable: Spannable = SpannableString(text)
    var substringStart = 0
    var start: Int
    while (text.indexOf(word, substringStart).also { start = it } >= 0) {
        spannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, color)), start, start + word.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        substringStart = start + word.length
    }
    return spannable
}