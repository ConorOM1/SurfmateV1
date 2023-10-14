package org.wit.surfmate.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SurfspotModel(var id: Long = 0,
                         var title: String = "",
                         var description: String = "") : Parcelable
