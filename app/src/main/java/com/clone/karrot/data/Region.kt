package com.clone.karrot.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//데이터로 id와 name을 정의
@Entity(tableName = "regions")
data class Region(
    val name: String
) {
    @PrimaryKey(autoGenerate = true) var id: Long = 0L
}