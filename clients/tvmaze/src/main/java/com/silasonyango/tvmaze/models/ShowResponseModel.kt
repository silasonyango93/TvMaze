package com.silasonyango.tvmaze.models

import android.os.Parcel
import android.os.Parcelable

data class ShowResponseModel(
    val id: Long,
    val url: String?,
    val name: String?,
    val genres: List<String>?,
    val image: Image?,
    val summary: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readParcelable<Image>(Image::class.java.classLoader),
        parcel.readString()
    ) {
    }

    data class Image(val medium: String?, val original: String?): Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(medium)
            parcel.writeString(original)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Image> {
            override fun createFromParcel(parcel: Parcel): Image {
                return Image(parcel)
            }

            override fun newArray(size: Int): Array<Image?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(url)
        parcel.writeString(name)
        parcel.writeStringList(genres)
        parcel.writeParcelable(image,flags)
        parcel.writeString(summary)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ShowResponseModel> {
        override fun createFromParcel(parcel: Parcel): ShowResponseModel {
            return ShowResponseModel(parcel)
        }

        override fun newArray(size: Int): Array<ShowResponseModel?> {
            return arrayOfNulls(size)
        }
    }
}
