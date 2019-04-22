package com.android.warefly.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "RecipeIngredient",
        foreignKeys = [
            ForeignKey(
                    entity = Recipe::class,
                    parentColumns = arrayOf("id"),
                    childColumns = arrayOf("recipe_id"),
                    onDelete = CASCADE
            )
        ])
data class RecipeIngredient(
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        val title: String,
        val amount: Double,
        val unit: String,
        @ColumnInfo(name = "recipe_id")
        val recipeId: Long
)