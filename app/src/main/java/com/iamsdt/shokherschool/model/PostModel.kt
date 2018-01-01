package com.iamsdt.shokherschool.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Shudipto Trafder on 12/30/2017.
 * at 4:08 PM
 */
class PostModel(var id: Int? = 0,
                var date: String? = "",
                var title: String? = "",
                var author: String? = "",
                var mediaLink: String? = "") : Parcelable {


    constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(date)
        parcel.writeString(title)
        parcel.writeString(author)
        parcel.writeString(mediaLink)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PostModel> {
        override fun createFromParcel(parcel: Parcel): PostModel {
            return PostModel(parcel)
        }

        override fun newArray(size: Int): Array<PostModel?> {
            return arrayOfNulls(size)
        }
    }
}