package com.reactright.reactright.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class User
    (@PrimaryKey(autoGenerate = true) var uid: Int,
     @ColumnInfo("username") var username: String,
     @ColumnInfo("number") var number: String,
     @ColumnInfo("fincode") var fincode: String): Parcelable {
}