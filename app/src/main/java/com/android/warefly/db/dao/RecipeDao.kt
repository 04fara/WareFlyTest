package com.android.warefly.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.android.warefly.db.models.Recipe


@Dao
interface RecipeDao {
    @Query("SELECT * from Recipe")
    fun getAllRecipes(): LiveData<List<Recipe>>

    @Insert
    fun insert(recipe: Recipe): Long

    @Query("DELETE FROM Recipe")
    fun deleteAll()
}
