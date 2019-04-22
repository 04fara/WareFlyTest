package com.android.warefly.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Recipe")
data class Recipe(
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        @ColumnInfo(name = "thumbnail_path")
        val thumbnailPath: Int,
        val title: String
)