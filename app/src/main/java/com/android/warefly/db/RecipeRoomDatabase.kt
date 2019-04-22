package com.android.warefly.db

import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import android.util.Log
import com.android.warefly.R
import com.android.warefly.db.models.Recipe
import com.android.warefly.db.models.RecipeIngredient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Recipe::class, RecipeIngredient::class], version = 1)
abstract class RecipeRoomDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun recipeIngredientDao(): RecipeIngredientDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeRoomDatabase? = null

        fun getDatabase(
                context: Context,
                scope: CoroutineScope
        ): RecipeRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        RecipeRoomDatabase::class.java,
                        "recipe_database"
                )
                        // Wipes and rebuilds instead of migrating if no Migration object.
                        // Migration is not part of this codelab.
                        .fallbackToDestructiveMigration()
                        .addCallback(RecipeDatabaseCallback(scope))
                        .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class RecipeDatabaseCallback(
                private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onOpen method to populate the database.
             * For this sample, we clear the database every time it is created or opened.
             */
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.recipeDao(), database.recipeIngredientDao())
                    }
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         */
        fun populateDatabase(recipeDao: RecipeDao, recipeIngredientDao: RecipeIngredientDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            recipeDao.deleteAll()

            val recipe = Recipe(
                    id = 0,
                    title = "Example Recipe #1",
                    thumbnailPath = R.mipmap.thumbnail
            )
            val id1 = recipeDao.insert(recipe)
            val recipe2 = Recipe(
                    id = 0,
                    title = "Example Recipe #2",
                    thumbnailPath = R.mipmap.thumbnail
            )
            val id2 = recipeDao.insert(recipe2)

            Log.d("RECIPE ID", id1.toString())
            Log.d("RECIPE2 ID", id2.toString())

            val recipeIngredient1 = RecipeIngredient(
                    id = 0,
                    recipeId = id1,
                    title = "Milk",
                    amount = 200.0,
                    unit = "ml"
            )
            val recipeIngredient2 = RecipeIngredient(
                    id = 0,
                    recipeId = id1,
                    title = "Jacobs Monarch coffee",
                    amount = 1.0,
                    unit = "cup"
            )

            val recipeIngredient3 = RecipeIngredient(
                    id = 0,
                    recipeId = id2,
                    title = "Water",
                    amount = 150.0,
                    unit = "ml"
            )
            val recipeIngredient4 = RecipeIngredient(
                    id = 0,
                    recipeId = id2,
                    title = "Coffee",
                    amount = 50.0,
                    unit = "g"
            )
            val recipeIngredient5 = RecipeIngredient(
                    id = 0,
                    recipeId = id2,
                    title = "Milk",
                    amount = 200.0,
                    unit = "ml"
            )

            recipeIngredientDao.insert(recipeIngredient1)
            recipeIngredientDao.insert(recipeIngredient2)
            recipeIngredientDao.insert(recipeIngredient3)
            recipeIngredientDao.insert(recipeIngredient4)
            recipeIngredientDao.insert(recipeIngredient5)

        }
    }

}
