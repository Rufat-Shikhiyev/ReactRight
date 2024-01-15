package com.reactright.reactright.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity (tableName = "items")
data class Items
    (@PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo("title") var title: String,
    @ColumnInfo("mainImage") var mainImage: String,
    @ColumnInfo("describe") var describe: String,
    @ColumnInfo("detailsImage") var detailsImage: String): Parcelable {
}