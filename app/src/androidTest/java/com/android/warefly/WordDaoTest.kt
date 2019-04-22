package com.android.warefly

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.android.warefly.db.RecipeDao
import com.android.warefly.db.RecipeRoomDatabase
import com.android.warefly.db.models.Recipe
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


/**
 * This is not meant to be a full set of tests.
 * When building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class WordDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var recipeDao: RecipeDao
    private lateinit var db: RecipeRoomDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, RecipeRoomDatabase::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
        recipeDao = db.recipeDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetRecipe() {
        val recipe = Recipe(id = 0, title = "Example Recipe", thumbnailPath = R.mipmap.thumbnail)
        recipeDao.insert(recipe)
        val allRecipes = recipeDao.getAllRecipes().waitForValue()
        assertEquals(allRecipes[0].title, recipe.title)
    }

    @Test
    @Throws(Exception::class)
    fun getAllRecipes() {
        val recipe1 = Recipe(id = 0, title = "Example Recipe 1", thumbnailPath = R.mipmap.thumbnail)
        recipeDao.insert(recipe1)
        val recipe2 = Recipe(id = 0, title = "Example Recipe 2", thumbnailPath = R.mipmap.thumbnail)
        recipeDao.insert(recipe2)
        val allRecipes = recipeDao.getAllRecipes().waitForValue()
        assertEquals(allRecipes[0].title, recipe1.title)
        assertEquals(allRecipes[1].title, recipe2.title)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAll() {
        val recipe1 = Recipe(id = 0, title = "Example Recipe 1", thumbnailPath = R.mipmap.thumbnail)
        recipeDao.insert(recipe1)
        val recipe2 = Recipe(id = 0, title = "Example Recipe 2", thumbnailPath = R.mipmap.thumbnail)
        recipeDao.insert(recipe2)
        recipeDao.deleteAll()
        val allRecipes = recipeDao.getAllRecipes().waitForValue()
        assertTrue(allRecipes.isEmpty())
    }
}
