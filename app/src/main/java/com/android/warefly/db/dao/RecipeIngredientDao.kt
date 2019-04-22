package com.android.warefly.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.android.warefly.db.models.RecipeIngredient


@Dao
interface RecipeIngredientDao {
    @Query("SELECT * from RecipeIngredient")
    fun getAllRecipeIngredients(): LiveData<List<RecipeIngredient>>

    @Query("SELECT * from RecipeIngredient WHERE recipe_id = :id")
    fun getAllRecipeIngredientsByRecipe(id: Long): LiveData<List<RecipeIngredient>>

    @Insert
    fun insert(recipeIngredient: RecipeIngredient)

    @Query("DELETE FROM RecipeIngredient")
    fun deleteAll()
}
