package com.example.covid19tracker

import android.text.Spannable
import android.text.SpannableString


class SpannableDelta(text: String, start: Int): SpannableString(text){

    init {
        setSpan(
                null,
                start, //start value
                text.length, //end value
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE //defines if taking start and end length values or not
        )
    }
}