package com.android.warefly.db.repository

import androidx.lifecycle.LiveData
import androidx.annotation.WorkerThread
import com.android.warefly.db.dao.RecipeDao
import com.android.warefly.db.models.Recipe

class RecipeRepository(private val recipeDao: RecipeDao) {
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allRecipes: LiveData<List<Recipe>> = recipeDao.getAllRecipes()

    // You must call this on a non-UI thread or your app will crash. So we're making this a
    // suspend function so the caller methods know this.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    @WorkerThread
    suspend fun insert(recipe: Recipe) {
        recipeDao.insert(recipe)
    }
}
