package com.android.warefly.db

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.android.warefly.db.models.RecipeIngredient

class RecipeIngredientRepository(private val recipeIngredientDao: RecipeIngredientDao) {
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allRecipeIngredients: LiveData<List<RecipeIngredient>> = recipeIngredientDao.getAllRecipeIngredients()

    // You must call this on a non-UI thread or your app will crash. So we're making this a
    // suspend function so the caller methods know this.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    @WorkerThread
    suspend fun insert(recipeIngredient: RecipeIngredient) {
        recipeIngredientDao.insert(recipeIngredient)
    }

//    @WorkerThread
//    suspend fun allRecipeIngredientsByRecipe(id: Long): LiveData<List<RecipeIngredient>> {
//        return recipeIngredientDao.getAllRecipeIngredientsByRecipe(id)
//    }
}
