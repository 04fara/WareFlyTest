package com.android.warefly.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.android.warefly.db.models.Recipe
import com.android.warefly.db.repository.RecipeRepository
import com.android.warefly.db.RecipeRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * ViewModel to keep a reference to the RecipeRepository and
 * an up-to-date list of all Recipes.
 */

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    private var parentJob = Job()
    // By default all the coroutines launched in this scope should be using the Main dispatcher
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: RecipeRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allRecipes: LiveData<List<Recipe>>

    init {
        val recipesDao = RecipeRoomDatabase.getDatabase(application, scope).recipeDao()
        repository = RecipeRepository(recipesDao)
        allRecipes = repository.allRecipes
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(recipe: Recipe) = scope.launch(Dispatchers.IO) {
        repository.insert(recipe)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}
