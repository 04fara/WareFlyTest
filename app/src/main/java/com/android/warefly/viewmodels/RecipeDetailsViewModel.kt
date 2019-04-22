package com.android.warefly.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.android.warefly.db.RecipeIngredientRepository
import com.android.warefly.db.RecipeRoomDatabase
import com.android.warefly.db.models.RecipeIngredient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * ViewModel to keep a reference to the RecipeRepository and
 * an up-to-date list of all Recipes.
 */

class RecipeDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private var parentJob = Job()
    // By default all the coroutines launched in this scope should be using the Main dispatcher
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: RecipeIngredientRepository
    // Using LiveData and caching what getRecipeIngredientByRecipe returns has several benefits:
    // - an observer can be put on the data (instead of polling for changes) and the UI only updates
    // when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allRecipeIngredients: LiveData<List<RecipeIngredient>>

    init {
        val recipesIngredientDao = RecipeRoomDatabase.getDatabase(application, scope).recipeIngredientDao()
        repository = RecipeIngredientRepository(recipesIngredientDao)
        allRecipeIngredients = repository.allRecipeIngredients
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(ingredient: RecipeIngredient) = scope.launch(Dispatchers.IO) {
        repository.insert(ingredient)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}
