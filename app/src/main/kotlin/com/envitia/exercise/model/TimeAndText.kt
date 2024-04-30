package com.envitia.exercise.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TimeAndText(
    var time: String,
    var text: String
): Parcelable
