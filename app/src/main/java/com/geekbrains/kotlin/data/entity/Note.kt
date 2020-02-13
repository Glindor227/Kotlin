package com.geekbrains.kotlin.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
data class Note(val id:String= "", val title: String = "", val text: String = "",
                val bgrColor: Color=Color.WHITE,val lastChanged: Date = Date()
): Parcelable {
    override fun equals(other: Any?): Boolean {
        if(javaClass != other?.javaClass) return false
        if (this===other) return true
        other as Note
        if(id != other.id) return false
        return true
}
    enum class Color{
        WHITE,
        YELLOW,
        GREEN,
        BLUE,
        RED,
        VIOLET,
        PINK
    }

}