package com.aplimelta.coffeebidapp.data.source.model

import android.os.Parcel
import android.os.Parcelable

data class ProductModel(
    val endDate: String?,
    val description: String?,
    val openPrice: Int,
    val finalPrice: Int,
    val productPict: String?,
    val type: String?,
    val uuid: String?,
    val userId: Int,
    val createdAt: String?,
    val winner: String?,
    val name: String?,
    val id: Int,
    val user: ProductUserModel?,
    val startDate: String?,
    val status: String?,
    val updatedAt: String?,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        null,
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(endDate)
        parcel.writeString(description)
        parcel.writeInt(openPrice)
        parcel.writeInt(finalPrice)
        parcel.writeString(productPict)
        parcel.writeString(type)
        parcel.writeString(uuid)
        parcel.writeInt(userId)
        parcel.writeString(createdAt)
        parcel.writeString(winner)
        parcel.writeString(name)
        parcel.writeInt(id)
        parcel.writeString(startDate)
        parcel.writeString(status)
        parcel.writeString(updatedAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductModel> {
        override fun createFromParcel(parcel: Parcel): ProductModel {
            return ProductModel(parcel)
        }

        override fun newArray(size: Int): Array<ProductModel?> {
            return arrayOfNulls(size)
        }
    }
}